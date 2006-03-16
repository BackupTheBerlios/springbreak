package at.generic.web.commandObj;

import java.util.Date;
import java.util.List;

/**
 * @author szabolcs
 * @version $Id: AdminCommand.java,v 1.6 2006/03/16 23:35:50 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.6 $
 * 
 * Data Object for the ETL Admin section
 * 
 */
public class AdminCommand { 
	// etl stuff
	private String updatestart;
	private String updatestop;
	private String etlThreadStartedAt;
	private List identifiedEvents;
	private List identifiedCorrs;
	private int numberOfIdentifiedEvents;
	private int numberOfProcessedEvents;
	private int numberOfEventsInSource;
	private double etlBarLength;
	private boolean etlRunning;
	
	// index event 
	private String indexLocationForEvents;
	private int numberOfIndexedIemsForEvents = 0;
	private boolean indexCreatedForEvents = false;
	
	// index corr event
	private String indexLocationForCorrEvents;
	private int numberOfIndexedIemsForCorrEvents = 0;
	private boolean indexCreatedForCorrEvents = false;
	
	// profile navigation stuff
	private int profileMenue = 1;		// menu item to be shown
	private String msg = new String();	// Status messages
	private List profileList;			// Holding a List of ProfileCons
	private ProfileCons profileCons;	// for editing a Profile
	private boolean editingProfile = false; 	// indicates if a Profile is opened for edit
	
	
	/**
	 * @return Returns the identifiedEvents.
	 */
	public List getIdentifiedEvents() {
		return identifiedEvents;
	}
	/**
	 * @param identifiedEvents The identifiedEvents to set.
	 */
	public void setIdentifiedEvents(List identifiedEvents) {
		this.identifiedEvents = identifiedEvents;
	}
	
