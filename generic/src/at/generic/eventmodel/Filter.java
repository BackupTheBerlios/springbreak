package at.generic.eventmodel;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Filter implements Serializable {

    /** identifier field */
    private Integer fid;

    /** persistent field */
    private int pid;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String ranktype;

    /** full constructor */
    public Filter(Integer fid, int pid, String name, String ranktype) {
        this.fid = fid;
        this.pid = pid;
        this.name = name;
        this.ranktype = ranktype;
    }

    /** default constructor */
    public Filter() {
    }

    /** minimal constructor */
    public Filter(Integer fid, int pid) {
        this.fid = fid;
        this.pid = pid;
    }

    public Integer getFid() {
        return this.fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public int getPid() {
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRanktype() {
        return this.ranktype;
    }

    public void setRanktype(String ranktype) {
        this.ranktype = ranktype;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("fid", getFid())
            .toString();
    }

}
