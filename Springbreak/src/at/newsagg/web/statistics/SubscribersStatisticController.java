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

import at.newsagg.dao.ChannelDAO;

/**
 * @author king
 * @version created on 30.04.2005 12:44:58
 *  
 */
public class SubscribersStatisticController implements Controller {

    private static Log log = LogFactory
            .getLog(SubscribersStatisticController.class);

    private StatisticDataIF subscriberStatisticData;
    private StatisticDataIF postingsStatisticData;

    private ChannelDAO channelDAO;

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response)

    {
        log.debug(request.getParameter("channel_id"));
        String channelname = channelDAO.getChannel(
                Integer.parseInt(request.getParameter("channel_id")))
                .getTitle();
        //**************************************************************
        //Create Input for picture "Number of subscribers of RSS-Channel
        //**************************************************************
        HashMap h = new HashMap();

        h.put("channel_id", Integer.valueOf(request
                        .getParameter("channel_id")));
       
        h.put("numberWeeks", Integer.valueOf(request
                .getParameter("numberWeeks")));
        /*
         * TODO: internationalisiere:
         */
        h.put("seriesName", "Number of subcribers for RSS-channel '"
                + channelname + "'");
        log.debug("ID: " + h.get("channel_id") + " WEEKS:"
                + h.get("numberWeeks"));
        //set parameters which should produce data
        this.subscriberStatisticData.setMap(h);

        request.setAttribute("dataSubscribers", subscriberStatisticData);

        //************************************************************
        //Create Input for picture "Number of postings for RSS-Channel
        //************************************************************
        HashMap g = new HashMap();
        g.put("channel_id",h.get("channel_id"));
        g.put("numberWeeks", h.get("numberWeeks"));
        g.put("seriesName", "Number of postings in RSS-channel '"+ channelname+"'");
        this.postingsStatisticData.setMap(g);
        request.setAttribute("dataPostings", this.postingsStatisticData);
        
        
        
        request.setAttribute("channeltitle", channelname);
        return new ModelAndView("showSubscribersStatistics");
    }

   

    /**
     * @return Returns the channelDAO.
     */
    public ChannelDAO getChannelDAO() {
        return channelDAO;
    }

    /**
     * @param channelDAO
     *            The channelDAO to set.
     */
    public void setChannelDAO(ChannelDAO channelDAO) {
        this.channelDAO = channelDAO;
    }
    /**
     * @return Returns the postingsStatisticData.
     */
    public StatisticDataIF getPostingsStatisticData() {
        return postingsStatisticData;
    }
    /**
     * @param postingsStatisticData The postingsStatisticData to set.
     */
    public void setPostingsStatisticData(StatisticDataIF postingsStatisticData) {
        this.postingsStatisticData = postingsStatisticData;
    }
    /**
     * @return Returns the subscriberStatisticData.
     */
    public StatisticDataIF getSubscriberStatisticData() {
        return subscriberStatisticData;
    }
    /**
     * @param subscriberStatisticData The subscriberStatisticData to set.
     */
    public void setSubscriberStatisticData(
            StatisticDataIF subscriberStatisticData) {
        this.subscriberStatisticData = subscriberStatisticData;
    }
}
