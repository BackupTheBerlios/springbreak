package at.newsagg.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.service.IndexFeeds;

/**
 * Creates a lucene index over rss feeds
 * 
 * @author sr
 * @version $Id: IndexFeedsImpl.java,v 1.2 2005/11/30 20:42:45 szabolcs Exp $
 *  
 */
public class IndexFeedsImpl  implements IndexFeeds {
	private static Log log = LogFactory.getLog(IndexFeedsImpl.class);
	
	public void indexFeeds () {
		this.run();
	}
	
	public void run() {
        log.info("starting indexing process");
    }
}