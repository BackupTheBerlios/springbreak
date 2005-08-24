/*
 * Created on 23.08.2005
 * roland vecera
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

import at.newsagg.web.feed.AddCategoryController;

/**
 * @author Roland Vecera
 * @version
 * created on 23.08.2005 11:26:12
 * 
 * Servlet that returns html-color from given parameters red, green, blue.
 * 
 * Example for using AJAX. Used in addCategoryForm.jsp to preview color.
 * A bit overkill, but it's working!
 *
 */
public class AjaxTest extends HttpServlet {
    private static Log log = LogFactory.getLog(AjaxTest.class);

    public void doGet(HttpServletRequest request, HttpServletResponse  response)
    throws IOException, ServletException {

    String[] color = new String [3];
    
    try {
        color[0] = Integer.toHexString(Integer.valueOf(request.getParameter("red")).intValue());
        color[1] = Integer.toHexString(Integer.valueOf(request.getParameter("green")).intValue());
        color[2] = Integer.toHexString(Integer.valueOf(request.getParameter("blue")).intValue());
        
        for (byte i =0; i < 3; i++)
        {
            if (color[i].length() == 1)
            {
            color[i]="0"+color[i];    
            }
        }
        
        StringBuffer str= new StringBuffer();
        str.append("#");
        str.append(color[0]);
        str.append(color[1]);
        str.append(color[2]);
        log.debug(str.toString());
        
        
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<message>"+str.toString()+"</message>");
    
    
    } catch (NumberFormatException e) {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<message>notvalid</message>");
    }
    
   
    
    
    
    
    
    

    
}

}
