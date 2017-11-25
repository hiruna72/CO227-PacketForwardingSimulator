package com.co227.project.packetForwadingSimulator.simulators;


public class Link {
	private String linkID;
	private double linkDistance;
	private double linkPropagationSpeed;
	private boolean linkIsClear;
	private String packetOnLink;
	private String currentLocationType;
	public Link(String linkId,String currentLocationType, double linkDistance,double linkPropagationSpeed) {
		this.currentLocationType = currentLocationType;
		this.linkID = linkId;
		this.linkIsClear=true;
		this.linkDistance = linkDistance;
		this.linkPropagationSpeed = linkPropagationSpeed;
		this.packetOnLink = null;
	}
	boolean linkIsClear(){
		return linkIsClear;
	}
	public String getPacketOut(){
		linkIsClear=!linkIsClear;
		return this.packetOnLink;
	}
	public void addPacketIn(String packetName){
		this.packetOnLink = packetName;
		linkIsClear=!linkIsClear;
	}
	public double getPropagatingDelay() {
		return this.linkDistance/this.linkPropagationSpeed;
	}

}
