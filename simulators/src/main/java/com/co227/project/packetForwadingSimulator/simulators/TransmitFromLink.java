package com.co227.project.packetForwadingSimulator.simulators;

public class TransmitFromLink extends NextEvent {
	private String linkKey,inputQKey;
	public TransmitFromLink(String eventID, double timeForEvent,String linkKey, String inputQKey, String packetName) {
		super(eventID, timeForEvent, packetName);
		this.linkKey = linkKey;
		this.inputQKey = inputQKey;
	}

	@Override
	public void excuteEvent(double leastEventTime) {
		this.timeForEvent=leastEventTime;
		String tpn = Simulator.Links.get(linkKey).getPacketOut();
		Simulator.InputBuffer.get(inputQKey).addPacket(this.packetName);
		Simulator.Packets.get(packetName).updateCurrentLocationType("InputQ");
		System.out.println(this.packetName+" is transmitted from link "+linkKey);
	}

	@Override
	public void halfExecuteEvent(double leastEventTime) {
		this.timeForEvent-=leastEventTime;
		System.out.println(this.packetName+" is transmitting");
	}

}
