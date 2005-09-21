package at.newsagg.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.dao.CategoryDAO;
import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.dao.UserDAO;
import at.newsagg.model.Category;
import at.newsagg.model.FeedSubscriber;
import at.newsagg.model.User;
import at.newsagg.model.parser.ParseException;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.service.ParserCronJobService;
import at.newsagg.service.UserManager;

public class UserManagerImpl implements UserManager {
	private static Log log = LogFactory.getLog(UserManagerImpl.class);

	private UserDAO dao;

	private CategoryDAO catDAO;

	private ChannelDAO channelDAO;

	private FeedSubscriberDAO feedSubscriberDAO;

	private ParserCronJobService parserService;


	public List getUsers() {
		return dao.getUsers();
	}

	public User getUser(String username) {
		User user = dao.getUser(username);

		if (user == null) {
			log.warn("Username '" + username + "' not found in database.");
		}

		return user;
	}

	public User checkUser(String username) {
		User user = dao.checkUser(username);

		if (user == null) {
			log.warn("Username '" + username + "' not found in database.");
		}

		return user;
	}

	public User saveUser(User user) {
		dao.saveUser(user);
		return user;
	}

	/**
	 * Add Category and Feeds to new User.
	 * 
	 * @param user 
	 * @param cat add this cat to user
	 * @param StandardFeeds add this Collection of RSS-channels to the users category cat 
	 */
	public void setupNewUser(User user, Category cat, Collection StandardFeeds) {

		ArrayList feeds = new ArrayList(StandardFeeds.size());
		Channel channel;

		Iterator i = StandardFeeds.iterator();
		while (i.hasNext()) {
			channel = new Channel();
			try {
				channel.setLocationString((String) i.next());
			} catch (MalformedURLException e) {
				// nothing
			}
			feeds.add(channel);
		}

		// make sure category and user are "connected"
		if (!cat.getUser().equals(user))
			cat.setUser(user);

		this.catDAO.saveCategory(cat);

		// try if we have to persist channels into db
		Channel c;
		for (i = feeds.iterator(); i.hasNext();) {
			c = (Channel) i.next();
			try {
				c = channelDAO.getChannel(c.getLocation());
			}
			// if channel not already in db
			catch (IndexOutOfBoundsException e) {
				try {
					c.setItems(new ArrayList());
					// update it
					c = (Channel) parserService.runUpdateOnChannel(c);
				} catch (IOException e1) {

				} catch (ParseException e1) {

				}
				channelDAO.saveOrUpdateChannel(c);
			}
			FeedSubscriber fs = new FeedSubscriber();

			fs.setCategory(cat);
			fs.setChannel(c);
			fs.setUser(user);
			// add that this user subscribes this channel in this category
			
			this.feedSubscriberDAO.saveFeedSubscriber(fs);

		}
	}

	public User updateUser(User user) {
		dao.updateUser(user);
		return user;
	}

	public void removeUser(String username) {
		dao.removeUser(username);
	}

	public ParserCronJobService getParserService() {
		return parserService;
	}

	public void setParserService(ParserCronJobService parserService) {
		this.parserService = parserService;
	}

	public CategoryDAO getCatDAO() {
		return catDAO;
	}

	public void setCatDAO(CategoryDAO catDAO) {
		this.catDAO = catDAO;
	}

	public ChannelDAO getChannelDAO() {
		return channelDAO;
	}

	public void setChannelDAO(ChannelDAO channelDAO) {
		this.channelDAO = channelDAO;
	}

	public void setUserDAO(UserDAO dao) {
		this.dao = dao;
	}
	

	public FeedSubscriberDAO getFeedSubscriberDAO() {
		return feedSubscriberDAO;
	}

	public void setFeedSubscriberDAO(FeedSubscriberDAO feedSubscriberDAO) {
		this.feedSubscriberDAO = feedSubscriberDAO;
	}
}