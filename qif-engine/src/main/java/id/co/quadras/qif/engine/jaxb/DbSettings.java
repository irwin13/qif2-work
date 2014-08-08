//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.08 at 10:28:41 PM WIT 
//


package id.co.quadras.qif.engine.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="cacheEnabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lazyLoadingEnabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="multipleResultSetsEnabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="useColumnLabel" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="useGeneratedKeys" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="autoMappingBehavior" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultExecutorType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultStatementTimeout" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="safeRowBoundsEnabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mapUnderscoreToCamelCase" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="localCacheScope" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="jdbcTypeForNull" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lazyLoadTriggerMethods" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="logPrefix" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="logImpl" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "dbSettings")
public class DbSettings {

    @XmlValue
    protected String value;
    @XmlAttribute
    protected String cacheEnabled;
    @XmlAttribute
    protected String lazyLoadingEnabled;
    @XmlAttribute
    protected String multipleResultSetsEnabled;
    @XmlAttribute
    protected String useColumnLabel;
    @XmlAttribute
    protected String useGeneratedKeys;
    @XmlAttribute
    protected String autoMappingBehavior;
    @XmlAttribute
    protected String defaultExecutorType;
    @XmlAttribute
    protected String defaultStatementTimeout;
    @XmlAttribute
    protected String safeRowBoundsEnabled;
    @XmlAttribute
    protected String mapUnderscoreToCamelCase;
    @XmlAttribute
    protected String localCacheScope;
    @XmlAttribute
    protected String jdbcTypeForNull;
    @XmlAttribute
    protected String lazyLoadTriggerMethods;
    @XmlAttribute
    protected String logPrefix;
    @XmlAttribute
    protected String logImpl;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the cacheEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCacheEnabled() {
        return cacheEnabled;
    }

    /**
     * Sets the value of the cacheEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCacheEnabled(String value) {
        this.cacheEnabled = value;
    }

    /**
     * Gets the value of the lazyLoadingEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    /**
     * Sets the value of the lazyLoadingEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLazyLoadingEnabled(String value) {
        this.lazyLoadingEnabled = value;
    }

    /**
     * Gets the value of the multipleResultSetsEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMultipleResultSetsEnabled() {
        return multipleResultSetsEnabled;
    }

    /**
     * Sets the value of the multipleResultSetsEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMultipleResultSetsEnabled(String value) {
        this.multipleResultSetsEnabled = value;
    }

    /**
     * Gets the value of the useColumnLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseColumnLabel() {
        return useColumnLabel;
    }

    /**
     * Sets the value of the useColumnLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseColumnLabel(String value) {
        this.useColumnLabel = value;
    }

    /**
     * Gets the value of the useGeneratedKeys property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseGeneratedKeys() {
        return useGeneratedKeys;
    }

    /**
     * Sets the value of the useGeneratedKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseGeneratedKeys(String value) {
        this.useGeneratedKeys = value;
    }

    /**
     * Gets the value of the autoMappingBehavior property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoMappingBehavior() {
        return autoMappingBehavior;
    }

    /**
     * Sets the value of the autoMappingBehavior property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoMappingBehavior(String value) {
        this.autoMappingBehavior = value;
    }

    /**
     * Gets the value of the defaultExecutorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultExecutorType() {
        return defaultExecutorType;
    }

    /**
     * Sets the value of the defaultExecutorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultExecutorType(String value) {
        this.defaultExecutorType = value;
    }

    /**
     * Gets the value of the defaultStatementTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultStatementTimeout() {
        return defaultStatementTimeout;
    }

    /**
     * Sets the value of the defaultStatementTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultStatementTimeout(String value) {
        this.defaultStatementTimeout = value;
    }

    /**
     * Gets the value of the safeRowBoundsEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSafeRowBoundsEnabled() {
        return safeRowBoundsEnabled;
    }

    /**
     * Sets the value of the safeRowBoundsEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSafeRowBoundsEnabled(String value) {
        this.safeRowBoundsEnabled = value;
    }

    /**
     * Gets the value of the mapUnderscoreToCamelCase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    /**
     * Sets the value of the mapUnderscoreToCamelCase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapUnderscoreToCamelCase(String value) {
        this.mapUnderscoreToCamelCase = value;
    }

    /**
     * Gets the value of the localCacheScope property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalCacheScope() {
        return localCacheScope;
    }

    /**
     * Sets the value of the localCacheScope property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalCacheScope(String value) {
        this.localCacheScope = value;
    }

    /**
     * Gets the value of the jdbcTypeForNull property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJdbcTypeForNull() {
        return jdbcTypeForNull;
    }

    /**
     * Sets the value of the jdbcTypeForNull property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJdbcTypeForNull(String value) {
        this.jdbcTypeForNull = value;
    }

    /**
     * Gets the value of the lazyLoadTriggerMethods property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLazyLoadTriggerMethods() {
        return lazyLoadTriggerMethods;
    }

    /**
     * Sets the value of the lazyLoadTriggerMethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLazyLoadTriggerMethods(String value) {
        this.lazyLoadTriggerMethods = value;
    }

    /**
     * Gets the value of the logPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogPrefix() {
        return logPrefix;
    }

    /**
     * Sets the value of the logPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogPrefix(String value) {
        this.logPrefix = value;
    }

    /**
     * Gets the value of the logImpl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogImpl() {
        return logImpl;
    }

    /**
     * Sets the value of the logImpl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogImpl(String value) {
        this.logImpl = value;
    }

}
