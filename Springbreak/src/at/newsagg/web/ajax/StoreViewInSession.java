/*
 * Created on 06.09.2005
 * king
 * 
 */
package at.newsagg.web.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Stores in session-attribute "view" the upper limit of items a user wants to display.
 * 
 * <br/>
 * view = 0 means, that all items "since last visit" should be displayed.<br/>
 * Bsp.: view = 10 means, that limit 10 items should be displayed.
 * @author Roland Vecera
 * @version created on 06.09.2005 16:45:15
 *  
 */
public class StoreViewInSession extends HttpServlet {

    private static Log log = LogFactory.getLog(StoreViewInSession.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        

        try {
            Short view = new Short(request.getParameter("view"));

            request.getSession().setAttribute("view", view);
            log.info("view set to" + view.toString() + "in Session!");

            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(
                    "<message>" + view.toString() + "</message>");

        } catch (NumberFormatException e) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<message>0</message>");
        }

    }
}
