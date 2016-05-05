import java.net.InetAddress;

public class RoutingEntry {
	public InetAddress sourceip;
	public int sourceport;
	public InetAddress destip;
	public int destport;
	public int cost;
	public InetAddress nextip;
	public int nextport;
}
