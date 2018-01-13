package com.co227.project.packetForwadingSimulator.simulators;

import java.util.ArrayDeque;
import java.util.Deque;

public class Router {
	private  double processingTime;
	private String routerID;
	public Router(int i,double processingTime) {
		this.processingTime = processingTime;
		this.routerID = i +"";
	}
	public double getProcessingDelay() {
		return this.processingTime/1000;
	}
	public void changeProcessingDelay(double processingTime){
		this.processingTime = processingTime;
	}

}
