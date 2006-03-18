package at.generic.web;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import at.generic.etl.SourceEventEtl;
import at.generic.eventmodel.Dbinfo;
import at.generic.service.AdminPersistenceService;
import at.generic.service.CorrelatingEventsPersistenceService;
import at.generic.service.EventPersistenceService;
import at.generic.service.IndexingService;
import at.generic.service.SearchService;
import at.generic.web.commandObj.AdminCommand;


/**
 * @author szabolcs
 * @version $Id: AdminController.java,v 1.8 2006/03/18 15:24:09 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.8 $
 * 
 * <p>Controller for the ETL Process</p>
 * 
 */
public class AdminController implements Controller { 
	private static Log log = LogFactory.getLog(AdminController.class); 
	
	private SourceEventEtl sourceEventEtl;
	private EventPersistenceService eventPersistenceService;
	private CorrelatingEventsPersistenceService corrPersistenceService;
	private AdminPersistenceService adminPersistenceService;
	private IndexingService indexingServiceEvents;
	private IndexingService indexingServiceCorrEvents;
	private SearchService searchService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		if (request.getParameter("startTransformation") != null && request.getParameter("startTransformation").equals("true")) {
			sourceEventEtl.transformSourceEvents();
		} else {
		    sourceEventEtl.getBasicInfos();
		}
		
		AdminCommand adminData = new AdminCommand();
		adminData.setNumberOfIdentifiedEvents(sourceEventEtl.getNumberOfIdentifiedEvents());
		//adminData.setIdentifiedEvents(sourceEventEtl.getIdentifiedEvents());
		adminData.setNumberOfProcessedEvents(sourceEventEtl.getNumberOfProcessedEvents());
		adminData.setIdentifiedEvents(eventPersistenceService.getIdentifiedEventTypes());
		
		// calc the elt bar length
		adminData.setEtlBarLength(((sourceEventEtl.getNumberOfIdentifiedEvents() / (sourceEventEtl.getNumberOfProcessedEvents() * 0.01))/100) * 300); 
		
		// get last update infos
		Dbinfo dbInfo = adminPersistenceService.getLastEtlUpdate();
		
		if (dbInfo != null) {
			adminData.setUpdatestart(dbInfo.getUpdatestart());
			adminData.setUpdatestop(dbInfo.getUpdatestop());
		}
		
		// check if ETL Process is running
		if (sourceEventEtl.isEtlRunning() == true) {
			adminData.setEtlRunning(true);
			
			// if so then add time when process has been started
			adminData.setEtlThreadStartedAt(sourceEventEtl.getEtlThreadStartedAt().toGMTString());
		}
		
		adminData.setIndexLocationForEvents(indexingServiceEvents.getIndexLocation());
		adminData.setNumberOfIndexedIemsForEvents(indexingServiceEvents.getNumberOfIndexedIems());
		adminData.setIndexCreatedForEvents(indexingServiceEvents.isIndexCreated());
		
		adminData.setIndexLocationForCorrEvents(indexingServiceCorrEvents.getIndexLocation());
		adminData.setNumberOfIndexedIemsForCorrEvents(indexingServiceCorrEvents.getNumberOfIndexedIems());
		adminData.setIndexCreatedForCorrEvents(indexingServiceCorrEvents.isIndexCreated());
		
		// check which Profile Menue is selected if nothing then its 1
		// 1... List Profiles
		// 2... Add Profile
		// 666... message View
		if (request.getParameter("profileMenue") != null && !request.getParameter("profileMenue").equals(""))
			adminData.setProfileMenue(Integer.parseInt(request.getParameter("profileMenue")));
		else 
			adminData.setProfileMenue(1);
		
		// if 1 "List Profiles"
		if (adminData.getProfileMenue() == 1) {
			List profileList = adminPersistenceService.getProfiles();
			adminData.setProfileList(profileList);
		}
		
		// if 2 "Add Profile"
		if (adminData.getProfileMenue() == 2) {
			adminData.setIdentifiedCorrs(corrPersistenceService.getCorrelationsSetTypes());
		}
		
