package at.generic.jms;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.reset;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import junit.framework.TestCase;
import at.generic.event.BaseEvent;
import at.generic.event.IEventDefinitionResolver;
import at.generic.event.correlation.CorrelationData;
import at.generic.event.correlation.CorrelationTuple;

public class XML2EventTransformerTest extends TestCase {

	private IEventTransformer examinee;

	private IEventDefinitionResolver mock;

	private String eventstring = 
		"<TicketRequest type='Senactive.InTime.Core.Event.BaseEvent' "+
		"typeUri='eventtype://www.testdomain.com/CRM/TicketRequest' "+
		"guid='253806b6-8c27-4617-bb9f-1b4ed59a4f3d' "+
		"originalGuid='253806b6-8c27-4617-bb9f-1b4ed59a4f3d' "+
		"localTimeCreated='2006-04-27T15:57:24.7708376+02:00' utcTimeCreated='2006-04-27T15:57:24.7708376+02:00' localTimeCreatedRW='2006-04-27T15:57:24.7708376+02:00' utcTimeCreatedRW='2006-04-27T15:57:24.7708376+02:00' priority='Medium' persistence='false'> "+
		"<RequestId type='System.Int32'>901</RequestId><CustomerId type='System.Int32'>900</CustomerId>"+
		"<DateTime type='System.DateTime'>2006-01-01T11:00:00+01:00</DateTime><Channel type='System.String'><![CDATA[WEB]]></Channel>"+
		"<Location type='System.String'><![CDATA[2051]]></Location><OrderType type='System.String'>TRAIN</OrderType></TicketRequest>";

	private URI eventURI;

	protected void setUp() throws Exception {
		super.setUp();
		examinee = new XML2EventTransformer();
		mock = createMock(IEventDefinitionResolver.class);
		examinee.setEventDefinitionResolver(mock);
		try {
			eventURI = new URI(
					"eventtype://www.testdomain.com/CRM/TicketRequest");
		} catch (URISyntaxException e) {

		}
	}

	public void testValidEvent() {

		initMock(eventURI, "MYCORRELATION", "//RequestId");

		try {
			BaseEvent event = examinee.transform(eventstring);

			assertEquals(eventstring, event.getXmlEventString());

			assertEquals(eventURI, event.getEventype());
			assertEquals("253806b6-8c27-4617-bb9f-1b4ed59a4f3d",event.getAttribute("guid"));

			String corrValue = event.getCorrelationValue("MYCORRELATION");
			assertEquals("MYCORRELATION"
					+ IEventTransformer.CORRELATIONDATA_SPLITTER + "901",
					corrValue);

		} catch (Exception e) {
			
			assertFalse(true);
		}

		verify(mock);
	}

	public void testValidEvent2() {

		initMock(eventURI, "MYCORRELATION", "//OrderType");

		try {
			BaseEvent event = examinee.transform(eventstring);

			assertEquals(eventstring, event.getXmlEventString());

			assertEquals(eventURI, event.getEventype());

			String corrValue = event.getCorrelationValue("MYCORRELATION");
			assertEquals("MYCORRELATION"
					+ IEventTransformer.CORRELATIONDATA_SPLITTER + "TRAIN",
					corrValue);

		} catch (Exception e) {
			
			assertFalse(true);
		}

		verify(mock);
	}

	public void testValidEventComplexCorrelation() {

		// Code for easyMock
		reset(mock);
		CorrelationTuple ct = new CorrelationTuple();
		ct.setCorrelationName("MYCORRELATION");
		
		CorrelationData cd = new CorrelationData();
		cd.setXPathSelector("//OrderType");
		ct.addCorrelationData(cd);
		
		cd = new CorrelationData();
		cd.setXPathSelector("//RequestId");
		ct.addCorrelationData(cd);
		
		ArrayList<CorrelationTuple> ctList = new ArrayList<CorrelationTuple>();
		ctList.add(ct);

		expect(mock.getCorrelationInfos(eventURI)).andReturn(ctList);
		replay(mock);
		// End code for easyMock

		try {
			BaseEvent event = examinee.transform(eventstring);

			assertEquals(eventstring, event.getXmlEventString());

			assertEquals(eventURI, event.getEventype());

			String corrValue = event.getCorrelationValue("MYCORRELATION");
			assertEquals("MYCORRELATION"
					+ IEventTransformer.CORRELATIONDATA_SPLITTER + "TRAIN"
					+ IEventTransformer.CORRELATIONDATA_SPLITTER + "901",
					corrValue);

		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(true);
		}

		verify(mock);

	}

	public void testInValidEvent() {

		try {
			BaseEvent event = examinee.transform(new String("NO XML STRING"));

		} catch (Exception e) {

			assertTrue(true);
		}

		
	}

	public void testInValidEvent2() {

		try {
			BaseEvent event = examinee.transform(new Object());

		} catch (Exception e) {

			assertTrue(true);
		}

	}

	public void testBadCorrelationSelector() {

		initMock(eventURI, "MYCORRELATION", "//IM_NOT_HERE");

		try {
			BaseEvent event = examinee.transform(eventstring);
		} catch (Exception e) {
			
			assertTrue(true);
		}

		verify(mock);
	}

	/**
	 * Helper to setup Mock
	 * 
	 * @param eventURI
	 * @param correlationName
	 * @param xPath
	 */
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

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
