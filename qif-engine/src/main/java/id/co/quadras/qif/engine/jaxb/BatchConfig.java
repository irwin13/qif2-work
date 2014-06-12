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
 *       &lt;attribute name="eventLogPersistInterval" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="eventLogMsgPersistInterval" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="activityLogPersistInterval" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="activityLogDataPersistInterval" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="activityLogInputMsgPersistInterval" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="activityLogOutputMsgPersistInterval" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="counterUpdateInterval" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlRootElement(name = "batchConfig")
public class BatchConfig {

    @XmlValue
    protected String value;
    @XmlAttribute
    protected String eventLogPersistInterval;
    @XmlAttribute
    protected String eventLogMsgPersistInterval;
    @XmlAttribute
    protected String activityLogPersistInterval;
    @XmlAttribute
    protected String activityLogDataPersistInterval;
    @XmlAttribute
    protected String activityLogInputMsgPersistInterval;
    @XmlAttribute
    protected String activityLogOutputMsgPersistInterval;
    @XmlAttribute
    protected String counterUpdateInterval;

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
     * Gets the value of the eventLogPersistInterval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventLogPersistInterval() {
        return eventLogPersistInterval;
    }

    /**
     * Sets the value of the eventLogPersistInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventLogPersistInterval(String value) {
        this.eventLogPersistInterval = value;
    }

    /**
     * Gets the value of the eventLogMsgPersistInterval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventLogMsgPersistInterval() {
        return eventLogMsgPersistInterval;
    }

    /**
     * Sets the value of the eventLogMsgPersistInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventLogMsgPersistInterval(String value) {
        this.eventLogMsgPersistInterval = value;
    }

    /**
     * Gets the value of the activityLogPersistInterval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityLogPersistInterval() {
        return activityLogPersistInterval;
    }

    /**
     * Sets the value of the activityLogPersistInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityLogPersistInterval(String value) {
        this.activityLogPersistInterval = value;
    }

    /**
     * Gets the value of the activityLogDataPersistInterval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityLogDataPersistInterval() {
        return activityLogDataPersistInterval;
    }

    /**
     * Sets the value of the activityLogDataPersistInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityLogDataPersistInterval(String value) {
        this.activityLogDataPersistInterval = value;
    }

    /**
     * Gets the value of the activityLogInputMsgPersistInterval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityLogInputMsgPersistInterval() {
        return activityLogInputMsgPersistInterval;
    }

    /**
     * Sets the value of the activityLogInputMsgPersistInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityLogInputMsgPersistInterval(String value) {
        this.activityLogInputMsgPersistInterval = value;
    }

    /**
     * Gets the value of the activityLogOutputMsgPersistInterval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityLogOutputMsgPersistInterval() {
        return activityLogOutputMsgPersistInterval;
    }

    /**
     * Sets the value of the activityLogOutputMsgPersistInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityLogOutputMsgPersistInterval(String value) {
        this.activityLogOutputMsgPersistInterval = value;
    }

    /**
     * Gets the value of the counterUpdateInterval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCounterUpdateInterval() {
        return counterUpdateInterval;
    }

    /**
     * Sets the value of the counterUpdateInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCounterUpdateInterval(String value) {
        this.counterUpdateInterval = value;
    }

    @Override
    public String toString() {
        return PojoUtil.beanToString(this, false);
    }

}
