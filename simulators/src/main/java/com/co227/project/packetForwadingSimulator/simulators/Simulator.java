package com.co227.project.packetForwadingSimulator.simulators;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;



public class Simulator {
	public  int noRouters = 0,noLinks;
	private int[][] adjecencyMat = null,forwardingTable;
	private boolean settingTopology=false;
	public static HashMap<String,Router>Routers;
	public static HashMap<String,Link>Links;
	public static HashMap<String,Packet>Packets;
	public static HashMap<String,Queue>InputBuffer;
	public static HashMap<String,Queue>OutputBuffer;
	public static ArrayList<ArrayList<String>> timeStamps;
	public static ArrayList<String> simpleLinks;
	public static boolean injectDone = true;
	public static double timeElapsed=0;
	Simulator(){
		simpleLinks = new ArrayList<String>();
		timeStamps = new ArrayList<ArrayList<String>>(); 
		InputBuffer = new HashMap<String,Queue>();
		OutputBuffer = new HashMap<String,Queue>();
		Links = new HashMap<String,Link>();
		Routers = new HashMap<String,Router>();
		this.setUpForwadingTable();
		Packets = new HashMap<String,Packet>();
		gui227 inputWindow= new gui227();
		this.settingTopology = true;
		this.start();
	}
	private void start() {
		
		while(true){
			this.iteratePackets(timeStamps);
		}
		
	}
	private void iteratePackets(ArrayList<ArrayList<String>> timeStamps) {
		ArrayList<String> aTimeStamp = new ArrayList<String>();
		//set new event
		for (HashMap.Entry<String, Packet> entry2 : Packets.entrySet())
		{
//		    System.out.println(entry.getKey() + "/" + entry.getValue());
			boolean livingPacket = entry2.getValue().getPacketState();
			if(entry2.getValue().getNextEvent()==null && livingPacket){
			//	System.out.println("event is null");
				this.processPacketNextEvent(entry2.getKey());
			}
		}
		//check for next possible event
		double leastEventTime=Double.MAX_VALUE;
		
		for (HashMap.Entry<String, Packet> entry2 : Packets.entrySet())
		{
			
			boolean livingPacket = entry2.getValue().getPacketState();
			if(livingPacket){
				double eventTime = entry2.getValue().getNextEvent().getTimeTaken();
				if(eventTime < leastEventTime){
					leastEventTime = eventTime;
				//System.out.println("leastEventTime "+leastEventTime);
				}
			}
			
		}
		//excute the events
		if(leastEventTime<Double.MAX_VALUE){
			timeElapsed+=leastEventTime;
			
		//	System.out.println("################################");
			for (HashMap.Entry<String, Packet> entry2 : Packets.entrySet())
			{
				boolean livingPacket = entry2.getValue().getPacketState();
				if(livingPacket){
					//System.out.println("before execution  "+entry2.getValue().getID()+" is on "+entry2.getValue().getCurrentLocation()+"  "+entry2.getValue().getCurrentLocationType());
					this.executeNextEvent(entry2.getKey(),leastEventTime);
					addToTimeSlot(entry2.getValue(),aTimeStamp);
				//	System.out.println(entry2.getValue().getID()+" is on "+entry2.getValue().getCurrentLocation()+"  "+entry2.getValue().getCurrentLocationType());
				}
				
			}
			timeStamps.add(aTimeStamp);
			System.out.println("######################################################### time: "+timeElapsed+" ms");
		}
		
		//check for new injected packets
		gui227.addNewPackets();
	}
	
