package at.newsagg.search;

import java.util.Vector;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer; 
import org.apache.lucene.search.Query;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Hits;

import at.newsagg.search.RssDbIndexer;
import at.newsagg.dao.ItemDAO;
import at.newsagg.model.parser.hibernate.Item;

/**
 * Creates a full text index over the stored rss feeds located in the database
 * 
 * @author sr
 *
 */
public class RssDbIndexer {
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
                try {
                    final IndexWriter writer = new IndexWriter(indexLocation, new StandardAnalyzer(), true);
                    
                    for (Iterator iter = items.iterator(); iter.hasNext(); ) { 
                        Item rssItem = (Item) iter.next();
                        //System.out.println("####### " + rssItem.getTitle());
                        Document doc = new Document();
                        doc.add(Field.Text("id",  Long.toString(rssItem.getId())));
                        doc.add(Field.Text("title", rssItem.getTitle()));
                        doc.add(Field.Text("description", rssItem.getTitle()));
                                writer.addDocument(doc);
                                indexCreated = true;
                    }
                    
                    writer.optimize();
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
    
    /**
     * Search index for something
     * 
     * @param search SearchString
     * @param numberOfResults 
     */
    public void search(String search, int numberOfResults) {
            System.out.println("#### search " + search);
            System.out.println("#### numberOfResults " + numberOfResults);
            // create query from searchstring
            Query query = null;
            try {
                query = QueryParser.parse(search, "description", new StandardAnalyzer());
                System.out.println("#### 1");
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("#### 2");
            // create List with ids to retrieve the rss db objects
            ArrayList ids = new ArrayList();
            try {
                System.out.println("#### 3");
                IndexReader reader = IndexReader.open(index);
                IndexSearcher searcher = new IndexSearcher(reader);
                Hits hits = searcher.search(query);
                System.out.println("#### 4");
                for (int i = 0; i != hits.length() && i != numberOfResults; ++i) {
                    System.out.println("#### 5");
                    Document doc = hits.doc(i);
                    ids.add(new Integer(doc.getField("id").stringValue()));
                }
                searcher.close();
                reader.close();
                System.out.println("#### 6 size " + ids.size());
                for (Iterator iter = ids.iterator(); iter.hasNext(); ) { 
                    Integer id = (Integer) iter.next();
                    System.out.println("#### " + id);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
            
            // create rss objects based on the resultset
            
        
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