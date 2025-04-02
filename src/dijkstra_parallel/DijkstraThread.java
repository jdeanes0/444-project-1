package dijkstra_parallel;

import java.util.Map;

public class DijkstraThread extends Thread {
    static final int INF = Integer.MAX_VALUE;

    private int[] dist;
    private boolean[] visited;
    private final int[][] graph;
    private Map<Integer, Integer> previous;

    // Constructor to initialize the necessary parameters
    public DijkstraThread(boolean[] visited, int[] dist, int[][] graph, Map<Integer, Integer> previous) {
        this.visited = visited;
        this.dist = dist;
        this.graph = graph;
        this.previous = previous;
    }

    public void run() {
        long startTime = System.nanoTime();
        //int count = Driver.threadCount;
        //long threadStartTime = System.nanoTime();

        int u = minDistance(dist, visited);
        visited[u] = true;

        // Start a thread for each unvisited neighbor of node u
        for (int v = 0; v < graph.length; v++) {
            if (graph[u][v] != 0 && dist[u] != INF && !visited[v]) {
                previous.put(u,v);
                if (dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        //long threadEndTime = System.nanoTime(); //<<<<<
        //long threadDuration = threadEndTime - threadStartTime; //<<<<<
        //System.out.println("Thread " + count + " for edge (" + u + " -> " + neighbor + ") took " + threadDuration + " nanoseconds."); //<<<<<
        long endTime = System.nanoTime(); // End timing this iteration (jump) //<<<<
    	//System.out.println(startTime + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    	//System.out.println(endTime + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<E");
        long duration = endTime - startTime; // Calculate duration //<<<<
        //System.out.println("Time taken in total = " + duration + " nanoseconds");
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
}
