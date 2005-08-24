/*
 * Created on 18.04.2005
 * king
 * 
 */
package at.newsagg.web.feed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.newsagg.dao.ChannelDAO;

/**
 * @author Roland Vecera
 * @version
 * created on 18.04.2005 12:24:25
 *
 *
 * Simply returns all Channels in DB. 
 */
public class ViewChannelsController implements Controller { 
    	private static Log log = LogFactory.getLog(ViewChannelsController.class); 
    	private ChannelDAO channelDAO;
    	
    	
    	 
    	
        /**
         * @return Returns the channelDAO.
         */
        public ChannelDAO getChannelDAO() {
            return channelDAO;
        }
        /**
         * @param channelDAO The channelDAO to set.
         */
        public void setChannelDAO(ChannelDAO channelDAO) {
            this.channelDAO = channelDAO;
        }
    	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
    	    ModelAndView mv =  new ModelAndView("allChannels", "channels", channelDAO.getChannels());
    	    mv.addObject("stars",new Integer(1));
    	    return mv;
    	}
    }


