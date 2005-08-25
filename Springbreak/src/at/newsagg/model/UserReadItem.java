/*
 * Created on 25.08.2005
 * king
 * 
 */
package at.newsagg.model;

import at.newsagg.model.parser.ItemIF;
import at.newsagg.model.parser.hibernate.Item;

/**
 * @author king
 * @version
 * created on 25.08.2005 10:25:31
 *
 *TODO: 
 *CREATE TABLE usersreaditems (id  int4, item_id  int4, username  VARCHAR(255), read boolean)
 *und Hibernate Mapping automatisieren (XDOCLET);
 */
public class UserReadItem extends BaseObject {
    
    private long id = -1;
    private Item item;
    private User user;
    private boolean read;
    
    

    /**
     * @return Returns the id.
     */
    public long getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @return Returns the item.
     */
    public Item getItem() {
        return item;
    }
    /**
     * @param item The item to set.
     */
    public void setItem(Item item) {
        this.item = item;
    }
    /**
     * @return Returns the read.
     */
    public boolean isRead() {
        return read;
    }
    /**
     * @param read The read to set.
     */
    public void setRead(boolean read) {
        this.read = read;
    }
    /**
     * @return Returns the user.
     */
    public User getUser() {
        return user;
    }
    /**
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    public boolean equals(Object other) {
        
        return super.equals(other);
    }
//        if (this==other) return true;
//        if ( !(other instanceof UserReadItem) ) return false;
//        final UserReadItem that = (UserReadItem) other;
//        return this.getUser().getUsername().equals(that.getUser().getUsername()) && this.getItem().getId() == that.getItem().getId();
//        }
    
    
    
    public int hashCode() {
        return super.hashCode();
        
        }

    
}
