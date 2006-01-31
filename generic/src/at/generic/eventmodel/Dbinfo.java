package at.generic.eventmodel;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Dbinfo implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String updatestart;

    /** nullable persistent field */
    private String updatestop;

    /** nullable persistent field */
    private Short processeditems;

    /** full constructor */
    public Dbinfo(String updatestart, String updatestop, Short processeditems) {
        this.updatestart = updatestart;
        this.updatestop = updatestop;
        this.processeditems = processeditems;
    }

    /** default constructor */
    public Dbinfo() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpdatestart() {
        return this.updatestart;
    }

    public void setUpdatestart(String updatestart) {
        this.updatestart = updatestart;
    }

    public String getUpdatestop() {
        return this.updatestop;
    }

    public void setUpdatestop(String updatestop) {
        this.updatestop = updatestop;
    }

    public Short getProcesseditems() {
        return this.processeditems;
    }

    public void setProcesseditems(Short processeditems) {
        this.processeditems = processeditems;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
