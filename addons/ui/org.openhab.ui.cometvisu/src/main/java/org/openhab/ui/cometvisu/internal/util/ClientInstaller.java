/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.ui.cometvisu.internal.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.eclipse.smarthome.io.net.http.HttpUtil;
import org.openhab.ui.cometvisu.backend.beans.ActionBean;
import org.openhab.ui.cometvisu.backend.beans.LinkActionBean;
import org.openhab.ui.cometvisu.backend.beans.NotificationBean;
import org.openhab.ui.cometvisu.internal.Config;
import org.openhab.ui.cometvisu.internal.SseBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Utility class for CometVisu client availability checks. It also provides methods to download
 * the CometVisu client if it does not exist in the configured COMETVISU_WEBFOLDER
 *
 * @author Tobias Bräutigam - Initial contribution
 *
 */
public class ClientInstaller {

    private final Logger logger = LoggerFactory.getLogger(ClientInstaller.class);

    private static final ClientInstaller INSTANCE = new ClientInstaller();

    /** the timeout to use for connecting to a given host (defaults to 5000 milliseconds) */
    private int timeout = 5000;

    /** URL for releases in github API */
    private static final String releaseURL = "https://api.github.com/repos/CometVisu/CometVisu/releases";

    private static final byte[] buffer = new byte[0xFFFF];

    private Map<String, Object> latestRelease;

    private List<String> alreadyCheckedFolders = new ArrayList<>();

    private boolean downloadAvailableButBlocked = false;

    private ClientInstaller() {
    }

    public static ClientInstaller getInstance() {
        return INSTANCE;
    }

    /**
     * Check the webfolder for existance and if there is a cometvisu in it.
     * Create the folder and download the cometvisu otherwise
     */
    public void check() {
        this.check(false);
    }

    /**
     * Check the webfolder for existance and if there is a cometvisu in it.
     * Create the folder and download the cometvisu otherwise
     *
     * @param force {boolean} force downloading if client not found
     */
    public void check(boolean force) {
        if (alreadyCheckedFolders.contains(Config.COMETVISU_WEBFOLDER) && !force) {
            // this folder has been checked already
            logger.debug("web folder {} has already been checked", Config.COMETVISU_WEBFOLDER);
            return;
        }
        if (!force) {
            // do not add the forced checks
            alreadyCheckedFolders.add(Config.COMETVISU_WEBFOLDER);
        }

        File webFolder = new File(Config.COMETVISU_WEBFOLDER);
        if (!webFolder.exists()) {
            logger.debug("creating cometvisu webfolder {}", webFolder.getAbsolutePath());
            webFolder.mkdirs();
        }
        if (webFolder.isDirectory()) {

            // check for cometvisu either we have a index.html in it (for releases) or we have a package.json in it (for
            // source versions)
            if (!new File(webFolder, "index.html").exists() || !new File(webFolder, "package.json").exists()) {
                // no cometvisu found if folder is empty download the cometvisu
                if (Config.COMETVISU_AUTO_DOWNLOAD || force) {
                    downloadLatestRelease();
                } else {
                    downloadAvailableButBlocked = true;
                    logger.error("No CometVisu client found in '{}' and automatic download is disabled. "
                            + "Please enable this feature by setting 'autoDownload=true' in your 'services/cometvisu.cfg' file.",
                            webFolder.getAbsolutePath());
                }
            } else {
                checkVersion(webFolder);
            }
        } else {
            logger.error("webfolder {} is no directory", webFolder.getAbsolutePath());
        }
    }

    /**
     * Check if currently used CometVisu client version is still up-to-date.
     * Sends a notification to the client if not.
     */
    public void checkVersion() {
        File webFolder = new File(Config.COMETVISU_WEBFOLDER);
        if (webFolder.exists() && webFolder.isDirectory()) {
            checkVersion(webFolder);
        }
    }

