/*
 * Created on 26.03.2005
 * king
 * 
 */
package at.newsagg.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.newsagg.dao.ChannelDAO;
import at.newsagg.dao.ItemDAO;
import at.newsagg.model.parser.ChannelIF;
import at.newsagg.model.parser.ItemIF;
import at.newsagg.model.parser.ParseException;
import at.newsagg.model.parser.hibernate.Channel;
import at.newsagg.parser.FeedParser;
import at.newsagg.service.ParserCronJobService;

/**
 * Cronjob class to parse over all persistent Feed-Channels.
 * 
 * @author Roland Vecera
 * @version created on 26.03.2005 13:48:25
 *  
 */
public class ParserCronJobServiceImpl extends TimerTask implements
        ParserCronJobService {
    private static Log log = LogFactory.getLog(ParserCronJobServiceImpl.class);

    private ChannelDAO channelDao;

    private ItemDAO itemDao;

    private FeedParser parser;

    ParserCronJobServiceImpl() {
    }

    /**
     * executes runUpdate().
     *  
     */
    public void run() {
        log.info("start parserrun");
        System.out.println((new java.util.Date()).toLocaleString()
                + "NEUER RUN ParserCronjob");
        this.runUpdate();
    }

    /**
     * This methode resolves all Channels needed to be updated now, parses their
     * RSS Feed and saves/updates in DB.
     * 
     * This methode should be called by a timer. In every run it is updating the
     * neccessary Channels.
     *  
     */
    public void runUpdate() {

        /**
         * TODO: implement a better algorithm to just take the needed channels
         */
        java.util.List channels = channelDao.getChannels();
        java.util.Iterator i = channels.iterator();

        ChannelIF channelresult;

        while (i.hasNext()) {
            ChannelIF channel = (ChannelIF) i.next();
            //          initialize empty Items!
            channel.setItems(new ArrayList());
            try {
                this.runUpdateOnChannel(channel);

            } catch (IOException e) {

                log.error(e.fillInStackTrace());
            } catch (ParseException e) {

                log.error(e.fillInStackTrace());
            }

        }

    }

    /**
     * run Update on new Channel.
     * 
     * Checks wheter the channel.getLocation() is already persistet (== this
     * Channel is already in DB).
     * 
     * 
     * 
     * @param channel
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public ChannelIF runNewChannel(ChannelIF channel) throws IOException,
            ParseException {
        try {
            Channel c = (Channel) channelDao.getChannel(channel.getLocation());
            log.info("Channel already in DB: " + c.getId());
            channel.setId(c.getId());

        } catch (IndexOutOfBoundsException e) {
            //it's a new channel
        }
        //initialize empty Items!
        channel.setItems(new ArrayList());
        return runUpdateOnChannel(channel);

    }

    /**
     * runs Update+Persistence on one given ChannelIF.
     * 
     * 
     * @return
     */
    private ChannelIF runUpdateOnChannel(ChannelIF channel) throws IOException,
            ParseException {

        //Take channel and parse it!
        channel = (Channel) parser.parse(channel, channel.getLocation());

        this.checkForNewItems(channel);

        //persist in DB
        //saveOrUpdateCopy!
        channelDao.saveOrUpdateChannel((Channel) channel);

        return channel;

    }

    //helper
    /**
     * Checks for paresed Items if they are already in Database, or not.
     * 
     * Changes Id of channelresult to the id of an equal Item that is already in
     * DB.
     * 
     * @author roland vecera
     * @return ChannelIF ChannelIF with updated id-Attributes of its Items.
     */
    private ChannelIF checkForNewItems(ChannelIF channelresult) {
        java.util.Iterator j;
        ItemIF itemnew;

        //      For every item check, wheter its new, or not!
        j = channelresult.getItems().iterator();
        while (j.hasNext()) {
            itemnew = (ItemIF) j.next();

            ItemIF currItem;
            try {
                currItem = itemDao.getItemByLink(itemnew.getLink());
            } catch (IndexOutOfBoundsException e) {

                currItem = null;
            }
            //set this itemnew.id to the already stored currItem.id
            if (itemnew.equals(currItem)) {
                itemnew.setId(currItem.getId());
            }

        }
        return channelresult;

    }

    //Getter and Setter
    /**
     * @return Returns the log.
     */
    public static Log getLog() {
        return log;
    }

    /**
     * @param log
     *            The log to set.
     */
    public static void setLog(Log log) {
        ParserCronJobServiceImpl.log = log;
    }

    /**
     * @return Returns the channelDao.
     */
    public ChannelDAO getChannelDao() {
        return channelDao;
    }

    /**
     * @param channelDao
     *            The channelDao to set.
     */
    public void setChannelDao(ChannelDAO channelDao) {
        this.channelDao = channelDao;
    }

    /**
     * @return Returns the itemDao.
     */
    public ItemDAO getItemDao() {
        return itemDao;
    }

    /**
     * @param itemDao
     *            The itemDao to set.
     */
    public void setItemDao(ItemDAO itemDao) {
        this.itemDao = itemDao;
    }

    /**
     * @return Returns the parser.
     */
    public FeedParser getParser() {
        return parser;
    }

    /**
     * @param parser
     *            The parser to set.
     */
    public void setParser(FeedParser parser) {
        this.parser = parser;
    }
}