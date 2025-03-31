package dijkstra_parallel;

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
        if (dist[u] + graph[u][neighbor] < dist[neighbor]) {
            dist[neighbor] = dist[u] + graph[u][neighbor];
        }
    }

}
