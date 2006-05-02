package at.generic.event;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import junit.framework.TestCase;

import org.dom4j.Node;

public class BaseEventTest extends TestCase {

	BaseEvent _examinee;

	String event = "<Root><Child1><Child>10</Child><Child>20</Child></Child1><Child2>2</Child2></Root>";

	protected void setUp(){
		try {
			_examinee = new BaseEvent(new URI("eventtype://test"));
		} catch (URISyntaxException e) {
		}
		_examinee.setXmlEventString(event);

	}

	public void testSelectXPathSingleValue() {

		String s = _examinee.selectNodeText("/Root/Child1/Child");
		assertEquals("10", s);
		
		s = _examinee.selectNodeText("//Child");
		assertEquals("10", s);
		
		s = _examinee.selectNodeText("//Child2");
		assertEquals("2", s);
		
		//no text in //Child1
		s = _examinee.selectNodeText("//Child1");
		assertEquals("", s);
		
		s = _examinee.selectNodeText("//WRONGTAG");
		assertEquals(null,s);
	}
	
	public void testSelectXPathValues () 
	{
		List result = _examinee.selectXPathValues("//Root");
		if (result.get(0)instanceof String) {
			assertTrue(false);
		}
		assertEquals(event,((Node)result.get(0)).asXML());
		
		
		result =  _examinee.selectXPathValues("/Root/Child1/Child");
		assertEquals(2,result.size());
		assertEquals("10",((Node)result.get(0)).getText());
		assertEquals("20",((Node)result.get(1)).getText());
		
		result = _examinee.selectXPathValues("//WRONGTAG");
		assertEquals(0,result.size());
		
		
		
		
		
	}

}
