//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2022.05.03 um 02:11:53 PM CEST 
//


package com.example.carrentalcurrency;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="values" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfdouble" minOccurs="0"/&gt;
 *         &lt;element name="sourceCurrency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="destinationCurrency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "values",
    "sourceCurrency",
    "destinationCurrency"
})
@XmlRootElement(name = "convertCurrencyList")
public class ConvertCurrencyList {

    @XmlElementRef(name = "values", namespace = "http://currencyservice.com", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfdouble> values;
    @XmlElementRef(name = "sourceCurrency", namespace = "http://currencyservice.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sourceCurrency;
    @XmlElementRef(name = "destinationCurrency", namespace = "http://currencyservice.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> destinationCurrency;

    /**
     * Ruft den Wert der values-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfdouble }{@code >}
     *     
     */
    public JAXBElement<ArrayOfdouble> getValues() {
        return values;
    }

    /**
     * Legt den Wert der values-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfdouble }{@code >}
     *     
     */
    public void setValues(JAXBElement<ArrayOfdouble> value) {
        this.values = value;
    }

    /**
     * Ruft den Wert der sourceCurrency-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSourceCurrency() {
        return sourceCurrency;
    }

    /**
     * Legt den Wert der sourceCurrency-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSourceCurrency(JAXBElement<String> value) {
        this.sourceCurrency = value;
    }

    /**
     * Ruft den Wert der destinationCurrency-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDestinationCurrency() {
        return destinationCurrency;
    }

    /**
     * Legt den Wert der destinationCurrency-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDestinationCurrency(JAXBElement<String> value) {
        this.destinationCurrency = value;
    }

}
