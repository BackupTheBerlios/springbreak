/*
 * Created on 01.05.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import java.util.Collection;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import at.newsagg.model.logstatistics.LogPostings;
import at.newsagg.model.logstatistics.LogSubscribers;

/**
 * Access Log-Statistics.
 * 
 * @author Roland Vecera
 * @version
 * created on 01.05.2005 11:23:36
 * 
 *
 */
public class LogDAOHibernate extends HibernateDaoSupport implements at.newsagg.dao.LogDAO {
    
    private Log log = LogFactory.getLog(LogDAOHibernate.class);
    
    /**
     * Store an LogSubscripers or LogPostings Object
     * 
     * @param o given Object 
     */
    public void save(Object o)    
    {
        getHibernateTemplate().save(o);
       
            if (o instanceof LogSubscribers) {
                log.debug("LogSubscribers stored: "+((LogSubscribers)o).getDate()+" "+((LogSubscribers)o).getChannel().getId());
                
            }
            if (o instanceof LogPostings) {
                log.debug("LogPostings stored: "+((LogPostings)o).getDate()+" "+((LogPostings)o).getChannel().getId());
                
            }
       
        
        

           
    }
    /**
     * Returns all entries of a channel in LogSubscribers order by date DESC.
     * 
     * @param channel_id id of a given Channel.
     * @return
     */
    public java.util.Collection getAllLogSubscribers (int channel_id)
    {
        log.debug("Channel_id" + channel_id);
        
        java.util.Collection c = getHibernateTemplate().find("from LogSubscribers l where l.channel.id like ? order by l.date ASC", new Integer (channel_id),Hibernate.INTEGER); 
        if (c != null)
        {   
        log.debug("Size result "+c.size());
        }
        else
            log.debug("Collection is null");
        return c;
    
    }
    /**
     * Returns all entries of a channel in LogPostings order by date DESC
     * @param channel_id
     * @return
     */
    public Collection getLogPostings (int channel_id)
    {
        return getHibernateTemplate().find("from LogPostings l where l.channel.id like ? order by l.date ASC",new Integer (channel_id),Hibernate.INTEGER); 
    }

}
