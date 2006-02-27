package at.generic.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.generic.dao.DbinfoDAO;
import at.generic.eventmodel.Dbinfo;
import at.generic.service.AdminPersistenceService;

/**
 * @author szabolcs
 * @version $Id: AdminPersistenceServiceImpl.java,v 1.1 2006/02/27 14:59:03 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Facade for event persitence operations
 */
public class AdminPersistenceServiceImpl implements AdminPersistenceService {
	private static Log log = LogFactory.getLog(AdminPersistenceServiceImpl.class);
	private DbinfoDAO dbInfoDAO;
	
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
	
}