/*
 * Created on 01.05.2005
 * king
 * 
 */
package at.newsagg.model.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;

import at.newsagg.model.logstatistics.LogPostings;
import at.newsagg.model.logstatistics.LogSubscribers;

/**
 * @author king
 * @version
 * created on 01.05.2005 21:34:24
 *
 */
public interface ChannelIF {
    
    // ----- Valid update period values

    /** Update of channel expected to be specified in number of hours */
    public static final String UPDATE_HOURLY = "hourly";

    /** Update of channel expected to be specified in number of days */
    public static final String UPDATE_DAILY = "daily";

    /** Update of channel expected to be specified in number of weeks */
    public static final String UPDATE_WEEKLY = "weekly";

    /** Update of channel expected to be specified in number of months */
    public static final String UPDATE_MONTHLY = "monthly";

    /** Update of channel expected to be specified in number of years */
    public static final String UPDATE_YEARLY = "yearly";
    
    // --------------------------------------------------------------
    public int getIntId();

    public void setIntId(int anId);

    public long getId();

    public void setId(long longid);

    /**
     * @hibernate.property
     *  column="TITLE"
     *  not-null="true"
     *  type="text"
     *
     * @return title.
     */
    public String getTitle();

    public void setTitle(String aTitle);

    /**
     * @hibernate.property
     *  column="DESCRIPTION"
     *  type="text"
     *
     * @return description.
     */
    public String getDescription();

    public void setDescription(String aDescription);

    // We store the Location as a text string in the database, but as a URL in the memory based object.
    public String getLocationString();

    public void setLocationString(String loc) throws MalformedURLException;

    public URL getLocation();

    public void setLocation(URL location);

    /**
     * @hibernate.property
     *  column="SITE"
     *
     * @return URL of the site.
     */
    public URL getSite();

    public void setSite(URL siteUrl);

    /**
     * @hibernate.property
     *  column="CREATOR"
     *  type="text"
     * @return name of creator.
     */
    public String getCreator();

    public void setCreator(String aCreator);

    /**
     * @hibernate.property
     *  column="PUBLISHER"
     *  type="text"
     * @return publisher.
     */
    public String getPublisher();

    public void setPublisher(String aPublisher);

    /**
     * @hibernate.property
     *  column="LANGUAGE"
     *
     * @return language of channel.
     */
    public String getLanguage();

    public void setLanguage(String aLanguage);

    /**
     * @hibernate.property
     *  column="FORMAT"
     *  type="string"
     *
     * @return format string.
     */
    public String getFormatString();

    public void setFormatString(String strFormat);

    public ChannelFormat getFormat();

    public void setFormat(ChannelFormat aFormat);

    /**
     * @hibernate.bag
     *  table="ITEMS"
     *  cascade="all"
     *  inverse="true"
     *  lazy="true"
     *  order-by="date DESC"
     * @hibernate.collection-key
     *  column="CHANNEL_ID"
     * @hibernate.collection-one-to-many
     *  class="at.newsagg.model.parser.hibernate.Item"
     *
     * @return items of channel.
     */
    public Collection getItems();

    public void setItems(Collection anItems);

    public void addItem(ItemIF item);

    public void removeItem(ItemIF item);

    public ItemIF getItem(long itemId);

    /**
     * @hibernate.many-to-one
     *  column="IMAGE_ID"
     *  class="at.newsagg.model.parser.hibernate.Image"
     *  not-null="false"
     *
     * @return image.
     */
    public ImageIF getImage();

    public void setImage(ImageIF anImage);

    /**
     * @hibernate.property
     *  column="COPYRIGHT"
     *
     * @return copyright note.
     */
    public String getCopyright();

    public void setCopyright(String aCopyright);

    /**
     * @hibernate.property
     *  column="RATING"
     *
     * @return rating.
     */
    public String getRating();

    public void setRating(String aRating);

    /**
     * @hibernate.property
     *  column="GENERATOR"
     *
     * @return generator.
     */
    public String getGenerator();

    public void setGenerator(String aGenerator);

    /**
     * @hibernate.property
     *  column="DOCS"
     *
     * @return docs.
     */
    public String getDocs();

    public void setDocs(String aDocs);

    /**
     * @hibernate.property
     *  column="TTL"
     *
     * @return TTL value.
     */
    public int getTtl();

    public void setTtl(int aTtl);

    /**
     * @hibernate.property
     *  column="LAST_UPDATED"
     *
     * @return date of last update.
     */
    public Date getLastUpdated();

    public void setLastUpdated(Date date);

    /**
     * @hibernate.property
     *  column="LAST_BUILD_DATE"
     *
     * @return date of last builing.
     */
    public Date getLastBuildDate();

    public void setLastBuildDate(Date date);

    /**
     * @hibernate.property
     *  column="PUB_DATE"
     *
     * @return publication date.
     */
    public Date getPubDate();

    public void setPubDate(Date date);

    // RSS 1.0 Syndication Module methods
    public String getUpdatePeriod();

    public void setUpdatePeriod(String anUpdatePeriod);

    /**
     * @hibernate.property
     *  column="UPDATE_FREQUENCY"
     *
     * @return update frequency.
     */
    public int getUpdateFrequency();

    public void setUpdateFrequency(int anUpdateFrequency);

    /**
     * @hibernate.property
     *  column="UPDATE_BASE"
     *
     * @return update base.
     */
    public Date getUpdateBase();

    public void setUpdateBase(Date date);

    public String getElementValue(final String path);

    public String[] getElementValues(final String path, final String[] elements);

    public String getAttributeValue(final String path, final String attribute);

    public String[] getAttributeValues(final String path,
            final String[] attributes);

    /**
     * @hibernate.bag
     *  table="COMMENTS"
     *  cascade="none"
     *  inverse="true"
     *  orderby="addedDate DESC"
     *  lazy="true"
     * @hibernate.collection-key
     *  column="CHANNEL_ID"
     * @hibernate.collection-one-to-many
     *  class="at.newsagg.model.Comment"
     *
     * @return the comments.
     */
    public Collection getComments();

    /**
     * @param comments The comments to set.
     */
    public void setComments(Collection comments);

    //overwriting equals and hashCode!
    public boolean equals(Object other);

    public int hashCode();

    /**
     *  
     * @return Returns the logPostings.
     */
    public Collection getLogPostings();

    /**
     * @param logPostings The logPostings to set.
     */
    public void setLogPostings(Collection logPostings);

    /**
     * 
     * @return Returns the logSubscribers.
     */
    public Collection getLogSubscribers();

    /**
     * @param logSubscribers The logSubscribers to set.
     */
    public void setLogSubscribers(Collection logSubscribers);
    
    public String getSiteString();
    
    public void setSiteString(String siteUrl) throws MalformedURLException;
}