package at.generic.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.generic.dao.DbinfoDAO;
import at.generic.dao.FilterDAO;
import at.generic.dao.ProfileDAO;
import at.generic.eventmodel.Dbinfo;
import at.generic.eventmodel.Filter;
import at.generic.eventmodel.Profile;
import at.generic.service.AdminPersistenceService;
import at.generic.web.commandObj.ProfileCons;

/**
 * @author szabolcs
 * @version $Id: AdminPersistenceServiceImpl.java,v 1.2 2006/03/16 23:35:50 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Facade for event persitence operations
 */
public class AdminPersistenceServiceImpl implements AdminPersistenceService {
	private static Log log = LogFactory.getLog(AdminPersistenceServiceImpl.class);
	private DbinfoDAO dbInfoDAO;
	private FilterDAO filterDAO;
	private ProfileDAO profileDAO;
	
	// =============== DbInfo Stuff ==================
	/**
	 * @return List with Dbinfos
	 */
	public List getDbinfos() {
		return dbInfoDAO.getDbinfos();
	}
	
	/**
	 * @return List with Dbinfos using pagination
	 */
	public List getDbinfosByPage(int pageNumber, int pageSize) {
		return dbInfoDAO.getDbinfosByPage(pageNumber, pageSize);
	}
	
	/**
	 * @param id dbinfo id
	 * @return Dbinfo dbinfo
	 */
	public Dbinfo getDbinfo(Integer id) {
		return dbInfoDAO.getDbinfo(id);
	}
	
	/**
	 * @param dbinfo dbinfo to save
	 */
	public void saveOrUpdateDbinfo(Dbinfo dbInfo) {
		dbInfoDAO.saveOrUpdateDbinfo(dbInfo);
	}
	
	/**
	 * @param Dbinfo dbinfo to update
	 */
	public void updateDbinfo(Dbinfo dbInfo) {
		dbInfoDAO.updateDbinfo(dbInfo);
	}
	
	/**
	 * @param id dbinfo id to remove
	 */
	public void removeDbinfo(Integer id) {
		dbInfoDAO.removeDbinfo(id);
	}
	
	/**
	 * Returns the last etl process infos
	 * 
	 */
	public Dbinfo getLastEtlUpdate() {
		List dbInfo = this.getDbinfos();
		
		if (dbInfo.size() == 0) 
			return null;
		else 
			return (Dbinfo) dbInfo.get(dbInfo.size() - 1);
	}
	
	// 	=============== Profile Stuff ==================
	/**
	 * Saves a profile which profilename has changed
	 * 
	 * @param name
	 * @param origname
	 * @param profileEventSet
	 * @param profileCorrelationSet
	 */
	public void saveProfile(String origname, String name, List profileEventSet, List profileCorrelationSet) {
		
		Profile profile = profileDAO.getProfileByName(origname);
		if (profile != null)
			this.removeProfile(profile.getPid());
		
		this.saveProfile(name,profileEventSet,profileCorrelationSet);
	}
	
	/**
	 * Saves a profile 
	 * 
	 * @param name
	 * @param profileEventSet
	 * @param profileCorrelationSet
	 */
	public void saveProfile(String name, List profileEventSet, List profileCorrelationSet) {
		
		Profile profile = profileDAO.getProfileByName(name);
		if (profile != null)
			this.removeProfile(profile.getPid());
		
		profile = new Profile();
		profile.setName(name);
		
		profileDAO.saveOrUpdateProfile(profile);
		profile = profileDAO.getProfileByName(name);
		
		Iterator eventSet = profileEventSet.iterator();
		while (eventSet.hasNext()) {
			String filtername = (String) eventSet.next(); 
			Filter filter = new Filter();
			filter.setPid(Integer.parseInt(profile.getPid().toString()));
			filter.setName(filtername);
			filter.setRanktype("event");
			filterDAO.saveOrUpdateFilter(filter);
		}
		
		Iterator correlationSet = profileCorrelationSet.iterator();
		while (correlationSet.hasNext()) {
			String filtername = (String) correlationSet.next(); 
			Filter filter = new Filter();
			filter.setPid(Integer.parseInt(profile.getPid().toString()));
			filter.setName(filtername);
			filter.setRanktype("corr");
			filterDAO.saveOrUpdateFilter(filter);
		}
	}
	
	/**
	 * <p>Returns a List of ProfileCons</p>
	 * 
	 * @return List of ProfileCons
	 * @return profileConsList containing a List of ProfileCons 
	 */
	public List getProfiles() {
		List profileConsList = new Vector();
		// get all profiles
		List profiles = profileDAO.getProfiles();
		Iterator profileIt = profiles.iterator();
		while (profileIt.hasNext()) {
			Profile profile = (Profile) profileIt.next();
			
			// create ProfileCons and add Profile to it
			ProfileCons profileCons = new ProfileCons();
			profileCons.setProfile(profile);
			
			// get all filters for this profile
			List filterList = new Vector();
			List filters = filterDAO.getAllFiltersByProfile(profile.getPid());
			Iterator filterIt = filters.iterator();
			while (filterIt.hasNext()) {
				Filter filter = (Filter) filterIt.next();
				filterList.add(filter);
			}
			
			// add filter to profileCons
			profileCons.setFilters(filterList);
			
			// add ProfileCon to the List
			profileConsList.add(profileCons);
		}
		
		return profileConsList;
	}
	
	/**
	 * Creates an ProfileCons object out of an profile id
	 * 
	 * @param id Profiles id
	 * @return ProfileCons
	 */
	public ProfileCons getProfileCons(Integer id) {
		ProfileCons profileCons = new ProfileCons();
		
		Profile profile = profileDAO.getProfile(id);
		List filters = filterDAO.getAllFiltersByProfile(profile.getPid());
		
		profileCons.setProfile(profile);
		profileCons.setFilters(filters);
		
		return profileCons;
	}
	
	/**
	 * Removes a Profile
	 * 
	 * @param id
	 */
	public void removeProfile(Integer id) {
		profileDAO.removeProfile(id);
		
		//TODO: remove the Filters too!!
	}
	
	
	//	 =============== Getters and Setters ==================
	
	/**
	 * @return Returns the dbInfoDAO.
	 */
	public DbinfoDAO getDbInfoDAO() {
		return dbInfoDAO;
	}

	/**
	 * @param dbInfoDAO The dbInfoDAO to set.
	 */
	public void setDbInfoDAO(DbinfoDAO dbInfoDAO) {
		this.dbInfoDAO = dbInfoDAO;
	}

	/**
	 * @return Returns the filterDAO.
	 */
	public FilterDAO getFilterDAO() {
		return filterDAO;
	}

	/**
	 * @param filterDAO The filterDAO to set.
	 */
	public void setFilterDAO(FilterDAO filterDAO) {
		this.filterDAO = filterDAO;
	}

	/**
	 * @return Returns the profileDAO.
	 */
	public ProfileDAO getProfileDAO() {
		return profileDAO;
	}

	/**
	 * @param profileDAO The profileDAO to set.
	 */
	public void setProfileDAO(ProfileDAO profileDAO) {
		this.profileDAO = profileDAO;
	}
	
	
	
}