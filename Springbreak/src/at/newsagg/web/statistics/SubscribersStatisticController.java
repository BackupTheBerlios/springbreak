/*
 * Created on 30.04.2005
 * king
 * 
 */
package at.newsagg.web.statistics;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;



/**
 * @author king
 * @version
 * created on 30.04.2005 12:44:58
 *
 */
public class SubscribersStatisticController implements Controller{
    
    private static Log log = LogFactory.getLog(SubscribersStatisticController.class);
    
    private SubscriberStatisticData subscriberStatisticData;
    
   
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response)
    
    {
        
     /**
      * TODO: validation if this values are available
      * is "implements Controller" what I want?!
      * 
      * Roland Vecera
      * 06.Mai 2005
      */
           HashMap h = new HashMap();
           log.debug(request.getParameter("channel_id"));
           
           h.put("channel_id", Integer.valueOf(request.getParameter("channel_id")));
           h.put("numberWeeks", Integer.valueOf(request.getParameter("numberWeeks")));
           /**
            * TODO: internationalisiere:
            */
           h.put("seriesName", "Number of Subcribers of feed");
           log.debug("ID: "+h.get("channel_id")+" WEEKS:"+h.get("numberWeeks"));
           //set parameters which should produce data
           this.subscriberStatisticData.setMap(h);
           
          
    
        
    
    request.setAttribute("data", subscriberStatisticData);
    return new ModelAndView("showSubscribersStatistics");
    }

   
    /**
     * @return Returns the subscriberStatisticData.
     */
    public SubscriberStatisticData getSubscriberStatisticData() {
        return subscriberStatisticData;
    }
    /**
     * @param subscriberStatisticData The subscriberStatisticData to set.
     */
    public void setSubscriberStatisticData(
            SubscriberStatisticData subscriberStatisticData) {
        this.subscriberStatisticData = subscriberStatisticData;
    }
}
