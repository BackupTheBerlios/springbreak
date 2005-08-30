/*
 * Created on 30.08.2005
 * king
 * 
 */
package at.newsagg.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import at.newsagg.model.Comment;

/**
 * @author king
 * @version
 * created on 30.08.2005 11:26:35
 *
 */
public class AddCommentValidator implements Validator {

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
        return clazz.equals(Comment.class);
       
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object comment, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"title","comment.emptyTitle","Title not empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"text","comment.emptyText","Text not empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"stars","comment.emptyStars","Stars not empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"channel_id","comment.emptyChannel","Channel not empty");
        
        Comment c = (Comment)comment;
        
        if ((c.getStars().byteValue() < 0) || (c.getStars().byteValue() > 5))
            errors.rejectValue("stars","comment.starsInvalidNum","Stars must be in range 0 to 5");
        
       
    }

}
