package dijkstra_parallel;

import dijkstra_parallel.Driver;

public class DijkstraThread extends Thread {
    private final int u;
    private final int neighbor;
    private final int[] dist;
    private final int[][] graph;

    // Constructor to initialize the necessary parameters
    public DijkstraThread(int u, int neighbor, int[] dist, int[][] graph) {
        this.u = u;
        this.neighbor = neighbor;
        this.dist = dist;
        this.graph = graph;
    }

    public void run() {
    	long startTime = System.nanoTime(); // Start timing each iteration (jump) //<<<<
    	//System.out.println(startTime + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    	int count = Driver.threadCount;
    	long threadStartTime = System.nanoTime(); //<<<<<
        if (dist[u] + graph[u][neighbor] < dist[neighbor]) {
        	//System.out.println("Updating distance via edge (" + u + " -> " + neighbor + ")"); //<<<<< Debugging
            dist[neighbor] = dist[u] + graph[u][neighbor];
        }
        long threadEndTime = System.nanoTime(); //<<<<<
        long threadDuration = threadEndTime - threadStartTime; //<<<<<
        System.out.println("Thread " + count + " for edge (" + u + " -> " + neighbor + ") took " + threadDuration + " nanoseconds."); //<<<<<
        long endTime = System.nanoTime(); // End timing this iteration (jump) //<<<<
    	//System.out.println(startTime + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    	//System.out.println(endTime + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<E");
        long duration = endTime - startTime; // Calculate duration //<<<<
        System.out.println("Time taken in total = " + duration + " nanoseconds"); //<<<<
    }

}
