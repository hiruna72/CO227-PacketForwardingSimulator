import java.util.HashMap;
<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> 403bcf4a1287d44a98005e90731744639059bb26

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

    public boolean packetDestinationCheck(Link link)
    {
        if(link.forwardQueue.getFirst().getDestinationNode() == routerID)
        {
            return true;
        }
        return false;
    }

<<<<<<< HEAD
    /*public void cycleLinks()//Incomplete method,getOutputLink() method is to be completed
    {
        for(Map.Entry<Integer,Link> entry : linkMap.entrySet())
        {
            for(int j=0;j<entry.getValue().sizeForwardQueue();j++)//link path has to be stored
=======
    public void cycleLinks()//Incomplete method
    {
        for(Map.Entry<Integer,Link> entry : linkMap.entrySet())
        {
            for(int j=0;j<entry.getValue().sizeForwardQueue();j++)
>>>>>>> 403bcf4a1287d44a98005e90731744639059bb26
            {
                for(int t=0;t<i;t++)
                {
                    entry.getValue().forwardQueue.getFirst().setRoute(entry.getValue().getLinkId());
                }
                entry.getValue().forwardQueue.pollFirst().getDestinationNode().getOutputLink().forwardPacketTransmission();
            }
        }
<<<<<<< HEAD
    }*/
=======
    }
>>>>>>> 403bcf4a1287d44a98005e90731744639059bb26
}
