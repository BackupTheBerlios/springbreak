package at.generic.etl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.dao.CorrelatedsetDAO;
import at.generic.dao.EventDAO;
import at.generic.dao.GenericServiceDAO;
import at.generic.service.AdminPersistenceService;
import at.generic.service.CorrelatingEventsPersistenceService;
import at.generic.service.EventHandling;
import at.generic.service.EventPersistenceService;

/**
 * @author szabolcs
 * @version $Id: DataImport.java,v 1.1 2006/03/06 23:20:36 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Used to copy over the data from the vmware machine to the local database.
 * Change msSqlSource to the vmware location and set MS Sql database connection. 
 * 
 */
public interface DataImport {
	/**
	 * Starts the data import as a thread
	 */
	public void startImport();

	// ======================= Getters and Setters ===========================

	/**
	 * @return Returns the corrSourceDAO.
	 */
	public CorrelatedeventDAO getCorrSourceDAO();

	/**
	 * @param corrSourceDAO The corrSourceDAO to set.
	 */
	public void setCorrSourceDAO(CorrelatedeventDAO corrSourceDAO);

	/**
	 * @return Returns the corrTargetDAO.
	 */
	public CorrelatedeventDAO getCorrTargetDAO();

	/**
	 * @param corrTargetDAO The corrTargetDAO to set.
	 */
	public void setCorrTargetDAO(CorrelatedeventDAO corrTargetDAO);

	/**
	 * @return Returns the importRunning.
	 */
	public boolean isImportRunning();

	/**
	 * @param importRunning The importRunning to set.
	 */
	public void setImportRunning(boolean importRunning);

	/**
	 * @return Returns the setSourceDAO.
	 */
	public CorrelatedsetDAO getSetSourceDAO();

	/**
	 * @param setSourceDAO The setSourceDAO to set.
	 */
	public void setSetSourceDAO(CorrelatedsetDAO setSourceDAO);

	/**
	 * @return Returns the setTargetDAO.
	 */
	public CorrelatedsetDAO getSetTargetDAO();

	/**
	 * @param setTargetDAO The setTargetDAO to set.
	 */
	public void setSetTargetDAO(CorrelatedsetDAO setTargetDAO);
}