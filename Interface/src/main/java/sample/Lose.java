package sample;

public class Lose extends NextEvent {
	private String currentLocation;
	public Lose(String eventID, double timeForEvent, String packetName,String currentLocation) {
		super(eventID, timeForEvent, packetName);
		this.currentLocation = currentLocation;
	}

	@Override
	public void excuteEvent(double leastEventTime) {
		String tpn = Simulator.Links.get(this.currentLocation).getPacketOut();
		Simulator.Packets.get(packetName).markAsLost();
		System.out.println(packetName+" is lost on link "+this.currentLocation);
	}

	@Override
	public void halfExecuteEvent(double leastEventTime) {
		this.excuteEvent(leastEventTime);
	}

}
