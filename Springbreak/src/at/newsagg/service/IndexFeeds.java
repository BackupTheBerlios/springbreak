package at.newsagg.service;

import java.io.IOException;
import java.util.TimerTask;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.ItemDAO;
import at.newsagg.model.parser.ChannelIF;
import at.newsagg.model.parser.ParseException;
import at.newsagg.parser.FeedParser;

/**
 * Interface for creating a Lucene index over rss feeds
 * 
 * @author sr
 * @version $Id: IndexFeeds.java,v 1.1 2005/11/30 16:17:55 szabolcs Exp $
 */
public interface IndexFeeds {
	public void indexFeeds();
}