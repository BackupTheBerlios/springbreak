/*
 * Created on 29.03.2005
 * king
 * 
 */
package at.newsagg.dao;

import java.util.Collection;
import java.util.Date;

import at.newsagg.model.FeedSubscriber;
import at.newsagg.model.FeedSubscriberIF;
import at.newsagg.dao.DAO;

/**
 * @author king
 * @version
 * created on 29.03.2005 16:43:52
 *
 */
public interface FeedSubscriberDAO extends DAO{
    /**
     * Save a new FeedSubscriber.
     * 
     * @param feedsubscriber
     * @return
     */
    public FeedSubscriberIF saveFeedSubscriber(FeedSubscriberIF feedsubscriber);

    /**
     * Update a FeedSubscriber
     * 
     * @param feedsubscriber
     * @return
     */
    public FeedSubscriberIF updateFeedSubscriber(FeedSubscriberIF feedsubscriber);

    /**
     * Delete an Feedsubscriber.
     * 
     * Doesn't delete recursively.
     * 
     * @param feedsubscriber_id
     */
    public void removeFeedSubscriber(int feedsubscriber_id);

    /**
     * Returns all FeedSubscriber to a given User.
     * 
     * @param username
     * @return
     */
    public Collection getFeedSubscriberByUser(String username);

    /**
     * Returns all FeedSubscriber to a given Channel.
     * 
     * @param locationURL
     * @return
     */
    public Collection getFeedSubscriberByChannelURL(String locationURL);

    /**
     * Returns all FeedSubcriber to a given Category.
     * 
     * @param category_id
     * @return
     */
    public Collection getFeedSubscriberByCategory(int category_id);

    /**
     * Returns all FeedSubscriber of a given User in his/her given Category.
     * 
     * @param username
     * @param category_id
     * @return
     */
    public Collection getFeedSubscriberForUserByCategory(String username,
            int category_id);

    /**
     * Returns the FeedSubscriber of a given User and a given Channel.
     * 
     * @param username
     * @param category_id
     * @return
     */
    public FeedSubscriber getFeedSubscriberForUserOnChannel(String username,
            int channel_id) throws IndexOutOfBoundsException;

    /**
     * Counts number of Channels a User has subscribed in total.
     * 
     * @param username
     * @return
     */
    public int countFeedSubscriberByUser(String username);

    /**
     * Counts number of subscribes on a given Channel.
     * 
     * @param locationURL
     * @return
     */
    public int countFeedSubscriberByChannelURL(String locationURL);

    /**
     * Counts all subscribed Channels of a User in a Category.
     * 
     * @param username
     *            name=key of a User
     * @param category_id
     *            id of a Category
     * @return
     */
    public int countFeedSubscriberByUserWithCategory(String username,
            int category_id);

    /**
     * returns the latest=numberHottest Channel-Items for a given User.
     * 
     * @author Roland Vecera
     * @version created on 29.03.2005 09:41:30
     *  
     */
    public Collection getHottestItemsForUser(String username, int numberHottest);

    /**
     * Returns all Channel-Items for a given User that where found since
     * java.util.Date since.
     * 
     * @author Roland Vecera
     * @param username
     * @param since
     * @return
     */
    public Collection getItemsForUserSince(String username, java.util.Date since);

    /**
     * Returns a number=numberHottest Items of a given user and a given Channel.
     * 
     * Returns null if user not subscribed on Channel.
     * 
     * @param username
     * @param numberHottest
     * @param channel_id
     * @return
     */
    public Collection getHottestItemsForUserByChannel(String username,
            int numberHottest, int channel_id);

    /**
     * Returns a number=numberhottest Items for a given User in his/her
     * Category.
     * 
     * @param username
     * @param category_id
     * @param numberHottest
     * @return
     */
    public Collection getHottestItemsForUserByCategory(String username,
            int category_id, int numberHottest);

    /**
     * Returns Item for a given User in a subscribed Channel since DATE.
     * 
     * @param username
     * @param channel_id
     * @param date
     * @return
     */
    public Collection getItemsForUserOnChannelSince(String username,
            int channel_id, java.util.Date since);

    /**
     * Returns Items for a given User in given Category found at/after Date
     * since.
     * 
     * @param username
     * @param category_id
     * @param since
     * @return
     */
    public Collection getItemsForUserByCategorySince(String username,
            int category_id, java.util.Date since);
    
    /**
     * Gets a FeedSubscriber by id.
     * @param feedsubscriber_id
     * @return
     */
    public FeedSubscriberIF getFeedSubscriber (int feedsubscriber_id);
}