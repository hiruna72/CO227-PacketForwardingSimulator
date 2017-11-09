import java.util.ArrayDeque;
import java.util.HashMap;
/**
 * Created by Anjana Senanayake on 11/3/2017.
 */
public class Router
{
    private String routerID;
    private int numberofLinks;
    private int linkCounter=0;
    HashMap<Integer,Link> linkMap;

    public Router(String routerID,int numberofLinks)
    {
        this.routerID = routerID;
        this.numberofLinks = numberofLinks;
        linkMap = new HashMap<>(numberofLinks);
    }

    public void linkInitializer(Link link)
    {
        linkMap.put(linkCounter,link);
        linkCounter++;
    }

    /**Initial packet loading at the source node???should this method has to be implemented here or in the class
     * where we have the main method*/
   /* public void injectPackets(Packet packet)
    {
        forwardQueue.addLast(packet);
        forwardQueue.element();
    }*/

    /**Checks whether the specified link is active or not*/
    public boolean isActive(Link link)
    {
        if(getIncomingQueue(link).isEmpty())
        {
            return false;
        }
        return true;
    }

    /**Checks the status(incoming/outgoing) of queues in a link*/
    public ArrayDeque<Packet> getIncomingQueue(Link link)
    {
        if(!link.queue[0].isEmpty() && link.queue[0].getFirst().getPacketId().equals(routerID))
        {
            return link.queue[0];
        }
        else if(!link.queue[1].isEmpty())
        {
            return link.queue[1];
        }
        return link.queue[0];
    }

    /**Returns the queue of a link which carries outgoing packets w.r.t the current node*/
    public ArrayDeque<Packet> getOutgoingQueue(Link link)
    {
        if(link.queue[0].isEmpty() || !link.queue[0].getFirst().getPacketId().equals(routerID))
        {
            return  link.queue[0];
        }
        return link.queue[1];
    }

    /**Packet forwarding from source link to target link*/
    public  void linkForwarder(Link linkSrc,Link linkTrg)
    {
        getOutgoingQueue(linkTrg).addLast(linkSrc.dequeIncomingQueue(getIncomingQueue(linkSrc)));
    }

    /**Checks whether the packet has reached it's specified destination*/
    public boolean packetDestinationCheck(Link link)
    {
        if(getIncomingQueue(link).getFirst().getPacketId().equals(routerID))
        {
            return true;
        }
        return false;
    }

    /**Incomplete method.getOutputLink() is not implemented and few more coding has to be done*/
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
