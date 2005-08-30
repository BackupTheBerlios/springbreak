/*
 * Created on 30.03.2005
 * king
 * 
 */
package at.newsagg.web.feed;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.UserDAO;
import at.newsagg.model.User;
import at.newsagg.model.parser.ParseException;
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

    private ChannelDAO channelDAO;

    /**
     * @return Returns the channelDAO.
     */
    public ChannelDAO getChannelDAO() {
        return channelDAO;
    }

    /**
     * @param channelDAO
     *            The channelDAO to set.
     */
    public void setChannelDAO(ChannelDAO channelDAO) {
        this.channelDAO = channelDAO;
    }

    /**
     * @return Returns the userDAO.
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * @param userDAO
     *            The userDAO to set.
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

        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(
                request, "userSession");

        Channel channel = (Channel) command;
        log.info("Last try: " + channel.getLocationString());

        try {
            channel.setId(channelDAO.getChannel(channel.getLocation()).getId());
        } catch (IndexOutOfBoundsException e) {
            //nothing wrong: this channel is just some new one!
        } 
        
        channel.setItems(new ArrayList());

       
            channel = (Channel) parserService.runUpdateOnChannel(channel);
        
        channelDAO.saveOrUpdateChannel(channel);

        ModelAndView mav = new ModelAndView(getSuccessView(), "channel",
                channel);
        
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
     * @param userManager
     *            The userManager to set.
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}