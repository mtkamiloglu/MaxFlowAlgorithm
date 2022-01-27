
public class Edge {

	int from;	int to;
	int flow;	int capacity;
	Edge residual;

	//Constructor for edge class
	public Edge(int from, int to, int capacity) {
		this.from = from;
		this.to = to;
		this.capacity = capacity;
	}
	
	//This method returns true if an edge is residual
	public boolean isResidual() {
		return this.capacity == 0;
	}

	//This method returns remaining capacity of edge
	int remainingCapacity() {
		return this.capacity- this.flow;
	}

	//This method updates updates flow of this edge and residual edge
	void augment(int bottleneck) {
		this.flow += bottleneck;
		this.residual.flow -= bottleneck;
	}
}
