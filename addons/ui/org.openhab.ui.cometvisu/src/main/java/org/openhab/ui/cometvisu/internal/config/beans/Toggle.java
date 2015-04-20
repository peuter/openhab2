//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.17 at 05:50:37 PM CEST 
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
 * <p>Java class for toggle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="toggle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="layout" type="{}layout" minOccurs="0"/>
 *         &lt;element name="label" type="{}label" minOccurs="0"/>
 *         &lt;element name="address" type="{}address" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{}styling"/>
 *       &lt;attribute ref="{}mapping"/>
 *       &lt;attribute ref="{}align"/>
 *       &lt;attribute ref="{}flavour"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "toggle", propOrder = {
    "layout",
    "label",
    "address"
})
public class Toggle {

    protected Layout layout;
    protected Label label;
    @XmlElement(required = true)
    protected List<Address> address;
    @XmlAttribute(name = "styling")
    protected String styling;
    @XmlAttribute(name = "mapping")
    protected String mapping;
    @XmlAttribute(name = "align")
    protected String align;
    @XmlAttribute(name = "flavour")
    protected String flavour;

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

}
