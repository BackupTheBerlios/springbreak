package at.generic.indexing;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import junit.framework.TestCase;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;

import at.generic.event.IEventDefinitionResolver;
import at.generic.event.correlation.CorrelationData;
import at.generic.event.correlation.CorrelationTuple;

public class IndexingWorkerTest extends TestCase {

	IndexingWorker _examinee;
	LinkedBlockingQueue<Object> queue;
	
	private IEventDefinitionResolver mock;
	
	private String eventString="<TicketRequest type='Senactive.InTime.Core.Event.BaseEvent' "+
	"typeUri='eventtype://www.testdomain.com/CRM/TicketRequest' "+
	"guid='253806b6-8c27-4617-bb9f-1b4ed59a4f3d' "+
	"originalGuid='253806b6-8c27-4617-bb9f-1b4ed59a4f3d' "+
	"localTimeCreated='2006-04-27T15:57:24.7708376+02:00' utcTimeCreated='2006-04-27T15:57:24.7708376+02:00' localTimeCreatedRW='2006-04-27T15:57:24.7708376+02:00' utcTimeCreatedRW='2006-04-27T15:57:24.7708376+02:00' priority='Medium' persistence='false'> "+
	"<RequestId type='System.Int32'>901</RequestId><CustomerId type='System.Int32'>900</CustomerId>"+
	"<DateTime type='System.DateTime'>2006-01-01T11:00:00+01:00</DateTime><Channel type='System.String'><![CDATA[WEB]]></Channel>"+
	"<Location type='System.String'><![CDATA[2051]]></Location><OrderType type='System.String'>TRAIN</OrderType></TicketRequest>";

	
	
	public void setUp()
	{
		mock = createMock(IEventDefinitionResolver.class);
		
		
		
		queue = new LinkedBlockingQueue<Object>();
		queue.add(eventString);
		_examinee = new IndexingWorker(queue);
		_examinee.getEventTransformer().setEventDefinitionResolver(mock);
		
	}
	
	public void testIndexingWorker()
	{
		try {
			initMock(new URI("eventtype://www.testdomain.com/CRM/TicketRequest"), "MYCORRELATION", "//RequestId");
		} catch (URISyntaxException e) {
		}
		
		_examinee.processEvent();
		
		try {
			IndexSearcher searcher = new IndexSearcher("C:/tmp/lucene");
			QueryParser q = new QueryParser("text",new StandardAnalyzer());
			Hits h = searcher.search(q.parse("train"));
			
			Document result = h.doc(0);
			assertEquals("253806b6-8c27-4617-bb9f-1b4ed59a4f3d",result.getField("wid").stringValue());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	
	private void initMock(URI eventURI, String correlationName, String xPath) {
		// Code for easyMock
		reset(mock);
		CorrelationTuple ct = new CorrelationTuple();
		ct.setCorrelationName(correlationName);
		CorrelationData cd = new CorrelationData();
		cd.setXPathSelector(xPath);
		ct.addCorrelationData(cd);
		ArrayList<CorrelationTuple> ctList = new ArrayList<CorrelationTuple>();
		ctList.add(ct);

		expect(mock.getCorrelationInfos(eventURI)).andReturn(ctList);
		replay(mock);
		// End code for easyMock
	}
	
}
