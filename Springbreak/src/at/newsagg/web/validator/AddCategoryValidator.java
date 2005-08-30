/*
 * Created on 30.08.2005
 * king
 * 
 */
package at.newsagg.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import at.newsagg.web.commandObj.feed.AddCategoryCommand;

/**
 * @author king
 * @version
 * created on 30.08.2005 09:51:42
 *
 */
public class AddCategoryValidator implements org.springframework.validation.Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(AddCategoryCommand.class);
        
        
    }
    
    public void validate(Object category, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"title","category.emptyTitle","Title not empty");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors,"red","category.emptyRed", "Red not empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"green","category.emptyGreen", "Green not empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"red","category.emptyBlue", "Blue not empty");
        
        AddCategoryCommand c = (AddCategoryCommand)category;
        int col;
        
        try {
            col = Integer.parseInt(c.getRed());
            if ((col < 0) || (col > 255))
            {
                errors.rejectValue("red","category.invalidNumRed", "Red must be in the Range 0 to 255");
                
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("red","category.invalidNumRed", "Red must be in the Range 0 to 255");
        }
        
        //Same for Green
        try {
            col = Integer.parseInt(c.getGreen());
            if ((col < 0) || (col > 255))
            {
                errors.rejectValue("green","category.invalidNumGreen", "Green must be in the Range 0 to 255");
                
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("green","category.invalidNumGreen", "Green must be in the Range 0 to 255");
        }
        
        //same for Blue
        try {
            col = Integer.parseInt(c.getBlue());
            if ((col < 0) || (col > 255))
            {
                errors.rejectValue("blue","category.invalidNumBlue", "Blue must be in the Range 0 to 255");
                
            }
        } catch (NumberFormatException e) {
            errors.rejectValue("blue","category.invalidNumBlue", "Blue must be in the Range 0 to 255");
        }
        
        
    }
}
