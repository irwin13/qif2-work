//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.11 at 07:19:01 PM ICT 
//


package id.co.quadras.qif.engine.jaxb;

import com.irwin13.winwork.basic.utilities.PojoUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}rootPackage"/>
 *         &lt;element ref="{}batchConfig"/>
 *         &lt;element ref="{}databaseRepository"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "rootPackage",
    "batchConfig",
    "databaseRepository"
})
@XmlRootElement(name = "qif")
public class Qif {

    @XmlElement(required = true)
    protected RootPackage rootPackage;
    @XmlElement(required = true)
    protected BatchConfig batchConfig;
    @XmlElement(required = true)
    protected DatabaseRepository databaseRepository;

    /**
     * Gets the value of the rootPackage property.
     * 
     * @return
     *     possible object is
     *     {@link RootPackage }
     *     
     */
    public RootPackage getRootPackage() {
        return rootPackage;
    }

    /**
     * Sets the value of the rootPackage property.
     * 
     * @param value
     *     allowed object is
     *     {@link RootPackage }
     *     
     */
    public void setRootPackage(RootPackage value) {
        this.rootPackage = value;
    }

    /**
     * Gets the value of the batchConfig property.
     * 
     * @return
     *     possible object is
     *     {@link BatchConfig }
     *     
     */
    public BatchConfig getBatchConfig() {
        return batchConfig;
    }

    /**
     * Sets the value of the batchConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchConfig }
     *     
     */
    public void setBatchConfig(BatchConfig value) {
        this.batchConfig = value;
    }

    /**
     * JNDI This implementation of DataSource is intended for use with containers such as EJB or Application Servers
     *         that may configure the DataSource centrally or externally and place a reference to it in a JNDI context.
     * 
     *         initial_context This property is used for the Context lookup from the InitialContext
     *         (i.e. initialContext.lookup(initial_context)).
     *         This property is optional, and if omitted, then the data_source property will be looked up against the InitialContext directly.
     * 
     *         Similar to the other DataSource configurations,
     *         it's possible to send properties directly to the InitialContext by prefixing those properties with "env."
     *         example : env.encoding=UTF8
     *         This would send the property encoding with the value of UTF8 to the constructor of the InitialContext upon instantiation.
     *         
     *         <dataSource type="JNDI"
     *             data_source=""
     *             initial_context=""
     *         />
     * 
     * @return
     *     possible object is
     *     {@link DatabaseRepository }
     *     
     */
    public DatabaseRepository getDatabaseRepository() {
        return databaseRepository;
    }

    /**
     * Sets the value of the databaseRepository property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatabaseRepository }
     *     
     */
    public void setDatabaseRepository(DatabaseRepository value) {
        this.databaseRepository = value;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }
}