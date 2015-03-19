# Introduction to openHAB 2

openHAB 2 is the successor of openHAB 1. It is fully based on the Eclipse SmartHome project and thus in general not backward-compatible to openHAB 1.
Nonetheless, there is a [compatibility layer](compatibilitylayer.md), so that many openHAB 1 add-ons can still be used with it - but this is not the case for all of them, see the [compatibility matrix](compatibility.md) for details.

The general [setup process](runtime.md) is fairly similar to openHAB 1, you will not have many surprises if you are familiar with openHAB 1.

There are a few changes in openHAB 2 that should be highlighted though:
 - there is a new dashboard, which welcomes you at http://localhost:8080 (assuming openHAB is running on your local machine) and provides you links to the different UIs
 - there is an early prototype of a new HTML5-based UI that allows to configure and operate the system.
 - the Classic UI URL has changed from `/openhab.app` to `/classicui`, so you can access your sitemaps at `http://<server>:8080/classicui?sitemap=<yoursitemap>`
 - a new default sitemap provider is in place, which provides a dynamic sitemap with the name `default`, which lists all group items that are not contained within any other group.
 - the `configuration` folder has been renamed to `conf`
 - instead of the global `configuration/openhab.cfg` file, there is now an individual file per add-on in `conf/services`
 - The OSGi console commands are now available as "smarthome", not as "openhab" anymore.
 - the REST API does NOT support XML nor JSON-P anymore. It is now fully realized using JSON.
 - the REST API does not support websocket access anymore - it actually completely drops "push" support and only has a simple long-polling implementation to provide a basic backward-compatibility for clients. 
 - the webapps folder has been discontinued, so there is no way to make files available this way through HTTP.
 - it is possible to provide your own custom icons in the `conf/icons` folder - no need to overwrite the icons that come with the runtime
 - the rule syntax has slightly changed, you e.g. do not need import statements anymore for the most common classes. At the same time, there is no openHAB Designer anymore, but the Eclipse SmartHome designer can be used. 
 
The following restrictions are known (and need to be worked on):
  - there is no way yet to enable authentication on HTTP(S)
  - Startup rules are not necessarily fired at startup (bundle start order issue)
  - the Eclipse SmartHome Designer marks all openHAB actions as errors (yet the runtime will correctly execute them)
  - GreenT does not work as the webapps folder does not exist anymore
  - CometVisu is not yet compatible
  - HABmin is not yet compatible
  - my.openHAB is not yet compatible
 
