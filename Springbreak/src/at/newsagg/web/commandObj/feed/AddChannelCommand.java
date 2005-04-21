/*
 * Created on 30.03.2005
 * king
 * 
 */
package at.newsagg.web.commandObj.feed;

import at.newsagg.model.parser.hibernate.Channel;

/**
 * @author Roland Vecera
 * @version
 * created on 30.03.2005 13:35:53
 *
 */
public class AddChannelCommand {
    
    Channel channel;

    /**
     * @return Returns the channel.
     */
    public Channel getChannel() {
        return channel;
    }
    /**
     * @param channel The channel to set.
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