	private void addToTimeSlot(Packet packet, ArrayList<String> aTimeStamp) {
		String currentLocationType = packet.getCurrentLocationType();
		String currentLocation = packet.getCurrentLocation();
		if(currentLocationType.equals("OutputQ")){
			String graphLoaction = packet.getID()+" r "+currentLocation.split(" to ")[0];
			aTimeStamp.add(graphLoaction);
		}
		else if(currentLocationType.equals("InputQ")){
			String graphLoaction = packet.getID()+" r "+currentLocation.split(" to ")[1];
			aTimeStamp.add(graphLoaction);
		}
		else if(currentLocationType.equals("transmittedToLink")){
			String graphLoaction = packet.getID()+" l "+currentLocation;
			aTimeStamp.add(graphLoaction);
		}
		else if(currentLocationType.equals("onLinkEdge")){
			String graphLoaction = packet.getID()+" l "+currentLocation;
			aTimeStamp.add(graphLoaction);
		}
		else{
			System.out.println("hello what is the unknown location type");
		}
		
		
	}
	private void executeNextEvent(String key, double leastEventTime) {
		
		String eventID = Packets.get(key).getNextEvent().getEventID();
		if(eventID.equals("wait") || eventID.equals("lose") || eventID.equals("reached")){
			Packets.get(key).getNextEvent().excuteEvent(leastEventTime);
			Packets.get(key).addToEvents();
		}
		else if(Packets.get(key).getNextEvent().getTimeTaken()==leastEventTime){
			Packets.get(key).getNextEvent().excuteEvent(leastEventTime);
			Packets.get(key).addToEvents();
		}
		else{
			Packets.get(key).getNextEvent().halfExecuteEvent(leastEventTime);
		}
		
		
	}
	private void processPacketNextEvent(String packetName) {
		String currentLocationType = Packets.get(packetName).getCurrentLocationType();
		String currentLocation = Packets.get(packetName).getCurrentLocation();
		
		//System.out.println("@@  "+packetName+"  "+currentLocation+"  "+currentLocationType);
		
		if(currentLocationType.equals("InputQ")){
			
			
			//System.out.println(Packets.get(packetName).getID()+" is in inputQ "+currentLocation);
			int routerID = Integer.parseInt(currentLocation.split(" to ")[1]);
			if(InputBuffer.get(currentLocation).packetIsAtExit(packetName.toString())){
				int nextRouter=this.forwardingTable[routerID][Packets.get(packetName).getDest()];
				if(nextRouter==-1){
					NextEvent newEvent = new Reach("reached",Routers.get(routerID+"").getProcessingDelay(),packetName,currentLocation);
					Packets.get(packetName).setNextEvent(newEvent);
					
					
				}				
				else if(checkOutputQ(nextRouter,routerID,Packets.get(packetName).getSize())){
					NextEvent newEvent = new ProcessSwitch("process&switch",Routers.get(routerID+"").getProcessingDelay(),currentLocation,routerID+" to "+nextRouter,packetName);
					Packets.get(packetName).setNextEvent(newEvent);
				}
				else{
					//FIFO priority remove
					//now the packet has not enough space
					//hence check outputQ's packets(from the first packet) whether they are less prior to the packet 
					//if so the packet is removed
					//this is done until packet gets enough space
					//even if removing suitable packets does not create enough space all the packets(packet and the packets removed) are lost
					if(tryToPushPacket(nextRouter,routerID,Packets.get(packetName).getPriorityValue(),Packets.get(packetName).getSize())){
						NextEvent newEvent = new ProcessSwitch("process&switch",Routers.get(routerID+"").getProcessingDelay(),currentLocation,routerID+" to "+nextRouter,packetName);
						Packets.get(packetName).setNextEvent(newEvent);
					}
					else{
						//instead of waiting until outputQ gets empty lose the packet
	//					NextEvent newEvent = new Wait("wait",Double.MAX_VALUE,packetName,currentLocation);
	//					Packets.get(packetName).setNextEvent(newEvent);
						NextEvent newEvent = new Lose("lose",Double.MAX_VALUE,packetName,currentLocation,currentLocationType);
						Packets.get(packetName).setNextEvent(newEvent);
					}
				}
				
			}
			else{
				NextEvent newEvent = new Wait("wait",Double.MAX_VALUE,packetName,currentLocation);
				Packets.get(packetName).setNextEvent(newEvent);
			}
			
			
		}
		else if(currentLocationType.equals("OutputQ")){
			
			
			if(OutputBuffer.get(currentLocation).packetIsAtExit(packetName.toString())){
				NextEvent newEvent = new TransmitToLink("transmitToLink",Links.get(currentLocation).getTransmissionDelay(Packets.get(packetName).getSize()),currentLocation,currentLocation,packetName);
				Packets.get(packetName).setNextEvent(newEvent);
//				if(Links.get(currentLocation).linkIsClear()){
//					Links.get(currentLocation).acquireLink();
//				//	System.out.println(Packets.get(packetName).getID()+" ############is in outputQ "+currentLocation);
//					//System.out.println("time for transmission: "+Routers.get(currentLocation.split(" to ")[0]).getTransmittingDelay(Packets.get(packetName).getSize()));
//					NextEvent newEvent = new TransmitToLink("transmitToLink",Links.get(currentLocation).getTransmissionDelay(Packets.get(packetName).getSize()),currentLocation,currentLocation,packetName);
//					Packets.get(packetName).setNextEvent(newEvent);
//				}
//				else{
//					NextEvent newEvent = new Wait("wait",Double.MAX_VALUE,packetName,currentLocation);
//					Packets.get(packetName).setNextEvent(newEvent);
//				}
				
				
			}
			else{
				Wait newEvent = new Wait("wait",Double.MAX_VALUE,packetName,currentLocation);
				Packets.get(packetName).setNextEvent(newEvent);
			}
			
		}
		else if(currentLocationType.equals("onLinkEdge")){
			
	//		System.out.println(Packets.get(packetName).getID()+" is on edge of link "+currentLocation);
			if(checkInputQ(currentLocation,Packets.get(packetName).getSize())){
				NextEvent newEvent = new InputQueuing("inputQueuing",0D,currentLocation,currentLocation,packetName);
				Packets.get(packetName).setNextEvent(newEvent);
			}
			else{
				NextEvent newEvent = new Lose("lose",Double.MAX_VALUE,packetName,currentLocation);
				Packets.get(packetName).setNextEvent(newEvent);
			}
		}
		else if(currentLocationType.equals("transmittedToLink")){
			
	//		System.out.println(Packets.get(packetName).getID()+" is at the beginnig of link "+currentLocation);
			NextEvent newEvent = new Propagating("propagating",Links.get(currentLocation).getPropagatingDelay(),packetName,currentLocation);
			Packets.get(packetName).setNextEvent(newEvent);
		}
		else{
	//		System.out.println("ftw");
		}
	}
	private boolean tryToPushPacket(int nextRouter, int routerID,int priorityValue, double size) {		
		return OutputBuffer.get(routerID+" to "+nextRouter).priorityDiscard(priorityValue,size);
	}
	private boolean checkInputQ(String currentLocation, double packetSize) {
		return InputBuffer.get(currentLocation).addPacketVirtually(packetSize);
	}
	private boolean checkOutputQ(int nextRouter, int routerID, double packetSize) {
		return OutputBuffer.get(routerID+" to "+nextRouter).addPacketVirtually(packetSize);
	}
	private void setUpForwadingTable() {
		boolean firstLine=true;
		boolean secondLine=false;
		String network="C.txt";
		//String matFile = "C:/Hiruna/CO227/Project/src/"+network;
		String matFile = "./src/main/java/"+network;
		String line = "";
		try (BufferedReader br = new BufferedReader(new FileReader(matFile))) {
        	while ((line = br.readLine()) != null ) {
        		String [] cmd = line.split(" ");
        		if(firstLine){
        			noRouters=Integer.valueOf(cmd[0]);
        			noLinks=Integer.valueOf(cmd[1]);
        			adjecencyMat = new int[noRouters][noRouters];
        			firstLine=false;
        			secondLine=true;
        			for(int i=0;i<noRouters;i++){
        				Queue tempQ1 = new Queue(i+" to "+i,"InputQ",""+i,20D);
        				InputBuffer.put(i+" to "+i,tempQ1);
        			}
        		}
        		else if(secondLine){
        			for(int i=0;i<noRouters;i++){
        				Router tempRouter = new Router(i,Integer.valueOf(cmd[i]));
        				String tempKey = i+"";
        				Routers.put(tempKey, tempRouter);
        			}
        			secondLine=false;
        		}
        		else{
        			
        			int router1=Integer.valueOf(cmd[0]);
        			int router2=Integer.valueOf(cmd[1]);
        			double linkDistance = Double.parseDouble(cmd[2]);
        			double transmissionRate = Double.parseDouble(cmd[3]);
        			double qCapacity = 10F;//transmissionRate; //as for now
        			
        			Link tempLink1 = new Link((router1)+" to "+(router2),"onLink",linkDistance,transmissionRate);
        			Links.put((router1)+" to "+(router2), tempLink1);
        			Link tempLink2 = new Link((router2)+" to "+(router1),"onLink",linkDistance,transmissionRate);
        			Links.put((router2)+" to "+(router1), tempLink2);
        			simpleLinks.add(router1+" to "+router2);
        			
        			Queue tempQ1 = new Queue((router1)+" to "+(router2),"InputQ",""+(router2),qCapacity);
        			Queue tempQ2 = new Queue((router2)+" to "+(router1),"InputQ",""+(router1),qCapacity);
        			Queue tempQ3 = new Queue((router1)+" to "+(router2),"OutputQ",""+(router1),qCapacity);
        			Queue tempQ4 = new Queue((router2)+" to "+(router1),"OutputQ",""+(router2),qCapacity);
        			
        			
        			InputBuffer.put((router1)+" to "+(router2), tempQ1);
        			InputBuffer.put((router2)+" to "+(router1), tempQ2);
        			OutputBuffer.put((router1)+" to "+(router2), tempQ3);
        			OutputBuffer.put((router2)+" to "+(router1), tempQ4);
        			
        			adjecencyMat[router1][router2]=1;
        			adjecencyMat[router2][router1]=1;
        		}     		
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//		for(int i=0;i<adjecencyMat.length;i++){
//			for(int j=0;j<adjecencyMat.length;j++){
//				System.out.print(adjecencyMat[i][j]+" ");
//			}
//			System.out.println();
//		}

		int src=0;
		forwardingTable= new int[noRouters][noRouters];
		ForwadingTable table1= new ForwadingTable(adjecencyMat,src,forwardingTable);
	}
	
}