		// if a profile wants to be saved 
		if (request.getParameter("saveProfile") != null && request.getParameter("saveProfile").equals("true")) {
			log.debug("### profilename:" + request.getParameter("profilename"));
			//log.debug("### profileCorrelationSet:" + request.getParameterValues("profileCorrelationSet"));
			//log.debug("### profileEventSet:" + request.getParameter("profileEventSet"));
			
			List profileCorrelationSet = this.createListOutOfMultipleOptionField(request.getParameterValues("profileCorrelationSet"));
			List profileEventSet = this.createListOutOfMultipleOptionField(request.getParameterValues("profileEventSet"));
			
			// save new profile but check if everything is filled out correctly
			if ( profileCorrelationSet.size() == 0 && profileEventSet.size() == 0 ) {
				adminData.setProfileMenue(666);
				adminData.setMsg("Could not save! You must select at least one Eventtype from the list!");
			} else if (request.getParameter("profilename") == null || request.getParameter("profilename").equals("")) { 
				adminData.setProfileMenue(666);
				adminData.setMsg("Could not save! You have to enter a profile name!");
			} else {
				if (request.getParameter("origprofilename") != null 
						&& !request.getParameter("origprofilename").trim().equals("") 
						&& request.getParameter("origprofilename").trim().equals(request.getParameter("profilename").trim())) 
					this.adminPersistenceService.saveProfile(request.getParameter("origprofilename").trim(), request.getParameter("profilename").trim(), profileCorrelationSet, profileEventSet);
				else
					this.adminPersistenceService.saveProfile( request.getParameter("profilename").trim(), profileCorrelationSet, profileEventSet);
				adminData.setProfileMenue(666);
				adminData.setMsg("Profile Saved!");
			}
		}
		
		// if profile wants to be edited
		if (request.getParameter("editProfile") != null && !request.getParameter("editProfile").equals("")) {
			// load profile and filters into ProfileCons contruct
			adminData.setProfileCons(adminPersistenceService.getProfileCons(new Integer(request.getParameter("editProfile"))));
			adminData.setEditingProfile(true);
			adminData.setIdentifiedCorrs(corrPersistenceService.getCorrelationsSetTypes());
			adminData.setProfileMenue(2);
			log.debug("### getFilters().size(): " + adminData.getProfileCons().getFilters().size());
			log.debug("### getFilters().size(): " + adminData.getIdentifiedCorrs().size());
			
		}
		
		// if profile wants to be deleted
		if (request.getParameter("deleteProfile") != null && !request.getParameter("deleteProfile").equals("")) {
			this.adminPersistenceService.removeProfile(new Integer(request.getParameter("deleteProfile")));
			adminData.setProfileMenue(666);
			adminData.setMsg("Profile Deleted!");
		}
		
		// retrieve data for Search Admin
		if (request.getParameter("updateSearchInfos") != null && !request.getParameter("updateSearchInfos").equals("")) {
			if (request.getParameter("lookAheadRank1").equals("")
					|| request.getParameter("lookAheadRank2").equals("")
					|| request.getParameter("pageSize").equals("")
				) {
				adminData.setMsg("Don't leave anything empty");
			} else {
				try {
					int la1 = Integer.parseInt(request.getParameter("lookAheadRank1"));
					int la2 = Integer.parseInt(request.getParameter("lookAheadRank2"));
					int pS = Integer.parseInt(request.getParameter("pageSize"));
					
					indexingServiceEvents.setMaxNumberOfLookAhead(la1);
					indexingServiceCorrEvents.setMaxNumberOfLookAhead(la2);
					searchService.setMaxSearchResults(pS);
					
				} catch (NumberFormatException e) {
					adminData.setMsg("only numeric values!");
				}
			}
		}
		
