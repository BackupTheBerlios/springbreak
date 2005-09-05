/*
 * Created on 26.08.2005
 * king
 * 
 */
package at.newsagg.web.ajax;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.CategoryDAO;
import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.model.Category;
import at.newsagg.model.User;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.web.UserSession;

/**
 * @author Roland Vecera
 * @version created on 26.08.2005 16:33:16
 *  
 * Move Servlet that obtains Channel, Category information to fill SELECT-Boxes on 
 * openmovefeed.jsp in an AJAX fashion way.
 */
public class MoveFeed extends BaseAjaxServlet {
    private static Log log = LogFactory.getLog(MoveFeed.class);

    private FeedSubscriberDAO feedSubscriberDAO;

    private CategoryDAO categoryDAO;

    public String getXmlContent(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(
                request, "userSession");
        User user = userSession.getUserData();

        String cat_id = request.getParameter("make");
        log.info("here");
        StringBuffer xml = new StringBuffer().append("<?xml version=\"1.0\"?>");
        xml.append("<list>");
       
        if (cat_id != null) {
            xml.append("<item value=''>Select Feed</item>");
            for (Iterator iter = feedSubscriberDAO
                    .getChannelsForUserByCategory(user.getUsername(),
                            Integer.valueOf(cat_id).intValue()).iterator(); iter
                    .hasNext();)

            {
                Channel chan = (Channel) iter.next();
                xml.append("<item value='").append(chan.getId()).append("'>")
                        .append(chan.getTitle()).append("</item>");

                //}
            }

        } else if (request.getParameter("make2") != null) {
            xml.append("<item value=''>Select New Category</item>");

            //            xml.append("<item value='234'>");
            //            xml.append("23423</item>");
            //            xml.append("<item value='2324'>");
            //            xml.append("234243</item>");

            for (Iterator iter = categoryDAO.getCategoriesByUser(
                    user.getUsername()).iterator(); iter.hasNext();)

            {
                Category cat = (Category) iter.next();
                //            log.info(xml);
                xml.append("<item value='").append(cat.getId()).append("'>")
                        .append(cat.getTitle()).append("</item>");

                //}
            }

        }
        xml.append("</list>");
        log.info(xml.toString());
        return xml.toString();
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
}
