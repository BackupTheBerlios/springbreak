/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.type.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import at.newsagg.dao.ItemDAO;
import at.newsagg.model.UserReadItem;
import at.newsagg.model.parser.hibernate.Item;
  
/**
 * Hibernate DAO for Item. 
 * 
 * @author Roland Vecera
 * @version created on 26.03.2005 11:00:05
 *  
 */
public class ItemDAOHibernate extends HibernateDaoSupport implements ItemDAO {
    private Log log = LogFactory.getLog(ItemDAOHibernate.class);

    private short upperLimit = 100;

    /**
     * Returns count of persistent Items with given url.
     * 
     * Should return 0 or 1, because the URL should be the natural key to item.
     * 
     * @param url
     *            url-value of an FeedItem.
     * @return
     */
    public int countItemwithURL(String url) {
        log.debug(url);
        return ((Integer) getHibernateTemplate().find(
                "select count (*) from Item i where i.link like ?", url,
                Hibernate.STRING).get(0)).intValue();
    }

    /**
     * Get Item by id.
     * 
     * @param id
     * @return
     */
    public Item getItem(int id) {
        return (Item) getHibernateTemplate().load(Item.class, new Long(id));
    }

    /**
     * Get Item by Link.
     * 
     * Returns null when not found.
     * 
     * @param id
     * @return
     */
    public Item getItemByLink(String link) throws IndexOutOfBoundsException {
        return (Item) getHibernateTemplate().find(
                "from Item u where u.link like ?", link, Hibernate.STRING).get(
                0);

    }

