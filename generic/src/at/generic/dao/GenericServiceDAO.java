package at.generic.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author szabolcs
 * @version $Id: GenericServiceDAO.java,v 1.2 2006/01/31 20:15:15 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.2 $
 * 
 * Interface for the Generic DAO 
 * 
 */
public interface GenericServiceDAO {
	
	/**
	 * Returns all objects of a given type
	 * 
	 * @param obj
	 * @return
	 */
	public List getAllObjects(Object obj); 
	
	/**
	 * Returns object with given id
	 * 
	 * @param obj
	 * @param id
	 * @return
	 */
	public Object getObjectById(Object obj, Serializable id);
	
	/**
	 * Persists given object
	 * 
	 * @param obj
	 * @param id
	 */
	public void save(Object obj, Serializable id);
	
	/**
	 * Persists given object without to check saveorupdate problematic
	 * 
	 * @param obj
	 */
	public void saveWithoutCheck(Object obj);
	
	/**
	 * Updates persistent object
	 * 
	 * @param obj
	 */
	public void update(Object obj);
	 
	/**
	 * Returns a Query using hql query syntax
	 * 
	 * @param hqlQry
	 * @return List with objects
	 */
	public List getObjectsByQuery(String hqlQry);
	
}
