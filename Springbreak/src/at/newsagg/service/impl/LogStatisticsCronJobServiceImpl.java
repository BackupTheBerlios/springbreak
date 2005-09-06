/*
 * Created on 01.05.2005
 * king
 * 
 */
package at.newsagg.service.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.dao.LogDAO;
import at.newsagg.model.logstatistics.LogPostings;
import at.newsagg.model.logstatistics.LogSubscribers;
import at.newsagg.model.parser.hibernate.Channel;
/**
 * 
 * 
 * @author Roland Vecera
 * @version
 * created on 01.05.2005 20:00:08
 *
 */
public class LogStatisticsCronJobServiceImpl {
     
    private ChannelDAO channelDAO;
    private FeedSubscriberDAO feedSubscriberDAO;
    private LogDAO logDAO;
    
    private Log log = LogFactory.getLog(LogStatisticsCronJobServiceImpl.class);
    
    
    public void runStatistics()
    {
        java.util.Collection c = channelDAO.getChannels();
        runLogPostings(c);
        runLogSubscribers(c);
        
    }
    
    public void runLogPostings(Collection c)
    {
        log.info("run Log Postings!");
        Iterator i = c.iterator();
        LogPostings logPostings;
        Channel channel;
        while (i.hasNext())
        {
            logPostings= new LogPostings();
            GregorianCalendar cal = new GregorianCalendar ();
            
            log.debug("**-"+cal.toString());
            logPostings.setDate(cal.getTime()); //set actual time
            
            cal.add(Calendar.DATE, -7); //set to last week
            channel = (Channel)i.next();
            logPostings.setChannel(channel);
            //get number of postings since last week
            logPostings.setNum_postings(channelDAO.countItemsOnChannelSince(channel.getIntId(),cal.getTime()));
            logPostings.setTimespan(7);
            logDAO.save(logPostings);
        }
    }
    
    /**
     * Log for all Channels how many Subscribers they have got.
     *
     */
    public void runLogSubscribers (Collection c)
    {
       log.info("run Log Subscribers!");
       
       java.util.Iterator i = c.iterator();
       LogSubscribers logsub; 
       Channel channel;
       while (i.hasNext())
       {
           logsub = new LogSubscribers();
           logsub.setDate(new Date());
           channel = (Channel)i.next();
           logsub.setNum_subscribers(feedSubscriberDAO.countFeedSubscriberByChannelURL(channel.getLocationString()));
           logsub.setChannel(channel);
           logDAO.save(logsub);
       }
    }
    

    /**
     * @return Returns the channelDAO.
     */
    public ChannelDAO getChannelDAO() {
        return channelDAO;
    }
    /**
     * @param channelDAO The channelDAO to set.
     */
    public void setChannelDAO(ChannelDAO csDAO) {
        this.channelDAO = csDAO;
    }
    /**
     * @return Returns the logDAO.
     */
    public LogDAO getLogDAO() {
        return logDAO;
    }
    /**
     * @param logDAO The logDAO to set.
     */
    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
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
