package at.generic.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Correlatedevent implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String guid;

    /** persistent field */
    private String eventXml;

    /** persistent field */
    private Date dbtimeCreated;

    /** persistent field */
    private Set correlationsets;

    /** full constructor */
    public Correlatedevent(Integer id, String guid, String eventXml, Date dbtimeCreated, Set correlationsets) {
        this.id = id;
        this.guid = guid;
        this.eventXml = eventXml;
        this.dbtimeCreated = dbtimeCreated;
        this.correlationsets = correlationsets;
    }

    /** default constructor */
    public Correlatedevent() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getEventXml() {
        return this.eventXml;
    }

    public void setEventXml(String eventXml) {
        this.eventXml = eventXml;
    }

    public Date getDbtimeCreated() {
        return this.dbtimeCreated;
    }

    public void setDbtimeCreated(Date dbtimeCreated) {
        this.dbtimeCreated = dbtimeCreated;
    }

    public Set getCorrelationsets() {
        return this.correlationsets;
    }

    public void setCorrelationsets(Set correlationsets) {
        this.correlationsets = correlationsets;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
