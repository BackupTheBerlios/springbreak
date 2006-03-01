package at.generic.search.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import at.generic.search.lucene.TermsFilter;
import at.generic.service.IndexingService;

/**
 * @author szabolcs
 * @version $Id: LuceneIndexingImpl.java,v 1.2 2006/03/01 11:44:52 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Fulltext index service
 * 
 * TODO:
 * - create batch update for documents --> speed much better
 * - IndexSearcher is capable of caching if you create one instance!! p.177
 *   But you have to recreate the instance if the index changes. 
 *   A possible solution would be to make a batch update for bigger inserts
 *   and when closing the batch update to create a new instance of IndexSearcher.
 *   If a normal index update has been performed then the IndexSearch will be recreated.
 * - check out CachingWrappingFilter when using ChainedFilter p. 307 
 * 
 */
public class LuceneIndexingImpl implements IndexingService {
	private static Log log = LogFactory.getLog(LuceneIndexingImpl.class);
	
	private String indexLocation;
	private int numberOfIndexedIems = 0;
	private boolean indexCreated = false;
	private final Analyzer analyzer = new StandardAnalyzer();
	private IndexWriter writer;
	private boolean init = false;
	
	public void init() {
		if (init == false) {
			IndexWriter writer = null;
			try {
				writer = new IndexWriter(indexLocation, analyzer, false);
				
				numberOfIndexedIems = writer.docCount();
				indexCreated = true;
				init = true;
			} catch (IOException e) {
			    log.error("Error opening index location " + indexLocation, e);
			} finally {
			    closeWriter(writer);
			}
			
		}
	}
	
