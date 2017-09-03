/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.14 at 08:55:21 PM CEST 
//


package org.openhab.ui.cometvisu.internal.config.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for group complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="group"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="layout" type="{}layout" minOccurs="0"/&gt;
 *         &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;element name="page" type="{}page"/&gt;
 *           &lt;element name="group" type="{}group"/&gt;
 *           &lt;group ref="{}Widgets"/&gt;
 *           &lt;group ref="{}AvailablePlugins"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="nowidget" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute ref="{}flavour"/&gt;
 *       &lt;attribute ref="{}target"/&gt;
 *       &lt;attribute ref="{}align"/&gt;
 *       &lt;attribute ref="{}class"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "group", propOrder = {
    "layout",
    "pageOrGroupOrLine"
})
public class Group {

    protected Layout layout;
    @XmlElementRefs({
        @XmlElementRef(name = "multitrigger", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "timeout", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "reload", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "calendarlist", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "break", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "mobilemenu", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "openweathermap", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "imagetrigger", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "diagram_info", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "clock", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "image", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "urltrigger", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "rsslog", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "include", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "switch", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "strftime", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "gauge", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "video", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "pagejump", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "infoaction", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "group", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "toggle", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "infotrigger", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "line", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "text", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "designtoggle", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "web", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "upnpcontroller", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "wgplugin_info", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "rss", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "svg", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "trigger", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "speech", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "audio", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "powerspectrum", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "colorchooser", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "diagram", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "slide", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "page", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "rgb", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "notificationcenterbadge", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "pushbutton", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "refresh", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "shade", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "info", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> pageOrGroupOrLine;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "nowidget")
    protected Boolean nowidget;
    @XmlAttribute(name = "flavour")
    protected String flavour;
    @XmlAttribute(name = "target")
    protected String target;
    @XmlAttribute(name = "align")
    protected String align;
    @XmlAttribute(name = "class")
    protected String clazz;

    /**
     * Gets the value of the layout property.
     * 
     * @return
     *     possible object is
     *     {@link Layout }
     *     
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * Sets the value of the layout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Layout }
     *     
     */
    public void setLayout(Layout value) {
        this.layout = value;
    }

    /**
     * Gets the value of the pageOrGroupOrLine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageOrGroupOrLine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageOrGroupOrLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Multitrigger }{@code >}
     * {@link JAXBElement }{@code <}{@link Timeout }{@code >}
     * {@link JAXBElement }{@code <}{@link Reload }{@code >}
     * {@link JAXBElement }{@code <}{@link Calendarlist }{@code >}
     * {@link JAXBElement }{@code <}{@link Break }{@code >}
     * {@link JAXBElement }{@code <}{@link Mobilemenu }{@code >}
     * {@link JAXBElement }{@code <}{@link Openweathermap }{@code >}
     * {@link JAXBElement }{@code <}{@link Imagetrigger }{@code >}
     * {@link JAXBElement }{@code <}{@link DiagramInfo }{@code >}
     * {@link JAXBElement }{@code <}{@link Clock }{@code >}
     * {@link JAXBElement }{@code <}{@link Image }{@code >}
     * {@link JAXBElement }{@code <}{@link Urltrigger }{@code >}
     * {@link JAXBElement }{@code <}{@link Rsslog }{@code >}
     * {@link JAXBElement }{@code <}{@link Include }{@code >}
     * {@link JAXBElement }{@code <}{@link Switch }{@code >}
     * {@link JAXBElement }{@code <}{@link Strftime }{@code >}
     * {@link JAXBElement }{@code <}{@link Gauge }{@code >}
     * {@link JAXBElement }{@code <}{@link Video }{@code >}
     * {@link JAXBElement }{@code <}{@link Pagejump }{@code >}
     * {@link JAXBElement }{@code <}{@link Infoaction }{@code >}
     * {@link JAXBElement }{@code <}{@link Group }{@code >}
     * {@link JAXBElement }{@code <}{@link Toggle }{@code >}
     * {@link JAXBElement }{@code <}{@link Infotrigger }{@code >}
     * {@link JAXBElement }{@code <}{@link Line }{@code >}
     * {@link JAXBElement }{@code <}{@link Text }{@code >}
     * {@link JAXBElement }{@code <}{@link Designtoggle }{@code >}
     * {@link JAXBElement }{@code <}{@link Web }{@code >}
     * {@link JAXBElement }{@code <}{@link Upnpcontroller }{@code >}
     * {@link JAXBElement }{@code <}{@link WgpluginInfo }{@code >}
     * {@link JAXBElement }{@code <}{@link Rss }{@code >}
     * {@link JAXBElement }{@code <}{@link Svg }{@code >}
     * {@link JAXBElement }{@code <}{@link Trigger }{@code >}
     * {@link JAXBElement }{@code <}{@link Speech }{@code >}
     * {@link JAXBElement }{@code <}{@link Audio }{@code >}
     * {@link JAXBElement }{@code <}{@link Powerspectrum }{@code >}
     * {@link JAXBElement }{@code <}{@link Colorchooser }{@code >}
     * {@link JAXBElement }{@code <}{@link Diagram }{@code >}
     * {@link JAXBElement }{@code <}{@link Slide }{@code >}
     * {@link JAXBElement }{@code <}{@link Page }{@code >}
     * {@link JAXBElement }{@code <}{@link Rgb }{@code >}
     * {@link JAXBElement }{@code <}{@link Notificationcenterbadge }{@code >}
     * {@link JAXBElement }{@code <}{@link Pushbutton }{@code >}
     * {@link JAXBElement }{@code <}{@link Refresh }{@code >}
     * {@link JAXBElement }{@code <}{@link Info }{@code >}
     * {@link JAXBElement }{@code <}{@link Info }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getPageOrGroupOrLine() {
        if (pageOrGroupOrLine == null) {
            pageOrGroupOrLine = new ArrayList<JAXBElement<?>>();
        }
        return this.pageOrGroupOrLine;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the nowidget property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNowidget() {
        return nowidget;
    }

    /**
     * Sets the value of the nowidget property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNowidget(Boolean value) {
        this.nowidget = value;
    }

    /**
     * Gets the value of the flavour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlavour() {
        return flavour;
    }

    /**
     * Sets the value of the flavour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlavour(String value) {
        this.flavour = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Gets the value of the align property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlign() {
        return align;
    }

    /**
     * Sets the value of the align property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlign(String value) {
        this.align = value;
    }

    /**
     * Gets the value of the clazz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Sets the value of the clazz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

}