    /**
     * Returns the Id of an Item with the given url.
     * 
     * returns -1 if no Item was found.
     * 
     * @param url
     * @return
     */
    public long getIDfromItemwithURL(String url) {
        try {
            return ((Long) getHibernateTemplate().find(
                    "select i.id from Item i where i.link like ?", url,
                    Hibernate.STRING).get(0)).longValue();
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    public void saveUserReadItem(UserReadItem i) {
        getHibernateTemplate().save(i);

        if (log.isDebugEnabled()) {
            log.debug("UserReadItem " + i.getId() + " stored!");
        }
    }

    public int removeUserReadItem(String username, long itemid) {
        return getHibernateTemplate()
                .delete(
                        "from UserReadItem as uri where uri.user.username like ? and uri.item.id = ?",
                        new Object[] { username, new Long(itemid) },
                        new Type[] { Hibernate.STRING, Hibernate.LONG });
    }

    public Collection getItemsForUser(String username, int limit)
            throws IndexOutOfBoundsException {
        // System.out.println("hallo!");
        // Object[] o = { username, username/*, new Boolean(true) */};
        // net.sf.hibernate.type.Type[] t = { Hibernate.STRING, Hibernate.STRING
        // /*, Hibernate.BOOLEAN*/};
        //"from FeedSubscriber f where f.user.username like ? and
        // f.channel.items in 16"
        //"from Item i, FeedSubscriber f, UserReadItem uri where i in (Select
        // f.channel.items where f.user.username like ?)"
        //return getHibernateTemplate().find("select DISTINCT i.id from Item i,
        // UserReadItem uri where i in (select elements(f1.channel.items) from
        // FeedSubscriber f1 where f1.user.username like ?) and i.id =
        // uri.item.id and uri.user.username like ? and uri.read = ?",o,t);
        //return getHibernateTemplate().find("select * from Item as i LEFT
        // OUTER JOIN UserReadItem as uri ON (i.id=uri.item.id)");
        //        String query = "select i.item_id AS {item.id} from items as i LEFT
        // OUTER JOIN usersreaditems u ON (i.item_id = u.item_id AND u.username
        // like 'vec')";
        //     and(item0_.ITEM_ID in(select items4_.ITEM_ID from FEEDSUBSCRIBERS
        // feedsubscr2_, CHANNELS channel3_, ITEMS items4_ where
        // feedsubscr2_.CHANNEL_ID=channel3_.CHANNEL_ID and
        // channel3_.CHANNEL_ID=items4_.CHANNEL_ID and ((feedsubscr2_.username
        // like 'vec' ))))
        if (limit > this.upperLimit) {
            log
                    .warn("Someone want more see more Items than specified in this.upperLimit!");
            log.warn("Cut down " + limit + " to " + this.upperLimit);
            limit = this.upperLimit;
        }

        String query = "select {i.*}, {uri.*} from Items as i LEFT OUTER JOIN usersReadItems as uri "
                + "on (i.item_id=uri.item_id and uri.username='"
                + username
                + "') "
                + "where i.item_id in "
                + "(select items4_.ITEM_ID from FEEDSUBSCRIBERS feedsubscr2_, CHANNELS channel3_, ITEMS items4_ "
                + "where feedsubscr2_.CHANNEL_ID=channel3_.CHANNEL_ID and channel3_.CHANNEL_ID=items4_.CHANNEL_ID and "
                + "(feedsubscr2_.username like '"
                + username
                + "')) order by i.date DESC, i.item_id ASC LIMIT " + limit;

        try {
            java.util.Collection c = this.getSession().createSQLQuery(query,
                    new String[] { "i", "uri" },
                    new Class[] { Item.class, UserReadItem.class }).list();
            log.debug("Resultset: " + c.size());
            return this.simplifyCollection(c);

        } catch (DataAccessResourceFailureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
        //return getHibernateTemplate().find("select {i.*}, {uri.*} from Item
        // as i LEFT OUTER JOIN i.usersReadItem as uri on (i.id=uri.item.id and
        // uri.user.username='vec' )where i in (select
        // elements(f1.channel.items) from FeedSubscriber f1 where
        // f1.user.username like 'vec') order by order by i.date DESC LIMIT
        // 10");
    }

    public Collection getItemsForUserByChannel(String username, int limit,
            int channel_id) throws IndexOutOfBoundsException {

        if (limit > this.upperLimit) {
            log
                    .warn("Someone want more see more Items than specified in this.upperLimit!");
            log.warn("Cut down " + limit + " to " + this.upperLimit);
            limit = this.upperLimit;
        }

        String query = "select {i.*}, {uri.*} from Items as i LEFT OUTER JOIN usersReadItems as uri "
                + "on (i.item_id=uri.item_id and uri.username='"
                + username
                + "') "
                + "where i.item_id in "
                + "(select items4_.ITEM_ID from FEEDSUBSCRIBERS feedsubscr2_, CHANNELS channel3_, ITEMS items4_ "
                + "where feedsubscr2_.CHANNEL_ID=channel3_.CHANNEL_ID and channel3_.CHANNEL_ID=items4_.CHANNEL_ID and "
                + "(feedsubscr2_.username like '"
                + username
                + "') and "
                + "(feedsubscr2_.channel_id = " //here is the diff!
                + channel_id + ")) order by i.date DESC, i.item_id ASC LIMIT " + limit;

        try {
            java.util.Collection c = this.getSession().createSQLQuery(query,
                    new String[] { "i", "uri" },
                    new Class[] { Item.class, UserReadItem.class }).list();
            log.debug("Resultset: " + c.size());
            return this.simplifyCollection(c);

        } catch (DataAccessResourceFailureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public Collection getItemsForUserByCategory(String username, int limit,
            int cat_id) throws IndexOutOfBoundsException {
        log.info("+++++++++++++++++++ here");
        if (limit > this.upperLimit) {
            log
                    .warn("Someone want more see more Items than specified in this.upperLimit!");
            log.warn("Cut down " + limit + " to " + this.upperLimit);
            limit = this.upperLimit;
        }

        String query = "select {i.*}, {uri.*} from Items as i LEFT OUTER JOIN usersReadItems as uri "
                + "on (i.item_id=uri.item_id and uri.username='"
                + username
                + "') "
                + "where i.item_id in "
                + "(select items4_.ITEM_ID from FEEDSUBSCRIBERS feedsubscr2_, CHANNELS channel3_, ITEMS items4_ "
                + "where feedsubscr2_.CHANNEL_ID=channel3_.CHANNEL_ID and channel3_.CHANNEL_ID=items4_.CHANNEL_ID and "
                + "(feedsubscr2_.username like '"
                + username
                + "') and "
                + "(feedsubscr2_.category_id = " //here is the diff!
                + cat_id + ")) order by i.date DESC, i.item_id ASC LIMIT " + limit;

        try {
            java.util.Collection c = this.getSession().createSQLQuery(query,
                    new String[] { "i", "uri" },
                    new Class[] { Item.class, UserReadItem.class }).list();
            log.debug("Resultset: " + c.size());
            return this.simplifyCollection(c);

        } catch (DataAccessResourceFailureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public Collection getItemsForUser(String username, Date since)
            throws IndexOutOfBoundsException {
        String query = "select {i.*}, {uri.*} from Items as i LEFT OUTER JOIN usersReadItems as uri "
                + "on (i.item_id=uri.item_id and uri.username='"
                + username
                + "') "
                + "where i.item_id in "
                + "(select items4_.ITEM_ID from FEEDSUBSCRIBERS feedsubscr2_, CHANNELS channel3_, ITEMS items4_ "
                + "where feedsubscr2_.CHANNEL_ID=channel3_.CHANNEL_ID and channel3_.CHANNEL_ID=items4_.CHANNEL_ID and "
                + "(feedsubscr2_.username like '"
                + username
                + "')) and i.date BETWEEN ? AND ? order by i.date DESC, i.item_id ASC LIMIT "
                + this.upperLimit;

        try {

            Query q = this.getSession().createSQLQuery(query,
                    new String[] { "i", "uri" },
                    new Class[] { Item.class, UserReadItem.class });

            Timestamp t = new Timestamp(since.getTime());
            q.setParameter(0, t, Hibernate.TIMESTAMP);
//            Calendar calendar = new GregorianCalendar();
//            calendar.setTime(new Date());
//            calendar.add(Calendar.DATE, 1);
            
            q.setParameter(1, new Timestamp(new Date().getTime()), Hibernate.TIMESTAMP);

            java.util.Collection c = q.list();
            log.debug("Resultset: " + c.size());
            return this.simplifyCollection(c);

        } catch (DataAccessResourceFailureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * Count all unread Items since Date of a given User.
     * 
     * Counts items in all subscribed channels.
     * */
    public Integer countNewItemsForUser(String username, Date since)
            throws IndexOutOfBoundsException {
        	
        	String query2 ="select count(i.item_id) from Items i where i.item_id in "
                + "(select items4_.ITEM_ID from FEEDSUBSCRIBERS feedsubscr2_, CHANNELS channel3_, ITEMS items4_ where feedsubscr2_.CHANNEL_ID=channel3_.CHANNEL_ID and channel3_.CHANNEL_ID=items4_.CHANNEL_ID "
                + "and(feedsubscr2_.username like '"
                + username
                + "') and i.date BETWEEN ? AND ? )"
                + " and i.item_id not in (select uri.item_id from usersReadItems uri where (uri.username='"
                + username
                + "'))";
        try {
            
            logger.info(query2);
            
            //Must be done via JDBC, because it's not possible with Hibernate
            
            PreparedStatement ps = this.getSession().connection().prepareStatement(query2);
            
           
            ps.setTimestamp(1,new java.sql.Timestamp(since.getTime()));
            ps.setTimestamp(2,new java.sql.Timestamp(new Date().getTime()));
            
            
            
            ResultSet rs = ps.executeQuery();
           if (!rs.next()) {
              throw new SQLException("SELECT COUNT(*): no result");
              }
            return new Integer(rs.getInt(1));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Collection getItemsForUserByCategory(String username, Date since,
            int cat_id) throws IndexOutOfBoundsException {
        	log.info("+++++++++++++++++++ here");
        
        String query = "select {i.*}, {uri.*} from Items as i LEFT OUTER JOIN usersReadItems as uri "
                + "on (i.item_id=uri.item_id and uri.username='"
                + username
                + "') "
                + "where i.item_id in "
                + "(select items4_.ITEM_ID from FEEDSUBSCRIBERS feedsubscr2_, CHANNELS channel3_, ITEMS items4_ "
                + "where feedsubscr2_.CHANNEL_ID=channel3_.CHANNEL_ID and channel3_.CHANNEL_ID=items4_.CHANNEL_ID and "
                + "(feedsubscr2_.username like '"
                + username
                + "')"
                + "and "
                + "(feedsubscr2_.category_id = " //here is the diff!
                + cat_id
                + ")) and i.date BETWEEN ? AND ? order by i.date DESC, i.item_id ASC LIMIT "
                + this.upperLimit;

        try {

            Query q = this.getSession().createSQLQuery(query,
                    new String[] { "i", "uri" },
                    new Class[] { Item.class, UserReadItem.class });

            q.setParameter(0, new Timestamp(since.getTime()), Hibernate.TIMESTAMP);
            
            q.setParameter(1, new Timestamp(new Date().getTime()), Hibernate.TIMESTAMP);
            java.util.Collection c = q.list();
            log.debug("Resultset: " + c.size());
            return this.simplifyCollection(c);

        } catch (DataAccessResourceFailureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //   (feedsubscr2_.channel_id =324)

    public Collection getItemsForUserByChannel(String username, Date since,
            int channel_id) throws IndexOutOfBoundsException {
        String query = "select {i.*}, {uri.*} from Items as i LEFT OUTER JOIN usersReadItems as uri "
                + "on (i.item_id=uri.item_id and uri.username='"
                + username
                + "') "
                + "where i.item_id in "
                + "(select items4_.ITEM_ID from FEEDSUBSCRIBERS feedsubscr2_, CHANNELS channel3_, ITEMS items4_ "
                + "where feedsubscr2_.CHANNEL_ID=channel3_.CHANNEL_ID and channel3_.CHANNEL_ID=items4_.CHANNEL_ID and "
                + "(feedsubscr2_.username like '"
                + username
                + "')"
                + "and "
                + "(feedsubscr2_.channel_id = " //here is the diff!
                + channel_id
                + ")) and i.date BETWEEN ? AND ? order by i.date DESC, i.item_id ASC LIMIT "
                + this.upperLimit;

        try {

            Query q = this.getSession().createSQLQuery(query,
                    new String[] { "i", "uri" },
                    new Class[] { Item.class, UserReadItem.class });

            q.setParameter(0, new Timestamp(since.getTime()), Hibernate.TIMESTAMP);
            q.setParameter(1, new Timestamp(new Date().getTime()), Hibernate.TIMESTAMP);
            log.debug(q.toString());
            java.util.Collection c = q.list();
            log.debug(q.toString());
            log.debug("Resultset: " + c.size());
            return this.simplifyCollection(c);

        } catch (DataAccessResourceFailureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Vereinfache die Collection, die von .createSQLQuery zur�ckgegeben wird.
     * Wenn UserReadItem ungleich null, setze Item.read=true;
     * 
     * 
     * @param c
     *            Collection from .createSQLQuery Statement with placeholders
     *            for Item.class,UserReadItem.class
     * @return Collection only with Item
     */
    private Collection simplifyCollection(Collection c) {

        Iterator ii = c.iterator();
        Object oo[];
        Item iitem = null;
        Collection result = new ArrayList(c.size());

        while (ii.hasNext()) {
            oo = (Object[]) ii.next();
            iitem = (Item) oo[0];
            if (oo[1] != null) {

                iitem.setRead(true);
                log.info("Read Item: " + iitem.getId() + iitem.getRead());
            } else {
                iitem.setRead(false);
            }
            result.add(iitem);
        }

        return result;

    }

    /**
     * Upper Limit ist the maximum size of a resultset ItemDAOHibernate should
     * return.
     * 
     * @return Returns the upperlimit.
     */
    public short getUpperLimit() {
        return upperLimit;
    }

    /**
     * @param upperlimit
     *            The upperlimit to set.
     */
    public void setUpperLimit(short upperlimit) {
        this.upperLimit = upperlimit;
    }
    
    public Collection getAllItems() {
        return getHibernateTemplate().find("from Item"); 
    }
}
