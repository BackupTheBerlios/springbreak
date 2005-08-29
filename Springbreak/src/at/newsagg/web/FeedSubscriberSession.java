/*
 * Created on 29.08.2005
 * king
 * 
 */
package at.newsagg.web;

import java.util.Collection;

/**
 * @author king
 * @version
 * created on 29.08.2005 14:52:28
 *
 */
public class FeedSubscriberSession {
    private Collection feedSubscribers;
    
    
    /**
     * @return Returns the feedSubscriber.
     */
    public Collection getFeedSubscribers() {
        return feedSubscribers;
    }
    /**
     * @param feedSubscriber The feedSubscriber to set.
     */
    public void setFeedSubscribers(Collection feedSubscribers) {
        this.feedSubscribers = feedSubscribers;
    }
}
