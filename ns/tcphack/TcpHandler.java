package ns.tcphack;

abstract class TcpHandler {
	private ns.tcphack.TcpHackClient client;

	public TcpHandler() {
		client = new ns.tcphack.TcpHackClient();
	}

	protected void sendData(int[] data) {
		client.send(data);
	}

	protected int[] receiveData(long timeout) {
		return client.dequeuePacket(timeout);
	}
}
