package at.generic.eventmodel;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Eventtype implements Serializable {

    /** identifier field */
    private Integer eventtypeid;

    /** persistent field */
    private String eventname;

    /** persistent field */
    private Set events;

    /** full constructor */
    public Eventtype(String eventname, Set events) {
        this.eventname = eventname;
        this.events = events;
    }

    /** default constructor */
    public Eventtype() {
    }

    public Integer getEventtypeid() {
        return this.eventtypeid;
    }

    public void setEventtypeid(Integer eventtypeid) {
        this.eventtypeid = eventtypeid;
    }

    public String getEventname() {
        return this.eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public Set getEvents() {
        return this.events;
    }

    public void setEvents(Set events) {
        this.events = events;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("eventtypeid", getEventtypeid())
            .toString();
    }

}
