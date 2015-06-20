
package xml.mt102;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TMT102 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TMT102">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SWIFT_Duznika" type="{http://xml/globals}TSwiftCode"/>
 *         &lt;element name="ObrRacunBankeDuznika" type="{http://xml/globals}TOznakaRacuna"/>
 *         &lt;element name="SWIFT_Poverioca" type="{http://xml/globals}TSwiftCode"/>
 *         &lt;element name="ObrRacunBankePoverioca" type="{http://xml/globals}TOznakaRacuna"/>
 *         &lt;element name="UkupanIznos">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="15"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="sifraValute" type="{http://xml/globals}TOznakaValute"/>
 *         &lt;element name="DatumValute" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Datum" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Placanja" type="{http://xml/mt102}Placanje" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TMT102", propOrder = {
    "id",
    "swiftDuznika",
    "obrRacunBankeDuznika",
    "swiftPoverioca",
    "obrRacunBankePoverioca",
    "ukupanIznos",
    "sifraValute",
    "datumValute",
    "datum",
    "placanja"
})
public class TMT102 {

    @XmlElement(name = "ID", required = true)
    protected String id;
    @XmlElement(name = "SWIFT_Duznika", required = true)
    protected String swiftDuznika;
    @XmlElement(name = "ObrRacunBankeDuznika", required = true)
    protected String obrRacunBankeDuznika;
    @XmlElement(name = "SWIFT_Poverioca", required = true)
    protected String swiftPoverioca;
    @XmlElement(name = "ObrRacunBankePoverioca", required = true)
    protected String obrRacunBankePoverioca;
    @XmlElement(name = "UkupanIznos", required = true)
    protected BigDecimal ukupanIznos;
    @XmlElement(required = true)
    protected String sifraValute;
    @XmlElement(name = "DatumValute", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumValute;
    @XmlElement(name = "Datum", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datum;
    @XmlElement(name = "Placanja", required = true)
    protected List<Placanje> placanja;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the swiftDuznika property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSWIFTDuznika() {
        return swiftDuznika;
    }

    /**
     * Sets the value of the swiftDuznika property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSWIFTDuznika(String value) {
        this.swiftDuznika = value;
    }

    /**
     * Gets the value of the obrRacunBankeDuznika property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObrRacunBankeDuznika() {
        return obrRacunBankeDuznika;
    }

    /**
     * Sets the value of the obrRacunBankeDuznika property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObrRacunBankeDuznika(String value) {
        this.obrRacunBankeDuznika = value;
    }

    /**
     * Gets the value of the swiftPoverioca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSWIFTPoverioca() {
        return swiftPoverioca;
    }

    /**
     * Sets the value of the swiftPoverioca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSWIFTPoverioca(String value) {
        this.swiftPoverioca = value;
    }

    /**
     * Gets the value of the obrRacunBankePoverioca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObrRacunBankePoverioca() {
        return obrRacunBankePoverioca;
    }

    /**
     * Sets the value of the obrRacunBankePoverioca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObrRacunBankePoverioca(String value) {
        this.obrRacunBankePoverioca = value;
    }

    /**
     * Gets the value of the ukupanIznos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupanIznos() {
        return ukupanIznos;
    }

    /**
     * Sets the value of the ukupanIznos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupanIznos(BigDecimal value) {
        this.ukupanIznos = value;
    }

    /**
     * Gets the value of the sifraValute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSifraValute() {
        return sifraValute;
    }

    /**
     * Sets the value of the sifraValute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSifraValute(String value) {
        this.sifraValute = value;
    }

    /**
     * Gets the value of the datumValute property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumValute() {
        return datumValute;
    }

    /**
     * Sets the value of the datumValute property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumValute(XMLGregorianCalendar value) {
        this.datumValute = value;
    }

    /**
     * Gets the value of the datum property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatum(XMLGregorianCalendar value) {
        this.datum = value;
    }

    /**
     * Gets the value of the placanja property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the placanja property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlacanja().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Placanje }
     * 
     * 
     */
    public List<Placanje> getPlacanja() {
        if (placanja == null) {
            placanja = new ArrayList<Placanje>();
        }
        return this.placanja;
    }

}
