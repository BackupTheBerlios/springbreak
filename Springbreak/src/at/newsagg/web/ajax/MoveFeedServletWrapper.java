/*
 * Created on 26.08.2005
 * king
 * 
 */
package at.newsagg.web.ajax;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import at.newsagg.dao.FeedSubscriberDAO;

/**
 * @author Roland Vecera
 * @version created on 25.08.2005 18:25:38
 *  
 */
public class MoveFeedServletWrapper extends AbstractController implements
        BeanNameAware, InitializingBean, DisposableBean {

    private FeedSubscriberDAO feedSubscriberDAO;

    private Class servletClass;

    private String servletName;

    private Properties initParameters = new Properties();

    private String beanName;

    private Servlet servletInstance;

    /**
     * Set the class of the servlet to wrap. Needs to implement
     * <code>javax.servlet.Servlet</code>.
     * 
     * @see javax.servlet.Servlet
     */
    public void setServletClass(Class servletClass) {
        this.servletClass = servletClass;
    }

    /**
     * Set the name of the servlet to wrap. Default is the bean name of this
     * controller.
     */
    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    /**
     * Specify init parameters for the servlet to wrap, as name-value pairs.
     */
    public void setInitParameters(Properties initParameters) {
        this.initParameters = initParameters;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void afterPropertiesSet() throws Exception {

        if (this.servletClass == null) {
            throw new IllegalArgumentException("servletClass is required");
        }
        if (!Servlet.class.isAssignableFrom(this.servletClass)) {
            throw new IllegalArgumentException("servletClass ["
                    + this.servletClass.getName()
                    + "] needs to implement interface [javax.servlet.Servlet]");
        }
        if (this.servletName == null) {
            this.servletName = this.beanName;
        }
        this.servletInstance = (Servlet) this.servletClass.newInstance();

        /*
         * Diff ServletWrappingController Roland Vecera
         */
        if (this.servletClass.getName().equals("at.newsagg.web.ajax.MoveFeed")) {
            logger.debug("MoveFeed found");
            ((MoveFeed) this.servletInstance).setFeedSubscriberDAO(this
                    .getFeedSubscriberDAO());
        } else
            logger
                    .warn("VecServletWrappingController should be used with at.newsagg.web.ajax.AutoComplete");
        this.servletInstance.init(new DelegatingServletConfig());
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        this.servletInstance.service(request, response);
        return null;
    }

    public void destroy() {
        this.servletInstance.destroy();
    }

    /**
     * Internal implementation of the ServletConfig interface, to be passed to
     * the wrapped servlet. Delegates to ServletWrappingController fields and
     * methods to provide init parameters and other environment info.
     */
    private class DelegatingServletConfig implements ServletConfig {

        public String getServletName() {
            return servletName;
        }

        public ServletContext getServletContext() {
            return getWebApplicationContext().getServletContext();
        }

        public String getInitParameter(String paramName) {
            return initParameters.getProperty(paramName);
        }

        public Enumeration getInitParameterNames() {
            return initParameters.keys();
        }
    }

    /**
     * @return Returns the feedSubscriberDAO.
     */
    public FeedSubscriberDAO getFeedSubscriberDAO() {
        return feedSubscriberDAO;
    }

    /**
     * @param feedSubscriberDAO
     *            The feedSubscriberDAO to set.
     */
    public void setFeedSubscriberDAO(FeedSubscriberDAO feedSubscriberDAO) {
        this.feedSubscriberDAO = feedSubscriberDAO;
    }
}
