package at.generic.web.commandObj;

import java.util.Date;

/**
* @author szabolcs
* @version $Id: BrowserEventList.java,v 1.1 2006/02/14 10:10:08 szabolcs Exp $
* $Author: szabolcs $  
* $Revision: 1.1 $
* 
* Lists main event data for the event browser
* 
*/
public class BrowserEventList {
    private Long eventid;
    private String xmlcontent;
    private Date localtimeid;
    private String eventtype;

	/**
	 * @return Returns the eventid.
	 */
	public Long getEventid() {
		return eventid;
	}

	/**
	 * @param eventid The eventid to set.
	 */
	public void setEventid(Long eventid) {
		this.eventid = eventid;
	}

	/**
	 * @return Returns the eventtype.
	 */
	public String getEventtype() {
		return eventtype;
	}

	/**
	 * @param eventtype The eventtype to set.
	 */
	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}

	/**
	 * @return Returns the localtimeid.
	 */
	public Date getLocaltimeid() {
		return localtimeid;
	}

	/**
	 * @param localtimeid The localtimeid to set.
	 */
	public void setLocaltimeid(Date localtimeid) {
		this.localtimeid = localtimeid;
	}

	/**
	 * @return Returns the xmlcontent.
	 */
	public String getXmlcontent() {
		return xmlcontent;
	}

	/**
	 * @param xmlcontent The xmlcontent to set.
	 */
	public void setXmlcontent(String xmlcontent) {
		this.xmlcontent = xmlcontent;
	}
    
    
}