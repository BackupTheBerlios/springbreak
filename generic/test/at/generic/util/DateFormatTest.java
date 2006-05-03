package at.generic.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import at.generic.event.BaseEvent;

public class DateFormatTest extends TestCase {
	
	public void testDateFormat ()
	{
//		2006-04-27T15:57:24.7708376+02:00
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = sdf.parse("2006-04-27T15:57:24.7708376+02:00");
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(d);
			
			assertEquals(gc.get(GregorianCalendar.YEAR), 2006);
			assertEquals(gc.get(GregorianCalendar.MONTH), 3);
			assertEquals(gc.get(GregorianCalendar.DAY_OF_MONTH), 27);
			
			
		
		
		} catch (ParseException e) {
			assertFalse(true);
		}

	}
	
	
	
	public void testtest()
	{
		System.out.println(System.getProperty("java.io.tmpdir"));
		
	}

}
