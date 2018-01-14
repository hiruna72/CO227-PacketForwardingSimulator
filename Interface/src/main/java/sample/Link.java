package sample;

public class Link
{
	public static double linkPropagationSpeed = 200000; //2*10^5 m per second
	private String linkID;
	private double linkDistance;
	private double transmissionRate;
	private boolean linkIsClear;
	private String packetOnLink;
	private String currentLocationType;

	public Link(String linkId,String currentLocationType, double linkDistance,double transmissionRate)
	{
		this.currentLocationType = currentLocationType;
		this.linkID = linkId;
		this.linkIsClear=true;
		this.linkDistance = linkDistance;
		this.transmissionRate = transmissionRate;
		this.packetOnLink = null;
	}

	boolean linkIsClear()
	{
		return linkIsClear;
	}

	public String getPacketOut()
	{
		linkIsClear=!linkIsClear;
		return this.packetOnLink;
	}

	public void addPacketIn(String packetName)
	{
		this.packetOnLink = packetName;
		linkIsClear=false;
	}

	public double getPropagatingDelay()
	{
		return this.linkDistance/linkPropagationSpeed;
	}

	public double getTransmissionDelay(double psize)
	{
		return (psize*1000)/this.transmissionRate;
	}

	public String getLinkID()
	{
		return linkID;
	}

	public double getLinkDistance()
	{
		return linkDistance;
	}

	public double getTransmissionRate()
	{
		return transmissionRate;
	}
	public void acquireLink(){
		linkIsClear=false;
	}
}
