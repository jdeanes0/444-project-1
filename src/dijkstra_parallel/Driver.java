package dijkstra_parallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import java.util.concurrent.ConcurrentHashMap;		//for printing code
import java.util.Map;


/**
 * This is the driver class for the larger Dijkstra's Algorithm.
 *
 * It will spawn the required threads for the algorithm to truly make it a parallel algorithm
 */

public class Driver {
	public static int threadCount = 0;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
//        int[][] graph = {
//                {0, 10, 1, 0, 0, 5},  // Vertex 0 is connected to Vertex 1 with weight 10
//                {10, 1, 5, 0, 0, 0},  // Vertex 1 is connected to Vertex 0 with weight 10, and Vertex 2 with weight 5
//                {0, 5, 1, 1, 0, 0},  // Vertex 2 is connected to Vertex 1 with weight 5, and Vertex 3 with weight 1
//                {0, 1, 20, 0, 10, 0},  // Vertex 3 is connected to Vertex 2 with weight 20, and Vertex 4 with weight 10
//                {0, 0, 0, 10, 0, 5},  // Vertex 4 is connected to Vertex 3 with weight 10, and Vertex 5 with weight 5
//                {0, 0, 0, 0, 5, 0}    // Vertex 5 is connected to Vertex 4 with weight 5
//                
//                
//                //make larger for test
//        };
    	int[][] graph = {
                {0, 10, 1, 5, 9, 2, 0, 0, 7, 6},  // Expanded test case
                {10, 0, 4, 0, 0, 8, 3, 0, 0, 0},  
                {1, 4, 0, 6, 0, 3, 0, 7, 0, 5},  
                {5, 0, 6, 0, 2, 0, 4, 0, 9, 0},  
                {9, 0, 0, 2, 0, 0, 0, 8, 0, 3},  
                {2, 8, 3, 0, 0, 0, 5, 0, 4, 0},  
                {0, 3, 0, 4, 0, 5, 0, 2, 0, 1},  
                {0, 0, 7, 0, 8, 0, 2, 0, 6, 0},  
                {7, 0, 0, 9, 0, 4, 0, 6, 0, 2},  
                {6, 0, 5, 0, 3, 0, 1, 0, 2, 0}   
    	};

        int source = 0;

        // begin calling threads
        run(graph, source);
    }

    public static void run(int[][] graph, int source) {
        int numVertices = graph.length;
    	//int[] previous = new int[numVertices]; 								//from Abby's code
        Map<Integer, Integer> previous = new ConcurrentHashMap<>();				//attempted correction for parallel
        int[] dist = new int[numVertices];  // Shortest distance from source
        boolean[] visited = new boolean[numVertices];  // Visited nodes
        Arrays.fill(dist, INF);							//sets initial values to INF
        dist[source] = 0;								//sets source to 0
    	
        // Process nodes one by one
        for (int count = 0; count < numVertices; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            List<Thread> threads = new ArrayList<>();  // To hold all threads for current node

            // Start a thread for each unvisited neighbor of node u
            for (int v = 0; v < numVertices; v++) {
                if (graph[u][v] != 0 && dist[u] != INF && !visited[v]) {
           //where new threads are made by calling Dijkstra Thread
                	Thread t = new DijkstraThread(u, v, dist, graph);
                	threadCount++;
                    threads.add(t);  // Add the thread to the list
                    t.start();  // Start the thread
                    previous.put(v, u); 							// for printing, parallel focused update
                }
            }

            // Wait for all threads to finish before moving to the next node
            for (Thread t : threads) {
                try {
                    t.join();  // Wait for each thread to finish
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            
        }

        printSolution(dist, previous);
        
        System.out.println("Shortest distances from node " + source + ":");
        for (int i = 0; i < dist.length; i++) {
            System.out.println("Node " + i + ": " + (dist[i] == INF ? "INF" : dist[i]));
        }

    }

    private static int minDistance(int[] distance, boolean[] visited) {
        int min = INF, minIndex = -1;

        for (int v = 0; v < distance.length; v++) {
            if (!visited[v] && distance[v] <= min) {
                min = distance[v];
                minIndex = v;
            }
        }
        return minIndex;
    }
    
    
    // Function to print the shortest distance to all vertices along with the path
    private static void printSolution(int[] dist, Map<Integer, Integer> previousMap) {
        System.out.println("Vertex\t\tDistance from Source\t\tPath");
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == INF) {
                System.out.println(i + "\t\tNo path\t\t\tNo path");
            } else {
                System.out.print(i + "\t\t" + dist[i] + "\t\t\t");
                printPath(i, previousMap); // Updated call
            }
        }
    }


    // Function to print the path from source to the current vertex
    private static void printPath(int vertex, Map<Integer, Integer> previous) {
        Stack<Integer> path = new Stack<>();
        Integer v = vertex;
        while (v != null) {  // Null check prevents infinite loops
            path.push(v);
            v = previous.get(v);
        }

        while (!path.isEmpty()) {
            System.out.print(path.pop());
            if (!path.isEmpty()) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }


}
