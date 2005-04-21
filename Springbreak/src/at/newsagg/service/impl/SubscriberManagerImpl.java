/*
 * Created on 30.03.2005
 * king
 * 
 */
package at.newsagg.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.dao.CategoryDAO;
import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.dao.UserDAO;
import at.newsagg.model.Category;
import at.newsagg.model.FeedSubscriber;
import at.newsagg.model.FeedSubscriberIF;
import at.newsagg.model.User;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.service.SubscriberManager;

/**
 * Manager class to subscribe/unsubscribe from a Channel.
 * 
 * @author Roland Vecera
 * @version
 * created on 30.03.2005 12:07:20
 *
 */
public class SubscriberManagerImpl implements SubscriberManager {
    
    private static Log log = LogFactory.getLog(SubscriberManagerImpl.class); 
	private FeedSubscriberDAO feedDAO;
	private UserDAO userDAO;
	private ChannelDAO channelDAO;
	private CategoryDAO catDAO;
	
	/**
	 * 
	 * @param username
	 * @param channel_id
	 * @param category_id
	 * @return
	 */
	public FeedSubscriberIF addFeedSubscriber (String username, int channel_id, int category_id)
	{
	    User u = userDAO.getUser(username);
	    Channel c = channelDAO.getChannel(channel_id);
	    Category cat =  catDAO.getCategory(category_id);
	    
	    FeedSubscriber f = new FeedSubscriber ();
	    f.setChannel(c);
	    f.setUser(u);
	    f.setCategory(cat);
	    
	    f = (FeedSubscriber)feedDAO.saveFeedSubscriber(f);
	    log.info("FeedSubscriber "+f.getId()+ " added at "+f.getAddedDate());
	    
	    return f;
	}
	
	/**
	 * Deletes a FeedSubscriber.
	 * 
	 * @param feedsubscriber_id
	 */
	public void deleteFeedSubscriber (int feedsubscriber_id)
	{
	    feedDAO.removeFeedSubscriber(feedsubscriber_id);
	
	}
	/**
	 * Updates the Category of a FeedSubscriber.
	 * 
	 * @param feedsubscriber_id
	 * @param category_id
	 * @return
	 */
	public FeedSubscriberIF changeFeedSubscriberCategory (int feedsubscriber_id, int category_id)
	{
	 FeedSubscriberIF f = feedDAO.getFeedSubscriber(feedsubscriber_id);
	 Category c = catDAO.getCategory(category_id);
	 
	 f.setCategory(c);
	 feedDAO.updateFeedSubscriber(f);
	 return f;
	 
	}

   
    /**
     * @return Returns the catDAO.
     */
    public CategoryDAO getCatDAO() {
        return catDAO;
    }
    /**
     * @param catDAO The catDAO to set.
     */
    public void setCatDAO(CategoryDAO catDAO) {
        this.catDAO = catDAO;
    }
    /**
     * @return Returns the channelDAO.
     */
    public ChannelDAO getChannelDAO() {
        return channelDAO;
    }
    /**
     * @param channelDAO The channelDAO to set.
     */
    public void setChannelDAO(ChannelDAO channelDAO) {
        this.channelDAO = channelDAO;
    }
    /**
     * @return Returns the feedDAO.
     */
    public FeedSubscriberDAO getFeedDAO() {
        return feedDAO;
    }
    /**
     * @param feedDAO The feedDAO to set.
     */
    public void setFeedDAO(FeedSubscriberDAO feedDAO) {
        this.feedDAO = feedDAO;
    }
    /**
     * @return Returns the userDAO.
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }
    /**
     * @param userDAO The userDAO to set.
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
