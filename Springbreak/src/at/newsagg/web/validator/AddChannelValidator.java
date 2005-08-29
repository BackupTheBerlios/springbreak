/*
 * Created on 29.08.2005
 * king
 * 
 */
package at.newsagg.web.validator;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import at.newsagg.model.parser.hibernate.Channel;

/**
 * @author king
 * @version
 * created on 29.08.2005 17:11:22
 *
 */
public class AddChannelValidator implements Validator {

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clazz) {
        return clazz.equals(Channel.class);
        
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object channel, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "locationString", "channel.emptyURL",
                "URL not empty");
        
        try {
            URL url = new URL (((Channel)channel).getLocationString());
        } catch (MalformedURLException e) {
            errors.rejectValue("locationString","channel.malformURL","URL ist malformed");
        }
       

    }

}
