package at.generic.search.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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
import org.apache.lucene.search.RangeFilter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import at.generic.search.lucene.ChainedFilter;
import at.generic.search.lucene.TermsFilter;
import at.generic.service.IndexingService;

/**
 * @author szabolcs
 * @version $Id: LuceneIndexingImpl.java,v 1.7 2006/03/18 15:24:09 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.7 $
 * 
 * Fulltext index service
 * 
 */
public class LuceneIndexingImpl implements IndexingService {
	private static Log log = LogFactory.getLog(LuceneIndexingImpl.class);
	
	private String indexLocation;
	private String indexLocationType; // event or correvent - used to determine on which index location this instance is pointing 
	private int numberOfIndexedIems = 0;
	private boolean indexCreated = false;
	private final Analyzer analyzer = new StandardAnalyzer();
	private IndexWriter indexWriter;
	private IndexSearcher indexSearcher;
	private boolean init = false;
	private int numberOfFoundCorrEvents;
	private HashMap foundEventtypes = new HashMap();
	private int maxNumberOfLookAhead = 0;	// determines how far the search should look ahead to determine the Eventtype or Correlationtype  
	
	/**
	 * Tries to create an index at the given location
	 *
	 */
	public void init() {
		if (init == false) {
			try {
				IndexWriter writer = new IndexWriter(indexLocation, analyzer, false);
				numberOfIndexedIems = writer.docCount();
				indexCreated = true;
				init = true;
				writer.close();
				writer = null;
			} catch (IOException e1) {
			    log.info("Error opening index location " + indexLocation);
			    try {
			    	log.info("Trying to create an index at location " + indexLocation);
			    	this.createIndexLocation();
			    } catch (IOException e2) {
			    	log.error("Giving up creating that damn lucene index at " + indexLocation, e2 );
			    }
			} 
		}
	}
	
