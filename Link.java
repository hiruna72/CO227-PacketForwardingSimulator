package co227PacketSimulator;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Anjana Senanayake on 11/3/2017.
 **/
public class Link
{	
	public static int queueLimit=5;
    private String linkID;
    public LinkedBlockingDeque<Packet>queue;

    public Link(String linkId)
    {
    	queue = new LinkedBlockingDeque<Packet>(queueLimit);
        this.linkID = linkId;
    }

    //Packet removing from the incoming queue according to FIFO concept
    public boolean checkEmpty(){
    	return queue.isEmpty();
    }
    public Packet getPacketOut(){
    		return queue.pollFirst(); //if empty null
    }
    public void addPacketIn(Packet P){
    	P.setRoute("linkID "+linkID);
    	if(!queue.add(P)){
    		//add packet to garbageHeap
    	}
    	
    }

//    public int queueSize(ArrayDeque<Packet> queue)
//    {
//        return queue.size();
//    }
//
//    public void clearQueue(ArrayDeque<Packet> queue)
//    {
//        queue.clear();
//    }

    public String getLinkId()
    {
        return linkID;
    }
    
}