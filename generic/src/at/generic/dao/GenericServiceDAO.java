package at.generic.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author szabolcs
 * @version $Id: GenericServiceDAO.java,v 1.1 2005/12/21 22:06:10 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
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
