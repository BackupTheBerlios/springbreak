/*
 * Created on 06.09.2005
 * king
 * 
 */
package at.newsagg.web.ajax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Stores in session-attribute "openElements" CategoryIDs that are AUFGEKLAPPT in the users menu.
 * 
 * @author Roland Vecera
 * @version
 * created on 06.09.2005 11:56:49
 *
 */
public class StoreOpenElementsInSession extends HttpServlet {
    
    private static Log log = LogFactory.getLog(StoreViewInSession.class);

    public void doGet(HttpServletRequest request, HttpServletResponse  response)
    throws IOException, ServletException {

    
    
    try {
        String categoryid = (String)request.getParameter("cat_id");
        Map openElements = (HashMap)request.getSession().getAttribute("openElements");
        if (openElements == null)
        {
            log.debug("reset Menu? no openElements Session Attribute!");
            openElements = new HashMap();
        }
        if (openElements.containsKey(categoryid))
        {
            log.debug ("close "+categoryid);
            openElements.remove(categoryid);
        }
        else
        {
            log.debug("open "+categoryid);
            openElements.put(categoryid,new Boolean(true));
        }
        
        request.getSession().setAttribute("openElements",openElements);
        
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<message>true</message>");
    
    
    } catch (NumberFormatException e) {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<message>false</message>");
    }
    
   
    
    
    
    
    
    

    
}

}