	/**
	 * Creates an index at given location
	 * 
	 * @throws IOException
	 */
	private void createIndexLocation() throws IOException {
		IndexWriter writer = null;
		
		writer = new IndexWriter(indexLocation, analyzer, true);
		
		numberOfIndexedIems = writer.docCount();
		indexCreated = true;
		init = true;
		
	    writer.close();

	    writer = null;
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
	 * == Datefilter == p.96
	 * 
	 * using DateFormat.SHORT style YYYYMMDD
     * 
     * @param search SearchString
     * @param numberOfResults 
     * @param page
     * @param lowerBound
     * @param upperBound
     * @param filterNames
     */
    public Vector search(String search, int numberOfResults, int page, String lowerBound, String upperBound, List filterNames) {
    	//log.debug("#### searchQuery=" + search);
    	//log.debug("### numberOfResults " + numberOfResults);
    	
    	this.foundEventtypes = new HashMap();
    	
    	try {
    		if (indexSearcher == null) {
		    	Directory fsDir = FSDirectory.getDirectory(this.indexLocation, false);
    			indexSearcher = new IndexSearcher(fsDir);
    		}
    		
    		// build a filter
    		/*TermsFilter filter = new TermsFilter();
			filter.addTerm(new Term("date", "20060304 TO 20060304"));*/
    		Query query = QueryParser.parse(search.trim(), "text", new StandardAnalyzer());
    		Hits hits = null;
    		Filter dateFilter = null;
    		TermsFilter profileFilter = null;
    		if (lowerBound != null && upperBound != null && lowerBound.length() > 0 && upperBound.length() > 0) {
    			dateFilter = new RangeFilter("date", lowerBound, upperBound, true, true);
    			//hits = indexSearcher.search(query, filter);
    		} 
    		
    		if (filterNames != null && filterNames.size() > 0) {
    			profileFilter = new TermsFilter();
    	    	Iterator nameIt = filterNames.iterator();
    			while (nameIt.hasNext()) {
    				String name = (String)nameIt.next();
    				log.debug("### profileFilter addTerm:" + name);
    				profileFilter.addTerm(new Term("type", name)); 
    			} 
    		}
    		
    		if (dateFilter != null && profileFilter == null) {
    			log.debug("### only dateFilter");
    			hits = indexSearcher.search(query, dateFilter);
    		} else if (dateFilter == null && profileFilter != null) {
    			log.debug("### only profileFilter");
    			hits = indexSearcher.search(query, profileFilter);
    		} else if (dateFilter != null && profileFilter != null) {
    			log.debug("### chainFilter");
    			ChainedFilter chain = new ChainedFilter(new Filter[] {dateFilter, profileFilter}, ChainedFilter.AND); 
    			hits = indexSearcher.search(query, chain);
    		} else {
    			log.debug("### no filter");
    			hits = indexSearcher.search(query);
    		}
	    	
	    	this.numberOfFoundCorrEvents =  hits.length();
	    	
	    	log.debug("### page * numberOfResults - numberOfResults:" + (page * numberOfResults - numberOfResults));
	    	log.debug("### hits.length():" + hits.length());
	    	// if the chosen page exceeds the hit numbers just do nothing
	    	if (!(page * numberOfResults - numberOfResults  > hits.length())) {
		    	Vector items = new Vector();
		    	//log.debug("#### hits.length() " + hits.length());
		    	
		    	
		    	int start = page * numberOfResults - numberOfResults;
		    	int maxHitSize = 0;
		    	if (page * numberOfResults >= hits.length())
		    		maxHitSize = hits.length();
		    	else 
		    		maxHitSize = page * numberOfResults;
		    	
		    	int stopRetrieving = 0;
		    	if (this.maxNumberOfLookAhead >= hits.length())
		    		stopRetrieving = hits.length();
		    	else
		    		stopRetrieving = this.maxNumberOfLookAhead;
		    	
		    	log.debug("### start:" + start);
		    	log.debug("### maxHitSize:" + maxHitSize);
		    	log.debug("### maxNumberOfLookAhead:" + maxNumberOfLookAhead);
		    	log.debug("### stoptrieving:" + stopRetrieving);
		    	log.debug("### search:" + search);
		    	
		    	//for (; start < maxHitSize; start++) {
		    	for (int i = 0; i < stopRetrieving; i++) {
		    		Document doc = hits.doc(i);
		    		//log.debug("#### found object wid = " + doc.get("wid"));
		    		//log.debug("#### found object type = " + doc.get("type"));
		    		//log.debug("#### found object text = " + doc.get("text"));
		    		
		    		// just add results according to page
		    		if (start == i && start < maxHitSize) {
		    			items.add(doc.get("wid"));
		    			start++;
		    		}
		    		
		    		log.debug("### doc.get('wid')" + doc.get("wid"));
		    		log.debug("### doc.get('type')" + doc.get("type"));
		    		log.debug("### doc.get('date')" + doc.get("date"));
		    		
		    		// if this is set on event index
		    		//if (this.indexLocationType.equals("event"))
		    		foundEventtypes.put(doc.get("type"), new Boolean(false));	
		    	}
		    	
		    	return items;
	    	}
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
    	//log.debug("#### searchQuery=" + search);
    	//log.debug("### numberOfResults " + numberOfResults);
    	
    	try {
    		if (indexSearcher == null) {
		    	Directory fsDir = FSDirectory.getDirectory(this.indexLocation, false);
    			indexSearcher = new IndexSearcher(fsDir);
    		}
	    	
	    	// build a filter
	    	TermsFilter filter = new TermsFilter();
	    	Iterator widIt = widList.iterator();
			while (widIt.hasNext()) {
				Long id = (Long)widIt.next();
				filter.addTerm(new Term("wid", id.toString())); 
			} 
	    	
	    	Query query = QueryParser.parse(search.trim(), "text", new StandardAnalyzer());

	    	Hits hits = indexSearcher.search(query, filter);
	    	
	    	Vector items = new Vector();
	    	//log.debug("#### hits.length() " + hits.length());
	    	
	    	int maxHitSize = 0;
	    	if (numberOfResults > hits.length()) 
	    		maxHitSize = hits.length();
	    	else 
	    		maxHitSize = numberOfResults;
	    	
	    	for (int i = 0; i < maxHitSize; i++) {
	    		Document doc = hits.doc(i);
	    		//log.debug("#### found object wid = " + doc.get("wid"));
	    		//log.debug("#### found object type = " + doc.get("type"));
	    		//log.debug("#### found object text = " + doc.get("text"));
	    		
	    		items.add(new Long(doc.get("wid")));
	    		foundEventtypes.put(doc.get("type"),doc.get("type"));
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
     * Adds a document to the index either using batch mode or single mode
     * 
     * @param key
     * @param text
     * @param type
     * @param date
     */
    public void addDocument (String key, String text, String type, String date) {
    	indexSearcher = null;
    	if (indexWriter == null)
    		this.addDocumentSingleStyle(key,text,type,date);
    	else
    		this.addDocumentBatchStyle(key,text,type);
    }
    
    /**
     * Adds a document to the index 
     * 
     * @param key
     * @param text
     * @param type
     */
	private void addDocumentSingleStyle(String key, String text, String type) {
		
		IndexWriter writer = null;
		
		try {
			removeDocument(key);
			writer = new IndexWriter(indexLocation, analyzer, false);
			
		    Document document = new Document();
		    document.add(Field.Keyword("wid", key));
		    document.add(Field.Keyword("type", type));
		    document.add(Field.Text("text",text));
		    
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
     * Adds a document to the index with a date field
     * 
     * @param key
     * @param text
     * @param type
     * @param date
     */
	private void addDocumentSingleStyle(String key, String text, String type, String date) {
		
		IndexWriter writer = null;
		
		try {
			removeDocument(key);
			writer = new IndexWriter(indexLocation, analyzer, false);
			
		    Document document = new Document();
		    document.add(Field.Keyword("wid", key));
		    document.add(Field.Keyword("type", type));
		    document.add(Field.Text("text",text));
		    document.add(Field.Text("date",date));
		    
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
     * Adds a document to the index in batch mode
     * 
     * @param key
     * @param text
     * @param type
     */
	private void addDocumentBatchStyle(String key, String text, String type) {
		try {
			removeDocument(key);
			
		    Document document = new Document();
		    document.add(Field.Keyword("wid", key));
		    document.add(Field.Keyword("type", type));
		    document.add(Field.Text("text",text));
		    
		    //log.debug("### creating index for wid=" + key + ", type=" + type + ", text=" + text);
		    //log.debug("### addDocument writer.docCount() " + indexWriter.docCount());
		    
		    indexWriter.addDocument(document);
		} catch (IOException e) {
		    log.error("Error updating index for wid=" + key + ", type=" + type , e);
		} finally {
		    closeWriter(indexWriter);
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
	 
	 /**
	  * Returns a Hashset extracting the search terms
	  * 
	  * @param search
	  * @return
	  */
	 public HashSet extractSearchTerms (String search) {
		 HashSet terms = new HashSet();
		 
		 try {
			 Directory fsDir = FSDirectory.getDirectory(this.indexLocation, false);
			 IndexSearcher is = new IndexSearcher(fsDir);
			 Query query = QueryParser.parse(search.trim(), "text", new StandardAnalyzer());
			 
			 terms = new HashSet(); 
			 // TODO: put back after date search works
			 query.extractTerms(terms);
		 } catch (IOException e) {
			 e.printStackTrace();
		 } catch (ParseException e) {
			 e.printStackTrace();
		 }
		 return terms;
	 }
	
	 /**
	  * Closes the given IndexWriter and calls optimize()
	  * @param writer
	  */
	private void closeWriter(IndexWriter writer) {
	    if (null != writer) {
	        try {
	        	writer.optimize();
	            writer.close();
	            writer = null;
	        } catch (IOException e) {
	            log.warn("Error while closing index writer", e);
	        }
	    }
	}
	
	/**
	 * Closes the given IndexReader
	 * 
	 * @param reader
	 */
	private void closeReader(IndexReader reader) {
        if (null != reader) {
            try {
                reader.close();
            } catch (IOException e) {
                log.warn("Error closing index reader for index " + indexLocation, e);
            }
        }
    }

	
	/**
	 * Opens an IndexWriter for a Batch index operation on the Index
	 */
	public void startBatchInsert() {
		try {
			indexWriter = new IndexWriter(indexLocation, analyzer, false);
		} catch (IOException e) {
		    log.error("Error creating/accessing index", e);
		}
	}
	
	/**
	 * Stops a batch index operation 
	 */
	public void stopBatchInsert() {
		numberOfIndexedIems = indexWriter.docCount();
		this.closeWriter(indexWriter);
	}
	
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

	/**
	 * @return Returns the numberOfFoundCorrEvents.
	 */
	public int getNumberOfFoundCorrEvents() {
		return numberOfFoundCorrEvents;
	}

	/**
	 * @param numberOfFoundCorrEvents The numberOfFoundCorrEvents to set.
	 */
	public void setNumberOfFoundCorrEvents(int numberOfFoundCorrEvents) {
		this.numberOfFoundCorrEvents = numberOfFoundCorrEvents;
	}

	/**
	 * @return Returns the foundEventtypes.
	 */
	public HashMap getFoundEventtypes() {
		return foundEventtypes;
	}

	/**
	 * @param foundEventtypes The foundEventtypes to set.
	 */
	public void setFoundEventtypes(HashMap foundEventtypes) {
		this.foundEventtypes = foundEventtypes;
	}

	/**
	 * @return Returns the indexLocationType.
	 */
	public String getIndexLocationType() {
		return indexLocationType;
	}

	/**
	 * @param indexLocationType The indexLocationType to set.
	 */
	public void setIndexLocationType(String indexLocationType) {
		this.indexLocationType = indexLocationType;
	}

	/**
	 * @return Returns the maxNumberOfLookAhead.
	 */
	public int getMaxNumberOfLookAhead() {
		return maxNumberOfLookAhead;
	}

	/**
	 * @param maxNumberOfLookAhead The maxNumberOfLookAhead to set.
	 */
	public void setMaxNumberOfLookAhead(int maxNumberOfLookAhead) {
		this.maxNumberOfLookAhead = maxNumberOfLookAhead;
	}
	
	
}