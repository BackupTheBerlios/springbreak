/*
 * Created on 01.05.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import java.util.Collection;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

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
    
    private int upperLimit;
    
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
     * Returns  entries of a channel in LogSubscribers order by date DESC.
     * 
     * Limited by upperLimit.
     * @param channel_id id of a given Channel.
     * @return
     */
    public java.util.Collection getLogSubscribers (int channel_id)
    {
        log.debug("Channel_id" + channel_id);
        
        java.util.Collection c = getHibernateTemplate().find("from LogSubscribers l where l.channel.id like ? order by l.date ASC limit ?", new Object[] {new Integer (channel_id), new Integer(upperLimit)}, new Type [] {Hibernate.INTEGER, Hibernate.INTEGER}); 
        if (c != null)
        {   
        log.debug("Size result "+c.size());
        }
        else
            log.debug("Collection is null");
        return c;
    
    }
    /**
     * Returns entries of a channel in LogPostings order by date DESC.
     * 
     * Limited by upperLimit.
     * @param channel_id
     * @return
     */
    public Collection getLogPostings (int channel_id)
    {
        return getHibernateTemplate().find("from LogPostings l where l.channel.id like ? order by l.date ASC limit ?", new Object[] {new Integer (channel_id), new Integer(upperLimit)}, new Type [] {Hibernate.INTEGER, Hibernate.INTEGER}); 
    }

    /**
     * @return Returns the upperLimit.
     */
    public int getUpperLimit() {
        return upperLimit;
    }
    /**
     * @param upperLimit The upperLimit to set.
     */
    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }
}
