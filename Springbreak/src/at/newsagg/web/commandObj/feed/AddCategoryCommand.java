/*
 * Created on 23.08.2005
 * king
 * 
 */
package at.newsagg.web.commandObj.feed;

/**
 * @author Roland Vecera
 * @version
 * created on 23.08.2005 11:08:33
 *
 */
public class AddCategoryCommand {
    
    private String title;
    private String red;
    private String green;
    private String blue;
    

    /**
     * @return Returns the blue.
     */
    public String getBlue() {
        return blue;
    }
    /**
     * @param blue The blue to set.
     */
    public void setBlue(String blue) {
        this.blue = blue;
    }
    /**
     * @return Returns the green.
     */
    public String getGreen() {
        return green;
    }
    /**
     * @param green The green to set.
     */
    public void setGreen(String green) {
        this.green = green;
    }
    /**
     * @return Returns the red.
     */
    public String getRed() {
        return red;
    }
    /**
     * @param red The red to set.
     */
    public void setRed(String red) {
        this.red = red;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
