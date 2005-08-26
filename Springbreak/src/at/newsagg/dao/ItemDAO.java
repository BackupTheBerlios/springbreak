/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.dao;

import java.util.Collection;
import java.util.Date;

import at.newsagg.model.UserReadItem;
import at.newsagg.model.parser.hibernate.Item;

/**
 * @author king
 * @version created on 26.03.2005 11:14:09
 *  
 */
public interface ItemDAO {
    /**
     * Returns count of persistent Items with given url.
     * 
     * Should return 0 or 1, because the URL should be the natural key to item.
     * 
     * @param url
     *            url-value of an FeedItem.
     * @return
     */
    public int countItemwithURL(String url);

    /**
     * Get Item by id.
     * 
     * @param id
     * @return
     */
    public Item getItem(int id);

    /**
     * Get Item by Link.
     * 
     * @param id
     * @return
     */
    public Item getItemByLink(String link);

    /**
     * Returns the Id of an Item with the given url.
     * 
     * @param url
     *            url-value of an FeedItem.
     * @return
     */
    public long getIDfromItemwithURL(String url);

    public void saveUserReadItem(UserReadItem i);

    public Collection getItemsForUser(String username, int limit)
            throws IndexOutOfBoundsException;

    public Collection getItemsForUserByCategory(String username, int limit,
            int cat_id) throws IndexOutOfBoundsException;

    public Collection getItemsForUserByChannel(String username, int limit,
            int channel_id) throws IndexOutOfBoundsException;

    public Collection getItemsForUser(String username, Date since)
            throws IndexOutOfBoundsException;

    public Collection getItemsForUserByCategory(String username, Date since,
            int cat_id) throws IndexOutOfBoundsException;

    public Collection getItemsForUserByChannel(String username, Date since,
            int channel_id) throws IndexOutOfBoundsException;

    public int removeUserReadItem(String username, long itemid);

    public short getUpperLimit();

    public void setUpperLimit(short upperlimit);

}