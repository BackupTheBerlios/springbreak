package at.generic.service;

import java.util.Properties;
import java.util.Vector;

/**
 * @author szabolcs
 * @version $Id: IndexingService.java,v 1.1 2006/02/27 14:59:03 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Fulltext index service
 * 
 */
public interface IndexingService {

	public void addDocument(String key, String text, String type);
	
	public void removeDocument(String key);
	
	public Vector search(String search, int numberOfResults);
	
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