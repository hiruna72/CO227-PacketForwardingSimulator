package sample;

import java.util.ArrayDeque;

public class Queue
{
	private String qID,currentLocationType;	
	private double qCapacity;
	private ArrayDeque<String>buffer;
	private String currentLocation;

	public Queue(String qID,String currentLocationType, String currentLocation, double qCapacity)
	{
		this.qID = qID;
		this.qCapacity = qCapacity;
		this.currentLocationType = currentLocationType;
		this.currentLocation = currentLocation;
		this.buffer = new ArrayDeque<>();
	}

	public double getEmptySpace()
	{
		return this.qCapacity;
	}

	public boolean addPacketVirtually(double packetSize)
	{
		if(packetSize<=this.qCapacity){
			this.qCapacity-=packetSize;
			return true;
		}
		return false;
	}

	public boolean packetIsAtExit(String packetName)
	{
		return this.buffer.getFirst().equals(packetName);
	}

	public String removePacket()
	{
		this.qCapacity+=Simulator.Packets.get(this.buffer.getFirst()).getSize();
		return this.buffer.removeFirst();
	}

	public void addPacket(String packetName)
	{
		this.buffer.add(packetName);
	}

	//Remove the exact given packet from the buffer virtually and physically
	public void removeThisPacket(String packetName)
	{
		this.qCapacity+=Simulator.Packets.get(packetName).getSize();
		this.buffer.remove(Simulator.Packets.get(packetName));
	}
}
