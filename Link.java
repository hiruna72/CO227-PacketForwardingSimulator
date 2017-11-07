import java.util.ArrayDeque;
import java.util.Deque;
/**
 * Created by Anjana Senanayake on 11/3/2017.
 **/
public class Link
{
    private String linkId;
    public Deque<Packet> forwardQueue = new ArrayDeque<>();
    public Deque<Packet> backwardQueue = new ArrayDeque<>();

    public Link()
    {
       // this.linkId = linkID;
    }

    //Initial packet loading to at the source node
    public void enqueForwardQueue(Packet packet)
    {
        forwardQueue.addLast(packet);
        forwardQueue.element();
    }

    //Packet forwarding from link to link
    public  void forwardPacketTransmission(Link link)
    {
        forwardQueue.addLast(link.dequeForwardQueue());
    }

    //Packet removing in FIFO concept
    public Packet dequeForwardQueue()
    {
        return forwardQueue.removeFirst();
    }

    public int sizeForwardQueue()
    {
        return forwardQueue.size();
    }

    public int sizeBackwardQueue()
    {
        return backwardQueue.size();
    }

    public void clearForwardQueue()
    {
        forwardQueue.clear();
    }

    public void clearBackwardQueue()
    {
        backwardQueue.clear();
    }

    public String getLinkId()
    {
        return linkId;
    }
}