	 /**
     * Search whole index for something
     *
     * == Query syntax ==
     * 
     * Take a look at "Lucene in Action" p. 74 for the query syntax
     * 
     * == Score == p. 78
     * The Hits collection lends itself to environments where users are
     * presented with only the top few documents and typically don’t need more than
     * those because only the best-scoring hits are the desired documents.
     * 
     * == Paginiation  vs. Requerying ==
     * It turns out that requerying is most often the best solution. Requerying eliminates
     * the need to store per-user state. In a web application, staying stateless (no
     * HTTP session) is often desirable. Requerying at first glance seems a waste, but
     * Lucene’s blazing speed more than compensates.
	 * In order to requery, the original search is reexecuted and the results are displayed
	 * beginning on the desired page. How the original query is kept depends
	 * on your application architecture. In a web application where the user types in an
	 * expression that is parsed with QueryParser, the original expression could be
	 * made part of the hyperlinks for navigating the pages and reparsed for each
	 * request, or the expression could be kept in a hidden HTML field or as a cookie.
	 * Don’t prematurely optimize your paging implementations with caching or
	 * persistence. First implement your paging feature with a straightforward requery;
	 * chances are you’ll find this sufficient for your needs.
     * 
     * @param search SearchString
     * @param numberOfResults 
     */
    public Vector search(String search, int numberOfResults) {
    	log.debug("#### searchQuery=" + search);
    	log.debug("### numberOfResults " + numberOfResults);
    	
    	try {
	    	Directory fsDir = FSDirectory.getDirectory(this.indexLocation, false);
	    	IndexSearcher is = new IndexSearcher(fsDir);
	    	Query query = QueryParser.parse(search.trim(), "text", new StandardAnalyzer());

	    	Hits hits = is.search(query);
	    	
	    	Vector items = new Vector();
	    	log.debug("#### hits.length() " + hits.length());
	    	
	    	int maxHitSize = 0;
	    	if (numberOfResults > hits.length()) 
	    		maxHitSize = hits.length();
	    	else 
	    		maxHitSize = numberOfResults;
	    			
	    	//for (int i = 0; i < hits.length(); i++) {
	    	for (int i = 0; i < maxHitSize; i++) {
	    		Document doc = hits.doc(i);
	    		log.debug("#### found object wid = " + doc.get("wid"));
	    		log.debug("#### found object type = " + doc.get("type"));
	    		log.debug("#### found object text = " + doc.get("text"));
	    		
	    		items.add(doc.get("wid"));
	    	}
	    	
	    	return items;
	    	
    	} catch (IOException e) {
    		 e.printStackTrace();
    	} catch (ParseException e) {
    		 e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * Search whole index using a list of wids to filter for
     * 
     * @param search
     * @param numberOfResults
     * @param widList
     * @return
     */
    public Vector search(String search, int numberOfResults, List widList) {
    	log.debug("#### searchQuery=" + search);
    	log.debug("### numberOfResults " + numberOfResults);
    	
    	try {
	    	Directory fsDir = FSDirectory.getDirectory(this.indexLocation, false);
	    	IndexSearcher is = new IndexSearcher(fsDir);
	    	
	    	// build a filter
	    	TermsFilter filter = new TermsFilter();
	    	Iterator widIt = widList.iterator();
			while (widIt.hasNext()) {
				String id = (String)widIt.next();
				filter.addTerm(new Term("wid", id)); 
			} 
	    	
	    	
	    	Query query = QueryParser.parse(search.trim(), "text", new StandardAnalyzer());

	    	Hits hits = is.search(query, filter);
	    	
	    	Vector items = new Vector();
	    	log.debug("#### hits.length() " + hits.length());
	    	
	    	int maxHitSize = 0;
	    	if (numberOfResults > hits.length()) 
	    		maxHitSize = hits.length();
	    	else 
	    		maxHitSize = numberOfResults;
	    			
	    	//for (int i = 0; i < hits.length(); i++) {
	    	for (int i = 0; i < maxHitSize; i++) {
	    		Document doc = hits.doc(i);
	    		log.debug("#### found object wid = " + doc.get("wid"));
	    		log.debug("#### found object type = " + doc.get("type"));
	    		log.debug("#### found object text = " + doc.get("text"));
	    		
	    		items.add(doc.get("wid"));
	    	}
	    	
	    	return items;
	    	
    	} catch (IOException e) {
    		 e.printStackTrace();
    	} catch (ParseException e) {
    		 e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * Adds a document to the index 
     * 
     * @param key
     * @param text
     * @param type
     */
	public void addDocument(String key, String text, String type) {
		IndexWriter writer = null;
		try {
			if (indexCreated == false) {
				writer = new IndexWriter(indexLocation, analyzer, true);
				indexCreated = true;
			} else {
				removeDocument(key);
				writer = new IndexWriter(indexLocation, analyzer, false);
			}
		    Document document = new Document();
		    document.add(Field.Keyword("wid", key));
		    document.add(Field.Keyword("type", type));
		    document.add(Field.Text("text",text));
		    
		    log.debug("### creating index for wid=" + key + ", type=" + type + ", text=" + text);
		    
		    log.debug("### addDocument writer.docCount() " + writer.docCount());
		    
		    writer.optimize();
		    writer.addDocument(document);
		    numberOfIndexedIems = writer.docCount();
		} catch (IOException e) {
		    log.error("Error updating index for wid=" + key + ", type=" + type , e);
		} finally {
		    closeWriter(writer);
		}
	}
	
	/**
	 * Removes a document from index 
	 * 
	 * @param key
	 */
	 public void removeDocument(String key) {
        IndexReader reader = null;
        try {
            reader = IndexReader.open(indexLocation);
            int numDeleted = reader.delete(new Term("wid", key));
            if (0 < numDeleted) {
                log.info("Removed " + numDeleted + " documents from index " + indexLocation);
            }
        } catch (IOException e) {
            log.error("Error removing documents for " + key + " from index " + indexLocation, e);
        } finally {
            closeReader(reader);
        }
    }
	
	private void closeWriter(IndexWriter writer) {
	    if (null != writer) {
	        try {
	            writer.close();
	        } catch (IOException e) {
	            log.warn("Error while closing index writer", e);
	        }
	    }
	}
	
	private void closeReader(IndexReader reader) {
        if (null != reader) {
            try {
                reader.close();
            } catch (IOException e) {
                log.warn("Error closing index reader for index " + indexLocation, e);
            }
        }
    }

	
	/*public void startBatchInsert() {
	try {
		if (indexCreated == false) {
			writer = new IndexWriter(indexLocation, analyzer, true);
			indexCreated = true;
		} else {
			writer = new IndexWriter(indexLocation, analyzer, false);
		}
	} catch (IOException e) {
	    log.error("Error creating/accessing index", e);
	}
	}
	
	public void stopBatchInsert() {
		closeWriter(writer);
	}*/
	
	/*public void addDocument(String key, String text, String type) {
		try {
		    Document document = new Document();
		    document.add(Field.Keyword("wid", key));
		    document.add(Field.Keyword("type", type));
		    document.add(Field.Text("text",text));
		    
		    log.debug("### creating index for wid=" + key + ", type=" + type + ", text=" + text);
		    
		    writer.addDocument(document);
		} catch (IOException e) {
		    log.error("Error updating index for wid=" + key + ", type=" + type , e);
		} 
	
	}*/
	
	// ================ Getters and Setters ==================
	
	/**
	 * @return Returns the indexCreated.
	 */
	public boolean isIndexCreated() {
		return indexCreated;
	}

	/**
	 * @param indexCreated The indexCreated to set.
	 */
	public void setIndexCreated(boolean indexCreated) {
		this.indexCreated = indexCreated;
	}

	/**
	 * @return Returns the indexLocation.
	 */
	public String getIndexLocation() {
		return indexLocation;
	}

	/**
	 * @param indexLocation The indexLocation to set.
	 */
	public void setIndexLocation(String indexLocation) {
		this.indexLocation = indexLocation;
	}

	/**
	 * @return Returns the numberOfIndexedIems.
	 */
	public int getNumberOfIndexedIems() {
		return numberOfIndexedIems;
	}

	/**
	 * @param numberOfIndexedIems The numberOfIndexedIems to set.
	 */
	public void setNumberOfIndexedIems(int numberOfIndexedIems) {
		this.numberOfIndexedIems = numberOfIndexedIems;
	}
}