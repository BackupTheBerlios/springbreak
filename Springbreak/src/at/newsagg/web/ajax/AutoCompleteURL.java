/*
 * Created on 23.08.2005
 * king
 * 
 */
package at.newsagg.web.ajax;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.model.parser.ChannelIF;

/**
 * @author king
 * @version created on 23.08.2005 14:42:26
 *  
 */
public class AutoCompleteURL extends at.newsagg.web.ajax.BaseAjaxServlet {

    private static Log log = LogFactory.getLog(AutoCompleteURL.class);

    private ChannelDAO channelDAO = null;

    /**
     * setChannelDAO is called by AutoCompleteURLServletWrappingController
     * during startup!
     * 
     * @param channelDAO
     *            The channelDAO to set.
     */
    public void setChannelDAO(ChannelDAO channelDAO) {

        log.debug("AutoComplete.setChannel");
        if (this.channelDAO == null) {
            this.channelDAO = channelDAO;
            log.debug("ChannelDAO set in AutoComplete");
            // Schlechte Version, weil man mit factory.refresh() zwingend alle
            // Beans neu laded!
            // Roland Vecera 24. August 2005

            //            XmlWebApplicationContext factory = new
            // XmlWebApplicationContext();
            //            factory.setServletContext(request.getSession().getServletContext());
            //            log.info("Available channelDAO:
            // "+factory.containsBean("channelDAO"));
            //           factory.refresh();
            //            
            //            
            //           try {
            //                /* ApplicationContext ctx =
            //                    new
            // ClassPathXmlApplicationContext("/WEB-INF/applicationContext.xml");*/
            //                this.channelDAO = (ChannelDAO) factory.getBean("channelDAO");
            //            } catch (BeansException e) {
            //                // TODO Auto-generated catch block
            //                e.printStackTrace();
            //            }
        }
    }

    public String getXmlContent(HttpServletRequest request) {

        String startsWith = request.getParameter("input");
        StringBuffer xml = new StringBuffer().append("<?xml version=\"1.0\"?>");
        xml.append("<list>");
        //to minimize DB-Access Autocomplete starts at Inputlength of 11
        // http://www. <== start Autocomplete with next input!
        if (startsWith.length() > 11) {

            log.debug(startsWith);
            //setChannelDAO (request);
            List channels = channelDAO.getChannels(startsWith, 10);
            log.debug("comes");

            Iterator i = channels.iterator();

            ChannelIF c;
            while (i.hasNext()) {
                log.debug("find");
                c = (ChannelIF) i.next();
                xml.append("<item value=\"").append(c.getLocationString())
                        .append("\">");
                xml.append(c.getLocationString());
                xml.append("</item>");
            }

            xml.append("</list>");
            log.debug("Output:" + xml.toString());

        }
        return xml.toString();
    }

}
