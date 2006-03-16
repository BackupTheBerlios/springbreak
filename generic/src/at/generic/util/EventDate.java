package at.generic.util;

import java.util.Date;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author szabolcs
 * @version $Id: EventDate.java,v 1.3 2006/03/16 13:48:44 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.3 $
 * 
 * Creates usefull Date representation of an event date string and provides some services
 * Original Format looks like this: 2005-12-28T12:06:07.0305000+01:00
 * 
 */
public class EventDate {
	private static Log log = LogFactory.getLog(EventDate.class);
	
	private int year;
	private int day;
	private int month;
	private int hour;
	private int minute;
	private int seconds;
	private int milseconds;
	
	/**
	 * Simple Constructor 
	 * @param origDate
	 */
	public EventDate(String origDate) {
		this.extractDatesFromString(origDate);
	}
	
	/**
	 * Constructor returns converted Date
	 * 
	 * @param origDate
	 * @return
	 */
	public Date EventDate(String origDate) {
		this.extractDatesFromString(origDate);
		return this.getDateFormat();
	}
	
	/**
	 * Decompose String 
	 * 
	 * @param origDate
	 */
	private void extractDatesFromString(String origDate) {
		year = Integer.parseInt(origDate.substring(0,4));
		month = Integer.parseInt(origDate.substring(5,7));
		day = Integer.parseInt(origDate.substring(8,10));
		hour = Integer.parseInt(origDate.substring(11,13));
		minute = Integer.parseInt(origDate.substring(14,16));
		seconds = Integer.parseInt(origDate.substring(17,19));
		milseconds = Integer.parseInt(origDate.substring(20,26));
	}
	
	/**
	 * Returns converted Date
	 * 
	 * @return Date
	 */
	public Date getDateFormat() {
		/*Date date = new Date(year, month, day);
		date.setYear(this.year);
		date.setDate(this.day);
		date.setMonth(this.month);
		date.setHours(this.hour);
		date.setMinutes(this.minute);
		date.setSeconds(this.seconds);*/
		 Calendar cal = Calendar.getInstance();
		cal.set( cal.YEAR, this.year );
	    cal.set( cal.MONTH, this.month - 1);
	    cal.set( cal.DATE, this.day );
	    
	    cal.set( cal.HOUR_OF_DAY, this.hour + 1);
	    cal.set( cal.MINUTE, this.minute );
	    cal.set( cal.SECOND, this.seconds );
	    
	    java.util.Date date = 
	       new java.sql.Date( cal.getTime().getTime() );
		
		return date;
	}
	
	/**
	 * <p>Creates a formatted Date String according to DateFormat.SHORT for the Lucene 
	 * index out of day, month and year.</p>
	 * 
	 * <p>Return String is in Format YYYYMMDD</p>
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public static String getBoundFormatForLucene(int day, int month, int year) {
		String sDay;
		String sMonth;
		String sYear;
		
		if (day < 10)
			sDay = "0" + Integer.toString(day);
		else 
			sDay = Integer.toString(day);
		
		if (month < 10)
			sMonth = "0" +Integer.toString(month);
		else 
			sMonth = Integer.toString(month);
		
		sYear = Integer.toString( year);
		
		return sYear + sMonth + sDay; 
		
	}

	/**
	 * @return Returns the day.
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day The day to set.
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @return Returns the hour.
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @param hour The hour to set.
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * @return Returns the milseconds.
	 */
	public int getMilseconds() {
		return milseconds;
	}

	/**
	 * @param milseconds The milseconds to set.
	 */
	public void setMilseconds(int milseconds) {
		this.milseconds = milseconds;
	}

	/**
	 * @return Returns the minute.
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * @param minute The minute to set.
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}

	/**
	 * @return Returns the month.
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month The month to set.
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return Returns the seconds.
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * @param seconds The seconds to set.
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	
}