package cy.ac.ucy.cs.epl231.IDs913240.homework3;

import java.util.LinkedList;

public class Graph {
	int V;
	LinkedList<Integer> adjListArray[];

	public Graph(int V) {
		this.V = V;

		adjListArray = new LinkedList[V];

		for (int i = 0; i < V; i++) {
			adjListArray[i] = new LinkedList<>();
		}
	}

	public void addEdge(Graph graph, int src, int dest) {
		graph.adjListArray[src].add(dest);

	}
}
