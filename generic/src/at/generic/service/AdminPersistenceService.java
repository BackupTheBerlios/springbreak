package at.generic.service;

import java.util.List;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.dao.CorrelatedsetDAO;
import at.generic.dao.DbinfoDAO;
import at.generic.dao.EventDAO;
import at.generic.dao.EventattributeDAO;
import at.generic.dao.EventtypeDAO;
import at.generic.dao.FilterDAO;
import at.generic.dao.ProfileDAO;
import at.generic.dao.RwtimeDAO;
import at.generic.dao.TxtimeDAO;
import at.generic.eventmodel.Dbinfo;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;
import at.generic.model.Correlatedevent;
import at.generic.model.Correlationset;
import at.generic.web.commandObj.ProfileCons;

/**
 * @author szabolcs
 * @version $Id: AdminPersistenceService.java,v 1.2 2006/03/16 23:35:50 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Facade for correlating events persitence operations
 */
public interface AdminPersistenceService {
	//	 =============== DbInfo Stuff ==================
	
	/**
	 * @return List with Dbinfos
	 */
	public List getDbinfos();
	
	/**
	 * @return List with Dbinfos using pagination
	 */
	public List getDbinfosByPage(int pageNumber, int pageSize); 
	
	/**
	 * @param id dbinfo id
	 * @return Dbinfo dbinfo
	 */
	public Dbinfo getDbinfo(Integer id);
	
	/**
	 * @param dbinfo dbinfo to save
	 */
	public void saveOrUpdateDbinfo(Dbinfo dbInfo); 
	
	/**
	 * @param Dbinfo dbinfo to update
	 */
	public void updateDbinfo(Dbinfo dbInfo); 
	
	/**
	 * @param id dbinfo id to remove
	 */
	public void removeDbinfo(Integer id);
	
	/**
	 * Returns the last etl process infos
	 * 
	 * @param Dbinfo
	 */
	public Dbinfo getLastEtlUpdate();
	
	//	 =============== Profile Stuff ==================
	
	/**
	 * Saves a profile which profilename has changed
	 * 
	 * @param name
	 * @param origname
	 * @param profileEventSet
	 * @param profileCorrelationSet
	 */
	public void saveProfile(String origname, String name, List profileEventSet, List profileCorrelationSet);
	
	/**
	 * Saves a profile 
	 * 
	 * @param name
	 * @param profileEventSet
	 * @param profileCorrelationSet
	 */
	public void saveProfile(String name, List profileEventSet, List profileCorrelationSet);
	
	/**
	 * <p>Returns a List of ProfileCons</p>
	 * 
	 * @return List of ProfileCons
	 * @return profileConsList containing a List of ProfileCons 
	 */
	public List getProfiles() ;
	
	/**
	 * Creates an ProfileCons object out of an profile id
	 * 
	 * @param id Profiles id
	 * @return ProfileCons
	 */
	public ProfileCons getProfileCons(Integer id);
	
	/**
	 * Removes a Profile
	 * 
	 * @param id
	 */
	public void removeProfile(Integer id);
	
	//	 =============== Getters and Setters ==================
	
	/**
	 * @return Returns the dbInfoDAO.
	 */
	public DbinfoDAO getDbInfoDAO();

	/**
	 * @param dbInfoDAO The dbInfoDAO to set.
	 */
	public void setDbInfoDAO(DbinfoDAO dbInfoDAO);
	
	/**
	 * @return Returns the filterDAO.
	 */
	public FilterDAO getFilterDAO();

	/**
	 * @param filterDAO The filterDAO to set.
	 */
	public void setFilterDAO(FilterDAO filterDAO);

	/**
	 * @return Returns the profileDAO.
	 */
	public ProfileDAO getProfileDAO();

	/**
	 * @param profileDAO The profileDAO to set.
	 */
	public void setProfileDAO(ProfileDAO profileDAO);
	
}