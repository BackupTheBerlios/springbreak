/*
 * Created on 30.03.2005
 * king
 * 
 */
package at.newsagg.web.feed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.UserDAO;
import at.newsagg.model.User;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.service.ParserCronJobService;
import at.newsagg.service.UserManager;
import at.newsagg.web.UserSession;

/**
 * @author Roland Vecera
 * @version created on 30.03.2005 13:20:29
 *  
 */
public class AddChannelController extends SimpleFormController {
    private static Log log = LogFactory.getLog(AddChannelController.class);

    private ParserCronJobService parserService;
    private UserManager userManager;
    private UserDAO userDAO;
 
    /**
     * @return Returns the userDAO.
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }
    /**
     * @param userDAO The userDAO to set.
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    /**
     * Set up a custom property editor for converting Longs
     */
//    protected void initBinder(HttpServletRequest request,
//            ServletRequestDataBinder binder) {
//        NumberFormat nf = NumberFormat.getNumberInstance();
//        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(
//                Long.class, nf, true));
//    }

    /**
     * Takes the user input on a Channel (at least an URL), parses and persists
     * the channel.
     * 
     * Returns persistes Channel-object to ModelandView.
     */
    public ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {

        
        Channel channel = (Channel) command;
        /**
         * TODO: hier sollte validiert sein, daﬂ channel.getLocation() ein valid
         * URL ist!
         */

        channel = (Channel)parserService.runNewChannel(channel);
        log.info("Channel " + channel.getLocation().toString()
                + " stored as channel_id " + channel.getId());
        
        ModelAndView mav = new ModelAndView(getSuccessView(),"channel",channel);
        //Add User's categories to model!
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        
        User user = userDAO.getUser(userSession.getUserData().getUsername());
        mav.addObject("user", user);
        
        return mav;

    }

    /**
     * @return Returns the parserService.
     */
    public ParserCronJobService getParserService() {
        return parserService;
    }

    /**
     * @param parserService
     *            The parserService to set.
     */
    public void setParserService(ParserCronJobService parserService) {
        this.parserService = parserService;
    }
    /**
     * @return Returns the userManager.
     */
    public UserManager getUserManager() {
        return userManager;
    }
    /**
     * @param userManager The userManager to set.
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}