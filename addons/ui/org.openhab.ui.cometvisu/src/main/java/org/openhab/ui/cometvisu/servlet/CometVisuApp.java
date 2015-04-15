/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.ui.cometvisu.servlet;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.openhab.ui.cometvisu.internal.Config;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.eclipse.smarthome.core.events.EventPublisher;
import org.eclipse.smarthome.core.items.ItemRegistry;
import org.eclipse.smarthome.core.persistence.PersistenceService;
import org.eclipse.smarthome.core.persistence.QueryablePersistenceService;
import org.eclipse.smarthome.model.sitemap.SitemapProvider;
import org.eclipse.smarthome.ui.icon.IconProvider;
import org.eclipse.smarthome.ui.items.ItemUIRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * registers the CometVisuServlet-Service
 * 
 * @author Tobias Bräutigam
 * @since 2.0.0
 */
public class CometVisuApp {

	private static final Logger logger = LoggerFactory
			.getLogger(CometVisuApp.class);

	protected HttpService httpService;

	private ItemUIRegistry itemUIRegistry;

	private ItemRegistry itemRegistry;

	private Set<SitemapProvider> sitemapProviders = new HashSet<>();

	private IconProvider iconProvider;

	private EventPublisher eventPublisher;

	static protected Map<String, QueryablePersistenceService> persistenceServices = new HashMap<String, QueryablePersistenceService>();

	private String webAlias = Config.COMETVISU_WEBAPP_ALIAS;
	private String webFolder = Config.COMETVISU_WEBFOLDER;

	protected void setEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	protected void unsetEventPublisher(EventPublisher eventPublisher) {
		this.eventPublisher = null;
	}

	public EventPublisher getEventPublisher() {
		return this.eventPublisher;
	}

	public void addPersistenceService(PersistenceService service) {
		if (service instanceof QueryablePersistenceService)
			persistenceServices.put(service.getName(),
					(QueryablePersistenceService) service);
	}

	public void removePersistenceService(PersistenceService service) {
		persistenceServices.remove(service.getName());
	}

	static public Map<String, QueryablePersistenceService> getPersistenceServices() {
		return persistenceServices;
	}

	public IconProvider getIconProvider() {
		return iconProvider;
	}

	protected void setIconProvider(IconProvider iconProvider) {
		this.iconProvider = iconProvider;
	}

	protected void unsetIconProvider(IconProvider iconProvider) {
		this.iconProvider = null;
	}

	protected void setItemRegistry(ItemRegistry itemRegistry) {
		this.itemRegistry = itemRegistry;
	}

	public ItemRegistry getItemRegistry() {
		return itemRegistry;
	}

	protected void unsetItemRegistry(ItemRegistry itemRegistry) {
		this.itemRegistry = null;
	}

	public void setItemUIRegistry(ItemUIRegistry itemUIRegistry) {
		this.itemUIRegistry = itemUIRegistry;
	}

	public void unsetItemUIRegistry(ItemUIRegistry itemUIRegistry) {
		this.itemUIRegistry = null;
	}

	public void addSitemapProvider(SitemapProvider provider) {
		sitemapProviders.add(provider);
	}

	public void removeSitemapProvider(SitemapProvider provider) {
		sitemapProviders.remove(provider);
	}

	public ItemUIRegistry getItemUIRegistry() {
		return itemUIRegistry;
	}

	public Set<SitemapProvider> getSitemapProviders() {
		return sitemapProviders;
	}

	protected void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}

	protected void unsetHttpService(HttpService httpService) {
		this.httpService = null;
	}

	private void readConfiguration(final Map<String, Object> properties) {
		if (properties != null) {
			if (properties.get(Config.COMETVISU_WEBFOLDER_PROPERTY) != null) {
				webFolder = (String) properties
						.get(Config.COMETVISU_WEBFOLDER_PROPERTY);
			}
			if (properties.get(Config.COMETVISU_WEBAPP_ALIAS_PROPERTY) != null) {
				webAlias = (String) properties
						.get(Config.COMETVISU_WEBAPP_ALIAS_PROPERTY);
			}
			for (String key : properties.keySet()) {
				String[] parts = key.split(">");
				String propKey = parts.length > 1 ? parts[1] : parts[0];
				String propPid = parts.length > 1 ? parts[0] : "";

				logger.debug("Property: {}->{}:{}, Parts {}", propPid, propKey,
						properties.get(key), parts.length);
				if (!propPid.isEmpty()) {
					if (Config.configMappings.containsKey(propPid)) {
						Config.configMappings.get(propPid).put(propKey,
								properties.get(key));
					}
				}
			}
		}
	}

	/**
	 * Called by the SCR to activate the component with its configuration read
	 * from CAS
	 * 
	 * @param bundleContext
	 *            BundleContext of the Bundle that defines this component
	 * @param configuration
	 *            Configuration properties for this component obtained from the
	 *            ConfigAdmin service
	 */
	protected void activate(Map<String, Object> configProps) throws ConfigurationException {
		readConfiguration(configProps);
		registerServlet();
		logger.info("Started CometVisu UI at {} serving {}", webAlias,
					webFolder);
	}

	public void deactivate(BundleContext componentContext) {
		unregisterServlet();
		logger.info("Stopped CometVisu UI");
	}

	private void registerServlet() {
		// As the alias is user configurable, we have to check if it has a
		// trailing slash but no leading slash
		if (!webAlias.startsWith("/"))
			webAlias = "/" + webAlias;

		if (webAlias.endsWith("/"))
			webAlias = webAlias.substring(0, webAlias.length() - 1);
		
		Dictionary<String, String> servletParams = new Hashtable<String, String>();
		CometVisuServlet servlet = new CometVisuServlet(webFolder, this);
		try {
			httpService.registerServlet(webAlias, servlet, servletParams, null);
		} catch (ServletException e) {
			logger.error("Error during servlet startup", e);
		} catch (NamespaceException e) {
			logger.error("Error during servlet startup", e);
		}
	}

	private void unregisterServlet() {
		httpService.unregister(webAlias);
	}

	/**
	 * Called by the SCR when the configuration of a binding has been changed
	 * through the ConfigAdmin service.
	 * 
	 * @param configuration
	 *            Updated configuration properties
	 */
	protected void modified(Map<String, Object> configProps) throws ConfigurationException {
		logger.info("updated({})", configProps);
		if (configProps == null)
			return;
		if (configProps.containsKey(Config.COMETVISU_WEBFOLDER_PROPERTY)
				|| configProps
						.containsKey(Config.COMETVISU_WEBAPP_ALIAS_PROPERTY)) {
			unregisterServlet();
		}
		readConfiguration(configProps);
		if (configProps.containsKey(Config.COMETVISU_WEBFOLDER_PROPERTY)
				|| configProps
						.containsKey(Config.COMETVISU_WEBAPP_ALIAS_PROPERTY)) {
			registerServlet();
		}
	}

}
