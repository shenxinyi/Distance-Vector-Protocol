import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

public class RoutingTable {
	public int port;
	public RoutingTable(int port){
		this.port=port;
	}
	ArrayList<RoutingEntry> table=new ArrayList<RoutingEntry>();
	public void add(NeighborInfo ni) throws UnknownHostException{
		synchronized(table){
			RoutingEntry entry=new RoutingEntry();
			entry.sourceip=InetAddress.getByName("localhost");
			entry.sourceport=port;
			entry.destip=ni.address;
			entry.destport=ni.port;
			entry.nextip=ni.address;
			entry.nextport=ni.port;
			entry.cost=ni.cost;
			table.add(entry);
			//System.out.println("in routing table "+ni.cost);
		}
		
	}
	
	public void update(ArrayList<NeighborInfo> neighbors,NeighborCost neighCost,int interfaceno) throws UnknownHostException{
		synchronized(table){
			boolean isChanged=false;
			int thisneighborcost=0;
			NeighborInfo changedNeighbor;
			//System.out.println("in routingtable the interfaceno is: "+interfaceno);
			ArrayList<SimpleEntry> changedinterface=neighCost.simpleTable.get(interfaceno);
			for(NeighborInfo ni:neighbors){
				if(ni.interfaceno==interfaceno){
					thisneighborcost=ni.cost;
				} 
			}
			for(SimpleEntry se:changedinterface){
				int count=0;
				//System.out.println("count"+count);
				for(RoutingEntry re:table){
					//System.out.println("different???"+re.destip.toString()+" , "+se.destip);
					if(re.destip.equals(se.destip)&&re.destport==se.destport){
						//System.out.println("why not here???");
						if(re.cost>se.cost+thisneighborcost){
							re.cost=se.cost+thisneighborcost;
							isChanged=true;
							break;
							
						}
					}else if(re.sourceip.equals(se.destip)&&re.sourceport==se.destport){
						break;
					}else count++;
					//System.out.println("count"+count);
				}
				if(count==table.size()){
					RoutingEntry newre=new RoutingEntry();
					newre.sourceip=InetAddress.getByName("localhost");
					newre.sourceport=port;
					newre.destip=se.destip;
					newre.destport=se.destport;
					newre.nextip=se.sourceip;
					newre.nextport=se.sourceport;
					newre.cost=thisneighborcost+se.cost;
					table.add(newre);
					isChanged=true;
				} 
			}
			if(isChanged==true){
				System.out.println("Node "+InetAddress.getByName("localhost").toString()+":"+port+" @ "+new Date());
				for(RoutingEntry re:table){
					System.out.println(re.destip+" "+re.destport+" "+re.cost+" "+interfaceno);
				}
				
			}
		}
	}
}
