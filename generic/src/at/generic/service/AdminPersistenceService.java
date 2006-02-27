package at.generic.service;

import java.util.List;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.dao.CorrelatedsetDAO;
import at.generic.dao.DbinfoDAO;
import at.generic.dao.EventDAO;
import at.generic.dao.EventattributeDAO;
import at.generic.dao.EventtypeDAO;
import at.generic.dao.RwtimeDAO;
import at.generic.dao.TxtimeDAO;
import at.generic.eventmodel.Dbinfo;
import at.generic.eventmodel.Event;
import at.generic.eventmodel.Eventattribute;
import at.generic.model.Correlatedevent;
import at.generic.model.Correlationset;

/**
 * @author szabolcs
 * @version $Id: AdminPersistenceService.java,v 1.1 2006/02/27 14:59:03 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
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
	
	//	 =============== Getters and Setters ==================
	
	/**
	 * @return Returns the dbInfoDAO.
	 */
	public DbinfoDAO getDbInfoDAO();

	/**
	 * @param dbInfoDAO The dbInfoDAO to set.
	 */
	public void setDbInfoDAO(DbinfoDAO dbInfoDAO);
	
}