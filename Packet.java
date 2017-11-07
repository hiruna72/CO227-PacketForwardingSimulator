/**
 * Created by Anjana Senanayake on 11/3/2017.
 */
import java.util.ArrayList;

public class Packet
{
    private String packetId;
    private String sourceNode;
    private String destinationNode;
    private ArrayList route;

    public Packet(String packetId,String sourceNode,String destinationNode)
    {
        this.packetId = packetId;
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
    }

    public void setRoute(String routeId)
    {
        route.add(routeId);
    }

    public ArrayList getRoute()
    {
        return route;
    }

    public String getPacketId()
    {
        return  packetId;
    }

    public String getSourceNode()
    {
        return  sourceNode;
    }

    public String getDestinationNode()
    {
        return destinationNode;
    }

}

