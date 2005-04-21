/*
 * Created on 30.03.2005
 * king
 * 
 */
package at.newsagg.dao;

import java.util.Collection;

import at.newsagg.model.Comment;

/**
 * @author Roland Vecera
 * @version
 * created on 30.03.2005 11:33:46
 *
 */
public interface CommentDAO {
    /**
     * save a new comment.
     * 
     * @param comment
     */
    public void savecomment(Comment comment);

    public void saveOrUpdatecomment(Comment comment);

    /**
     * update a persisted comment. updates a comment, thats already in db.
     * 
     * @param comment
     */
    public void updatecomment(Comment comment);

    /**
     * Delete comment Object.
     * 
     * @param comment
     */
    public void removecomment(Comment comment);
    
    /**
     * Get all comments from a given user order by addedDate (latest first).
     */
    public Collection getCommentsByUser (int user_id);
    
    /**
     * Get all comments to a given channel order by addedDate (latest first).
     * 
     */
    public Collection getCommentsToChannel (int channel_id);
    
    /**
     * Get Average rating of a given Channel
     * 
     */
    public float getAVGRatingToChannel (int channel_id) throws IndexOutOfBoundsException, NumberFormatException;
    /**
     * Counts how many Comments to given Channel are in DB.
     * @param channel_id
     * @return
     */
    public int countCommentsToChannel (int channel_id);
}