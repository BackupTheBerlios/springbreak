/*
 * Created on 20.03.2005
 * king
 * 
 */
package at.newsagg.parser;

import org.jdom.Element;

import at.newsagg.model.parser.ChannelIF;
import at.newsagg.model.parser.ParseException;
import at.newsagg.model.parser.hibernate.Channel;


/**
 * Base Interface for all Parsers.
 * 
 * 
 * @author roland vecera
 * @version
 * created on 20.03.2005 14:43:43
 *
 */
public interface ParserIF {
    public ChannelIF parse(ChannelIF cBuilder, Element channel)
    throws ParseException;
}
