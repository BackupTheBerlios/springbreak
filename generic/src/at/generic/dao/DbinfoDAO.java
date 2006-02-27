package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.Dbinfo;
import at.generic.eventmodel.Event;

/**
 * @author szabolcs
 * @version $Id: DbinfoDAO.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for Dbinfo
 * 
 */
public interface DbinfoDAO extends DAO 
{ 
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
}