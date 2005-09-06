/*
 * Created on 06.09.2005
 * king
 * 
 */
package at.newsagg.web.statistics;

import java.awt.BasicStroke;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import at.newsagg.dao.LogDAO;
import at.newsagg.model.logstatistics.LogPostingsIF;
import de.laures.cewolf.ChartPostProcessor;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

/**
 * @author king
 * @version created on 06.09.2005 10:43:48
 *  
 */
public class PostingsStatisticData implements DatasetProducer,
        ChartPostProcessor, Serializable, StatisticDataIF

{

    private static final Log log = LogFactory
            .getLog(PostingsStatisticData.class);

    private LogDAO logDAO;

    private Map map;

    /**
     * @return Returns the map.
     */
    public Map getMap() {
        return map;
    }

    /**
     * @param map
     *            The map to set.
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * @return Returns the lDAO.
     */
    public LogDAO getLogDAO() {
        return logDAO;
    }

    /**
     * @param ldao
     *            The lDAO to set.
     */
    public void setLogDAO(LogDAO logdao) {
        logDAO = logdao;
    }

    /**
     * produces subscriberstatistic for given channel_id, numberWeeks. Map
     * params must includes values for: <br/>"channel_id" --> channel you are
     * interested <br/>"numberWeeks" --> number of Weeks you want to look back.
     * 
     * @return a random XYDataset
     */
    public Object produceDataset(Map params) throws NullPointerException,
            DatasetProduceException {

        String seriesName;
        if (((Integer) map.get("channel_id")) == null) {
            throw new NullPointerException("channel_id not set!");
        }

        if (((Integer) map.get("numberWeeks")) == null) {
            throw new NullPointerException("numberWeeks not set!");
        }
        if (((String) map.get("seriesName")) != null) {
            seriesName = (String) map.get("seriesName");
        } else {
            seriesName = "Postings";
        }

        log.debug("ID: " + map.get("channel_id") + " WEEKS:"
                + map.get("numberWeeks"));

        return createDataset(((Integer) map.get("channel_id")).intValue(),
                ((Integer) map.get("numberWeeks")).intValue(), seriesName);

    }

    private CategoryDataset createDataset(int channel_id, int numberWeeks,
            String seriesName) {
        log.debug("ID " + channel_id);
        log.debug("weeks " + numberWeeks);
        Iterator i = logDAO.getLogPostings(channel_id).iterator();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        LogPostingsIF logPostings;
        Calendar cal = new GregorianCalendar();
        int max = 0;
        while (i.hasNext() && (max < numberWeeks)) {
            logPostings = (LogPostingsIF) i.next();
            cal.setTime(logPostings.getDate());
            log.debug("Tick: " + cal.get(Calendar.WEEK_OF_YEAR) + "/"
                    + cal.get(Calendar.YEAR));
            dataset.addValue(logPostings.getNum_postings(), seriesName, cal
                    .get(Calendar.WEEK_OF_YEAR)
                    + "/" + cal.get(Calendar.YEAR));
            max++;

        }

        return dataset;

    }

    /**
     * @see de.laures.cewolf.DatasetProducer#hasExpired(Map, Date)
     */
    public boolean hasExpired(Map params, Date since) {
        log.debug(this + ".hasExpired()");
        return false;
        //return ((System.currentTimeMillis() - since.getTime()) >
        // (60000*60*24));
    }

    public String getProducerId() {
        return "LogPostings DatsetProducer " + Math.random();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.laures.cewolf.ChartPostProcessor#processChart(java.lang.Object,
     *      java.util.Map)
     */
    public void processChart(Object chartobj, Map arg1) {
        JFreeChart chart = (JFreeChart) chartobj;
        log.info("***" + chartobj.getClass());

        CategoryPlot plot = chart.getCategoryPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
                .getRenderer();
        renderer.setDrawShapes(true);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
                new float[] { 10.0f, 6.0f }, 0.0f));

    }
}
