package at.newsagg.search;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.dao.ItemDAO;
import at.newsagg.model.FeedSubscriber;
import at.newsagg.model.User;
import at.newsagg.model.parser.hibernate.Item;

/**
 * Creates a full text index over the stored rss feeds located in the database
 * 
 * @author sr
 *
 */
public class RssDbIndexer {
	private static Log log = LogFactory.getLog(RssDbIndexer.class);
    private ItemDAO itemDao;
    private FeedSubscriberDAO feedSubscriberDAO;
    private File index;
    private String indexLocation;
    private boolean indexCreated = false;
    private Date lastIndexUpdate;
    private int numberOfIndexedItems;
    
    /**
     * Creates Index over all rss feeds. 
     * 
     * @param indexLocation indexlocation e:/tmp
     */
    public void build(String indexLocation) {
    	this.setIndexLocation(indexLocation);
        this.buildIndex();
    }
    
    /**
     * Creates Index over all rss feeds. Takes standard path as 
     * Lucene index location
     */
    public void build() {
    	this.buildIndex();
    }
    
    /**
     * Creates Index over all rss feeds
     */
    private void buildIndex() {
    	 if (indexLocation != null || !indexLocation.equals("")) {
             index = new File(indexLocation);
             List items = (List)itemDao.getAllItems();
             log.debug("### items empty? " + items);
             try {
                 final IndexWriter writer = new IndexWriter(indexLocation, new StandardAnalyzer(), true);
                 
                 for (Iterator iter = items.iterator(); iter.hasNext(); ) { 
                     Item rssItem = (Item) iter.next();
                     Document doc = new Document();
                     doc.add(Field.Text("id",  Long.toString(rssItem.getId())));
                     doc.add(Field.Text("title", rssItem.getTitle()));
                     doc.add(Field.Text("description", rssItem.getTitle()));
                     writer.addDocument(doc);
                     indexCreated = true;
                 }
                 this.lastIndexUpdate = new Date(System.currentTimeMillis());
                 this.numberOfIndexedItems = writer.docCount();
                 log.debug("#### docCount=" + writer.docCount());
                 writer.optimize();
                 writer.close();
             }
             catch (IOException e) {
                 e.printStackTrace();
             }
         }
    }
    
    /**
     * Search whole index for something
     * 
     * @param search SearchString
     * @param numberOfResults 
     */
    public Vector searchAllRssItems(String search, int numberOfResults) {
    	log.debug("#### searchQuery=" + search);
    	log.debug("### numberOfResults " + numberOfResults);
    	
    	try {
	    	Directory fsDir = FSDirectory.getDirectory(index, false);
	    	IndexSearcher is = new IndexSearcher(fsDir);
	    	
	    	Query query = QueryParser.parse(search.trim(), "description", new StandardAnalyzer());
	    	
	    	long start = new Date().getTime();
	    	Hits hits = is.search(query);
	    	long end = new Date().getTime();
	    	
	    	Vector rssItems = new Vector();
	    	log.debug("#### hits.length() " + hits.length());
	    	for (int i = 0; i < hits.length(); i++) {
	    		Document doc = hits.doc(i);
	    		log.debug("#### found object id = " + doc.get("id"));
	    		
	    		// retrieve Item by Id from database
	    		rssItems.add(itemDao.getItem(Integer.parseInt(doc.get("id"))));
	    	}
	    	
	    	return rssItems;
	    	
    	} catch (IOException e) {
    		 e.printStackTrace();
    	} catch (ParseException e) {
    		 e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * Search whole index for something
     * 
     * @param search SearchString
     * @param numberOfResults 
     */
    public Vector searchRssItemsByUserSubscription(String search, int numberOfResults, User user) {
    	log.debug("#### searchQuery=" + search);
    	log.debug("### numberOfResults " + numberOfResults);
    	log.debug("### user " + user.getUsername());
    	
    	try {
	    	Directory fsDir = FSDirectory.getDirectory(index, false);
	    	IndexSearcher is = new IndexSearcher(fsDir);
	    	
	    	Query query = QueryParser.parse(search.trim(), "description", new StandardAnalyzer());
	    	
	    	long start = new Date().getTime();
	    	Hits hits = is.search(query);
	    	long end = new Date().getTime();
	    	
	    	Vector rssItems = new Vector();
	    	Collection subscribedFeeds = feedSubscriberDAO.getFeedSubscriberByUser(user.getUsername());
	    	
	    	log.debug("#### hits.length() " + hits.length());
	    	for (int i = 0; i < hits.length(); i++) {
	    		Document doc = hits.doc(i);
	    		log.debug("#### found object id = " + doc.get("id"));
	    		
	    		// retrieve Item by Id from database
	    		Item item = itemDao.getItem(Integer.parseInt(doc.get("id")));
	    		
	    		// check if this object has been subscribed by the user
	    		
	    		for (Iterator iter = subscribedFeeds.iterator(); iter.hasNext(); ) { 
	    			FeedSubscriber feedSubscriber = (FeedSubscriber) iter.next();
	    			
	    			// if item is subscribed then add to collection
	    			if ( feedSubscriber.getChannel().getId() == item.getChannel().getId() ) {
	    				rssItems.add(item);
	    			}
	    		}
	    	}
	    	
	    	return rssItems;
	    	
    	} catch (IOException e) {
    		 e.printStackTrace();
    	} catch (ParseException e) {
    		 e.printStackTrace();
    	}
    	return null;
    }

    public ItemDAO getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDAO itemDao) {
        this.itemDao = itemDao;
    }

    public String getIndexLocation() {
        return indexLocation;
    }

    public void setIndexLocation(String indexLocation) {
        this.indexLocation = indexLocation;
    }

	public FeedSubscriberDAO getFeedSubscriberDAO() {
		return feedSubscriberDAO;
	}

	public void setFeedSubscriberDAO(FeedSubscriberDAO feedSubscriberDAO) {
		this.feedSubscriberDAO = feedSubscriberDAO;
	}

	public boolean isIndexCreated() {
		return indexCreated;
	}

	public void setIndexCreated(boolean indexCreated) {
		this.indexCreated = indexCreated;
	}

	public Date getLastIndexUpdate() {
		return lastIndexUpdate;
	}

	public void setLastIndexUpdate(Date lastIndexUpdate) {
		this.lastIndexUpdate = lastIndexUpdate;
	}

	public int getNumberOfIndexedItems() {
		return numberOfIndexedItems;
	}

	public void setNumberOfIndexedItems(int numberOfIndexedItems) {
		this.numberOfIndexedItems = numberOfIndexedItems;
	}
    
    
 
    
}