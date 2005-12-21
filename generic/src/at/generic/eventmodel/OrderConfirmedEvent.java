package at.generic.eventmodel;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class OrderConfirmedEvent implements Serializable {

    /** identifier field */
    private long id;

    /** nullable persistent field */
    private String originalguid;

    /** nullable persistent field */
    private String priority;

    /** nullable persistent field */
    private String severity;

    /** nullable persistent field */
    private Date localtimecreated;

    /** nullable persistent field */
    private Date localtimecreatedrw;

    /** nullable persistent field */
    private Date utctimecreated;

    /** nullable persistent field */
    private Date utctimecreatedrw;

    /** nullable persistent field */
    private String majorversion;

    /** nullable persistent field */
    private String minorversion;

    /** nullable persistent field */
    private String orderid;

    /** nullable persistent field */
    private String datetime;

    /** full constructor */
    public OrderConfirmedEvent(long id, String originalguid, String priority, String severity, Date localtimecreated, Date localtimecreatedrw, Date utctimecreated, Date utctimecreatedrw, String majorversion, String minorversion, String orderid, String datetime) {
        this.id = id;
        this.originalguid = originalguid;
        this.priority = priority;
        this.severity = severity;
        this.localtimecreated = localtimecreated;
        this.localtimecreatedrw = localtimecreatedrw;
        this.utctimecreated = utctimecreated;
        this.utctimecreatedrw = utctimecreatedrw;
        this.majorversion = majorversion;
        this.minorversion = minorversion;
        this.orderid = orderid;
        this.datetime = datetime;
    }

    /** default constructor */
    public OrderConfirmedEvent() {
    }

    /** minimal constructor */
    public OrderConfirmedEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalguid() {
        return this.originalguid;
    }

    public void setOriginalguid(String originalguid) {
        this.originalguid = originalguid;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSeverity() {
        return this.severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Date getLocaltimecreated() {
        return this.localtimecreated;
    }

    public void setLocaltimecreated(Date localtimecreated) {
        this.localtimecreated = localtimecreated;
    }

    public Date getLocaltimecreatedrw() {
        return this.localtimecreatedrw;
    }

    public void setLocaltimecreatedrw(Date localtimecreatedrw) {
        this.localtimecreatedrw = localtimecreatedrw;
    }

    public Date getUtctimecreated() {
        return this.utctimecreated;
    }

    public void setUtctimecreated(Date utctimecreated) {
        this.utctimecreated = utctimecreated;
    }

    public Date getUtctimecreatedrw() {
        return this.utctimecreatedrw;
    }

    public void setUtctimecreatedrw(Date utctimecreatedrw) {
        this.utctimecreatedrw = utctimecreatedrw;
    }

    public String getMajorversion() {
        return this.majorversion;
    }

    public void setMajorversion(String majorversion) {
        this.majorversion = majorversion;
    }

    public String getMinorversion() {
        return this.minorversion;
    }

    public void setMinorversion(String minorversion) {
        this.minorversion = minorversion;
    }

    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
