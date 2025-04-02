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
    {0, 18, 14, 7, 1, 16, 15, 13, 14, 12, 6, 17, 9, 11, 19, 3, 20, 20, 6, 7},
    {3, 0, 3, 6, 3, 6, 11, 18, 1, 3, 6, 6, 11, 10, 13, 12, 10, 5, 6, 19},
    {4, 6, 0, 3, 4, 11, 3, 17, 15, 4, 5, 6, 9, 8, 7, 15, 5, 12, 5, 19},
    {1, 12, 14, 0, 2, 3, 17, 3, 5, 6, 13, 13, 9, 20, 10, 8, 10, 18, 9, 18},
    {7, 9, 14, 14, 0, 15, 17, 1, 4, 9, 9, 8, 18, 4, 20, 7, 2, 3, 7, 18},
    {1, 11, 6, 8, 4, 0, 6, 11, 16, 4, 19, 15, 5, 18, 7, 18, 18, 15, 4, 12},
    {10, 4, 4, 14, 2, 19, 0, 4, 20, 10, 5, 11, 14, 18, 11, 19, 20, 3, 15, 9},
    {20, 16, 17, 19, 19, 10, 5, 0, 16, 1, 2, 20, 4, 1, 13, 1, 3, 2, 2, 13},
    {11, 16, 20, 3, 11, 16, 4, 5, 0, 18, 15, 17, 19, 2, 15, 2, 3, 12, 3, 5},
    {8, 2, 4, 4, 14, 12, 10, 18, 5, 0, 14, 19, 13, 18, 12, 7, 2, 13, 4, 3},
    {13, 15, 13, 11, 8, 9, 6, 5, 17, 19, 0, 11, 14, 4, 14, 15, 9, 2, 2, 8},
    {3, 20, 6, 18, 17, 6, 13, 13, 12, 15, 8, 0, 9, 2, 11, 11, 15, 4, 18, 10},
    {13, 15, 4, 5, 17, 15, 9, 15, 19, 13, 15, 17, 0, 13, 3, 5, 11, 19, 3, 7},
    {1, 17, 12, 14, 2, 9, 11, 2, 1, 3, 9, 1, 16, 0, 8, 19, 4, 8, 1, 14},
    {6, 7, 16, 4, 3, 14, 2, 5, 18, 6, 7, 16, 12, 6, 0, 2, 2, 13, 6, 13},
    {19, 11, 17, 9, 16, 19, 7, 14, 7, 16, 5, 4, 1, 20, 7, 0, 13, 15, 17, 4},
    {1, 19, 4, 5, 19, 19, 3, 15, 5, 5, 17, 1, 20, 17, 7, 4, 0, 11, 11, 4},
    {2, 19, 17, 13, 1, 1, 9, 2, 6, 2, 4, 16, 14, 20, 17, 12, 4, 0, 15, 15},
    {17, 6, 20, 5, 16, 19, 9, 15, 17, 9, 11, 13, 18, 16, 13, 9, 10, 12, 0, 18},
    {12, 2, 6, 4, 13, 11, 20, 18, 10, 3, 9, 17, 19, 19, 4, 3, 1, 18, 14, 0}
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
        Map<Integer, Integer> previous = new ConcurrentHashMap<>();				//attempted correction for parallel
        int[] dist = new int[numVertices];  // Shortest distance from source
        boolean[] visited = new boolean[numVertices];  // Visited nodes
        Arrays.fill(dist, INF);
        dist[source] = 0;

        List<Thread> threads = new ArrayList<>();

        // Process nodes one by one
        for (int count = 0; count < numVertices; count++) {
            Thread t = new DijkstraThread(visited, dist, graph, previous);
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

        printSolution(dist, previous);

        //System.out.println("Shortest distances from node " + source + ":");
        //for (int i = 0; i < dist.length; i++) {
        //    System.out.println("Node " + i + ": " + (dist[i] == INF ? "INF" : dist[i]));
        //}

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
