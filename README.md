1.Description:
This mini distance-vector (RIP-like) protocol over UDP is implemented with JAVA.
I make several classes, they are Router.java, RouterListener.java, DistanceVector.java, RoutingTable.java, RoutingEntry.java, NeighborInfo.java, NeighborCost.java, SimpleEntry.java
By running Router for each node, we set its neighbors and their cost to it.
While running, each node send its distance vector to all its neighbors every 5 seconds. 
For each node, their neighbors’ information are stored in an arraylist of NeighborInfo Object, the NeighborInfo object consists of the neighbor’s ip address, port number, cost from local host to the neighbor.
Each node also listen on its receiving port, to receive it NeighborCost information. After receiving the information, each node update their RoutingTable using distance vector.
For each node, if its routing table changes after update, it print out the new table.
A directed graph is implemented.

2. Detail on development environment
Language: JAVA SE-1.6
OS: MAC OS 10.10.5
Editor: Sublime Text 2

3. Packet format
The basic data for each UDP packet is a string contains a group of (source ip, source port, destination ip, destination port, cost) tuples.
Then convert the string to a byte array. Then put it into a UDP datagram with destination ip and destination port.
Then we send the UDP packet through the UDP sending socket.

4.Sample commands to invoke code
First makefile as follows:
“make”

Run the four node as follows:
“java Router 2000 127.0.0.1:2001:1 127.0.0.1:2002:2 127.0.0.1:2003:5 | tee log2000.txt”
“java Router 2001 127.0.0.1:2000:1 127.0.0.1:2003:3 | tee log2001.txt”
“java Router 2002 127.0.0.1:2000:2 127.0.0.1:2003:1 | tee log2002.txt”
“java Router 2003 127.0.0.1:2000:5 127.0.0.1:2001:3 127.0.0.1:2002:1 | tee log2003.txt”

Re-start node 2000 and change its cost to port 2001 as follows:
“java Router 2000 127.0.0.1:2001:5 127.0.0.1:2002:2 127.0.0.1:2003:5 | tee -a log2000.txt”
