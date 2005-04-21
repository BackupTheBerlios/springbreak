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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.CommentDAO;
import at.newsagg.model.parser.hibernate.Channel;

/**
 * @author king
 * @version
 * created on 20.04.2005 15:19:16
 *
 */
public class ViewCommentsToChannelController implements Controller {
    
    private static Log log = LogFactory.getLog(ViewCommentsToChannelController.class);
    
    private ChannelDAO channelDAO;
    private CommentDAO commentDAO;
    
    
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response)
    
    {
    
     Channel channel = channelDAO.getChannel(Integer.parseInt(request.getParameter("id")));   
     ModelAndView mv = new ModelAndView("listCommentsToChannel","channel",channel);
     int countReviews = commentDAO.countCommentsToChannel (channel.getIntId());
     mv.addObject("countReviews", new Integer(countReviews));
     if (countReviews > 0)
     {    
      float avgReview = commentDAO.getAVGRatingToChannel(channel.getIntId()); 
     mv.addObject("avgRating", new Float (avgReview));
     }
     return     mv;
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
    /**
     * @return Returns the channelDAO.
     */
    public ChannelDAO getChannelDAO() {
        return channelDAO;
    }
    /**
     * @param channelDAO The channelDAO to set.
     */
    public void setChannelDAO(ChannelDAO channelDAO) {
        this.channelDAO = channelDAO;
    }
}
