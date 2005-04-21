/*
 * Created on 30.03.2005
 * king
 * 
 */
package at.newsagg.service;

import at.newsagg.model.FeedSubscriberIF;

/**
 * @author king
 * @version
 * created on 30.03.2005 12:28:53
 *
 */
public interface SubscriberManager {
    /**
     * 
     * @param username
     * @param channel_id
     * @param category_id
     * @return
     */
    public FeedSubscriberIF addFeedSubscriber(String username, int channel_id,
            int category_id);

    /**
     * Deletes a FeedSubscriber.
     * 
     * @param feedsubscriber_id
     */
    public void deleteFeedSubscriber(int feedsubscriber_id);

    /**
     * Updates the Category of a FeedSubscriber.
     * 
     * @param feedsubscriber_id
     * @param category_id
     * @return
     */
    public FeedSubscriberIF changeFeedSubscriberCategory(int feedsubscriber_id,
            int category_id);
}