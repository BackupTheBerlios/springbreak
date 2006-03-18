package at.generic.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * @author szabolcs
 * @version $Id: IndexingService.java,v 1.6 2006/03/18 15:24:09 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.6 $
 * 
 * Fulltext index service
 * 
 */
public interface IndexingService {

	/**
     * Adds a document to the index either using batch mode or single mode
     * 
     * @param key
     * @param text
     * @param type
     * @param date
     */
    public void addDocument (String key, String text, String type, String date);
	
	public void removeDocument(String key);
	
	/**
	 * Search whole index for something
	 * 
	 * @param search SearchString
     * @param numberOfResults 
     * @param page
     * @param lowerBound
     * @param upperBound
     * @param filterNames
     * @return vector
	 */
	 public Vector search(String search, int numberOfResults, int page, String lowerBound, String upperBound, List filterNames);
	
	/**
     * Search whole index using a list of wids to filter for
     * 
     * @param search
     * @param numberOfResults
     * @param widList
     * @param lowerBound
     * @param upperBound
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
	 
	/**
	 * Opens an IndexWriter for a Batch index operation on the Index
	 */
	public void startBatchInsert();
	
	/**
	 * Stops a batch index operation 
	 */
	public void stopBatchInsert();
	
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
	
	/**
	 * @return Returns the numberOfFoundCorrEvents.
	 */
	public int getNumberOfFoundCorrEvents();

	/**
	 * @param numberOfFoundCorrEvents The numberOfFoundCorrEvents to set.
	 */
	public void setNumberOfFoundCorrEvents(int numberOfFoundCorrEvents);
	
	/**
	 * @return Returns the foundEventtypes.
	 */
	public HashMap getFoundEventtypes();

	/**
	 * @param foundEventtypes The foundEventtypes to set.
	 */
	public void setFoundEventtypes(HashMap foundEventtypes);
	
	/**
	 * @return Returns the indexLocationType.
	 */
	public String getIndexLocationType();

	/**
	 * @param indexLocationType The indexLocationType to set.
	 */
	public void setIndexLocationType(String indexLocationType);
	
	/**
	 * @return Returns the maxNumberOfLookAhead.
	 */
	public int getMaxNumberOfLookAhead();

	/**
	 * @param maxNumberOfLookAhead The maxNumberOfLookAhead to set.
	 */
	public void setMaxNumberOfLookAhead(int maxNumberOfLookAhead);
}