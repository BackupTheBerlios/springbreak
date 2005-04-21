/*
 * Created on 20.04.2005
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

import at.newsagg.dao.CommentDAO;
import at.newsagg.model.Comment;
import at.newsagg.model.User;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.web.UserSession;

/**
 * @author Roland Vecera
 * @version
 * created on 20.04.2005 15:18:42
 *
 */
public class AddCommentController extends SimpleFormController {
    
    private static Log log = LogFactory.getLog(AddCommentController.class);

    
    private CommentDAO commentDAO;
    
    /**
     *  Gets comment-input from User and delegates it to persistance.
     * 
     */
    public ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception 
       {
         Comment comment = (Comment)command;
         
         //connect to channel
         log.debug("Channel_id given with: "+ comment.getChannel_id());
         Channel channel = new Channel();
         channel.setId(comment.getChannel_id());
         comment.setChannel(channel);
         log.debug("Comment to Channel "+comment.getChannel().getId());
         //connect to user
         User u = new User ();
         UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
         u.setUsername(userSession.getUserData().getUsername());
         comment.setUser(u);
         //store
         commentDAO.savecomment(comment);
         
         return new ModelAndView (getSuccessView(),"comment",comment);
         
         
         
     
        
       }
    
    
    

    /**
     * @return Returns the commentDAO.
     */
    public CommentDAO getCommentDAO() {
        return commentDAO;
    }
    /**
     * @param commentDAO The commentDAO to set.
     */
    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }
}
