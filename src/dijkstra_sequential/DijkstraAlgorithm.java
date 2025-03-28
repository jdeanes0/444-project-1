package dijkstra_sequential;
// This program is a sequential implementation of Dijkstra's algorithm
// It finds the shortest path from a source node to all other nodes in a graph
import java.util.Arrays;
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
        int[][] graph = {
            {0, 10, 1, 0, 0, 5},  // Vertex 0 is connected to Vertex 1 with weight 10
            {10, 1, 5, 0, 0, 0},  // Vertex 1 is connected to Vertex 0 with weight 10, and Vertex 2 with weight 5
            {0, 5, 1, 1, 0, 0},  // Vertex 2 is connected to Vertex 1 with weight 5, and Vertex 3 with weight 1
            {0, 1, 20, 0, 10, 0},  // Vertex 3 is connected to Vertex 2 with weight 20, and Vertex 4 with weight 10
            {0, 0, 0, 10, 0, 5},  // Vertex 4 is connected to Vertex 3 with weight 10, and Vertex 5 with weight 5
            {0, 0, 0, 0, 5, 0}    // Vertex 5 is connected to Vertex 4 with weight 5
        };

        int source = 0;
        
        // Run Dijkstra's algorithm to find the shortest paths from the source to all other vertices
        dijkstra(graph, source);
    }
}
