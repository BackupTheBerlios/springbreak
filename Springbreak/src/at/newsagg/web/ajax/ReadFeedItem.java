/*
 * Created on 25.08.2005
 * king
 * 
 */
package at.newsagg.web.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.ItemDAO;
import at.newsagg.model.User;
import at.newsagg.model.UserReadItem;
import at.newsagg.model.parser.hibernate.Item;
import at.newsagg.web.UserSession;

/**
 * @author king
 * @version created on 25.08.2005 17:32:06
 *  
 */
public class ReadFeedItem extends BaseAjaxServlet {
    private static Log log = LogFactory.getLog(ReadFeedItem.class);

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

    private boolean changeReadState(HttpServletRequest request) {
        boolean state = !Boolean.valueOf(request.getParameter("state"))
                .booleanValue();
        long id = Long.parseLong(request.getParameter("id"));
        log.info(new Boolean(state));
        log.info(request.getParameter("id"));

        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(
                request, "userSession");
        User u = (userSession.getUserData());
        //changing from false to true
        //add UserReadItem in DB
        if (state == true) {
            UserReadItem uri = new UserReadItem();
            uri.setRead(true);

            Item i = new Item();
            i.setId(id);

            uri.setItem(i);
            uri.setUser(u);

            this.itemDAO.saveUserReadItem(uri);
        } else //Remove entry from DB
            log.info("Remove: "
                    + this.itemDAO.removeUserReadItem(u.getUsername(), id));

        return state;

    }

    public String getXmlContent(HttpServletRequest request) {

        boolean state = changeReadState(request);

        StringBuffer xml = new StringBuffer()
                .append("<?xml version=\"1.0\"?>")
                .append("<list>")
                .append("<item value=\"")
                .append(state)
                .append("\">")
                .append(
                        state ? "<![CDATA[Item Read]]>"
                                : "Item Unread").append("</item>").append(
                        "</list>");

        return xml.toString();
    }
}
