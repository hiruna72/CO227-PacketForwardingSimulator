package com.co227.project.packetForwadingSimulator.simulators;

public class TransmitToLink extends NextEvent{
	private String linkKey,outputQKey;
	public TransmitToLink(String eventID, double timeForEvent, String outputQKey, String linkKey,String packetName) {
		super(eventID, timeForEvent,packetName);
		this.linkKey = linkKey;
		this.outputQKey = outputQKey;
	}

	@Override
	public void excuteEvent(double leastEventTime) {
		this.timeForEvent=leastEventTime;
		String packetName = Simulator.OutputBuffer.get(outputQKey).removePacket();
	//	System.out.println("removed packet: "+packetName);
		Simulator.Links.get(outputQKey).addPacketIn(this.packetName);
		Simulator.Packets.get(this.packetName).updateCurrentLocation(linkKey);
		Simulator.Packets.get(this.packetName).updateCurrentLocationType("transmittedToLink");
//		//
//		System.out.println(this.packetName+" is on here in the transmitToLink Class "+Simulator.Packets.get(packetName).getCurrentLocationType());
//		//
		System.out.println(this.packetName+" is transmitted to link "+linkKey);
	}

	@Override
	public void halfExecuteEvent(double leastEventTime) {
		this.timeForEvent-=leastEventTime;
		System.out.println(this.packetName+" is transmitting");
	}

}
