package co227PacketSimulator;

import java.util.Arrays;

public class Pair {
	private int orderedPair[];
	
	public Pair(int router1, int router2) {
		this.orderedPair = new int[2];
		this.orderedPair[0]=router1;
		this.orderedPair[1]=router2;
		Arrays.sort(orderedPair);

	}
	public String getPair(){
		return this.orderedPair[0]+" to "+this.orderedPair[1];
	}
	
}
