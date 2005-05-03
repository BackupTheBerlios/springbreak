/*
 * Created on 01.05.2005
 * king
 * 
 */
package at.newsagg.model.logstatistics;

import java.util.Date;

import at.newsagg.model.parser.hibernate.Channel;

/**
 * @author king
 * @version
 * created on 01.05.2005 10:56:33
 *
 */
public interface LogSubscribersIF {
    /**
     * @return Returns the date.
     */
    public Date getDate();

    /**
     * @param date The date to set.
     */
    public void setDate(Date date);

    public Channel getChannel();
    /**
     * @param feed_id The feed_id to set.
     */
    public void setChannel(Channel c);

    /**
     * 
     * @return Returns the num_subscripers.
     */
    public int getNum_subscribers();

    /**
     * @param num_subscripers The num_subscripers to set.
     */
    public void setNum_subscribers(int num_subscripers);
}