/*
 * Created on 17.09.2005
 * king
 * 
 */
package at.newsagg.web.feed;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.CategoryDAO;
import at.newsagg.model.FeedSubscriber;
import at.newsagg.model.User;
import at.newsagg.web.UserSession;

/**
 * @author Roland Vecera
 * @version
 * created on 17.09.2005 17:13:09
 *
 */
public class DeleteCategoryController extends MultiActionController {

    CategoryDAO categoryDAO;
    
    private static Log log = LogFactory.getLog(DeleteCategoryController .class);
    
    public ModelAndView input (HttpServletRequest request,
            HttpServletResponse response) {
            
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		User user = userSession.getUserData();
		Collection cc = categoryDAO.getEmptyCategoriesByUser(user.getUsername());
		log.info("Resultset:"+cc.size());
        return new ModelAndView("opendeletecateogry", "categories", cc);
   }
   
    public ModelAndView store (HttpServletRequest request,
            HttpServletResponse response) {
            
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		User user = userSession.getUserData();
		
		//log.info(request.getParameter("category"));
		int category = new Integer(request.getParameter("category")).intValue();
		
		categoryDAO.removeFeedSubscriber(category);
		
		 /*
		  * 
		  * TODO: Category,  could be empty! Have to catch this in validator!
		  */
		
		
	
		
		return new ModelAndView("redirect:main.html");
      
   }
    /**
     * @return Returns the categoryDAO.
     */
    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }
    /**
     * @param categoryDAO The categoryDAO to set.
     */
    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
}
