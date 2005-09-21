package at.newsagg.web; 

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import at.newsagg.model.Category;
import at.newsagg.model.User;
import at.newsagg.service.UserManager;
import at.newsagg.web.commandObj.RegisterCommand;

;
  
/**
 * @author szabolcs
 * 
 * 
 */
public class RegisterController extends SimpleFormController { 
	private static Log log = LogFactory.getLog(RegisterController.class); 
	private UserManager mgr = null;
	
	private String  standardCategoryTitle = null;
	private String  standardCategoryHTMLColor = null;
	private List standardFeeds = new ArrayList(1);
	
	
	
	/**
	 *  Set up a custom property editor for converting Longs 
	 */ 
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) { 
		NumberFormat nf = NumberFormat.getNumberInstance(); 
		binder.registerCustomEditor(Long.class, null,new CustomNumberEditor(Long.class, nf, true)); 
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, 
									HttpServletResponse response, 
									Object command, BindException errors) 
	throws Exception { 
		RegisterCommand userFormCmd = (RegisterCommand) command;
		User registerData = userFormCmd.getUser();
		
		// check if username exists
		User user =  mgr.checkUser(registerData.getUsername());
		if (user != null) {
			// if it exists return error message
			//return new ModelAndView(new RedirectView(getFormView()), "messageRegister",  getMessageSourceAccessor().getMessage("register.usernaAlreadyExists"));
			errors.rejectValue("user.username", "username exists", null, getMessageSourceAccessor().getMessage("register.usernaAlreadyExists")); 
			return showForm(request, response, errors); 
		} else {
			// if not create user 
			user = mgr.saveUser(registerData);
			
			//Setup Standard CATEGORY and StandardFEEDS to new user
			Category cat = new Category();
			
			cat.setTitle(this.getStandardCategoryTitle());
			cat.setUser(user);
			cat.setHtmlColor(this.getStandardCategoryHTMLColor());
			mgr.setupNewUser(user,cat,this.standardFeeds);
			
			//and goto login screen
			return new ModelAndView(getSuccessView(), "user", registerData);
		}
		 
	} 
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException { 
		RegisterCommand cmd = new RegisterCommand(); 
		cmd.setUser(new User());
		return cmd;
	} 
	
	
	public String getStandardCategoryHTMLColor() {
		if (standardCategoryHTMLColor == null)
			return "#82a6d2";
		else
			return standardCategoryHTMLColor;
	}

	public void setStandardCategoryHTMLColor(String standardCategoryHTMLColor) {
		this.standardCategoryHTMLColor = standardCategoryHTMLColor;
	}

	public String getStandardCategoryTitle() {
		if (standardCategoryTitle == null)
			return "My Cat";
		else
			return standardCategoryTitle;
	}

	public void setStandardCategoryTitle(String standardCategoryTitle) {
		this.standardCategoryTitle = standardCategoryTitle;
	}

	public List getStandardFeeds() {
		return standardFeeds;
	}

	public void setStandardFeeds(List standardFeeds) {
		this.standardFeeds = standardFeeds;
	}

	public void setUserManager(UserManager userManager) { 
		this.mgr = userManager; 
	} 
	
	public UserManager getUserManager() { 
		return this.mgr; 
	}
} 