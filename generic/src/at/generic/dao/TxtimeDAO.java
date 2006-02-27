package at.generic.dao;

import java.util.List;

import at.generic.eventmodel.Rwtime;
import at.generic.eventmodel.Txtime;

/**
 * @author szabolcs
 * @version $Id: TxtimeDAO.java,v 1.1 2006/02/27 14:57:34 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * DAO interface for Txtime
 * 
 */
public interface TxtimeDAO extends DAO 
{ 
	/**
	 * @return List with Twtimes
	 */
	public List getTxtimes(); 
	
	/**
	 * @return List with Txtimes using pagination
	 */
	public List getTxtimesByPage(int pageNumber, int pageSize); 
	
	/**
	 * @param id Txtime id
	 * @return Txtime txtime
	 */
	public Txtime getTxtime(Integer id);
	
	/**
	 * Retrieves Id
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return Txtime
	 */
	public Txtime getTxTimeByDates(int day, int month, int year);
	
	/**
	 * @param txtime Txtime to save
	 */
	public void saveOrUpdateTxtime(Txtime txtime); 
	
	/**
	 * @param Txtime Txtime to update
	 */
	public void updateTxtime(Txtime txtime); 
	
	/**
	 * @param id Txtime id to remove
	 */
	public void removeTxtime(Integer id);
}