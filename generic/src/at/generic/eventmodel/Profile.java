package at.generic.eventmodel;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Profile implements Serializable {

    /** identifier field */
    private Integer pid;

    /** nullable persistent field */
    private String name;

    /** full constructor */
    public Profile(Integer pid, String name) {
        this.pid = pid;
        this.name = name;
    }

    /** default constructor */
    public Profile() {
    }

    /** minimal constructor */
    public Profile(Integer pid) {
        this.pid = pid;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pid", getPid())
            .toString();
    }

}
