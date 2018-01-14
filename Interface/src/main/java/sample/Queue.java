package sample;

import java.util.ArrayDeque;
import java.util.Iterator;

public class Queue
{
	private double initialQcapacity;
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
	public boolean priorityDiscard(int priorityValue,double size)
	{
		if(size<=this.initialQcapacity){
			Iterator<String> itr = this.buffer.iterator();
			//to avoid messing up with packet about to be transmitted(or the packet that is already transmitting skip the first)
			itr.next();
			for(;itr.hasNext();)  {
				String packetID =  itr.next();
				if(Simulator.Packets.get(packetID).getPriorityValue()<priorityValue){
					this.buffer.remove(packetID);
					System.out.println("first element in the Q: "+this.buffer.getFirst());
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
