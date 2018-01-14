package com.co227.project.packetForwadingSimulator.simulators;

import java.util.ArrayList;

public class Packet {
	private int priorityValue;
	private int src,dest;
	private double size;
	private ArrayList<NextEvent>events;
	private ArrayList<String>locationsVisited;
	private String packetName;
	private String currentLocation;
	private String currentLocationType;
	private NextEvent nextEvent;
	private boolean livePacket;
	Packet(String packetName,int priorityValue, int src,int dest,double size, String currentLocationType, String currentLocation){
		this.src=src;
		this.dest=dest;
		this.packetName=packetName;
		this.priorityValue = priorityValue;
		this.size=size;
		this.events = new ArrayList<NextEvent>();
		this.locationsVisited = new ArrayList<String>();
		this.locationsVisited.add(this.src+"");
		this.nextEvent=null;
		this.livePacket = true;
		this.currentLocationType = currentLocationType;
		this.currentLocation = currentLocation;
	}
	public String getCurrentLocation(){
		return this.currentLocation;
	}
	public String getCurrentLocationType(){
		return this.currentLocationType;
	}
	public void setNextEvent(NextEvent e) {
		this.nextEvent = e;	
	}
	public int getSrc(){
		return this.src;
	}
	public int getDest(){
		return this.dest;
	}
	public double getSize(){
		return this.size;
	}
	public NextEvent getNextEvent() {
		return this.nextEvent;
	}
	public String getID() {
		return this.packetName;
	}
	public void addToEvents() {
		this.events.add(nextEvent);
		if(this.currentLocationType.equals("OutputQ"))
			this.addToLocations();
		this.nextEvent=null;
	}
	private void addToLocations() {
		this.locationsVisited.add(this.currentLocation.split(" to ")[1]);		
	}
	public void updateCurrentLocation(String outputQKey) {
		this.currentLocation = outputQKey;
	}
	public void updateCurrentLocationType(String currentLocationType) {
		this.currentLocationType = currentLocationType;
	}
	public void markAsLost() {
		this.livePacket = false;
	}
	public boolean getPacketState() {
		return this.livePacket;
	}
	public void showVisitedLocations() {
		System.out.println(this.packetName+" packet's path:");
		for(int i=0;i<this.locationsVisited.size();i++){
			System.out.println("router: "+this.locationsVisited.get(i));
		}
		
	}
	public int getPriorityValue(){
		return this.priorityValue;
	}
	
	
}
