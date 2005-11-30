package at.newsagg.service.impl;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.service.IndexFeeds;

/**
 * Creates a lucene index over rss feeds
 * 
 * @author sr
 * @version $Id: IndexFeedsImpl.java,v 1.1 2005/11/30 16:17:55 szabolcs Exp $
 *  
 */
public class IndexFeedsImpl extends TimerTask implements IndexFeeds {
	private static Log log = LogFactory.getLog(IndexFeedsImpl.class);
	
	public void indexFeeds () {
		
	}
	
	public void run() {
        log.info("starting indexing process");
    }
}