package edu.workingsystem.marta.model;

public class Event {
	
	private Integer eventRank;
	private String event_type;
	private Integer eventId;
	
	
	public Event(Integer eventRank, String event_type, Integer eventId) {
		super();
		this.eventRank = eventRank;
		this.event_type = event_type;
		this.eventId = eventId;
	}
	public Integer getEventRank() {
		return eventRank;
	}
	public void setEventRank(Integer eventRank) {
		this.eventRank = eventRank;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	

}
