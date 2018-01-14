package com.co227.project.packetForwadingSimulator.simulators;

public class Lose extends NextEvent {
	private String currentLocation;
	private String currentLocationType;
	public Lose(String eventID, double timeForEvent, String packetName,String currentLocation) {
		super(eventID, timeForEvent, packetName);
		this.currentLocation = currentLocation;
	}
	public Lose(String eventID, double timeForEvent, String packetName,String currentLocation,String currentLocationType) {
		super(eventID, timeForEvent, packetName);
		this.currentLocation = currentLocation;
		this.currentLocationType =  currentLocationType;
	}

	@Override
	public void excuteEvent(double leastEventTime) {
		if(this.currentLocation.equals("onLinkEdge")){
			String tpn = Simulator.Links.get(this.currentLocation).getPacketOut();
			Simulator.Packets.get(packetName).markAsLost();
			System.out.println(packetName+" is lost on link "+this.currentLocation);
		}
		else if(this.currentLocationType.equals("InputQ")){
			String tpn = Simulator.InputBuffer.get(this.currentLocation).removePacket();
			Simulator.Packets.get(packetName).markAsLost();
			System.out.println(packetName+" is lost in inputQ "+this.currentLocation);
		}
		else if(this.currentLocationType.equals("OutputQ")){
			//System.out.println(this.currentLocation);
//			String temp1 = this.currentLocation.split(" to ")[0];
//			String temp2= this.currentLocation.split(" to ")[1];
			//String tpn = Simulator.OutputBuffer.get(this.currentLocation).removePacket();
			Simulator.Packets.get(this.packetName).markAsLost();
			System.out.println(this.packetName+" is lost in outputQ "+this.currentLocation);
		}
		else{
			System.out.println("terminate");
		}
	}

	@Override
	public void halfExecuteEvent(double leastEventTime) {
		this.excuteEvent(leastEventTime);
	}

}
