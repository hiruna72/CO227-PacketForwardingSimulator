package com.co227.project.packetForwadingSimulator.simulators;

public abstract class NextEvent {
	
	private String eventID;
	protected double timeForEvent;
	protected String packetName;
	public  NextEvent(String eventID,double timeForEvent,String packetName) {
		this.eventID = eventID;		
		this.timeForEvent = timeForEvent;
		this.packetName = packetName;
	}
	public double getTimeTaken() {
		return this.timeForEvent;
	}
	public String getEventID() {
		return this.eventID;
	}
	
	public void reduceTimeTaken(double leastEventTime) {
		this.timeForEvent-=leastEventTime;
	}
	public abstract void excuteEvent(double leastEventTime);
	public abstract void halfExecuteEvent(double leastEventTime);
}
