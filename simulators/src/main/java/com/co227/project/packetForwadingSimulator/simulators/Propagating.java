package com.co227.project.packetForwadingSimulator.simulators;

public class Propagating extends NextEvent {
	private String linkID;
	public Propagating(String eventID, double timeForEvent,String packetName,String linkID) {
		super(eventID, timeForEvent,packetName);
		this.linkID = linkID;
	}

	@Override
	public void excuteEvent(double leastEventTime) {
		this.timeForEvent=leastEventTime;
		System.out.println(this.packetName+" is propagated through "+ this.linkID);
		Simulator.Packets.get(this.packetName).updateCurrentLocationType("onLinkEdge");
	}

	@Override
	public void halfExecuteEvent(double leastEventTime) {
		this.timeForEvent-=leastEventTime;
		System.out.println(this.packetName+" is propagating on link "+ this.linkID);

	}

}
