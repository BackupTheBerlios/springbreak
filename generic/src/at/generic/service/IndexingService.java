package at.generic.service;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * @author szabolcs
 * @version $Id: IndexingService.java,v 1.3 2006/03/03 15:25:32 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.3 $
 * 
 * Fulltext index service
 * 
 */
public interface IndexingService {

	public void addDocument(String key, String text, String type);
	
	public void removeDocument(String key);
	
	/**
	 * Search whole index for something
	 * 
	 * @param search
	 * @param numberOfResults
	 * @return
	 */
	public Vector search(String search, int numberOfResults);
	
	/**
     * Search whole index using a list of wids to filter for
     * 
     * @param search
     * @param numberOfResults
     * @param widList
     * @return
     */
    public Vector search(String search, int numberOfResults, List widList);
    
    /**
	  * Returns a Hashset extracting the search terms
	  * 
	  * @param search
	  * @return
	  */
	 public HashSet extractSearchTerms (String search);
	
	// ================ Getters and Setters ==================
	
	/**
	 * @return Returns the indexCreated.
	 */
	public boolean isIndexCreated();

	/**
	 * @param indexCreated The indexCreated to set.
	 */
	public void setIndexCreated(boolean indexCreated);
	
	/**
	 * @return Returns the indexLocation.
	 */
	public String getIndexLocation();

	/**
	 * @param indexLocation The indexLocation to set.
	 */
	public void setIndexLocation(String indexLocation);

	/**
	 * @return Returns the numberOfIndexedIems.
	 */
	public int getNumberOfIndexedIems();

	/**
	 * @param numberOfIndexedIems The numberOfIndexedIems to set.
	 */
	public void setNumberOfIndexedIems(int numberOfIndexedIems);
}