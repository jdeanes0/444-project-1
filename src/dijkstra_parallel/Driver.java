package dijkstra_parallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the driver class for the larger Dijkstra's Algorithm.
 *
 * It will spawn the required threads for the algorithm to truly make it a parallel algorithm
 */

public class Driver {

    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int[][] graph = {
                {0, 10, 1, 0, 0, 5},  // Vertex 0 is connected to Vertex 1 with weight 10
                {10, 1, 5, 0, 0, 0},  // Vertex 1 is connected to Vertex 0 with weight 10, and Vertex 2 with weight 5
                {0, 5, 1, 1, 0, 0},  // Vertex 2 is connected to Vertex 1 with weight 5, and Vertex 3 with weight 1
                {0, 1, 20, 0, 10, 0},  // Vertex 3 is connected to Vertex 2 with weight 20, and Vertex 4 with weight 10
                {0, 0, 0, 10, 0, 5},  // Vertex 4 is connected to Vertex 3 with weight 10, and Vertex 5 with weight 5
                {0, 0, 0, 0, 5, 0}    // Vertex 5 is connected to Vertex 4 with weight 5
        };

        int source = 0;

        // begin calling threads
        run(graph, source);
    }

    public static void run(int[][] graph, int source) {
        int numVertices = graph.length;
        int[] dist = new int[numVertices];  // Shortest distance from source
        boolean[] visited = new boolean[numVertices];  // Visited nodes
        Arrays.fill(dist, INF);
        dist[source] = 0;

        List<Thread> threads = new ArrayList<>();

        // Process nodes one by one
        for (int count = 0; count < numVertices; count++) {
            Thread t = new DijkstraThread(visited, dist, graph);
            threads.add(t);  // Add the thread to the list
            t.start();  // Start the thread
        }

        // Wait for all threads to finish before moving on
        for (Thread t : threads) {
            try {
                t.join();  // Wait for each thread to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Shortest distances from node " + source + ":");
        for (int i = 0; i < dist.length; i++) {
            System.out.println("Node " + i + ": " + (dist[i] == INF ? "INF" : dist[i]));
        }
    }
}
