/*
 * Created on 16.09.2005
 * king
 * 
 */
package at.newsagg.web.feed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.newsagg.model.Comment;
import at.newsagg.model.User;

/**
 * @author king
 * @version
 * created on 16.09.2005 08:55:23
 *
 */
public class AddCommentViewController implements Controller {

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse arg1) throws Exception {
        
        Comment comment = new Comment();
        comment.setChannel_id(Integer.parseInt(request.getParameter("id")));
        comment.setText("Your comment here!");
        
        return new ModelAndView("addCommentForm", "comment", comment); 
    }

}
