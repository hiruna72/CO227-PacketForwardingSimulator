package sample;
/**
 * Created by Anjana Senanayake on 11/3/2017.
 */
import java.util.ArrayList;

public class Packet
{
    private String packetId;
    private String sourceNode;
    private String destinationNode;
    private boolean select;
    private ArrayList<String> route;

    public Packet(String packetId,String sourceNode,String destinationNode,boolean select)
    {
        this.packetId = packetId;
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.select = select;
        this.route = new ArrayList<String>();
    }

    public void setRoute(String routeId)
    {
        route.add(routeId);
    }

    public ArrayList getRoute()
    {
        return route;
    }

    public void setSelect(boolean select)
    {
        this.select = select;
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

    public boolean getSelect()
    {
        return select;
    }
}
