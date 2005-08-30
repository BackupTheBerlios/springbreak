/*
 * Created on 23.08.2005
 * king
 * 
 */
package at.newsagg.web.feed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.CategoryDAO;
import at.newsagg.model.Category;
import at.newsagg.web.UserSession;
import at.newsagg.web.commandObj.feed.AddCategoryCommand;

/**
 * @author Roland Vecera
 * @version
 * created on 23.08.2005 10:04:51
 *
 */
public class AddCategoryController extends SimpleFormController {
    
    private static Log log = LogFactory.getLog(AddCategoryController.class);

    
    private CategoryDAO categoryDAO;
    
    /**
     *  Gets comment-input from User and delegates it to persistance.
     * 
     */
    public ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception 
       {
         Category category = new Category ();
         category.setTitle(((AddCategoryCommand)command).getTitle());
         
         log.debug(((AddCategoryCommand)command).getRed()+((AddCategoryCommand)command).getGreen()+((AddCategoryCommand)command).getBlue());
         
         int [] colorint = new int [3];
         colorint[0]=Integer.parseInt(((AddCategoryCommand)command).getRed());
         colorint[1]=Integer.parseInt(((AddCategoryCommand)command).getGreen());
         colorint[2]=Integer.parseInt(((AddCategoryCommand)command).getBlue());
 
         String [] color = new String [3];
     
         for (byte i =0; i < 3; i++)
         {
             color[i] = Integer.toHexString(colorint[i]);
             if (color[i].length() == 1)
             {
             color[i]="0"+color[i];    
             }
         }
         
         StringBuffer str= new StringBuffer();
         str.append("#");
         str.append(color[0]);
         str.append(color[1]);
         str.append(color[2]);
         log.debug(str.toString());
         
         category.setHtmlColor(str.toString());
         log.debug ("New Category: "+category.getTitle()+ "-"+category.getHtmlColor());
         UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
         category.setUser(userSession.getUserData());
       
         //store
         categoryDAO.saveCategory(category);
         
//       Invalidate Menu to display Changes
 		request.getSession().removeAttribute("feedSubscriberSession");
        
         return new ModelAndView ("main");
         
         
         
     
        
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
