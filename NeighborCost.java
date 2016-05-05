import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NeighborCost {
	public int interfaceno;
	public String[] info;
	HashMap<Integer, ArrayList<SimpleEntry>> simpleTable=new HashMap<Integer,ArrayList<SimpleEntry>>();
	//HashMap<Integer, SimpleRoutingTable> simpleTable=new HashMap<Integer,SimpleRoutingTable>();
	
	public int update(ArrayList<NeighborInfo> neighbors,byte[] pdata) throws UnsupportedEncodingException, NumberFormatException, UnknownHostException{
		synchronized(simpleTable){
			String data=new String(pdata,"UTF-8");
			info=data.split(",");
			//System.out.println(Arrays.toString(info));
			for(NeighborInfo ni:neighbors){
				//System.out.println("how about this "+info[0].substring(1));
				//ni.address.equals(InetAddress.getByName(info[0]))&&
				int index=info[0].indexOf("/")+1;
				if(ni.address.equals(InetAddress.getByName(info[0].substring(index)))&&ni.port==Integer.parseInt(info[1])){
					interfaceno=ni.interfaceno; 
					break;
				}
					
			}
			if(!simpleTable.containsKey(interfaceno)){
				//System.out.println("this interfaceno: "+interfaceno);
				ArrayList<SimpleEntry> thisst=new ArrayList<SimpleEntry>();
				for(int i=0;i<info.length/5;i++){
					SimpleEntry thisse=new SimpleEntry();
					int index1=info[5*i].indexOf("/")+1;
					thisse.sourceip=InetAddress.getByName(info[5*i].substring(index1));
					thisse.sourceport=Integer.parseInt(info[5*i+1]);
					int index2=info[5*i+2].indexOf("/")+1;
					thisse.destip=InetAddress.getByName(info[5*i+2].substring(index2));
					thisse.destport=Integer.parseInt(info[5*i+3]);
					thisse.cost=Integer.parseInt(info[5*i+4]);
					thisst.add(thisse);
				}
				simpleTable.put(interfaceno,thisst);
				
			}else{
				simpleTable.remove(interfaceno);
				ArrayList<SimpleEntry> thisst=new ArrayList<SimpleEntry>();
				for(int i=0;i<info.length/5;i++){
					SimpleEntry thisse=new SimpleEntry();
					int index3=info[5*i].indexOf("/")+1;
					thisse.sourceip=InetAddress.getByName(info[5*i].substring(index3));
					thisse.sourceport=Integer.parseInt(info[5*i+1]);
					int index4=info[5*i].indexOf("/")+1;
					thisse.destip=InetAddress.getByName(info[5*i+2].substring(index4));
					thisse.destport=Integer.parseInt(info[5*i+3]);
					thisse.cost=Integer.parseInt(info[5*i+4]);
					thisst.add(thisse);
				}
				simpleTable.put(interfaceno,thisst);
			}
			return interfaceno;
		}
		
		
	}
	
//	class SimpleEntry{
//		public InetAddress sourceip;
//		public int sourceport;
//		public InetAddress destip;
//		public int destport;
//		public int cost;
//	}
}
