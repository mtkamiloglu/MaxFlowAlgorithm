import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class MaxFlowAlgo {

	static int INF = Integer.MAX_VALUE/2;
	int n, s, t; //Number of nodes, source and sink nodes
	boolean solved;
	int maxFlow;
	List<Edge>[] graph;
	int[] nodeLevel;
	
	public MaxFlowAlgo(int n, int s, int t) {
		this.n=n;
		this.s=s;
		this.t=t;
		initializeGraph();
		nodeLevel = new int[n]; 
	}
	
	public void initializeGraph() {
		graph = new List[n];
		for (int i = 0; i < n; i++) 
			graph[i] = new ArrayList<Edge>();
	}
	
	public void addEdge(int from, int to, int capacity) {
		Edge e1 = new Edge(from, to, capacity);
		Edge e2 = new Edge(to, from, 0);
		e1.residual = e2; e2.residual = e1;
		graph[from].add(e1);
		graph[to].add(e2);
	}
	
	public int getMaxFlow() {
		execute();
		return maxFlow;
	}
	
	public void execute() {
		if(solved)
			return;
		solved = true;
		solveMaxFlow();
	}
	
	//To avoid dead ends
	public void solveMaxFlow() {
		int[] next = new int[n];
		
		while(bfs()) {
			Arrays.fill(next, 0);
			for(int f = dfs(s, next, INF); f!=0; f = dfs(s, next, INF)) {
				maxFlow += f;
			}
		}
	}
	
	public boolean bfs() {
		Arrays.fill(nodeLevel, -1);
		Deque<Integer> queue = new ArrayDeque<>(n);
		queue.offer(s);
		nodeLevel[s]=0;
		while(!queue.isEmpty()) {
			int node = queue.poll();
			for(Edge edge :graph[node]) {
				int capacity = edge.remainingCapacity();
				if(capacity > 0 && nodeLevel[edge.to] == -1) {
					nodeLevel[edge.to] = nodeLevel[node]+1;
					queue.offer(edge.to);
				}
			}
		}
		return nodeLevel[t] != -1;
	}
	
	public int dfs(int at, int[] next, int flow) {
		if(at==t)
			return flow;
		
		int edgeNum = graph[at].size();
		
		for(; next[at]<edgeNum; next[at]++) {
			Edge edge =graph[at].get(next[at]);
			int capacity = edge.remainingCapacity();
			if(capacity > 0 && nodeLevel[edge.to] == nodeLevel[at]+1) {
				int bottleneck = dfs(edge.to, next, Math.min(flow, capacity));
				if(bottleneck > 0) {
					edge.augment(bottleneck);
					return bottleneck;
				}
			}
		}
		return 0;
	}
	
}
