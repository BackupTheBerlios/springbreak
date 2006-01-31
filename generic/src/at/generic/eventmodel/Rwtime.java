package at.generic.eventmodel;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Rwtime implements Serializable {

    /** identifier field */
    private Integer rwtimeid;

    /** nullable persistent field */
    private Short rwday;

    /** nullable persistent field */
    private Short rwmonth;

    /** nullable persistent field */
    private Short rwyear;

    /** persistent field */
    private Set events;

    /** full constructor */
    public Rwtime(Short rwday, Short rwmonth, Short rwyear, Set events) {
        this.rwday = rwday;
        this.rwmonth = rwmonth;
        this.rwyear = rwyear;
        this.events = events;
    }

    /** default constructor */
    public Rwtime() {
    }

    /** minimal constructor */
    public Rwtime(Set events) {
        this.events = events;
    }

    public Integer getRwtimeid() {
        return this.rwtimeid;
    }

    public void setRwtimeid(Integer rwtimeid) {
        this.rwtimeid = rwtimeid;
    }

    public Short getRwday() {
        return this.rwday;
    }

    public void setRwday(Short rwday) {
        this.rwday = rwday;
    }

    public Short getRwmonth() {
        return this.rwmonth;
    }

    public void setRwmonth(Short rwmonth) {
        this.rwmonth = rwmonth;
    }

    public Short getRwyear() {
        return this.rwyear;
    }

    public void setRwyear(Short rwyear) {
        this.rwyear = rwyear;
    }

    public Set getEvents() {
        return this.events;
    }

    public void setEvents(Set events) {
        this.events = events;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rwtimeid", getRwtimeid())
            .toString();
    }

}
