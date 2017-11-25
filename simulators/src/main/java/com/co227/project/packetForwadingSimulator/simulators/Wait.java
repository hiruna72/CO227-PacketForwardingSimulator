package com.co227.project.packetForwadingSimulator.simulators;

public class Wait extends NextEvent{
	private String currentLocation;
	public Wait(String eventID, double timeForEvent, String packetName,String currentLocation) {
		super(eventID, timeForEvent,packetName);
		this.currentLocation = currentLocation;
	}

	@Override
	public void excuteEvent(double leastEventTime) {
		this.timeForEvent=leastEventTime;
		System.out.println(packetName+" is waiting on "+this.currentLocation);
	}

	@Override
	public void halfExecuteEvent(double leastEventTime) {
		this.timeForEvent=leastEventTime;		
	}

}
