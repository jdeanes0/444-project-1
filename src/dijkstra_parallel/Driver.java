package dijkstra_parallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

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
/*
        int size = 1000;
        int[][] graph = new int[size][size];
        Random rand = new Random();

        // Generate a random weighted adjacency matrix
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    graph[i][j] = rand.nextInt(20) + 1; // Random weights between 1 and 20
                }
            }
        }
        int source = 0;
*/
        int[][] graph = {
            {0, 4, 0, 0, 0, 0, 0, 8, 0},
            {4, 0, 8, 0, 0, 0, 0, 11, 0},
            {0, 8, 0, 7, 0, 4, 0, 0, 2},
            {0, 0, 7, 0, 9, 14, 0, 0, 0},
            {0, 0, 0, 9, 0, 10, 0, 0, 0},
            {0, 0, 4, 14, 10, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 1, 6},
            {8, 11, 0, 0, 0, 0, 1, 0, 7},
            {0, 0, 2, 0, 0, 0, 6, 7, 0}
    };
    
        int source = 0;


        long startTime = System.nanoTime();

        // begin calling threads
        run(graph, source);

        long endTime = System.nanoTime();
        long duration = endTime - startTime; // Calculate duration //<<<<
        System.out.println("Time taken in total = " + duration + " nanoseconds");

    }

    public static void run(int[][] graph, int source) {
        int numVertices = graph.length;
    	//int[] previous = new int[numVertices]; 								//from Abby's code
        // Map<Integer, Integer> previous = new ConcurrentHashMap<>();				//attempted correction for parallel
        int[] dist = new int[numVertices];  // Shortest distance from source
        boolean[] visited = new boolean[numVertices];  // Visited nodes
        Arrays.fill(dist, INF);
        dist[source] = 0;

        List<Thread> threads = new ArrayList<>();

        // Process nodes one by one
        for (int count = 0; count < numVertices; count++) {
            Thread t = new DijkstraThread(visited, dist, graph);
            threadCount++;
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

        printSolution(dist);

        // System.out.println("Shortest distances from node " + source + ":");
        // for (int i = 0; i < dist.length; i++) {
        //    System.out.println("Node " + i + ": " + (dist[i] == INF ? "INF" : dist[i]));
        // }

    }

    // Function to print the shortest distance to all vertices along with the path
    private static void printSolution(int[] dist) {
        System.out.println("Vertex\t\tDistance from Source\t\tPath");
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == INF) {
                System.out.println(i + "\t\tNo path\t\t\tNo path");
            } else {
                System.out.println(i + "\t\t" + dist[i] + "\t\t\t");
                // printPath(i, previousMap); // Updated call
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
