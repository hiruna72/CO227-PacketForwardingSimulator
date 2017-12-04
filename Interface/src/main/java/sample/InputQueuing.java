package sample;

public class InputQueuing extends NextEvent {
	private String linkKey,inputQKey;
	public InputQueuing(String eventID, double timeForEvent,String linkKey, String inputQKey, String packetName) {
		super(eventID, timeForEvent, packetName);
		this.linkKey = linkKey;
		this.inputQKey = inputQKey;
	}

	@Override
	public void excuteEvent(double leastEventTime) {
		this.timeForEvent=leastEventTime;
		String tpn = Simulator.Links.get(linkKey).getPacketOut();
		Simulator.InputBuffer.get(inputQKey).addPacket(this.packetName);
		Simulator.Packets.get(packetName).updateCurrentLocationType("InputQ");
		System.out.println(this.packetName+" is stored in the InputQ "+linkKey);
	}

	@Override
	public void halfExecuteEvent(double leastEventTime) {
		this.timeForEvent-=leastEventTime;
		System.out.println(this.packetName+" is queuing");
	}

}
