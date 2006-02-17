package at.generic.eventmodel;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Wordindex implements Serializable {

    /** identifier field */
    private Long wid;

    /** nullable persistent field */
    private String document;

    /** nullable persistent field */
    private String wtype;

    /** full constructor */
    public Wordindex(Long wid, String document, String wtype) {
        this.wid = wid;
        this.document = document;
        this.wtype = wtype;
    }

    /** default constructor */
    public Wordindex() {
    }

    /** minimal constructor */
    public Wordindex(Long wid) {
        this.wid = wid;
    }

    public Long getWid() {
        return this.wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public String getDocument() {
        return this.document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getWtype() {
        return this.wtype;
    }

    public void setWtype(String wtype) {
        this.wtype = wtype;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("wid", getWid())
            .toString();
    }

}
