package at.newsagg.web.commandObj;

import at.newsagg.model.User;

/**
 * @author Szabolcs Rozsnyai
 * $Id: IndexerFormCommand.java,v 1.2 2005/12/03 00:23:03 szabolcs Exp $
 */

public class IndexerFormCommand {
    private String indexLocation;
    private String indexCreated;
    private String lastIndexUpdate;
    private String numberOfIndexedItems;
    
	public String getIndexCreated() {
		return indexCreated;
	}
	public void setIndexCreated(String indexCreated) {
		this.indexCreated = indexCreated;
	}
	public String getIndexLocation() {
		return indexLocation;
	}
	public void setIndexLocation(String indexLocation) {
		this.indexLocation = indexLocation;
	}
	public String getLastIndexUpdate() {
		return lastIndexUpdate;
	}
	public void setLastIndexUpdate(String lastIndexUpdate) {
		this.lastIndexUpdate = lastIndexUpdate;
	}
	public String getNumberOfIndexedItems() {
		return numberOfIndexedItems;
	}
	public void setNumberOfIndexedItems(String numberOfIndexedItems) {
		this.numberOfIndexedItems = numberOfIndexedItems;
	}



	
    
    
     
}