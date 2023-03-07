package cy.ac.ucy.cs.epl231.IDs913240.homework3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class PageRank {
	public static void main(String[] args) throws IOException {

		List<String> urls = new ArrayList<>();
		System.out.println("> Creating Graph . . .\n\n\n");
		FileReader fr = new FileReader(args[0]);
		BufferedReader br = new BufferedReader(fr);
		String line;
		int index = 0;
		while (((line = br.readLine()) != null)) {
			// int c = line.indexOf("/");
			// line = line.substring(0, c);
			if (!urls.contains(line)) {
				urls.add(line);
			}
		}

		br.close();
		int V = urls.size();
		System.out.println("Number of Vertices: "+V);
		Graph graph = new Graph(V);
		graph.addEdge(graph, 0, 1);
		int edges[] = new int[V];
		Random rn = new Random();
		
		System.out.println("Select a URL based on number: ");
		Scanner sc=new Scanner(System.in);
		int selected=sc.nextInt();

		for (int i = 0; i < V; i++) {
			edges[i] = rn.nextInt(30) + 1;
			for (int j = 0; j < edges[i]; j++) {
				int k = rn.nextInt(V);
				if (k == 0)
					while (graph.adjListArray[i].contains(k)) {
						k = rn.nextInt(V);
					}
				graph.addEdge(graph, i, k);
			}
			// PRINT GRAPH
			// System.out.println(graph.adjListArray[i]);

		}
		double pagerank[] = new double[V];
		double temp[] = new double[V];

		for (int i = 0; i < V; i++) {
			pagerank[i] = 1;
			temp[i] = 1;
		}
		double result = 0;
		System.out.println("\n\n***** Method A *****\n\n");
		int counter = 0;
		do {
			counter++;
			for (int i = 0; i < V; i++) {
				temp[i] = pagerank[i];
			}

			for (int i = 0; i < V; i++) {
				double rank = 0;
				for (int j = 0; j < V; j++) {
					if (graph.adjListArray[j].contains(i)) {
						rank += 0.85 * temp[j] / edges[j];
					}
				}
				pagerank[i] = 0.15 + rank;
			}

			double distance = 0;
			for (int i = 0; i < V; i++) {
				distance += Math.abs(temp[i] - pagerank[i]);
			}
			result = distance;
		} while (result > 0.00001);

		System.out.println("Loops: " + counter);
		System.out.println("PageRank for " + urls.get(selected) + ": " + pagerank[selected]+"\n");
		for (int i=0;i<edges[selected];i++) {
		System.out.println("\tPageRank for link: "+urls.get(i)+": "+pagerank[i]);
		}
		System.out.println("\n\n***** Method B *****\n\n");

		for (int i = 0; i < V; i++) {
			pagerank[i] = 1;
			temp[i] = 1;

		}
		double threshhold = 0.5;
		result = 0;
		counter = 0;
		do {
			counter++;
			for (int i = 0; i < V; i++) {
				temp[i] = pagerank[i];
			}

			if (counter > 5) {
				for (int i = 0; i < V; i++) {
					if (pagerank[i] < threshhold) {
						pagerank[i] = 0;
						temp[i] = 0;
					}
				}
			}

			else {

				for (int i = 0; i < V; i++) {
					if (pagerank[i] != 0) {
						double rank = 0;
						for (int j = 0; j < V; j++) {
							if (graph.adjListArray[j].contains(i)) {
								rank += 0.85 * temp[j] / edges[j];

							}
						}

						pagerank[i] = rank + 0.15;
					}
				}
			}

			double distance = 0;
			for (int i = 0; i < V; i++) {
				distance += Math.abs(temp[i] - pagerank[i]);
			}
			result = distance;

		} while (result > 0.00001);

		System.out.println("Loops: " + counter);
		System.out.println("PageRank for " + urls.get(selected) + ": " + pagerank[selected]+"\n");
		for (int i=0;i<edges[selected];i++) {
			System.out.println("\tPageRank for link: "+urls.get(i)+": "+pagerank[i]);
			}

		System.out.println("\n\n***** Method C *****\n\n");
		for (int i = 0; i < V; i++) {
			pagerank[i] = 1;
			temp[i] = 1;

		}
		double siblingFactor = 0.85;
		result = 0;
		counter = 0;
		do {
			counter++;
			for (int i = 0; i < V; i++) {
				temp[i] = pagerank[i];
			}

			for (int i = 0; i < V; i++) {

				int c = urls.get(i).indexOf("/");
				String s = urls.get(i).substring(0, c);
				double rank = 0;
				for (int j = 0; j < V; j++) {

					if (graph.adjListArray[j].contains(i) && i!=j) {

						int c2 = urls.get(j).indexOf("/");
						String s2 = urls.get(j).substring(0, c2);
						if (s2.equals(s))
							rank += 0.85 * temp[j] / edges[j];
						else
							rank += siblingFactor * 0.85 * temp[j] / edges[j];

					}
				}
				pagerank[i] = 0.15 + rank;
			}

			double distance = 0;
			for (int i = 0; i < V; i++) {
				distance += Math.abs(temp[i] - pagerank[i]);
			}
			result = distance;
		} while (result > 0.00001);

		System.out.println("Loops: " + counter);
		System.out.println("PageRank for " + urls.get(selected) + ": " + pagerank[selected]+"\n");
		for (int i=0;i<edges[selected];i++) {
			System.out.println("\tPageRank for link: "+urls.get(i)+": "+pagerank[i]);
			}

	}

}
