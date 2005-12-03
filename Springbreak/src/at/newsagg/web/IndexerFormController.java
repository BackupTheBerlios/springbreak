package at.newsagg.web; 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import at.newsagg.model.User;
import at.newsagg.search.RssDbIndexer;  
import at.newsagg.web.commandObj.IndexerFormCommand;
import at.newsagg.web.commandObj.UserFormCommand;

/**
 * 
 * @author sr
 *
 */
public class IndexerFormController  extends SimpleFormController { 
    private static Log log = LogFactory.getLog(IndexerFormController.class); 
    private RssDbIndexer rssDbIndexer = null; 
    
     
    public RssDbIndexer getRssDbIndexer() {
        return rssDbIndexer;
    }

    public void setRssDbIndexer(RssDbIndexer rssDbIndexer) {
        this.rssDbIndexer = rssDbIndexer;
    }

    public ModelAndView onSubmit(HttpServletRequest request, 
            HttpServletResponse response, 
            Object command, BindException errors) 
        throws Exception { 
        IndexerFormCommand indexerFormCmd = (IndexerFormCommand) command;
        String indexLocation = indexerFormCmd.getIndexLocation();
        
        if (indexLocation == null || indexLocation.equals("")) {
            errors.rejectValue("indexLocation", "please provide a valid value", null, getMessageSourceAccessor().getMessage("indexer.noIndexLocationPathProvided"));
            return showForm(request, response, errors);
        } 
        
        rssDbIndexer.build(indexLocation);
        
        
        return new ModelAndView(new RedirectView(getSuccessView())); 
    }
    
    protected Object formBackingObject(HttpServletRequest request) throws ServletException { 
		IndexerFormCommand indexerFormCmd = new IndexerFormCommand();
		indexerFormCmd.setIndexLocation(rssDbIndexer.getIndexLocation());
		if (rssDbIndexer.isIndexCreated() == true) {
			indexerFormCmd.setIndexCreated("true");
			indexerFormCmd.setLastIndexUpdate(rssDbIndexer.getLastIndexUpdate().toGMTString());
		} else {
			indexerFormCmd.setIndexCreated("false");
		}
		
		indexerFormCmd.setNumberOfIndexedItems(Integer.toString(rssDbIndexer.getNumberOfIndexedItems()));
		
		return indexerFormCmd;
		
	} 
    
}