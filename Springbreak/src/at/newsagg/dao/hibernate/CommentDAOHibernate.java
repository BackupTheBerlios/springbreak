/*
 * Created on 30.03.2005
 * king
 * 
 */
package at.newsagg.dao.hibernate;

import java.util.Collection;

import net.sf.hibernate.Hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import at.newsagg.dao.CommentDAO;
import at.newsagg.model.Comment;

/**
 * @author Roland Vecera
 * @version
 * created on 30.03.2005 11:29:19
 *
 */
public class CommentDAOHibernate extends HibernateDaoSupport implements CommentDAO {

    
    private Log log = LogFactory.getLog(CommentDAOHibernate.class);

    /**
     * save a new comment.
     * 
     * AddedDate set to "now" with this method.
     * 
     * @param comment
     */
    public void savecomment(Comment comment) {
        comment.setAddedDate(new java.util.Date());
        getHibernateTemplate().save(comment);

        if (log.isDebugEnabled()) {
            log.debug("comment " + comment.getTitle() + " stored!");
        }
    }
    
    /**
     * Save or Update a given comment. 
     */
    public void saveOrUpdatecomment(Comment comment)
    {
        getHibernateTemplate().saveOrUpdate(comment);

        if (log.isDebugEnabled()) {
            log.debug("comment " + comment.getTitle() + " stored!");
        } 
    }

    /**
     * update a persisted comment. updates a comment, thats already in db.
     * 
     * @param comment
     */
    public void updatecomment(Comment comment) {
        getHibernateTemplate().update(comment);

        if (log.isDebugEnabled()) {
            log.debug("comment " + comment.getTitle() + " updated!");
        }
    }

    /**
     * Delete comment Object.
     * 
     * @param comment
     */
    public void removecomment(Comment comment) {

        getHibernateTemplate().delete(comment);
    }
    
    /**
     * Get all comments to a given channel order by addedDate (latest first).
     * 
     * @author Roland Vecera
     * @version
     * created on 30.03.2005 11:49:23
     *
     */
    public Collection getCommentsToChannel (int channel_id)
    {
        return getHibernateTemplate().find("from Comment c where c.channel.id like ? ordered by c.addedDate DESC",new Integer (channel_id),Hibernate.INTEGER); 
    }
    
    /**
     * Get Average rating of a given Channel
     * 
     */
    public float getAVGRatingToChannel (int channel_id) throws IndexOutOfBoundsException, NumberFormatException
    {
     return ((Float)getHibernateTemplate().find("Select AVG(com.stars) from Comment com where com.channel.id = ?", new Integer(channel_id), Hibernate.INTEGER).get(0)).floatValue();    
     
    }
    
    /**
     * Counts how many Comments to given Channel are in DB.
     * @param channel_id
     * @return
     */
    public int countCommentsToChannel (int channel_id)
    {
       return ((Integer)getHibernateTemplate().find("Select count (*) from Comment com where com.channel.id = ?",new Integer(channel_id),Hibernate.INTEGER).get(0)).intValue(); 
    }
    
    /**
     * Get all comments from a given user order by addedDate (latest first).
     * @author Roland Vecera
     * @version
     * created on 30.03.2005 11:54:31
     *
     */
    public Collection getCommentsByUser (int user_id)
    {
        return getHibernateTemplate().find("from Comment c where c.user.id like ? ordered by c.addedDate DESC",new Integer (user_id),Hibernate.INTEGER); 
    }
    
}
