package at.newsagg.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.dao.ItemDAO;
import at.newsagg.model.User;

public class MainController implements Controller {
    private static Log log = LogFactory.getLog(MainController.class);

    private FeedSubscriberDAO feedSubscriberDAO;

    private ItemDAO itemDAO;

    /**
     * @return Returns the itemDAO.
     */
    public ItemDAO getItemDAO() {
        return itemDAO;
    }

    /**
     * @param itemDAO
     *            The itemDAO to set.
     */
    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    private short HOTTEST; //Number of Items that should be returned from DB

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(
                request, "userSession");
        User user = userSession.getUserData();

        short hottestHelper = this.HOTTEST;
        ModelAndView mv = new ModelAndView("main", "user", user);

        //user gives something other than predefined hottest
        short view = 0;
        if (request.getSession().getAttribute("view") != null) {
            view = ((Short)(request.getSession().getAttribute("view"))).shortValue();
            log.debug("Session says, like to see "+view);
            if (view > 0) {
                hottestHelper = view;
            }
        }

        //hottest items to given channel
        if (request.getParameter("channel_id") != null)
            if (view == 0) //Show since last login
                mv.addObject("items", itemDAO.getItemsForUserByChannel(user
                        .getUsername(), user.getLastLogin(), Integer
                        .parseInt(request.getParameter("channel_id"))));
            else
                mv.addObject("items", itemDAO.getItemsForUserByChannel(user
                        .getUsername(), hottestHelper, Integer.parseInt(request
                        .getParameter("channel_id"))));
        // mv.addObject("items",this.feedSubscriberDAO.getHottestItemsForUserByChannel(user.getUsername(),Integer.parseInt(request.getParameter("channel_id")),hottestHelper));

        
        //hottest items to given category
        else if (request.getParameter("category_id") != null)
            if (view == 0) //show since last login
                mv.addObject("items", itemDAO.getItemsForUserByCategory(user
                        .getUsername(), user.getLastLogin(), Integer
                        .parseInt(request.getParameter("category_id"))));
            else
                mv.addObject("items", itemDAO.getItemsForUserByCategory(user
                        .getUsername(), hottestHelper, Integer.parseInt(request
                        .getParameter("category_id"))));
        //mv.addObject("items",feedSubscriberDAO.getHottestItemsForUserByCategory(user.getUsername(),Integer.parseInt(request.getParameter("category_id")),hottestHelper));
       
        
        else { //show over all Channels a user has registered
            if (view == 0) //show since last login
                mv.addObject("items", itemDAO.getItemsForUser(user
                        .getUsername(), user.getLastLogin()));
            else
                mv.addObject("items", itemDAO.getItemsForUser(user
                        .getUsername(), (int) hottestHelper));
        }
        return mv;

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
     * @return Returns the hOTTEST.
     */
    public short getHOTTEST() {
        return HOTTEST;
    }

    /**
     * @param hottest
     *            The hOTTEST to set.
     */
    public void setHOTTEST(short hottest) {
        HOTTEST = hottest;
    }
}