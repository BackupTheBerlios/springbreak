package at.generic.eventmodel;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Eventattribute implements Serializable {

    /** identifier field */
    private Long attributeid;

    /** nullable persistent field */
    private Long eventid;

    /** nullable persistent field */
    private String attributename;

    /** nullable persistent field */
    private String datatype;

    /** nullable persistent field */
    private String xmluri;

    /** nullable persistent field */
    private String value;

    /** nullable persistent field */
    private at.generic.eventmodel.Event event;

    /** full constructor */
    public Eventattribute(Long eventid, String attributename, String datatype, String xmluri, String value, at.generic.eventmodel.Event event) {
        this.eventid = eventid;
        this.attributename = attributename;
        this.datatype = datatype;
        this.xmluri = xmluri;
        this.value = value;
        this.event = event;
    }

    /** default constructor */
    public Eventattribute() {
    }

    public Long getAttributeid() {
        return this.attributeid;
    }

    public void setAttributeid(Long attributeid) {
        this.attributeid = attributeid;
    }

    public Long getEventid() {
        return this.eventid;
    }

    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    public String getAttributename() {
        return this.attributename;
    }

    public void setAttributename(String attributename) {
        this.attributename = attributename;
    }

    public String getDatatype() {
        return this.datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getXmluri() {
        return this.xmluri;
    }

    public void setXmluri(String xmluri) {
        this.xmluri = xmluri;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public at.generic.eventmodel.Event getEvent() {
        return this.event;
    }

    public void setEvent(at.generic.eventmodel.Event event) {
        this.event = event;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("attributeid", getAttributeid())
            .toString();
    }

}
