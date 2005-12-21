package at.generic.eventmodel;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class BaseEvent implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String guid;

    /** persistent field */
    private String eventXml;

    /** persistent field */
    private Date dbtimeCreated;

    /** persistent field */
    private String eventtype;

    /** full constructor */
    public BaseEvent(Integer id, String guid, String eventXml, Date dbtimeCreated, String eventtype) {
        this.id = id;
        this.guid = guid;
        this.eventXml = eventXml;
        this.dbtimeCreated = dbtimeCreated;
        this.eventtype = eventtype;
    }

    /** default constructor */
    public BaseEvent() {
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

    public String getEventtype() {
        return this.eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
