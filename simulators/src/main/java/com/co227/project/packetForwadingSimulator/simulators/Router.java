package com.co227.project.packetForwadingSimulator.simulators;

import java.util.ArrayDeque;
import java.util.Deque;

public class Router {
	private  double processingTime;
	private String routerID;
	private String locationType;
	private double transmissionRate;
	public Router(int i,double processingTime,double transmissionRate) {
		this.transmissionRate = transmissionRate;
		this.processingTime = processingTime;
		this.locationType = "PCconnectedQ";
		this.routerID = i +"";
	}
	public double getProcessingDelay() {
		return this.processingTime;
	}
	public double getTransmittingDelay(double packetSize) {
		return packetSize/this.transmissionRate;
	}

}
