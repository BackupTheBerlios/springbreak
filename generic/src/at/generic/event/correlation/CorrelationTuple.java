package at.generic.event.correlation;

import java.util.ArrayList;
import java.util.List;


public class CorrelationTuple {
	
	String correlationName;
	List<CorrelationData> correlationDatas = new ArrayList();
	
	public List<CorrelationData> getCorrelationDatas() {
		return correlationDatas;
	}
	
	public void setCorrelationDatas(List<CorrelationData> correlationDatas) {
		this.correlationDatas = correlationDatas;
	}
	
	public void addCorrelationData(CorrelationData correlationData)
	{
		this.correlationDatas.add(correlationData);	
	}
	
	public String getCorrelationName() {
		return correlationName;
	}
	
	public void setCorrelationName(String correlationName) {
		this.correlationName = correlationName;
	}
	

}
