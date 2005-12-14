package at.generic.eventmodel;

import java.util.List;
import java.util.Vector;

/**
 * @author szabolcs
 * @version $Id: OrderReceivedEvent.java,v 1.1 2005/12/14 22:15:14 szabolcs Exp $
 * $Author: szabolcs $  
 * $Revision: 1.1 $
 * 
 * Model for the OrderReceived XML Event 
 * 
 */
public class OrderReceivedEvent extends BaseCorrelatedEvent{
	// generated Event Infos
	private String guid;
	private String originalGuid;
	private String priority;
	private String severity;
	private String localTimeCreated;
	private String localTimeCreatedRW;
	private String utcTimeCreated;
	private String utcTimeCreatedRW;
	private String majorVersion;
	private String minorVersion;
	
	// Event Infos
	private String orderId;
	private String dateTime;
	private String deliveryDate;
	private String destination;
	private Vector productCollection; 		// Vector of ProductCollection
	
	
	/**
	 * @return Returns the dateTime.
	 */
	public String getDateTime() {
		return dateTime;
	}
	/**
	 * @param dateTime The dateTime to set.
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	/**
	 * @return Returns the deliveryDate.
	 */
	public String getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate The deliveryDate to set.
	 */
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return Returns the guid.
	 */
	public String getGuid() {
		return guid;
	}
	/**
	 * @param guid The guid to set.
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	/**
	 * @return Returns the localTimeCreated.
	 */
	public String getLocalTimeCreated() {
		return localTimeCreated;
	}
	/**
	 * @param localTimeCreated The localTimeCreated to set.
	 */
	public void setLocalTimeCreated(String localTimeCreated) {
		this.localTimeCreated = localTimeCreated;
	}
	/**
	 * @return Returns the localTimeCreatedRW.
	 */
	public String getLocalTimeCreatedRW() {
		return localTimeCreatedRW;
	}
	/**
	 * @param localTimeCreatedRW The localTimeCreatedRW to set.
	 */
	public void setLocalTimeCreatedRW(String localTimeCreatedRW) {
		this.localTimeCreatedRW = localTimeCreatedRW;
	}
	/**
	 * @return Returns the majorVersion.
	 */
	public String getMajorVersion() {
		return majorVersion;
	}
	/**
	 * @param majorVersion The majorVersion to set.
	 */
	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}
	/**
	 * @return Returns the minorVersion.
	 */
	public String getMinorVersion() {
		return minorVersion;
	}
	/**
	 * @param minorVersion The minorVersion to set.
	 */
	public void setMinorVersion(String minorVersion) {
		this.minorVersion = minorVersion;
	}
	/**
	 * @return Returns the orderId.
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId The orderId to set.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return Returns the originalGuid.
	 */
	public String getOriginalGuid() {
		return originalGuid;
	}
	/**
	 * @param originalGuid The originalGuid to set.
	 */
	public void setOriginalGuid(String originalGuid) {
		this.originalGuid = originalGuid;
	}
	/**
	 * @return Returns the priority.
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority The priority to set.
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	/**
	 * @return Returns the severity.
	 */
	public String getSeverity() {
		return severity;
	}
	/**
	 * @param severity The severity to set.
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	/**
	 * @return Returns the utcTimeCreated.
	 */
	public String getUtcTimeCreated() {
		return utcTimeCreated;
	}
	/**
	 * @param utcTimeCreated The utcTimeCreated to set.
	 */
	public void setUtcTimeCreated(String utcTimeCreated) {
		this.utcTimeCreated = utcTimeCreated;
	}
	/**
	 * @return Returns the utcTimeCreatedRW.
	 */
	public String getUtcTimeCreatedRW() {
		return utcTimeCreatedRW;
	}
	/**
	 * @param utcTimeCreatedRW The utcTimeCreatedRW to set.
	 */
	public void setUtcTimeCreatedRW(String utcTimeCreatedRW) {
		this.utcTimeCreatedRW = utcTimeCreatedRW;
	}
	
	/**
	 * @return Returns the productCollection.
	 */
	public Vector getProductCollection() {
		return productCollection;
	}
	/**
	 * @param productCollection The productCollection to set.
	 */
	public void setProductCollection(Vector productCollection) {
		this.productCollection = productCollection;
	}
	
	
}