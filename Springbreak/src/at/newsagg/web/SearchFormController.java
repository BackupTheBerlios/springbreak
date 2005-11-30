package at.newsagg.web; 

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import at.newsagg.search.RssDbIndexer;
import at.newsagg.web.commandObj.SearchFormCommand;

/**
 * 
 * @author sr
 *
 */
public class SearchFormController  extends SimpleFormController { 
    private static Log log = LogFactory.getLog(SearchFormController.class); 
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
        SearchFormCommand searchFormCmd = (SearchFormCommand) command;
        String search = searchFormCmd.getSearch();
        
        if (search == null || search.equals("")) {
            return showForm(request, response, errors);
        } 
        
        //return new ModelAndView(new RedirectView(getSuccessView()), "rssItems", rssDbIndexer.searchAllRssItems(search, 100));
        UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
        Vector rssItems = rssDbIndexer.searchRssItemsByUserSubscription(search, 100, userSession.getUserData());
        return new ModelAndView("searchRssFeeds", "rssItems", rssItems);
   } 
    
}