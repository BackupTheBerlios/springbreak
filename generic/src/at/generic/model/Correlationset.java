package at.generic.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Correlationset implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String correlationSetDef;

    /** persistent field */
    private String correlationSetGuid;

    /** persistent field */
    private String correlatingData;

    /** persistent field */
    private String eventType;

    /** persistent field */
    private Date dbtimeCreated;

    /** nullable persistent field */
    private Long eventid;

    /** full constructor */
    public Correlationset(Integer id, String correlationSetDef, String correlationSetGuid, String correlatingData, String eventType, Date dbtimeCreated, Long eventid) {
        this.id = id;
        this.correlationSetDef = correlationSetDef;
        this.correlationSetGuid = correlationSetGuid;
        this.correlatingData = correlatingData;
        this.eventType = eventType;
        this.dbtimeCreated = dbtimeCreated;
        this.eventid = eventid;
    }

    /** default constructor */
    public Correlationset() {
    }

    /** minimal constructor */
    public Correlationset(Integer id, String correlationSetDef, String correlationSetGuid, String correlatingData, String eventType, Date dbtimeCreated) {
        this.id = id;
        this.correlationSetDef = correlationSetDef;
        this.correlationSetGuid = correlationSetGuid;
        this.correlatingData = correlatingData;
        this.eventType = eventType;
        this.dbtimeCreated = dbtimeCreated;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorrelationSetDef() {
        return this.correlationSetDef;
    }

    public void setCorrelationSetDef(String correlationSetDef) {
        this.correlationSetDef = correlationSetDef;
    }

    public String getCorrelationSetGuid() {
        return this.correlationSetGuid;
    }

    public void setCorrelationSetGuid(String correlationSetGuid) {
        this.correlationSetGuid = correlationSetGuid;
    }

    public String getCorrelatingData() {
        return this.correlatingData;
    }

    public void setCorrelatingData(String correlatingData) {
        this.correlatingData = correlatingData;
    }

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getDbtimeCreated() {
        return this.dbtimeCreated;
    }

    public void setDbtimeCreated(Date dbtimeCreated) {
        this.dbtimeCreated = dbtimeCreated;
    }

    public Long getEventid() {
        return this.eventid;
    }

    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
