package com.co227.project.packetForwadingSimulator.simulators;

import java.util.ArrayDeque;
import java.util.Iterator;

public class Queue {
	private String qID,currentLocationType;	
	private double qCapacity;
	private double initialQcapacity;
	private ArrayDeque<String>buffer;
	private String currentLocation;
	public Queue(String qID,String currentLocationType, String currentLocation, double qCapacity) {
		this.qID = qID;
		this.qCapacity = qCapacity;
		this.initialQcapacity = qCapacity;
		this.currentLocationType = currentLocationType;
		this.currentLocation = currentLocation;
		this.buffer = new ArrayDeque<>();
	}
	public double getEmptySpace(){
		return this.qCapacity;
	}
	public boolean addPacketVirtually(double packetSize) {
		if(packetSize<=this.qCapacity){
			this.qCapacity-=packetSize;
			return true;
		}
		return false;
	}
	public boolean packetIsAtExit(String packetName) {
	//	System.out.println("########first element in the Q: is "+this.buffer.getFirst()+" not packet "+packetName);
		return this.buffer.getFirst().equals(packetName);
	}
	public String removePacket() {
		this.qCapacity+=Simulator.Packets.get(this.buffer.getFirst()).getSize();
		return this.buffer.removeFirst();
	}
	public void addPacket(String packetName) {
		this.buffer.addLast(packetName);
	}
	public boolean priorityDiscard(int priorityValue,double size) {	
		if(size<=this.initialQcapacity){
			Iterator<String> itr = this.buffer.iterator();
			//to avoid messing up with packet about to be transmitted(or the packet that is already transmitting skip the first)
			itr.next();
			for(;itr.hasNext();)  {
				String packetID =  itr.next();	
				if(Simulator.Packets.get(packetID).getPriorityValue()<priorityValue){
					this.buffer.remove(packetID);
					this.qCapacity+=Simulator.Packets.get(packetID).getSize();
					size-=Simulator.Packets.get(packetID).getSize();
					NextEvent newEvent = new Lose("lose",Double.MAX_VALUE,packetID,this.qID,this.currentLocationType);
					Simulator.Packets.get(packetID).setNextEvent(newEvent);
					if(size<=0){
						return true;
					}
				}
		      }
		}		
		return false;
	}
}