    /**
     * Check if currently used CometVisu client version is still up-to-date.
     * Sends a notification to the client if not
     *
     * @param webFolder CometVisu clients web folder
     */
    private void checkVersion(File webFolder) {

        // check for upgrades
        Map<String, Object> latestRelease = getLatestRelease();

        // find version in local client
        File version = findClientRoot(webFolder, "version");
        if (version.exists()) {
            try {
                String currentVersion = FileUtils.readFileToString(version);
                Config.CLIENT_VERSION = currentVersion;
                String currentRelease = (String) latestRelease.get("tag_name");
                if (currentRelease.startsWith("v")) {
                    currentRelease = currentRelease.substring(1);
                }
                if (Config.isNewer(currentRelease, currentVersion)) {
                    logger.info("CometVisu should be updated to version {}, you are using version {}", currentRelease,
                            currentVersion);
                    sendUpdateNotification(currentRelease, currentVersion);
                }
            } catch (IOException e) {
                logger.error("error reading version from installed CometVisu client: {}", e.getMessage(), e);
            }
        }
    }

    private void sendUpdateNotification(String newVersion, String currentVersion) {
        // create notification bean
        LinkActionBean link = new LinkActionBean();
        link.title = "Start update";
        link.hidden = true;
        link.deleteMessageAfterExecution = true;
        link.url = "/rest/" + Config.COMETVISU_BACKEND_ALIAS + "/" + Config.COMETVISU_BACKEND_CONFIG_ALIAS
                + "/download-client";

        NotificationBean n = new NotificationBean();
        n.title = String.format("New Version available");
        n.message = String.format("CometVisu should be updated to version %s, you are using version %s", newVersion,
                currentVersion);
        n.severity = NotificationBean.Severity.low;
        n.topic = "cv.backend.update";
        n.unique = true;
        n.actions = new HashMap<String, ActionBean>();
        n.actions.put("link", link);

        logger.debug("sending notification about update to client");
        SseBroadcaster.getInstance().broadcast(SseUtil.buildEvent(n, "notifications"));
    }

    private void sendUpdatedNotification(String newVersion, int progress) {
        // create notification bean
        NotificationBean n = new NotificationBean();
        n.title = String.format("Updating to version: %s", newVersion);

        if (progress == 100) {
            n.message = String.format("CometVisu has been succesfully updated.");
            LinkActionBean link = new LinkActionBean();
            link.title = "Restart";
            link.action = "restart";
            n.actions = new HashMap<String, ActionBean>();
            n.actions.put("link", link);

        } else {
            n.icon = "status_light_high";
            n.iconClasses = "spinner";
        }
        n.target = NotificationBean.Target.popup;
        n.progress = progress;
        n.severity = NotificationBean.Severity.normal;
        n.topic = "cv.backend.update";
        n.unique = true;

        SseBroadcaster.getInstance().broadcast(SseUtil.buildEvent(n, "notifications"));
    }

    /**
     * Search for a file in the known file structure of the CometVisu client.
     * The search oder is:
     *
     * . # for releases
     * build/ # for CometVisu >= 0.11 with generated build
     * source/ # for CometVisu >= 0.11 without generated build (source version)
     * src/ # for CometVisu < 0.11 (source version)
     *
     * @param webFolder {File} folder to start the search
     * @param fileName {String} Filename to lookup
     * @return
     */
    public static File findClientRoot(File webFolder, String fileName) {
        File file = new File(webFolder, fileName);
        if (!file.exists()) {
            File build = new File(webFolder, "build");
            File source = new File(webFolder, "source");
            File src = new File(webFolder, "src");
            if (build.exists()) {
                // new CometVisu client >= 0.11.x
                file = new File(build, fileName);
            } else if (source.exists()) {
                // new CometVisu client >= 0.11.x
                file = new File(source, fileName);
            } else if (src.exists()) {
                file = new File(src, fileName);
            }
        }
        return file;
    }

    /**
     * Checks if the CometVisu client is missing, but the automatic download is blocked by configuration.
     *
     * @return {boolean}
     */
    public boolean isDownloadAvailableButBlocked() {
        return downloadAvailableButBlocked;
    }

