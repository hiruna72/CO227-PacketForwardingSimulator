package sample;

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
		for(int i=0;i<10;i++){
			
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

	public void addToPCconnectedQ(Packet P)
	{
		P.setRoute("routerID "+routerID);
    	if(pcConnectedQ.size()<queueLimit )
    	{
    		pcConnectedQ.add(P);
    		return;    		
    	}
    	System.out.println(P.getPacketId()+" is lost in router "+this.routerID);
	}

	public void deleteFromPCconnectedQ(Packet P)
	{
		if(pcConnectedQ.contains(P))
		{
			pcConnectedQ.remove(P);
		}
	}
}