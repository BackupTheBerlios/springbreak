/*
 * Created on 01.05.2005
 * king
 * 
 */
package at.newsagg.model.logstatistics;

import java.util.Date;

import at.newsagg.model.BaseObject;
import at.newsagg.model.parser.hibernate.Channel;

/**
 * Number of Postings to a Channel at Date for a given Timespan (Daten - timeSpan).
 *  
 * @author Roland Vecera
 * @version
 * created on 01.05.2005 11:03:54
 *
 *
 *@hibernate.class
 *  table="LOG_POSTINGS"
 *
 */
public class LogPostings extends BaseObject implements LogPostingsIF {
    
    private int id = -1;
    private Date date;
    private int timespan;
    private Channel channel;
    private int num_postings;
    
    
    /**
     * @hibernate.id
     *  generator-class="native"
     *  column="ID"
     *  type="integer"
     *  unsaved-value="-1"
     *
     * @return integer representation of identity.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    
/**
 * @hibernate.many-to-one
 *  column="CHANNEL_ID"
 *  class="at.newsagg.model.parser.hibernate.Channel"
 *  not-null="true" 
 * @return Returns the channel_id.
 */
    public Channel getChannel() {
        return channel;
    }
    /**
     * @param channel The channel_id to set.
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    /**
     * @hibernate.property
     *  column="DATE"
     * @return Returns the date.
     */
    public Date getDate() {
        return date;
    }
    /**
     * @param date The date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * @hibernate.property
     *  column="NUM_POSTINGS"
     *  type="integer"
     * @return Returns the num_postings.
     */
    public int getNum_postings() {
        return num_postings;
    }
    /**
     * @param num_postings The num_postings to set.
     */
    public void setNum_postings(int num_postings) {
        this.num_postings = num_postings;
    }
    /**
     * @hibernate.property
     *  column="TIMESPAN"
     *  type="integer"
     * @return Returns the timespan.
     */
    public int getTimespan() {
        return timespan;
    }
    /**
     * @param timespan The timespan to set.
     */
    public void setTimespan(int timespan) {
        this.timespan = timespan;
    }
    

    public boolean equals(Object other) {
        if (this==other) return true;
        if ( !(other instanceof LogPostings) ) return false;
        final LogPostings that = (LogPostings) other;
        return this.getDate().equals( that.getDate()) && (this.getChannel() == (that.getChannel())) && (this.getNum_postings() == that.getNum_postings());
        }
    
    
    
    public int hashCode() {
        return (this.getDate().toString()+this.getChannel()+this.getNum_postings()).hashCode();
        
        }
}
