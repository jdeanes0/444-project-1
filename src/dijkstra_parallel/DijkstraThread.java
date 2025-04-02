package dijkstra_parallel;

public class DijkstraThread extends Thread {
    static final int INF = Integer.MAX_VALUE;

    private int[] dist;
    private boolean[] visited;
    private final int[][] graph;

    // Constructor to initialize the necessary parameters
    public DijkstraThread(boolean[] visited, int[] dist, int[][] graph) {
        this.visited = visited;
        this.dist = dist;
        this.graph = graph;
    }

    public void run() {
        int u = minDistance(dist, visited);
        visited[u] = true;

        // Start a thread for each unvisited neighbor of node u
        for (int v = 0; v < graph.length; v++) {
            if (graph[u][v] != 0 && dist[u] != INF && !visited[v]) {
                if (dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
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
}