    /**
     * Fetch the latest release description for the CometVisu project from github
     *
     * @return {Map}
     */
    private Map<String, Object> getLatestRelease() {
        if (latestRelease == null) {
            Properties headers = new Properties();
            headers.setProperty("Accept", "application/json");
            try {
                String response = HttpUtil.executeUrl("GET", releaseURL, headers, null, null, timeout);
                if (response == null) {
                    logger.error("No response received from '{}'", releaseURL);
                } else {
                    List<Map<String, Object>> jsonResponse = new Gson().fromJson(response, ArrayList.class);

                    // releases are ordered top-down, the latest release comes first
                    latestRelease = jsonResponse.get(0);
                }
            } catch (IOException e) {
                logger.error("error downloading release data: {}", e.getMessage(), e);
            }
        }
        return latestRelease;
    }

    /**
     * Download the latest release and extract it to the configured COMETVISU_WEBFOLDER
     */
    public void downloadLatestRelease() {
        // request the download URL for the latest CometVisu release from the github API
        sendUpdatedNotification("requesting...", 0);
        Map<String, Object> latestRelease = getLatestRelease();
        List<Map<String, Object>> assets = (ArrayList<Map<String, Object>>) latestRelease.get("assets");

        Map<String, Object> releaseAsset = null;
        for (Object assetObj : assets) {
            Map<String, Object> asset = (Map<String, Object>) assetObj;
            if (((String) asset.get("content_type")).equalsIgnoreCase("application/zip")) {
                releaseAsset = asset;
                break;
            }
        }
        if (releaseAsset == null) {
            logger.error("no zip download file found for release {}", latestRelease.get("name"));
        } else {
            File releaseFile = new File("release.zip");
            try {
                URL url = new URL((String) releaseAsset.get("browser_download_url"));

                FileUtils.copyURLToFile(url, releaseFile);

                ZipFile zip = new ZipFile(releaseFile, ZipFile.OPEN_READ);

                String currentRelease = (String) latestRelease.get("tag_name");
                if (currentRelease.startsWith("v")) {
                    currentRelease = currentRelease.substring(1);
                }

                sendUpdatedNotification(currentRelease, 0);
                extractFolder("cometvisu/release/", zip, Config.COMETVISU_WEBFOLDER,
                        (String) latestRelease.get("tag_name"));
                sendUpdatedNotification(currentRelease, 100);
            } catch (IOException e) {
                logger.error("error opening release zip file {}", e.getMessage(), e);
            } finally {
                if (releaseFile.exists()) {
                    releaseFile.delete();
                }
            }
        }
    }

    /**
     * Extract the content of the zip file to the target folder
     *
     * @param folderName {String} subfolder inside the zip file that should be extracted
     * @param zipFile {ZipFile} zip-file to extract
     * @param destDir {String} destination for the extracted files
     */
    private void extractFolder(String folderName, ZipFile zipFile, String destDir, String release) {
        List<? extends ZipEntry> fileCollection = Collections.list(zipFile.entries());
        int total = fileCollection.size();
        int current = 0;
        int currentProgress = 0;
        for (ZipEntry entry : Collections.list(zipFile.entries())) {
            current++;
            int progress = Math.round(total / 100 * current);
            if (progress > 0 && progress < 100 && currentProgress != progress) {
                sendUpdatedNotification(release, progress);
            }
            currentProgress = progress;
            if (entry.getName().startsWith(folderName)) {
                String target = entry.getName().substring(folderName.length());
                File file = new File(destDir, target);
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    if (file.exists() && (file.getPath().matches(".*/config/visu_config.*\\.xml")
                            || file.getPath().matches(".*/designs/.+/custom\\.css"))) {
                        // never ever overwrite existing config or custom.css files
                        continue;
                    }
                    new File(file.getParent()).mkdirs();

                    try (InputStream is = zipFile.getInputStream(entry);
                            OutputStream os = new FileOutputStream(file);) {
                        for (int len; (len = is.read(buffer)) != -1;) {
                            os.write(buffer, 0, len);
                        }
                        logger.info("extracted zip file {} to folder {}", zipFile.getName(), destDir);

                    } catch (IOException e) {
                        logger.error("error extracting file {}", e.getMessage(), e);
                    }
                }
            }
        }
    }
}
