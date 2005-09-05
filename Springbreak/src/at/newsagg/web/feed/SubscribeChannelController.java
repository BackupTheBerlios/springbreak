/*
 * Created on 18.04.2005
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
import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.dao.UserDAO;
import at.newsagg.model.FeedSubscriber;
import at.newsagg.model.FeedSubscriberIF;
import at.newsagg.web.UserSession;
import at.newsagg.web.commandObj.feed.SubscribeChannelCommand;

/**
 * Subscribe User to a Channel in a given User's Category.
 * 
 * @author Roland Vecera
 * @version created on 18.04.2005 10:07:15
 *  
 */
public class SubscribeChannelController extends SimpleFormController {
    private static Log log = LogFactory
            .getLog(SubscribeChannelController.class);

    private FeedSubscriberDAO feedSubscriberDAO;

    private CategoryDAO categoryDAO;
 
    private ChannelDAO channelDAO;

    private UserDAO userDAO;

    /**
     * Takes the user input on a Channel by id, a Category by id and subscribes the 
     * given User (through Session) to this feed!
     * 
     * Returns persistes Channel-object to ModelandView.
     */
    public ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        SubscribeChannelCommand scc = (SubscribeChannelCommand) command;

        FeedSubscriberIF feedSubscriber = new FeedSubscriber();
        log.debug ("FeedSubscribe in CategoryID"+scc.getCategory());
        feedSubscriber.setCategory(categoryDAO.getCategory(scc.getCategory()));
        
        log.debug("FeedSubscribe of ChannelID"+scc.getId());
        feedSubscriber.setChannel(channelDAO.getChannel(scc.getId()));
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(
                request, "userSession");
        //Set Session's user in feedSubscriber
        feedSubscriber.setUser(userDAO.getUser(userSession.getUserData()
                .getUsername()));
        //persist in DB!
        feedSubscriber = feedSubscriberDAO.saveFeedSubscriber(feedSubscriber);

        //force menu reload!
        request.getSession().removeAttribute("feedSubscriberSession");
        
        ModelAndView mav = new ModelAndView("redirect:main.html");
        return mav;

    }

    /**
     * @return Returns the categoryDAO.
     */
    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    /**
     * @param categoryDAO
     *            The categoryDAO to set.
     */
    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    /**
     * @return Returns the channelDAO.
     */
    public ChannelDAO getChannelDAO() {
        return channelDAO;
    }

    /**
     * @param channelDAO
     *            The channelDAO to set.
     */
    public void setChannelDAO(ChannelDAO channelDAO) {
        this.channelDAO = channelDAO;
    }

    /**
     * @return Returns the feedSubscriberDAO.
     */
    public FeedSubscriberDAO getFeedSubscriberDAO() {
        return feedSubscriberDAO;
    }

    /**
     * @param feedSubscriberDAO
     *            The feedSubscriberDAO to set.
     */
    public void setFeedSubscriberDAO(FeedSubscriberDAO feedSubscriberDAO) {
        this.feedSubscriberDAO = feedSubscriberDAO;
    }

    /**
     * @return Returns the userDAO.
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * @param userDAO
     *            The userDAO to set.
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
