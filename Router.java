package co227PacketSimulator;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
/**
 * Created by Anjana Senanayake on 11/3/2017.
 */
public class Router
{
    private int routerID;
    private int[][]adjecencyMat;
    private int[][]forwardingTable;
	private HashMap<String, Link> links;
	private int queueLimit=5;
    public LinkedBlockingDeque<Packet>pcConnectedQ;
    public Router(int routerID,int[][]adjecencyMat,int[][]forwardingTable, HashMap<String, Link> links)
    {
        this.routerID = routerID;
        this.adjecencyMat=adjecencyMat;
        this.forwardingTable=forwardingTable;
        this.links=links;
        this.pcConnectedQ = new LinkedBlockingDeque<Packet>(queueLimit);
    }
	public void process(int cycleNo) {
		//process incoming links
		for(int i=0;i<Main.noRouters;i++){
			
			String tempKey1=i+" to "+routerID;
			if(adjecencyMat[routerID][i]==1 && !links.get(tempKey1).checkEmpty()){
				Packet tempPacket=links.get(tempKey1).getPacketOut();
				int nextRouter=this.forwardingTable[routerID][Integer.parseInt(tempPacket.getDestinationNode())];
				if(nextRouter==-1){
					//destination arrived
					System.out.println(tempPacket.getPacketId()+"  destination arrived cycleNo "+cycleNo);
				}
				else{
					String tempKey2=routerID+" to "+nextRouter;
					tempPacket.setRoute("routerID "+routerID+" cycleNo "+cycleNo);
					links.get(tempKey2).addPacketIn(tempPacket);
				}
			}
	
		}
		//check the PC connected queue after checking links
		if(!pcConnectedQ.isEmpty()){
			Packet tempPacket = pcConnectedQ.remove();
			int nextRouter=this.forwardingTable[routerID][Integer.parseInt(tempPacket.getDestinationNode())];
			if(nextRouter==-1){
				//destination arrived
				System.out.println(tempPacket.getPacketId()+"  destination arrived cycleNo "+cycleNo);
			}
			else{
				String tempKey2=routerID+" to "+nextRouter;
				tempPacket.setRoute("routerID "+routerID+" cycleNo "+cycleNo);
				links.get(tempKey2).addPacketIn(tempPacket);
			}
		}
		
		
	}
	public void addToPCconnectedQ(Packet P){
		P.setRoute("routerID "+routerID);
    	if(pcConnectedQ.size()<queueLimit ){
    		pcConnectedQ.add(P);
    		return;    		
    	}
    	System.out.println(P.getPacketId()+" is lost in router "+this.routerID);
	}


//    public boolean isActive(Link link)
//    {
//        if(getIncomingQueue(link).isEmpty())
//        {
//            return false;
//        }
//        return true;
//    }

    /**Checks the status(incoming/outgoing) of queues in a link*/
//    public ArrayDeque<Packet> getIncomingQueue(Link link)
//    {
//        if(!link.queue[0].isEmpty() && link.queue[0].getFirst().getPacketId().equals(routerID))
//        {
//            return link.queue[0];
//        }
//        else if(!link.queue[1].isEmpty())
//        {
//            return link.queue[1];
//        }
//        return link.queue[0];
//    }
//
//    /**Returns the queue of a link which carries outgoing packets w.r.t the current node*/
//    public ArrayDeque<Packet> getOutgoingQueue(Link link)
//    {
//        if(link.queue[0].isEmpty() || !link.queue[0].getFirst().getPacketId().equals(routerID))
//        {
//            return  link.queue[0];
//        }
//        return link.queue[1];
//    }
//
//    /**Packet forwarding from source link to target link*/
//    public  void linkForwarder(Link linkSrc,Link linkTrg)
//    {
//        getOutgoingQueue(linkTrg).addLast(linkSrc.dequeIncomingQueue(getIncomingQueue(linkSrc)));
//    }
//
//    /**Checks whether the packet has reached it's specified destination*/
//    public boolean packetDestinationCheck(Link link)
//    {
//        if(getIncomingQueue(link).getFirst().getPacketId().equals(routerID))
//        {
//            return true;
//        }
//        return false;
//    }

   
    /*public void cycleLinks()
    {
        for(Map.Entry<Integer,Link> entry : linkMap.entrySet())
        {
            for(int j=0;j<entry.getValue().sizeForwardQueue();j++)
            {
                for(int t=0;t<i;t++)
                {
                    entry.getValue().forwardQueue.getFirst().setRoute(entry.getValue().getLinkId());
                }
                entry.getValue().forwardQueue.pollFirst().getDestinationNode().getOutputLink().forwardPacketTransmission();
            }
        }
    }*/
}