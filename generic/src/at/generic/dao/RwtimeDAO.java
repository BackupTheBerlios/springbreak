package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.Rwtime;

/**
 * @author szabolcs
 * @version $Id: RwtimeDAO.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for Rwtime
 * 
 */
public interface RwtimeDAO extends DAO 
{ 
	/**
	 * @return List with Rwtimes
	 */
	public List getRwtimes(); 
	
	/**
	 * @return List with Rwtimes using pagination
	 */
	public List getRwtimesByPage(int pageNumber, int pageSize); 
	
	/**
	 * @param id rwtime id
	 * @return Rwtime rwtime
	 */
	public Rwtime getRwtime(Integer id);
	
	/**
	 * Retrieves Id
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return Rwtime
	 */
	public Rwtime getRwTimeByDates(int day, int month, int year);
	
	/**
	 * @param rwtime Rwtime to save
	 */
	public void saveOrUpdateRwtime(Rwtime rwtime); 
	
	/**
	 * @param Rwtime Rwtime to update
	 */
	public void updateRwtime(Rwtime rwtime); 
	
	/**
	 * @param id Rwtime id to remove
	 */
	public void removeRwtime(Integer id);
}