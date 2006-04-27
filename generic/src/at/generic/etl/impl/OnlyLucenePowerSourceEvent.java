/**
 * 
 */
package at.generic.etl.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.RAMDirectory;

import at.generic.etl.SourceEventEtl;
import at.generic.model.Correlatedevent;
import at.generic.service.AdminPersistenceService;
import at.generic.service.CorrelatingEventsPersistenceService;
import at.generic.service.EventPersistenceService;
import at.generic.service.IndexingService;

/**
 * @author rvecera
 *
 */
public class OnlyLucenePowerSourceEvent implements SourceEventEtl {

	private Map identifiedEvents;
	private Map identifiedEventObjects;
	private java.util.Date etlThreadStartedAt;
	private EventPersistenceService eventPersistenceService;
	private IndexingService indexingServiceEvents;
	private CorrelatingEventsPersistenceService corrEventsPersistenceService;
	private IndexWriter writer;
	private String indexLocation ="C:/java/tmp";
	
	private Analyzer analyzer = new StandardAnalyzer();
	private RAMDirectory directory = new RAMDirectory();
	
	private boolean isEtlRunning = false;
	
	
	
	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#getBasicInfos()
	 */
	public void getBasicInfos() {
		System.out.println("Not Implemented");

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#transformSourceEvents()
	 */
	public void transformSourceEvents() {

		
		//Quick&Dirty for Test;
		//Index is created every thime transformSourceEvents is called!
			try {
				writer = new IndexWriter(directory,analyzer,true);
				//writer = new IndexWriter(indexLocation, analyzer, true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally
			{
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			etlThreadStartedAt = new java.util.Date(System.currentTimeMillis());
			
			System.out.println("Indexing started at:"+ new java.util.Date(System.currentTimeMillis()));
			//List correlatedEventList = corrEventsPersistenceService.getCorrelatedevents();
			for (int pagenr = 0 ; pagenr <= (eventPersistenceService.getNumberOfSourceEvents() / this.getBatchSizeForPaging()) + 1; pagenr++ ){
				try
				{
					writer = new IndexWriter(directory,analyzer,false);
				 //writer = new IndexWriter(indexLocation, analyzer, false);
				 
				
				List correlatedEventList = this.corrEventsPersistenceService.getCorrelatedeventsByPage(pagenr, this.getBatchSizeForPaging());
				Iterator i = correlatedEventList.iterator();
				while (i.hasNext()) {
					Correlatedevent correlatedEvent = (Correlatedevent) i.next();
					
					Document document = new Document();
				    document.add(Field.Keyword("wid", correlatedEvent.getId().toString()));
				    document.add(Field.Keyword("type", "testtype"));
				    document.add(Field.Text("text",correlatedEvent.getEventXml()));

				    writer.addDocument(document);
					
					
					}
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
			System.out.println("Indexing ended at:"+ new java.util.Date(System.currentTimeMillis()));
			try {
				new IndexWriter(indexLocation,analyzer,false).optimize();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			System.out.println("Optimize ended at:"+ new java.util.Date(System.currentTimeMillis()));
			isEtlRunning = false;
			
	}
	

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#getIdentifiedEvents()
	 */
	public Map getIdentifiedEvents() {
		// TODO Auto-generated method stub
		return identifiedEvents;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setIdentifiedEvents(java.util.Map)
	 */
	public void setIdentifiedEvents(Map identifiedEvents) {
		this.identifiedEvents = identifiedEvents;

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setNumberOfProcessedEvents(int)
	 */
	public void setNumberOfProcessedEvents(int numberOfProcessedEvents) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#getNumberOfIdentifiedEvents()
	 */
	public int getNumberOfIdentifiedEvents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setNumberOfIdentifiedEvents(int)
	 */
	public void setNumberOfIdentifiedEvents(int numberOfIdentifiedEvents) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#getNumberOfProcessedEvents()
	 */
	public int getNumberOfProcessedEvents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setNumberOfPorcessedEvents(int)
	 */
	public void setNumberOfPorcessedEvents(int numberOfProcessedEvents) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#isInitDone()
	 */
	public boolean isInitDone() {
		return true;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setInitDone(boolean)
	 */
	public void setInitDone(boolean initDone) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#getIdentifiedEventObjects()
	 */
	public Map getIdentifiedEventObjects() {
		return this.identifiedEventObjects;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setIdentifiedEventObjects(java.util.Map)
	 */
	public void setIdentifiedEventObjects(Map identifiedEventObjects) {
		this.identifiedEventObjects = identifiedEventObjects;

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#isEtlRunning()
	 */
	public boolean isEtlRunning() {
		// TODO Auto-generated method stub
		return this.isEtlRunning;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setEtlRunning(boolean)
	 */
	public void setEtlRunning(boolean etlRunning) {
		// TODO Auto-generated method stub
		this.isEtlRunning = etlRunning;

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#getEtlThreadStartedAt()
	 */
	public Date getEtlThreadStartedAt() {
		return this.etlThreadStartedAt;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setEtlThreadStartedAt(java.util.Date)
	 */
	public void setEtlThreadStartedAt(Date etlThreadStartedAt) {
		this.etlThreadStartedAt = etlThreadStartedAt;

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#getEventPersistenceService()
	 */
	public EventPersistenceService getEventPersistenceService() {
		// TODO Auto-generated method stub
		return this.eventPersistenceService;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setEventPersistenceService(at.generic.service.EventPersistenceService)
	 */
	public void setEventPersistenceService(
			EventPersistenceService eventPersistenceService) {
		this.eventPersistenceService = eventPersistenceService;

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#getCorrEventsPersistenceService()
	 */
	public CorrelatingEventsPersistenceService getCorrEventsPersistenceService() {
		// TODO Auto-generated method stub
		return this.corrEventsPersistenceService;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setCorrEventsPersistenceService(at.generic.service.CorrelatingEventsPersistenceService)
	 */
	public void setCorrEventsPersistenceService(
			CorrelatingEventsPersistenceService corrEventsPersistenceService) {
		this.corrEventsPersistenceService = corrEventsPersistenceService;

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#getAdminPersistenceService()
	 */
	public AdminPersistenceService getAdminPersistenceService() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setAdminPersistenceService(at.generic.service.AdminPersistenceService)
	 */
	public void setAdminPersistenceService(
			AdminPersistenceService adminPersistenceService) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#getBatchSizeForPaging()
	 */
	public int getBatchSizeForPaging() {
		// TODO Auto-generated method stub
		return 500;
	}

	/* (non-Javadoc)
	 * @see at.generic.etl.SourceEventEtl#setBatchSizeForPaging(int)
	 */
	public void setBatchSizeForPaging(int batchSizeForPaging) {
		// TODO Auto-generated method stub

	}

	public IndexingService getIndexingServiceEvents() {
		return indexingServiceEvents;
	}

	public void setIndexingServiceEvents(IndexingService indexingServiceEvents) {
		this.indexingServiceEvents = indexingServiceEvents;
	}

	public String getIndexLocation() {
		return indexLocation;
	}

	public void setIndexLocation(String indexLocation) {
		this.indexLocation = indexLocation;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer anaylzer) {
		this.analyzer = anaylzer;
	}

}
