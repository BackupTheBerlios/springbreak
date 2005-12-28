/*
 * Created on 20.03.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.model.parser.hibernate.Channel;

/**
 * Channel DAO.
 * 
 * @author Roland Vecera
 * @version created on 20.03.2005 19:57:39
 *  
 */
public class ChannelDAOHibernate extends HibernateDaoSupport implements
        ChannelDAO {
    private Log log = LogFactory.getLog(ChannelDAOHibernate.class);

    /**
     * save a new channel.
     * 
     * @param channel
     */
    public void saveChannel(Channel channel) {
        getHibernateTemplate().save(channel);

        if (log.isDebugEnabled()) {
            log.debug("Channel " + channel.getLocation() + " stored!");
        }
    }

    public void saveOrUpdateChannel(Channel channel) {
        log.info("channelid: " + channel.getId());
        
        getHibernateTemplate().evict(channel);
        getHibernateTemplate().saveOrUpdateCopy(channel);
        
      
        //getHibernateTemplate().saveOrUpdateAll(channel.getItems());
        
        if (log.isDebugEnabled()) {
            log.debug("Channel " + channel.getLocation() + " stored!");
            log.debug("channelid: " + channel.getId());
        }
    }

    /**
     * update a persisted channel. updates a channel, thats already in db.
     * 
     * @param channel
     */
    public void updateChannel(Channel channel) {
        getHibernateTemplate().update(channel);

        if (log.isDebugEnabled()) {
            log.debug("Channel " + channel.getLocation() + " updated!");
        }
    }

    /**
     * Delete Channel Object.
     * 
     * @param channel
     */
    public void removeChannel(Channel channel) {

        getHibernateTemplate().delete(channel);
    }

    /**
     * Get Channel by id.
     * 
     * @param id
     * @return
     */
    public Channel getChannel(int id) {
        return (Channel) getHibernateTemplate().load(Channel.class,
                new Integer(id));
        //return (Channel)getHibernateTemplate().find("from Channel u where
        // u.id = ?", new Integer(id)).get(0);

    }

    /**
     * Get a Channel by its URL.
     * 
     * 
     * 
     * @param id
     * @return
     * @throws IndexOutOfBoundsException
     *             if no Channel was found
     */
    public Channel getChannel(URL location) throws IndexOutOfBoundsException {

        return (Channel) getHibernateTemplate().find(
                "from Channel u where u.locationString like ?",
                location.toString(), Hibernate.STRING).get(0);

    }

    /**
     * Get all Channels order by title.
     * 
     * @return
     */
    public List getChannels() {
        return getHibernateTemplate().find("from Channel order by title");
    }

    /**
     * Get all Channels where locationString LIKE startswith% order by
     * locationString LIMIT limit.
     * 
     * @return
     */
    public List getChannels(String startswith, int limit) {
        String query = "from Channel as c where c.locationString like '"
                + startswith + "%' order by c.locationString limit " + limit;
        log.debug(query);
        return getHibernateTemplate().find(query);
    }

    /**
     * Free Object from Hibernate Session cache.
     * 
     * @param o
     */
    public void freeObject(Object o) {
        getHibernateTemplate().evict(o);
    }

    /**
     * Count number of Items in a given channel since date.
     * @param channel_id
     * @param date
     * @return
     */
    public int countItemsOnChannelSince(int channel_id, Date date) {
        return ((Integer) getHibernateTemplate()
                .find(
                        "select count (*) from Item as i where (i.date between ? and ?) and (i.channel.id = ?)",
                        //"select count (c.items) from Channel as c where (elements(c.items.date) between ? and ?) and (c.id = ?)",
                        new Object[] { new Timestamp(date.getTime()), new Timestamp(new Date().getTime()),
                                new Integer(channel_id) },
                        new Type[] { Hibernate.TIMESTAMP, Hibernate.TIMESTAMP,
                                Hibernate.INTEGER }).get(0)).intValue();

    }

}