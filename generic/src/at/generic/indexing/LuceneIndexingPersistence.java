package at.generic.indexing;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import at.generic.event.BaseEvent;


public class LuceneIndexingPersistence implements IIndexingPersistence {
	private static Log log = LogFactory.getLog(LuceneIndexingPersistence.class);

	private boolean init = false;

	private String indexLocation ="C:/TMP/lucene";

	private Analyzer analyzer = new StandardAnalyzer();

	private IndexWriter writer;

	public LuceneIndexingPersistence()
	{
		init();
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see at.generic.indexing.IIndexingPersistence#addDocument(at.generic.event.BaseEvent)
	 */
	public BaseEvent addDocument(BaseEvent event) {

		try {

			writer = new IndexWriter(indexLocation, analyzer, false);

			Document document = new Document();

			document.add(new Field("wid", event.getAttribute("guid"), Field.Store.YES, Field.Index.UN_TOKENIZED));
			document.add(new Field("type", event.getEventype().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
			document.add(new Field("text", event.getXmlEventString(), Field.Store.YES, Field.Index.TOKENIZED));
			
			writer.addDocument(document);
			
		} catch (IOException e) {
			log.error("Error updating index");
		} 
		finally {
			closeWriter(writer);
		}
		
		return event;
	}

	public void init() {
		if (init == false) {
			try {
				writer = new IndexWriter(indexLocation, analyzer, false);
				writer.docCount();
				init = true;
			} catch (IOException e1) {
				log.info("Error opening index location " + indexLocation);
				try {
					log.info("Trying to create an index at location "
							+ indexLocation);
					this.createIndexLocation();
				} catch (IOException e2) {
					log.error("Giving up creating lucene index at "
							+ indexLocation, e2);
				}
			} finally {
				closeWriter(writer);

			}

		}
	}

	private void createIndexLocation() throws IOException {
		IndexWriter writerCreateIndex = null;

		writerCreateIndex = new IndexWriter(indexLocation, analyzer, true);
		writerCreateIndex.close();
		init = true;

	}
	
	/**
	  * Closes the given IndexWriter and calls optimize()
	  * @param writer
	  */
	private void closeWriter(IndexWriter writer) {
	    if (null != writer) {
	        try {
	            writer.close();
	            writer = null;
	        } catch (IOException e) {
	            log.warn("Error while closing index writer", e);
	        }
	    }
	}
	
	/**
	  * Closes the given IndexWriter and calls optimize()
	  * @param writer
	  */
	private void optimizeAndCloseWriter(IndexWriter writer) {
	    if (null != writer) {
	        try {
	        	writer.optimize();
	            writer.close();
	            writer = null;
	        } catch (IOException e) {
	            log.warn("Error while closing index writer", e);
	        }
	    }
	}

	public String getIndexLocation() {
		return indexLocation;
	}

	public void setIndexLocation(String indexLocation) {
		this.indexLocation = indexLocation;
	}

	public boolean isInit() {
		return init;
	}

}
