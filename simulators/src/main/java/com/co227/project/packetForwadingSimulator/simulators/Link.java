package com.co227.project.packetForwadingSimulator.simulators;

import java.util.ArrayDeque;

public class Link {
	public static double linkPropagationSpeed = 2e+05; //2*10^8 m per second
	private String linkID;
	private double linkDistance;
	private double transmissionRate;
	private boolean linkIsClear;
	private String packetOnLink;
	private String currentLocationType;
	private ArrayDeque<String>buffer;
	public Link(String linkId,String currentLocationType, double linkDistance,double transmissionRate) {
		this.currentLocationType = currentLocationType;
		this.linkID = linkId;
		this.linkIsClear=true;
		this.linkDistance = linkDistance;
		this.transmissionRate = transmissionRate;
		this.packetOnLink = null;
		this.buffer = new ArrayDeque<String>();
	}
	boolean linkIsClear(){
		return linkIsClear;
	}
	public String getPacketOut(){
		linkIsClear=!linkIsClear;
		return this.buffer.removeFirst();
	}
	public void addPacketIn(String packetName){
		this.buffer.addLast(packetName);
		//this.packetOnLink = packetName;
		linkIsClear=false;
	}
	public double getPropagatingDelay() {
		return this.linkDistance/linkPropagationSpeed;
	}
	public double getTransmissionDelay(double psize) {
		return (psize*1000)/this.transmissionRate;
	}
	public void acquireLink(){
		linkIsClear=false;
	}

}
