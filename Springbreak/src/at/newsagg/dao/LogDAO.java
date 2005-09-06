/*
 * Created on 01.05.2005
 * king
 * 
 */
package at.newsagg.dao;

import java.util.Collection;

/**
 * @author king
 * @version
 * created on 01.05.2005 20:10:04
 *
 */
public interface LogDAO {
    /**
     * Store an LogSubscripers or LogPostings Object
     * 
     * @param o given Object 
     */
    public void save(Object o);

    /**
     * Returns entries of a channel in LogSubscribers order by date DESC.
     * 
     * @param channel_id id of a given Channel.
     * @return
     */
    public java.util.Collection getLogSubscribers(int channel_id);

    /**
     * Returns entries of a channel in LogPostings order by date DESC
     * @param channel_id
     * @return
     */
    public Collection getLogPostings(int channel_id);
    
    /**
     * @return Returns the upperLimit.
     */
    public int getUpperLimit();
    /**
     * @param upperLimit The upperLimit to set.
     */
    public void setUpperLimit(int upperLimit);
}