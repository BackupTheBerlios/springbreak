/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
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
 * @version
 * created on 26.03.2005 11:00:05
 *
 */
public class ItemDAOHibernate extends HibernateDaoSupport implements ItemDAO{
    private Log log = LogFactory.getLog(ItemDAOHibernate.class);
    
    /**
     * Returns count of persistent Items with given url.
     * 
     * Should return 0 or 1, because the URL should be the natural key to item.
     *  
     * @param url url-value of an FeedItem.
     * @return
     */
    public int countItemwithURL (String url)
    {
        log.debug(url);
        return ((Integer)getHibernateTemplate().find("select count (*) from Item i where i.link like ?",url,Hibernate.STRING).get(0)).intValue();  
    }
    /**
     * Get Item by id.
     * @param id
     * @return
     */
    public Item getItem(int id) {
        return (Item) getHibernateTemplate().load(Item.class,
                new Integer(id));
    }
    
    /**
     * Get Item by Link.
     * 
     * Returns null when not found.
     * 
     * @param id
     * @return
     */
    public Item getItemByLink(String link) throws IndexOutOfBoundsException
    {
        return (Item)getHibernateTemplate().find("from Item u where u.link like ?",link,Hibernate.STRING).get(0);    
        
    }
    
    /**
     * Returns the Id of an Item with the given url.
     * 
     * returns -1 if no Item was found.
     * 
     * @param url
     * @return
     */
    public long getIDfromItemwithURL (String url)  
    {
        try {
            return ((Long)getHibernateTemplate().find("select i.id from Item i where i.link like ?",url,Hibernate.STRING).get(0)).longValue();
        } catch (IndexOutOfBoundsException e) {
            return -1;
        } 
    }
    
    public void saveUserReadItem(UserReadItem i) {
        getHibernateTemplate().save(i);

        if (log.isDebugEnabled()) {
            log.debug("UserReadItem " + i.getId()+" stored!");
        }
    }
    
    public int removeUserReadItem (String username, long itemid)
    {
       return getHibernateTemplate().delete("from UserReadItem as uri where uri.user.username like ? and uri.item.id = ?", new Object[]{username, new Long(itemid)},new Type[]{Hibernate.STRING, Hibernate.LONG});
    }
    
    public Collection getItemsForUser(String username, int limit) throws IndexOutOfBoundsException
    {
       // System.out.println("hallo!");
       // Object[] o = { username, username/*, new Boolean(true) */};
       // net.sf.hibernate.type.Type[] t = { Hibernate.STRING, Hibernate.STRING /*, Hibernate.BOOLEAN*/};
        //"from FeedSubscriber f where f.user.username like ? and f.channel.items in 16"
        //"from Item i, FeedSubscriber f, UserReadItem uri where i in (Select f.channel.items where f.user.username like ?)"
        //return getHibernateTemplate().find("select DISTINCT i.id from Item i, UserReadItem uri where i in (select elements(f1.channel.items) from FeedSubscriber f1 where f1.user.username like ?) and i.id = uri.item.id and uri.user.username like ? and uri.read = ?",o,t);    
        //return getHibernateTemplate().find("select * from Item as i LEFT OUTER JOIN UserReadItem as uri ON (i.id=uri.item.id)");   
//        String query = "select i.item_id AS {item.id} from items as i LEFT OUTER JOIN usersreaditems u ON (i.item_id = u.item_id AND u.username like 'vec')";
   //     and(item0_.ITEM_ID in(select items4_.ITEM_ID from FEEDSUBSCRIBERS feedsubscr2_, CHANNELS channel3_, ITEMS items4_ where feedsubscr2_.CHANNEL_ID=channel3_.CHANNEL_ID and channel3_.CHANNEL_ID=items4_.CHANNEL_ID and ((feedsubscr2_.username like 'vec' ))))
        
        String query= "select {i.*}, {uri.*} from Items as i LEFT OUTER JOIN usersReadItems as uri "+
        "on (i.item_id=uri.item_id and uri.username='"+username+"') "+
        "where i.item_id in "+
        "(select i.ITEM_ID from FEEDSUBSCRIBERS feedsubscr2_, CHANNELS channel3_, ITEMS items4_ "+
        "where feedsubscr2_.CHANNEL_ID=channel3_.CHANNEL_ID and channel3_.CHANNEL_ID=items4_.CHANNEL_ID and "+
        "(feedsubscr2_.username like '"+username+"')) order by i.date DESC LIMIT "+limit;
        
       
        try {
            java.util.Collection c= this.getSession().createSQLQuery(query,new String[] {"i","uri"},new Class[] {Item.class,UserReadItem.class}).list();
            log.info ("Resultset: "+c.size());
            Iterator ii = c.iterator();
            Object oo [];
            Item iitem = null;
            Collection result = new ArrayList(limit);
            
            while (ii.hasNext())
            {
                oo = (Object[])ii.next();
                iitem = (Item)oo[0];
                if (oo[1] != null)
                {
                    
                    iitem.setRead(true);
                    log.info("Read Item: "+iitem.getId()+iitem.getRead());
                }
                else
                {
                    iitem.setRead(false);
                  }
                result.add(iitem);
            }
            
            
            return result;
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
        //return getHibernateTemplate().find("select {i.*}, {uri.*} from Item as i LEFT OUTER JOIN i.usersReadItem as uri on (i.id=uri.item.id and uri.user.username='vec' )where i in (select elements(f1.channel.items) from FeedSubscriber f1 where f1.user.username like 'vec')  order by order by i.date DESC LIMIT 10");
    }

}
