package dijkstra_sequential;
// This program is a sequential implementation of Dijkstra's algorithm
// It finds the shortest path from a source node to all other nodes in a graph
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class DijkstraAlgorithm {
    static final int INF = Integer.MAX_VALUE;

    // Function to implement Dijkstra's algorithm
    public static void dijkstra(int graph[][], int source) {
        int n = graph.length;
        int[] distance = new int[n]; // Array to hold the shortest distance from source to each vertex
        boolean[] visited = new boolean[n]; // Array to check if a vertex is visited or not
        int[] previous = new int[n]; // Array to store the previous vertex in the shortest path

        // Initialize the distance array and previous array
        Arrays.fill(distance, INF);
        Arrays.fill(previous, -1); // -1 will indicate that there is no previous vertex initially

        // Distance to the source itself is always 0
        distance[source] = 0;
        long startTotalTime = System.nanoTime(); // Start timing each jump
        // Find the shortest path for all vertices
        for (int count = 0; count < n - 1; count++) {
            int u = minDistance(distance, visited);
            visited[u] = true;

            // Update the distance of the adjacent vertices of the picked vertex
            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != 0 && distance[u] != INF &&
                        distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                    previous[v] = u; // Record the previous vertex for backtracking the path
                }
            }
        }
        //long endTotalTime = System.nanoTime(); // Start timing each jump
        //System.out.println("Total time: " + (endTotalTime - startTotalTime));
        printSolution(distance, previous);
    }

    // Function to find the vertex with the minimum distance value
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
    private static void printSolution(int dist[], int[] previous) {
        System.out.println("Vertex\t\tDistance from Source\t\tPath");
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == INF) {
                System.out.println(i + "\t\tNo path\t\t\tNo path");
            } else {
                System.out.print(i + "\t\t" + dist[i] + "\t\t\t");
                printPath(i, previous); // Print the path for the current vertex
            }
        }
    }

    // Function to print the path from source to the current vertex
    private static void printPath(int vertex, int[] previous) {
        Stack<Integer> path = new Stack<>();
        for (int v = vertex; v != -1; v = previous[v]) {
            path.push(v);
        }

        // Print the path
        while (!path.isEmpty()) {
            System.out.print(path.pop());
            if (!path.isEmpty()) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

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

        long startTotalTime = System.nanoTime();

        // Run Dijkstra's algorithm to find the shortest paths from the source to all other vertices
        dijkstra(graph, source);

        long endTotalTime = System.nanoTime();
        System.out.println("Total time: " + (endTotalTime - startTotalTime)+ " nanoseconds");
    }
}
