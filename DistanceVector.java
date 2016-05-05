import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class DistanceVector {
	public int port;
	public RoutingTable routingTable;
	public DistanceVector(int port){
		this.port=port;
		//System.out.println(port);
	}
	public void initialize(ArrayList<NeighborInfo> neighbors) throws UnknownHostException{
		routingTable=new RoutingTable(port);
		for(NeighborInfo ni:neighbors){
			routingTable.add(ni);
		}
		
	}
	public void update(ArrayList<NeighborInfo> neighbors,NeighborCost neighCost,int interfaceno) throws UnknownHostException{
		//System.out.println("in distance vector the interfaceno is: "+interfaceno);
		routingTable.update(neighbors,neighCost,interfaceno);
		
		
	}
}
