package sample;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Packet
{
	private int priorityValue;
	private int src,dest;
	private double size;
	private double timeDead;
	public ArrayList<NextEvent> events;
    public ArrayList<String> nodesVisited;
	public LinkedHashMap<String,ArrayList<NextEvent>> eventOrder;
	private String packetName;
	private String currentLocation;
	private String currentLocationType;
	private NextEvent nextEvent;
	private boolean livePacket;

	Packet(String packetName,int priorityValue,int src,int dest,double size, String currentLocationType, String currentLocation)
	{
		this.src=src;
		this.dest=dest;
		this.priorityValue = priorityValue;
		this.packetName=packetName;
		this.size=size;
		this.eventOrder = new LinkedHashMap<>();
		this.nodesVisited = new ArrayList<>();
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
	public void addToEvents()
    {
        events = new ArrayList<>();
        events.add(nextEvent);
        if(eventOrder.containsKey(currentLocation))
        {
            eventOrder.get(currentLocation).add(nextEvent);
        }
        else
        {
            eventOrder.put(currentLocation,events);
        }
		this.addtoNodes();
		this.nextEvent=null;
	}
	public void addtoNodes()
    {
        this.nodesVisited.add(currentLocation);
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
	public String getPacketName()
	{
		return packetName;
	}
	public int getPriorityValue(){
		return this.priorityValue;
	}

	public void setTimeDead(double timeDead)
	{
		this.timeDead = timeDead;
	}

	public double getTimeDead()
	{
		return this.timeDead;
	}
}
