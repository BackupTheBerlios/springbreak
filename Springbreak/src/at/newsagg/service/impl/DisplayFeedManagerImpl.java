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

/**
 * Handles selecting feeds from DB with given Criteria.
 * 
 * @author Roland Vecera.
 * @version
 * created on 30.03.2005 12:29:47
 *
 */
public class DisplayFeedManagerImpl {
    private static Log log = LogFactory.getLog(DisplayFeedManagerImpl.class); 
	private FeedSubscriberDAO feedDAO;
	private UserDAO userDAO;
	private ChannelDAO channelDAO;
	private CategoryDAO catDAO;
    
   
	
}
