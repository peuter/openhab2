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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for switch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="switch"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="layout" type="{}layout" minOccurs="0"/&gt;
 *         &lt;element name="label" type="{}label" minOccurs="0"/&gt;
 *         &lt;element name="address" type="{}address" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{}styling"/&gt;
 *       &lt;attribute ref="{}mapping"/&gt;
 *       &lt;attribute name="on_value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="off_value" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute ref="{}align"/&gt;
 *       &lt;attribute ref="{}flavour"/&gt;
 *       &lt;attribute ref="{}bind_click_to_widget"/&gt;
 *       &lt;attribute ref="{}class"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "switch", propOrder = {
    "layout",
    "label",
    "address"
})
public class Switch {

    protected Layout layout;
    protected Label label;
    @XmlElement(required = true)
    protected List<Address> address;
    @XmlAttribute(name = "styling")
    protected String styling;
    @XmlAttribute(name = "mapping")
    protected String mapping;
    @XmlAttribute(name = "on_value")
    protected String onValue;
    @XmlAttribute(name = "off_value")
    protected String offValue;
    @XmlAttribute(name = "align")
    protected String align;
    @XmlAttribute(name = "flavour")
    protected String flavour;
    @XmlAttribute(name = "bind_click_to_widget")
    protected Boolean bindClickToWidget;
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
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link Label }
     *     
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link Label }
     *     
     */
    public void setLabel(Label value) {
        this.label = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the address property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Address }
     * 
     * 
     */
    public List<Address> getAddress() {
        if (address == null) {
            address = new ArrayList<Address>();
        }
        return this.address;
    }

    /**
     * Gets the value of the styling property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyling() {
        return styling;
    }

    /**
     * Sets the value of the styling property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyling(String value) {
        this.styling = value;
    }

    /**
     * Gets the value of the mapping property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapping() {
        return mapping;
    }

    /**
     * Sets the value of the mapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapping(String value) {
        this.mapping = value;
    }

    /**
     * Gets the value of the onValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnValue() {
        return onValue;
    }

    /**
     * Sets the value of the onValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnValue(String value) {
        this.onValue = value;
    }

    /**
     * Gets the value of the offValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOffValue() {
        return offValue;
    }

    /**
     * Sets the value of the offValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOffValue(String value) {
        this.offValue = value;
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
     * Gets the value of the bindClickToWidget property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBindClickToWidget() {
        return bindClickToWidget;
    }

    /**
     * Sets the value of the bindClickToWidget property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBindClickToWidget(Boolean value) {
        this.bindClickToWidget = value;
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
