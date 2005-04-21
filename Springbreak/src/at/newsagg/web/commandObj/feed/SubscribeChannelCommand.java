/*
 * Created on 18.04.2005
 * king
 * 
 */
package at.newsagg.web.commandObj.feed;

/**
 * @author king
 * @version
 * created on 18.04.2005 10:12:38
 *
 */
public class SubscribeChannelCommand {

    int id;
    int category;
    String locationString;
    
    
    
    /**
     * @return Returns the locationString.
     */
    public String getLocationString() {
        return locationString;
    }
    /**
     * @param locationString The locationString to set.
     */
    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }
    /**
     * @return Returns the cat.
     */
    
    /**
     * @return Returns the id.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return Returns the category.
     */
    public int getCategory() {
        return category;
    }
    /**
     * @param category The category to set.
     */
    public void setCategory(int category) {
        this.category = category;
    }
}
