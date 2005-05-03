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

import de.laures.cewolf.DatasetProduceException;



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
        
     
           HashMap h = new HashMap();
           h.put("channel_id", new Integer(7));
           h.put("numberWeeks", new Integer(15));
           h.put("seriesName", "Number of Subcribers of feed");
           log.debug("ID: "+h.get("channel_id")+" WEEKS:"+h.get("numberWeeks"));
           //set parameters which should produce data
           this.subscriberStatisticData.setMap(h);
           
           //dont call that: obviously the produceDataset-Method is called by jsp-tags
           //subscriberStatisticData.produceDataset(h);
    
        
    
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
