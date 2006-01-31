package at.generic.eventmodel;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Event implements Serializable {

    /** identifier field */
    private Long eventid;

    /** nullable persistent field */
    private String xmlcontent;

    /** nullable persistent field */
    private String guid;

    /** nullable persistent field */
    private String priority;

    /** nullable persistent field */
    private Date localtimeid;

    /** nullable persistent field */
    private Date utctimeid;

    /** nullable persistent field */
    private Date localrwtimeid;

    /** nullable persistent field */
    private Date utcrwtimeid;

    /** nullable persistent field */
    private Integer rwtimeid;

    /** nullable persistent field */
    private Integer txtimeid;

    /** nullable persistent field */
    private Integer eventtypeid;

    /** full constructor */
    public Event(Long eventid, String xmlcontent, String guid, String priority, Date localtimeid, Date utctimeid, Date localrwtimeid, Date utcrwtimeid, Integer rwtimeid, Integer txtimeid, Integer eventtypeid) {
        this.eventid = eventid;
        this.xmlcontent = xmlcontent;
        this.guid = guid;
        this.priority = priority;
        this.localtimeid = localtimeid;
        this.utctimeid = utctimeid;
        this.localrwtimeid = localrwtimeid;
        this.utcrwtimeid = utcrwtimeid;
        this.rwtimeid = rwtimeid;
        this.txtimeid = txtimeid;
        this.eventtypeid = eventtypeid;
    }

    /** default constructor */
    public Event() {
    }

    /** minimal constructor */
    public Event(Long eventid) {
        this.eventid = eventid;
    }

    public Long getEventid() {
        return this.eventid;
    }

    public void setEventid(Long eventid) {
        this.eventid = eventid;
    }

    public String getXmlcontent() {
        return this.xmlcontent;
    }

    public void setXmlcontent(String xmlcontent) {
        this.xmlcontent = xmlcontent;
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getLocaltimeid() {
        return this.localtimeid;
    }

    public void setLocaltimeid(Date localtimeid) {
        this.localtimeid = localtimeid;
    }

    public Date getUtctimeid() {
        return this.utctimeid;
    }

    public void setUtctimeid(Date utctimeid) {
        this.utctimeid = utctimeid;
    }

    public Date getLocalrwtimeid() {
        return this.localrwtimeid;
    }

    public void setLocalrwtimeid(Date localrwtimeid) {
        this.localrwtimeid = localrwtimeid;
    }

    public Date getUtcrwtimeid() {
        return this.utcrwtimeid;
    }

    public void setUtcrwtimeid(Date utcrwtimeid) {
        this.utcrwtimeid = utcrwtimeid;
    }

    public Integer getRwtimeid() {
        return this.rwtimeid;
    }

    public void setRwtimeid(Integer rwtimeid) {
        this.rwtimeid = rwtimeid;
    }

    public Integer getTxtimeid() {
        return this.txtimeid;
    }

    public void setTxtimeid(Integer txtimeid) {
        this.txtimeid = txtimeid;
    }

    public Integer getEventtypeid() {
        return this.eventtypeid;
    }

    public void setEventtypeid(Integer eventtypeid) {
        this.eventtypeid = eventtypeid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("eventid", getEventid())
            .toString();
    }

}
