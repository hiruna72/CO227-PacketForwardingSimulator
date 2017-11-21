package sample;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Anjana Senanayake on 11/3/2017.
 **/
public class Link
{	
	public static int queueLimit=5;
    private String linkId;
    private String source;
    private String destination;
    public LinkedBlockingDeque<Packet> queue;

    public Link(String linkId,String source,String destination)
    {
    	queue = new LinkedBlockingDeque<>(queueLimit);
        this.linkId = linkId;
        this.source = source;
        this.destination = destination;
    }

    //Packet removing from the incoming queue according to FIFO concept
    public boolean checkEmpty()
    {
    	return queue.isEmpty();
    }

    public Packet getPacketOut()
    {
    		return queue.pollFirst(); //if empty, null
    }

    public void addPacketIn(Packet P)
    {
    	P.setRoute("linkID "+linkId);
    	if(!queue.add(P)){
    		//add packet to garbageHeap
    	}
    	
    }

    public String getLinkId()
    {
        return linkId;
    }

    public String getSource()
    {
        return source;
    }

    public  String getDestination()
    {
        return destination;
    }
}