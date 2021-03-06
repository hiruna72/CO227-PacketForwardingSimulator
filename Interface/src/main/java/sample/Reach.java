package sample;

public class Reach extends NextEvent {

	private String currentLocation;
	public Reach(String eventID, double timeForEvent, String packetName,String currentLocation)
	{
		super(eventID, timeForEvent, packetName);
		this.currentLocation = currentLocation;
	}

	@Override
	public void excuteEvent(double leastEventTime) {
		Simulator.InputBuffer.get(currentLocation).removePacket();
		Simulator.Packets.get(packetName).markAsLost();
		System.out.println(packetName+" reached destination");
	}

	@Override
	public void halfExecuteEvent(double leastEventTime) {
		this.excuteEvent(leastEventTime);
	}

}
