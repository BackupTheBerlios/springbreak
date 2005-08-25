/*
 * Created on 21.03.2005
 * Roland
 * 
 */
package at.newsagg.dao;

import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import at.newsagg.model.User;
import at.newsagg.model.UserReadItem;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.model.parser.hibernate.Item;

/**
 * @author Roland Vecera
 * 
 * Testing ChannelDAOHibernate
 * 
 * @version
 * created on 21.03.2005 13:39:34
 *
 */
public class ChannelDAOTest extends TestCase {

    protected static Log log = LogFactory.getLog(ChannelDAOTest.class);
    protected ApplicationContext ctx = null;
    
    public ChannelDAOTest()
    {
        String[] paths = {"applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths); 
    }
    /**
     * TODO: Make sure, that DB is filled
     * 
     */
    
    /**
     * testing equals methode.
     * testing getChannels().
     * 
     * 2 Channels are equal if they have the same lowercase-URL
     */
    public void testEqualsMethod() throws Exception
    {
     ChannelDAO c= (ChannelDAO)ctx.getBean("channelDAO");
     
     Channel channel =(Channel)c.getChannels().get(0);
     Channel channel2 =(Channel)c.getChannels().get(0);
     
     assertTrue(channel.equals(channel2));
     
     channel.setLocation(null);
     channel2.setLocation(null);
     
     assertTrue(channel.equals(channel2));
     
     channel.setLocationString("http://vecego.0wnz.at/rss.php");
     channel2.setLocationString("http://vecego.0wnz.at/rss.php");
     
     assertTrue(channel.equals(channel2));
     
     channel.setLocationString("http://vecego.0WNZ.at/rss.php");
     channel2.setLocationString("http://VECEGO.0wnz.at/rss.php");
     
     assertTrue(channel.equals(channel2));
     
     channel.setLocationString("http://vecego.0WNZ.at/rss.php");
     channel2.setLocationString("http://0wnz.at/rss.php");
     
     assertFalse(channel.equals(channel2));
     
     
    
    }
    
    public void testUserReadItem () throws Exception
    {
        ItemDAO itemDAO = (ItemDAO)ctx.getBean("itemDAO");
        
        UserReadItem urs = new UserReadItem();
        User user = new User ();
        user.setUsername("vec");
        Item item = new Item();
        item.setId(16);
        urs.setItem(item);
        urs.setUser(user);
        urs.setRead(true);
        
        itemDAO.saveUserReadItem(urs);
        
        Iterator i = itemDAO.getItemsForUser("vec",10).iterator();
        while (i.hasNext())
        {
            
          System.out.println(((Item)i.next()).getId());
        }
    }
}
