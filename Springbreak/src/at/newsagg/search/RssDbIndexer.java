package at.newsagg.search;

import java.io.File;
import java.io.IOException;
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

import at.newsagg.dao.ItemDAO;
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
    private String indexLocation;
    private File index;
    private boolean indexCreated = false;
    
    /**
     * creates Index over all rss feeds
     * 
     * @param indexLocation indexlocation e:/tmp
     */
    public void build(String indexLocation) {
            if (indexLocation != null || !indexLocation.equals("")) {
                index = new File(indexLocation);
                this.setIndexLocation(indexLocation);
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
    public Vector searchAllRssItems(String search, int numberOfResults, User user) {
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
    
    
 
    
}