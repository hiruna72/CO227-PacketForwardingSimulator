package com.co227.project.packetForwadingSimulator.simulators;

public class ProcessSwitch extends NextEvent {
	private String inputQKey,outputQKey;
	public ProcessSwitch(String eventID, double timeForEvent,String inputQKey,String outputQKey,String packetName) {
		super(eventID, timeForEvent,packetName);
		this.inputQKey = inputQKey;
		this.outputQKey = outputQKey;
	}

	@Override
	public void excuteEvent(double leastEventTime) {
		this.timeForEvent=leastEventTime;
		Simulator.InputBuffer.get(inputQKey).removePacket();
		Simulator.OutputBuffer.get(outputQKey).addPacket(this.packetName);
		Simulator.Packets.get(packetName).updateCurrentLocation(outputQKey);
		Simulator.Packets.get(packetName).updateCurrentLocationType("OutputQ");
		System.out.println(this.packetName+" is processed and switched to outputQ "+outputQKey);
	}

	@Override
	public void halfExecuteEvent(double leastEventTime) {
		this.timeForEvent-=leastEventTime;
		System.out.println(this.packetName+" is processing");
	}

}
