/*
 * Created on 01.05.2005
 * king
 * 
 */
package at.newsagg.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.FeedSubscriberDAO;
import at.newsagg.dao.LogDAO;
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
    
    /**
     * Log for all Channels how many Subscribers they have got.
     *
     */
    public void runLogSubscribers ()
    {
       log.info("run Log Subscribers!");
       java.util.Collection c = channelDAO.getChannels();
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
