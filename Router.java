import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class Router{
	private static Router r;
	//the listening port of this node
	private int listenport;
	//the sending port of this node
	private int sendport;
	//the router's distance vector
	private static DistanceVector distVect;
	//the router's neighbors' cost table
	private NeighborCost neighCost=new NeighborCost();
	//the router's list of neighbors
	private static ArrayList<NeighborInfo> neighbors;
	//udp socket and packet
	//define UDP socket
    private DatagramSocket sendsock;
    private DatagramPacket senddatapkt;
    private DatagramSocket recesock;
    private DatagramPacket recedatapkt;
	public Router(int port){
		this.listenport=port;
		this.sendport=listenport+1000;
	}
	//execute after initialization
	public void execute() throws IOException{
		recesock=new DatagramSocket(listenport);
		sendsock=new DatagramSocket(sendport);
		RouterListener rl=new RouterListener(recesock,neighbors,neighCost,distVect);
		rl.start();
		long lastsend=0;
		int count=0;
		while(true){
			if(lastsend==0||System.currentTimeMillis()-lastsend>=5000){
				r.sendPkt();
				lastsend=System.currentTimeMillis();
			}
		}
	}

	public void sendPkt() throws IOException{
		for(NeighborInfo ni : neighbors){
			InetAddress ipaddr=ni.address;
			int port=ni.port;
			senddatapkt=r.getPkt(ipaddr,port);
			//System.out.println("packet ready");
			sendsock.send(senddatapkt);
		}
		
	}
	
	public DatagramPacket getPkt(InetAddress ipaddr,int port){
		RoutingTable rt=distVect.routingTable;
		String stringentry="";
		for(RoutingEntry entry:rt.table){
			stringentry=stringentry+entry.sourceip+","+entry.sourceport+","+entry.destip+","+entry.destport+","+entry.cost+",";
			//System.out.println(stringentry);
		}
		DatagramPacket dp=new DatagramPacket(stringentry.getBytes(),stringentry.getBytes().length,ipaddr,port);
		return  dp;
	}
	
	public static void main(String[] args) throws IOException{
		int len=args.length;
		r=new Router(Integer.parseInt(args[0]));
		if(len<2) {
			System.out.println("this router has no neighbor");
		}
		else{
			neighbors=new ArrayList<NeighborInfo>();
			for(int j=1;j<len;j++){
				String[] thisneighbor=args[j].split(":");
				NeighborInfo thisn=new NeighborInfo();
				thisn.interfaceno=j;
				//System.out.println("interfaceno: "+j);
				thisn.address=InetAddress.getByName(thisneighbor[0]);
				//System.out.println("this address"+thisn.address);
				//System.out.println("this address"+thisn.address.toString());
				thisn.port=Integer.parseInt(thisneighbor[1]);
				thisn.cost=Integer.parseInt(thisneighbor[2]);
				neighbors.add(thisn);
			}
			distVect=new DistanceVector(Integer.parseInt(args[0]));
			distVect.initialize(neighbors);
			r.execute();
		}
	}
	
}