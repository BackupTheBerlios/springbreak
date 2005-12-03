package at.newsagg.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.search.RssDbIndexer;
import at.newsagg.service.IndexFeeds;

/**
 * Creates a lucene index over rss feeds
 * 
 * @author sr
 * @version $Id: IndexFeedsImpl.java,v 1.3 2005/12/03 00:23:03 szabolcs Exp $
 *  
 */
public class IndexFeedsImpl  implements IndexFeeds {
	private static Log log = LogFactory.getLog(IndexFeedsImpl.class);
	private RssDbIndexer rssDbIndexer;
	
	public void indexFeeds () {
		this.run();
	}
	
	public void run() {
        log.info("### starting indexing process");
        
        rssDbIndexer.build();
    }

	public RssDbIndexer getRssDbIndexer() {
		return rssDbIndexer;
	}

	public void setRssDbIndexer(RssDbIndexer rssDbIndexer) {
		this.rssDbIndexer = rssDbIndexer;
	}
	
	
}