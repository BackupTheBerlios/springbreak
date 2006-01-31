package at.generic.eventmodel;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Txtime implements Serializable {

    /** identifier field */
    private Integer txtimeid;

    /** nullable persistent field */
    private Short txday;

    /** nullable persistent field */
    private Short txmonth;

    /** nullable persistent field */
    private Short txyear;

    /** persistent field */
    private Set events;

    /** full constructor */
    public Txtime(Short txday, Short txmonth, Short txyear, Set events) {
        this.txday = txday;
        this.txmonth = txmonth;
        this.txyear = txyear;
        this.events = events;
    }

    /** default constructor */
    public Txtime() {
    }

    /** minimal constructor */
    public Txtime(Set events) {
        this.events = events;
    }

    public Integer getTxtimeid() {
        return this.txtimeid;
    }

    public void setTxtimeid(Integer txtimeid) {
        this.txtimeid = txtimeid;
    }

    public Short getTxday() {
        return this.txday;
    }

    public void setTxday(Short txday) {
        this.txday = txday;
    }

    public Short getTxmonth() {
        return this.txmonth;
    }

    public void setTxmonth(Short txmonth) {
        this.txmonth = txmonth;
    }

    public Short getTxyear() {
        return this.txyear;
    }

    public void setTxyear(Short txyear) {
        this.txyear = txyear;
    }

    public Set getEvents() {
        return this.events;
    }

    public void setEvents(Set events) {
        this.events = events;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("txtimeid", getTxtimeid())
            .toString();
    }

}
