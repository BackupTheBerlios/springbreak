/*
 * Created on 29.03.2005
 * king
 * 
 */
package at.newsagg.model;

import at.newsagg.model.parser.hibernate.Channel;

/**
 * A Comment is an opinion given by a User to a Channel.
 * 
 * @author Roland Vecera
 * @version
 * created on 29.03.2005 16:55:07
 * @hibernate.class
 *  table="COMMENTS"
 */
public class Comment extends BaseObject{
    
    private User user;
    private Channel channel;
    
    private int id;
    private String title;
    private String text;
    private Byte stars;
    private java.util.Date addedDate;
    
    //just a simplifier!
    //add this attribute here, so I can use
    //this class directly as "ActionForm" in the webapplication
    //must not be persisted in DB!
    private int channel_id;
    
    

    /**
     * @return Returns the channel_id.
     */
    public int getChannel_id() {
        return channel_id;
    }
    /**
     * @param channel_id The channel_id to set.
     */
    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }
    /**
     * @hibernate.property
     *  column="ADDEDDATE"
     *
     * @return date of last update.
     */
    public java.util.Date getAddedDate() {
        return addedDate;
    }
    /**
     * @param addedDate The addedDate to set.
     */
    public void setAddedDate(java.util.Date addedDate) {
        this.addedDate = addedDate;
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
     * @param channel The channel to set.
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    
    /**
     * @hibernate.id
     *  column="COMMENT_ID"
     *  generator-class="native"
     *  type="int"
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
     *  column="STARS"
     *  not-null="false"
     *  type="byte"
     *
     * @return text commenttext.
     */
    public Byte getStars() {
        return stars;
    }
    /**
     * @param stars The stars to set.
     */
    public void setStars(Byte stars) {
        this.stars = stars;
    }
    /**
     * @hibernate.property
     *  column="TEXT"
     *  not-null="true"
     *  type="text"
     *
     * @return text commenttext.
     */
    public String getText() {
        return text;
    }
    /**
     * @param text The text to set.
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * @hibernate.property
     *  column="TITLE"
     *  not-null="true"
     *  type="text"
     *
     * @return title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @hibernate.many-to-one
     *  column="USER_ID"
     *  class="at.newsagg.model.User"
     *  not-null="true"
     *
     * @return parent channel.
     */
    public User getUser() {
        return user;
    }
    /**
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
