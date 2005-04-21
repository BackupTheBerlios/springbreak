/*
 * Created on 20.04.2005
 * king
 * 
 */
package at.newsagg.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Code for Star-Taglib ala Amazon-Bewertung.
 * 
 * Examples of usage, if you are using springbreak.tld with prefix springbreak:
 * 
 * <springbreak:stars value="${val}" numberOfStars="5" classDiv="div"
 * classImg="img" starFull="images/star/star.gif"
 * starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 * <springbreak:stars value="0" numberOfStars="5" classDiv="div" classImg="img"
 * starFull="images/star/star.gif" starHalf="images/star/starhalf.gif"
 * starEmpty="images/star/starempty.gif" /> <springbreak:stars value="3"
 * numberOfStars="5" classDiv="div" classImg="img"
 * starFull="images/star/star.gif" starHalf="images/star/starhalf.gif"
 * starEmpty="images/star/starempty.gif" /> <springbreak:stars value="5"
 * numberOfStars="5" classDiv="div" classImg="img"
 * starFull="images/star/star.gif" starHalf="images/star/starhalf.gif"
 * starEmpty="images/star/starempty.gif" /> <springbreak:stars value="4"
 * numberOfStars="5" classDiv="div" classImg="img"
 * starFull="images/star/star.gif" starHalf="images/star/starhalf.gif"
 * starEmpty="images/star/starempty.gif" /> <springbreak:stars value="1.27"
 * numberOfStars="5" classDiv="div" classImg="img"
 * starFull="images/star/star.gif" starHalf="images/star/starhalf.gif"
 * starEmpty="images/star/starempty.gif" />
 * 
 * Path to image is relative Path from jsp-file without leading "/"!
 * 
 * @author Roland Vecera
 * @version created on 20.04.2005 10:40:24
 *  
 */
public class Stars extends TagSupport {

    private float value;

    private byte numberOfStars;

    private String classDiv;

    private String classImg;

    private String starFull;

    private String starHalf;

    private String starEmpty;

    public int doStartTag() throws JspException {
        try {

            pageContext.getOut().print(
                    "<div "
                            + (classDiv == null ? "" : " class=\"" + classDiv
                                    + "\"") + ">");
            if (validate()) {
                //For each Star to display.
                for (int i = 0; i < numberOfStars; i++) {
                    //Display full star
                    if (value >= 1) {
                        pageContext.getOut().print(
                                "<img src=\""
                                        + "./"
                                        + starFull
                                        + "\" alt=\"fullstar\""
                                        + (classImg == null ? "" : " class=\""
                                                + classImg + "\"") + "/>");
                    } else {
                        // BOUNDARY DEFINITIONS: 	from 0.76-1 show fullstar
                        //							from 0.26-0.75 show half star
                        			//				from 0 - 0.25 show empty star
                        //Display a "rounded" fullstar
                        if (value > 0.75) {
                            pageContext.getOut().print(
                                    "<img src=\""
                                            + "./"
                                            + starFull
                                            + "\" alt=\"fullstar\""
                                            + (classImg == null ? ""
                                                    : " class=\"" + classImg
                                                            + "\"") + "/>");
                        }

                        else {
                            //Display a Halfstar
                            if (value > 0.25) {
                                pageContext.getOut().print(
                                        "<img src=\""
                                                + "./"
                                                + starHalf
                                                + "\" alt=\"halfstar\""
                                                + (classImg == null ? ""
                                                        : " class=\""
                                                                + classImg
                                                                + "\"") + "/>");
                            } else {
                                //Fill up with empty stars
                                pageContext.getOut().print(
                                        "<img src=\""
                                                + "./"
                                                + starEmpty
                                                + "\" alt=\"emptystar\""
                                                + (classImg == null ? ""
                                                        : " class=\""
                                                                + classImg
                                                                + "\"") + "/>");
                            }
                        }
                    }
                    value--;

                }
            } else {
                //no valid input for meaningful output
                pageContext.getOut().print("no calc");
            }

            pageContext.getOut().print("</div>");
        } catch (Exception ex) {
            throw new JspTagException("at.newsagg.taglib.Stars: "
                    + ex.getMessage());
        }
        return SKIP_BODY;
    }

    public int doEndTag() {

        return EVAL_PAGE;
    }

    
    //value must be >= 0, numberOFStars at least 1 and value <= numberOfStars
    //returns false if one of these constraints
    private boolean validate() {
        if ((value < 0)) {
            return false;
        }
        if (numberOfStars < 1) {
            return false;
        }
        if (value > numberOfStars) {
            return false;
        }
        return true;

    }

    /**
     * @return Returns the classDiv.
     */
    public String getClassDiv() {
        return classDiv;
    }

    /**
     * @param classDiv
     *            The classDiv to set.
     */
    public void setClassDiv(String classDiv) {
        this.classDiv = classDiv;
    }

    /**
     * @return Returns the classImg.
     */
    public String getClassImg() {
        return classImg;
    }

    /**
     * @param classImg
     *            The classImg to set.
     */
    public void setClassImg(String classImg) {
        this.classImg = classImg;
    }

    /**
     * @return Returns the numberOfStars.
     */
    public byte getNumberOfStars() {
        return numberOfStars;
    }

    /**
     * @param numberOfStars
     *            The numberOfStars to set.
     */
    public void setNumberOfStars(byte numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    /**
     * @return Returns the starEmpty.
     */
    public String getStarEmpty() {
        return starEmpty;
    }

    /**
     * @param starEmpty
     *            The starEmpty to set.
     */
    public void setStarEmpty(String starEmpty) {
        this.starEmpty = starEmpty;
    }

    /**
     * @return Returns the starFull.
     */
    public String getStarFull() {
        return starFull;
    }

    /**
     * @param starFull
     *            The starFull to set.
     */
    public void setStarFull(String starFull) {
        this.starFull = starFull;
    }

    /**
     * @return Returns the starHalf.
     */
    public String getStarHalf() {
        return starHalf;
    }

    /**
     * @param starHalf
     *            The starHalf to set.
     */
    public void setStarHalf(String starHalf) {
        this.starHalf = starHalf;
    }

    /**
     * @return Returns the value.
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(float value) {
        this.value = value;
    }
}
