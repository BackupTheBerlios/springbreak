/*
 * Created on 20.04.2005
 * king
 * 
 */
package at.newsagg.taglib;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;

/**
 * Attribute-Constrains on Stars-Tag.
 * 
 * @deprecated For some Reason it doesn't work with EL! 
 * Validation directly Coded to at.newsagg.taglib.Stars. 
 * 
 * 
 * 
 * 
 * value >= 0
 * numberOfStars > 1
 * value <= numberOfStars
 * 
 * @author Roland Vecera
 * @version
 * created on 20.04.2005 11:00:06
 *
 */
public class StarsTEI extends TagExtraInfo {
    public boolean isValid(TagData data)
    {
        /*
         * value must be a Float >= 0
         */
       Object o = data.getAttribute("value");
       float value = Float.parseFloat((String)o);
       if ((o == null) ||  (value < 0))
       {
           return false;
       }
       
       /*
        *numberOfStars must be 1 at least! 
        */
       o = data.getAttribute("numberOfStars");
       byte numberStars = Byte.parseByte((String)o);
       if ((o == null) || (numberStars < 1))
       {
        return false;    
       } 
       
       /*
        * if value > numberOfStars to display, something is wrong
        */
       if (value > numberStars)
       {
        return false;    
       }
       
       
       return true;
               
    }
}