		adminData.setLookAheadRank1(indexingServiceEvents.getMaxNumberOfLookAhead());
		adminData.setLookAheadRank2(indexingServiceCorrEvents.getMaxNumberOfLookAhead());
		adminData.setPageSize(searchService.getMaxSearchResults());
		
		//sourceEventEtl.transformSourceEvents();
		return new ModelAndView("admin", "adminData", adminData);
	}
	
	
	/**
	 * <p>An HTML option field set to multiple can return several strings 
	 * put together by ":". This function creates a List containing those
	 * elements as Strings.</p>
	 * 
	 * @param opt String with ":"
	 * @return sep 
	 */
	private List createListOutOfMultipleOptionField(String opt) {
		Vector sep = new Vector();
		try {
			StringTokenizer st = new StringTokenizer(opt,":");
			while (st.hasMoreTokens()) {
				sep.add((String)st.nextToken());
			}
		} catch (NullPointerException e) {
			return sep;
		}
		
		return sep;
	}
	
	/**
	 * <p>This function creates a List out of String[].</p>
	 * 
	 * @param opt String with ":"
	 * @return sep 
	 */
	private List createListOutOfMultipleOptionField(String[] opt) {
		List sep = null;
		try {
			sep = Arrays.asList(opt);
		} catch (NullPointerException e) {
			return new Vector();
		}
		return sep;
	}
	
	// ************** Getters and Setters *********************

	/**
	 * @return Returns the sourceEventEtl.
	 */
	public SourceEventEtl getSourceEventEtl() {
		return sourceEventEtl;
	}

	/**
	 * @param sourceEventEtl The sourceEventEtl to set.
	 */
	public void setSourceEventEtl(SourceEventEtl sourceEventEtl) {
		this.sourceEventEtl = sourceEventEtl;
	}

	/**
	 * @return Returns the adminPersistenceService.
	 */
	public AdminPersistenceService getAdminPersistenceService() {
		return adminPersistenceService;
	}

	/**
	 * @param adminPersistenceService The adminPersistenceService to set.
	 */
	public void setAdminPersistenceService(
			AdminPersistenceService adminPersistenceService) {
		this.adminPersistenceService = adminPersistenceService;
	}

	/**
	 * @return Returns the eventPersistenceService.
	 */
	public EventPersistenceService getEventPersistenceService() {
		return eventPersistenceService;
	}

	/**
	 * @param eventPersistenceService The eventPersistenceService to set.
	 */
	public void setEventPersistenceService(
			EventPersistenceService eventPersistenceService) {
		this.eventPersistenceService = eventPersistenceService;
	}

	/**
	 * @return Returns the indexingServiceCorrEvents.
	 */
	public IndexingService getIndexingServiceCorrEvents() {
		return indexingServiceCorrEvents;
	}

	/**
	 * @param indexingServiceCorrEvents The indexingServiceCorrEvents to set.
	 */
	public void setIndexingServiceCorrEvents(
			IndexingService indexingServiceCorrEvents) {
		this.indexingServiceCorrEvents = indexingServiceCorrEvents;
	}

	/**
	 * @return Returns the indexingServiceEvents.
	 */
	public IndexingService getIndexingServiceEvents() {
		return indexingServiceEvents;
	}

	/**
	 * @param indexingServiceEvents The indexingServiceEvents to set.
	 */
	public void setIndexingServiceEvents(IndexingService indexingServiceEvents) {
		this.indexingServiceEvents = indexingServiceEvents;
	}

	/**
	 * @return Returns the corrPersistenceService.
	 */
	public CorrelatingEventsPersistenceService getCorrPersistenceService() {
		return corrPersistenceService;
	}

	/**
	 * @param corrPersistenceService The corrPersistenceService to set.
	 */
	public void setCorrPersistenceService(
			CorrelatingEventsPersistenceService corrPersistenceService) {
		this.corrPersistenceService = corrPersistenceService;
	}


	/**
	 * @return Returns the searchService.
	 */
	public SearchService getSearchService() {
		return searchService;
	}


	/**
	 * @param searchService The searchService to set.
	 */
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	
	
}