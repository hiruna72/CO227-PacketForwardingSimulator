import java.util.ArrayDeque;
/**
 * Created by Anjana Senanayake on 11/3/2017.
 **/
public class Link
{
    private String linkId;
    public ArrayDeque<Packet>[] queue = new ArrayDeque[2];

    public Link(String linkId)
    {
        queue[0] = new ArrayDeque<>();
        queue[1] = new ArrayDeque<>();
        this.linkId = linkId;
    }

    //Packet removing from the incoming queue according to FIFO concept
    public Packet dequeIncomingQueue(ArrayDeque<Packet> queue)
    {
        return queue.removeFirst();
    }

    public int queueSize(ArrayDeque<Packet> queue)
    {
        return queue.size();
    }

    public void clearQueue(ArrayDeque<Packet> queue)
    {
        queue.clear();
    }

    public String getLinkId()
    {
        return linkId;
    }
}
