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
 * created on 01.05.2005 11:07:25
 *
 */
public interface LogPostingsIF {
    /**
     * @return Returns the channel_id.
     */
    public Channel getChannel();

    /**
     * @param channel The channel_id to set.
     */
    public void setChannel(Channel channel);

    /**
     * @return Returns the date.
     */
    public Date getDate();

    /**
     * @param date The date to set.
     */
    public void setDate(Date date);

    /**
     * @return Returns the num_postings.
     */
    public int getNum_postings();

    /**
     * @param num_postings The num_postings to set.
     */
    public void setNum_postings(int num_postings);

    /**
     * @return Returns the timespan.
     */
    public int getTimespan();

    /**
     * @param timespan The timespan to set.
     */
    public void setTimespan(int timespan);
}