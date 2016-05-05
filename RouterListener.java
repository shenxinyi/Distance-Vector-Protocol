import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class RouterListener extends Thread{
	private DatagramSocket recesock;
    private DatagramPacket recedatapkt;
    private ArrayList<NeighborInfo> neighbors;
    NeighborCost neighCost;
    DistanceVector distVector;
	public RouterListener(DatagramSocket recesock,ArrayList<NeighborInfo> neighbors,NeighborCost neighCost,DistanceVector distVector){
		this.recesock=recesock;
		this.neighbors=neighbors;
		this.neighCost=neighCost;
		this.distVector=distVector;
	}
	public void run(){
		while(true){
			recedatapkt=new DatagramPacket(new byte[500],500);
			try {
				recesock.receive(recedatapkt);
				byte[] receData=recedatapkt.getData();
				String data;
				
				data = new String(receData,"UTF-8");
				
				//System.out.println("received "+data);
				int interfaceno=neighCost.update(neighbors,receData);
				distVector.update(neighbors,neighCost,interfaceno);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
