package com.co227.project.packetForwadingSimulator.simulators;

public class Lose extends NextEvent {

	public Lose(String eventID, double timeForEvent, String packetName) {
		super(eventID, timeForEvent, packetName);
	}

	@Override
	public void excuteEvent(double leastEventTime) {
		Simulator.Packets.get(packetName).markAsLost();
	}

	@Override
	public void halfExecuteEvent(double leastEventTime) {
		Simulator.Packets.get(packetName).markAsLost();
	}

}
