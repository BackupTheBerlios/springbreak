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
import at.newsagg.model.User;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.web.UserSession;

/**
 * @author king
 * @version created on 26.08.2005 16:33:16
 *  
 */
public class MoveFeed extends BaseAjaxServlet {
    private static Log log = LogFactory.getLog(MoveFeed.class);

    private FeedSubscriberDAO feedSubscriberDAO;

    public String getXmlContent(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(
                request, "userSession");
        User user = userSession.getUserData();

        String cat_id = request.getParameter("make");
        log.info("here");
        StringBuffer xml = new StringBuffer().append("<?xml version=\"1.0\"?>");
        xml.append("<list>");
        if (cat_id != null) {

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

            xml.append("<item value='234'>");
            xml.append("23423</item>");
            xml.append("<item value='2324'>");
            xml.append("234243</item>");

        }
        xml.append("</list>");

        return xml.toString();
    }

  
    /**
     * @return Returns the feedSubscriberDAO.
     */
    public FeedSubscriberDAO getFeedSubscriberDAO() {
        return feedSubscriberDAO;
    }
    /**
     * @param feedSubscriberDAO The feedSubscriberDAO to set.
     */
    public void setFeedSubscriberDAO(FeedSubscriberDAO feedSubscriberDAO) {
        this.feedSubscriberDAO = feedSubscriberDAO;
    }
}
