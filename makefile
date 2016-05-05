JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	DistanceVector.java \
	NeighborCost.java \
	NeighborInfo.java \
	Router.java \
	RouterListener.java \
	RoutingEntry.java \
	RoutingTable.java \
	SimpleEntry.java \
        

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
