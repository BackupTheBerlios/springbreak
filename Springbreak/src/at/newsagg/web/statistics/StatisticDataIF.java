/*
 * Created on 06.09.2005
 * king
 * 
 */
package at.newsagg.web.statistics;

import java.util.Date;
import java.util.Map;

import at.newsagg.dao.LogDAO;
import de.laures.cewolf.DatasetProduceException;

/**
 * @author king
 * @version
 * created on 06.09.2005 10:46:15
 *
 */
public interface StatisticDataIF {
    /**
     * @return Returns the map.
     */
    public Map getMap();

    /**
     * @param map
     *            The map to set.
     */
    public void setMap(Map map);

    /**
     * @return Returns the lDAO.
     */
    public LogDAO getLogDAO();

    /**
     * @param ldao
     *            The lDAO to set.
     */
    public void setLogDAO(LogDAO logdao);

    /**
     * produces subscriberstatistic for given channel_id, numberWeeks. Map
     * params must includes values for: <br/>"channel_id" --> channel you are
     * interested <br/>"numberWeeks" --> number of Weeks you want to look back.
     * 
     * @return a random XYDataset
     */
    public Object produceDataset(Map params) throws NullPointerException,
            DatasetProduceException;

    /**
     * @see de.laures.cewolf.DatasetProducer#hasExpired(Map, Date)
     */
    public boolean hasExpired(Map params, Date since);

    public String getProducerId();

    /*
     * (non-Javadoc)
     * 
     * @see de.laures.cewolf.ChartPostProcessor#processChart(java.lang.Object,
     *      java.util.Map)
     */public void processChart(Object chartobj, Map arg1);
}