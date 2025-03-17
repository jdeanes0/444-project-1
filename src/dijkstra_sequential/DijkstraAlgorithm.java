package dijkstra_sequential;
// this program is a sequential implementation of Dijkstra's algorithm
// it finds the shortest path from a source node to all other nodes in a graph
import java.util.Arrays;

public class DijkstraAlgorithm {
    static final int INF = Integer.MAX_VALUE;

    // Function to implement Dijkstra's algorithm
    public static void dijkstra(int graph[][], int source, int destination) {
        int n = graph.length;
        int[] distance = new int[n]; // Array to hold the shortest distance from source to each vertex
        boolean[] visited = new boolean[n]; // Array to check if a vertex is visited or not

        // Initialize the distance array
        Arrays.fill(distance, INF);

        // Distance to the source itself is always 0
        distance[source] = 0;

        // Find the shortest path for all vertices
        for (int count = 0; count < n - 1; count++) {
            int u = minDistance(distance, visited);
            visited[u] = true;

            // Update the distance of the adjacent vertices of the picked vertex
            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != 0 && distance[u] != INF &&
                        distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                }
            }
        }

        printSolution(distance, destination);
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

    // Function to print the shortest distance to the destination vertex
    private static void printSolution(int dist[], int destination) {
        if (dist[destination] == Integer.MAX_VALUE) {
            System.out.println("There is no path from source to vertex " + destination);
        } else {
            System.out.println("Shortest distance from source to vertex " + destination + " is: " + dist[destination]);
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
            {0, 10, 0, 0, 0, 0},  // Vertex 0 is connected to Vertex 1 with weight 10
            {10, 0, 5, 0, 0, 0},  // Vertex 1 is connected to Vertex 0 with weight 10, and Vertex 2 with weight 5
            {0, 5, 0, 20, 0, 0},  // Vertex 2 is connected to Vertex 1 with weight 5, and Vertex 3 with weight 20
            {0, 0, 20, 0, 10, 0},  // Vertex 3 is connected to Vertex 2 with weight 20, and Vertex 4 with weight 10
            {0, 0, 0, 10, 0, 5},  // Vertex 4 is connected to Vertex 3 with weight 10, and Vertex 5 with weight 5
            {0, 0, 0, 0, 5, 0}    // Vertex 5 is connected to Vertex 4 with weight 5
        };

        int source = 0;
        int destination = 3; // Change this to test different destinations
        dijkstra(graph, source, destination);
    }
}