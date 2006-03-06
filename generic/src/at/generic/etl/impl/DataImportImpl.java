package at.generic.etl.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.generic.dao.CorrelatedeventDAO;
import at.generic.dao.CorrelatedsetDAO;
import at.generic.etl.DataImport;
import at.generic.model.Correlatedevent;
import at.generic.model.Correlationset;

/**
 * @author szabolcs
 * @version $Id: DataImportImpl.java,v 1.1 2006/03/06 23:20:19 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Used to copy over the data from the vmware machine to the local database.
 * Change msSqlSource to the vmware location and set MS Sql database connection. 
 * 
 */
public class DataImportImpl implements DataImport, Runnable {
	private static Log log = LogFactory.getLog(DataImportImpl.class);
	
	private boolean importRunning = false;
	private CorrelatedeventDAO corrSourceDAO;
	private CorrelatedsetDAO setSourceDAO;
	private CorrelatedeventDAO corrTargetDAO;
	private CorrelatedsetDAO setTargetDAO;
	
	public void run() {
		log.info("### import running... ");
		
		if (importRunning == false) {
			importRunning = true;
			//new Transformthread().transformSourceEvents();
			ImportThread importThread = new ImportThread();
			new Thread(importThread).start();
		} 
	}
	
	public void stop() {
		log.info("### import stopped... ");
		importRunning = false;
	}
	
	/**
	 * Starts the data import as a thread
	 */
	public void startImport() {
		this.run();
	}
	
	/**
	 * Innerclass that performs the data import 
	 */
	public class ImportThread extends Thread {
		
		public void run() {
			log.debug("### ImportThread.start()");

			// copy corr events
			List corrEvents = corrSourceDAO.getCorrelatedevents();
			for ( Iterator i = corrEvents.iterator(); i.hasNext(); ) {
				Correlatedevent corrEvent = (Correlatedevent) i.next();
				log.debug("### copying correlated event id: " + corrEvent.getId());
				corrTargetDAO.saveCorrelatedevent(corrEvent);
			}
			
			log.debug("############################ FINISHED IMPORTING EVENTS #########################");
			
			//  copy corr sets
			List corrSet = setSourceDAO.getCorrelatedset();
			for ( Iterator i = corrSet.iterator(); i.hasNext(); ) {
				Correlationset setEvent = (Correlationset) i.next();
				log.debug("### copying correlated set id: " + setEvent.getId());
				setTargetDAO.saveCorrelatedset(setEvent);
			}
			
			log.debug("############################ FINISHED IMPORTING SETS #########################");
		}
	}

	// ======================= Getters and Setters ===========================
	

	/**
	 * @return Returns the corrSourceDAO.
	 */
	public CorrelatedeventDAO getCorrSourceDAO() {
		return corrSourceDAO;
	}

	/**
	 * @param corrSourceDAO The corrSourceDAO to set.
	 */
	public void setCorrSourceDAO(CorrelatedeventDAO corrSourceDAO) {
		this.corrSourceDAO = corrSourceDAO;
	}

	/**
	 * @return Returns the corrTargetDAO.
	 */
	public CorrelatedeventDAO getCorrTargetDAO() {
		return corrTargetDAO;
	}

	/**
	 * @param corrTargetDAO The corrTargetDAO to set.
	 */
	public void setCorrTargetDAO(CorrelatedeventDAO corrTargetDAO) {
		this.corrTargetDAO = corrTargetDAO;
	}

	/**
	 * @return Returns the importRunning.
	 */
	public boolean isImportRunning() {
		return importRunning;
	}

	/**
	 * @param importRunning The importRunning to set.
	 */
	public void setImportRunning(boolean importRunning) {
		this.importRunning = importRunning;
	}

	/**
	 * @return Returns the setSourceDAO.
	 */
	public CorrelatedsetDAO getSetSourceDAO() {
		return setSourceDAO;
	}

	/**
	 * @param setSourceDAO The setSourceDAO to set.
	 */
	public void setSetSourceDAO(CorrelatedsetDAO setSourceDAO) {
		this.setSourceDAO = setSourceDAO;
	}

	/**
	 * @return Returns the setTargetDAO.
	 */
	public CorrelatedsetDAO getSetTargetDAO() {
		return setTargetDAO;
	}

	/**
	 * @param setTargetDAO The setTargetDAO to set.
	 */
	public void setSetTargetDAO(CorrelatedsetDAO setTargetDAO) {
		this.setTargetDAO = setTargetDAO;
	}
	
	
}