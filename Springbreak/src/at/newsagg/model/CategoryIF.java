/*
 * Created on 31.03.2005
 * king
 * 
 */
package at.newsagg.model;

/**
 * @author king
 * @version
 * created on 31.03.2005 12:25:04
 *
 */
public interface CategoryIF {
    /**
     * @hibernate.property
     *  column="HTMLCOLOR"
     *  not-null="true"
     *  
     * @return Returns the htmlColor.
     */
    public String getHtmlColor();

    /**
     * @param htmlColor The htmlColor to set.
     */
    public void setHtmlColor(String htmlColor);

    /**
     * @hibernate.id
     *  generator-class="native"
     *  column="CATEGORY_ID"
     *  type="integer"
     *  unsaved-value="-1"
     *
     * @return integer representation of identity.
     */
    public int getId();

    /**
     * @param id The id to set.
     */
    public void setId(int id);

    /**
     * @hibernate.property
     *  column="TITLE"
     *  not-null="true"
     * @return Returns the title.
     */
    public String getTitle();

    /**
     * @param title The title to set.
     */
    public void setTitle(String title);

    /**
     * @hibernate.many-to-one
     *  column="USER_ID"
     *  class="at.newsagg.model.User"
     *  not-null="true"
     *
     * @return parent user.
     */
    public User getUser();

    /**
     * @param user The user to set.
     */
    public void setUser(User user);

    /**
     * TODO: die ist scheiﬂe!
     */
    public boolean equals(Object other);

    public int hashCode();
}