	/**
	 * @return Returns the numberOfIdentifiedEvents.
	 */
	public int getNumberOfIdentifiedEvents() {
		return numberOfIdentifiedEvents;
	}
	/**
	 * @param numberOfIdentifiedEvents The numberOfIdentifiedEvents to set.
	 */
	public void setNumberOfIdentifiedEvents(int numberOfIdentifiedEvents) {
		this.numberOfIdentifiedEvents = numberOfIdentifiedEvents;
	}
	/**
	 * @return Returns the numberOfProcessedEvents.
	 */
	public int getNumberOfProcessedEvents() {
		return numberOfProcessedEvents;
	}
	/**
	 * @param numberOfProcessedEvents The numberOfProcessedEvents to set.
	 */
	public void setNumberOfProcessedEvents(int numberOfProcessedEvents) {
		this.numberOfProcessedEvents = numberOfProcessedEvents;
	}
	/**
	 * @return Returns the etlBarLength.
	 */
	public double getEtlBarLength() {
		return etlBarLength;
	}
	/**
	 * @param etlBarLength The etlBarLength to set.
	 */
	public void setEtlBarLength(double etlBarLength) {
		this.etlBarLength = etlBarLength;
	}
	/**
	 * @return Returns the updatestart.
	 */
	public String getUpdatestart() {
		return updatestart;
	}
	/**
	 * @param updatestart The updatestart to set.
	 */
	public void setUpdatestart(String updatestart) {
		this.updatestart = updatestart;
	}
	/**
	 * @return Returns the updatestop.
	 */
	public String getUpdatestop() {
		return updatestop;
	}
	/**
	 * @param updatestop The updatestop to set.
	 */
	public void setUpdatestop(String updatestop) {
		this.updatestop = updatestop;
	}
	/**
	 * @return Returns the etlRunning.
	 */
	public boolean isEtlRunning() {
		return etlRunning;
	}
	/**
	 * @param etlRunning The etlRunning to set.
	 */
	public void setEtlRunning(boolean etlRunning) {
		this.etlRunning = etlRunning;
	}
	/**
	 * @return Returns the etlThreadStartedAt.
	 */
	public String getEtlThreadStartedAt() {
		return etlThreadStartedAt;
	}
	/**
	 * @param etlThreadStartedAt The etlThreadStartedAt to set.
	 */
	public void setEtlThreadStartedAt(String etlThreadStartedAt) {
		this.etlThreadStartedAt = etlThreadStartedAt;
	}
	/**
	 * @return Returns the indexCreatedForCorrEvents.
	 */
	public boolean isIndexCreatedForCorrEvents() {
		return indexCreatedForCorrEvents;
	}
	/**
	 * @param indexCreatedForCorrEvents The indexCreatedForCorrEvents to set.
	 */
	public void setIndexCreatedForCorrEvents(boolean indexCreatedForCorrEvents) {
		this.indexCreatedForCorrEvents = indexCreatedForCorrEvents;
	}
	/**
	 * @return Returns the indexCreatedForEvents.
	 */
	public boolean isIndexCreatedForEvents() {
		return indexCreatedForEvents;
	}
	/**
	 * @param indexCreatedForEvents The indexCreatedForEvents to set.
	 */
	public void setIndexCreatedForEvents(boolean indexCreatedForEvents) {
		this.indexCreatedForEvents = indexCreatedForEvents;
	}
	/**
	 * @return Returns the indexLocationForCorrEvents.
	 */
	public String getIndexLocationForCorrEvents() {
		return indexLocationForCorrEvents;
	}
	/**
	 * @param indexLocationForCorrEvents The indexLocationForCorrEvents to set.
	 */
	public void setIndexLocationForCorrEvents(String indexLocationForCorrEvents) {
		this.indexLocationForCorrEvents = indexLocationForCorrEvents;
	}
	/**
	 * @return Returns the indexLocationForEvents.
	 */
	public String getIndexLocationForEvents() {
		return indexLocationForEvents;
	}
	/**
	 * @param indexLocationForEvents The indexLocationForEvents to set.
	 */
	public void setIndexLocationForEvents(String indexLocationForEvents) {
		this.indexLocationForEvents = indexLocationForEvents;
	}
	/**
	 * @return Returns the numberOfEventsInSource.
	 */
	public int getNumberOfEventsInSource() {
		return numberOfEventsInSource;
	}
	/**
	 * @param numberOfEventsInSource The numberOfEventsInSource to set.
	 */
	public void setNumberOfEventsInSource(int numberOfEventsInSource) {
		this.numberOfEventsInSource = numberOfEventsInSource;
	}
	/**
	 * @return Returns the numberOfIndexedIemsForCorrEvents.
	 */
	public int getNumberOfIndexedIemsForCorrEvents() {
		return numberOfIndexedIemsForCorrEvents;
	}
	/**
	 * @param numberOfIndexedIemsForCorrEvents The numberOfIndexedIemsForCorrEvents to set.
	 */
	public void setNumberOfIndexedIemsForCorrEvents(
			int numberOfIndexedIemsForCorrEvents) {
		this.numberOfIndexedIemsForCorrEvents = numberOfIndexedIemsForCorrEvents;
	}
	/**
	 * @return Returns the numberOfIndexedIemsForEvents.
	 */
	public int getNumberOfIndexedIemsForEvents() {
		return numberOfIndexedIemsForEvents;
	}
	/**
	 * @param numberOfIndexedIemsForEvents The numberOfIndexedIemsForEvents to set.
	 */
	public void setNumberOfIndexedIemsForEvents(int numberOfIndexedIemsForEvents) {
		this.numberOfIndexedIemsForEvents = numberOfIndexedIemsForEvents;
	}
	
	/**
	 * @return Returns the profileMenue.
	 */
	public int getProfileMenue() {
		return profileMenue;
	}
	/**
	 * @param profileMenue The profileMenue to set.
	 */
	public void setProfileMenue(int profileMenue) {
		this.profileMenue = profileMenue;
	}
	/**
	 * @return Returns the identifiedCorrs.
	 */
	public List getIdentifiedCorrs() {
		return identifiedCorrs;
	}
	/**
	 * @param identifiedCorrs The identifiedCorrs to set.
	 */
	public void setIdentifiedCorrs(List identifiedCorrs) {
		this.identifiedCorrs = identifiedCorrs;
	}
	/**
	 * @return Returns the msg.
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg The msg to set.
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return Returns the profileList.
	 */
	public List getProfileList() {
		return profileList;
	}
	/**
	 * @param profileList The profileList to set.
	 */
	public void setProfileList(List profileList) {
		this.profileList = profileList;
	}
	/**
	 * @return Returns the profileCons.
	 */
	public ProfileCons getProfileCons() {
		return profileCons;
	}
	/**
	 * @param profileCons The profileCons to set.
	 */
	public void setProfileCons(ProfileCons profileCons) {
		this.profileCons = profileCons;
	}
	
	/**
	 * @return Returns the editingProfile.
	 */
	public boolean isEditingProfile() {
		return editingProfile;
	}
	/**
	 * @param editingProfile The editingProfile to set.
	 */
	public void setEditingProfile(boolean editingProfile) {
		this.editingProfile = editingProfile;
	}
	
	
	
	
}