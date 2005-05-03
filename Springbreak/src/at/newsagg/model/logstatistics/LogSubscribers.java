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
 * Number of subscribers of a Feed at a Date date!
 * 
 * 
 * @author Roland Vecera
 * @version
 * created on 01.05.2005 10:53:39
 *
 *@hibernate.class
 *  table="LOG_SUBSCRIBERS"
 */

public class LogSubscribers extends BaseObject implements LogSubscribersIF {
   
    private int id = -1;
    
    private Date date;
    private Channel channel;
    private int num_subscribers;
    
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
     * @hibernate.many-to-one
     *  column="CHANNEL_ID"
     *  class="at.newsagg.model.parser.hibernate.Channel"
     *  not-null="true"
     *
     * @return parent channel.
     */
    public Channel getChannel() {
        return channel;
    }
    /**
     * @param feed_id The feed_id to set.
     */
    public void setChannel(Channel c) {
        this.channel = c;
    }
    /**
     * @hibernate.property
     *  column="NUM_SUBSCRIBERS"
     *  type="integer"
     * @return Returns the num_subscripers.
     */
    public int getNum_subscribers() {
        return num_subscribers;
    }
    /**
     * @param num_subscripers The num_subscripers to set.
     */
    public void setNum_subscribers(int num_subscripers) {
        this.num_subscribers = num_subscripers;
    }
    
    //overwriting equals and hashCode!
    //2 LogSubscribers are equal..
    
    public boolean equals(Object other) {
        if (this==other) return true;
        if ( !(other instanceof LogSubscribers) ) return false;
        final LogSubscribers that = (LogSubscribers) other;
        return this.getDate().equals( that.getDate()) && (this.getChannel().getId() == (that.getChannel().getId())) && (this.getNum_subscribers() == that.getNum_subscribers());
        }
    
    
    
    public int hashCode() {
        return (this.getDate().toString()+this.getChannel().getId()+this.getNum_subscribers()).hashCode();
        
        }
}
