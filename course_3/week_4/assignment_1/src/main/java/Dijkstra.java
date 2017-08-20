import java.util.*;

public class Dijkstra {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int m = scanner.nextInt();

    @SuppressWarnings("unchecked")
    ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
    @SuppressWarnings("unchecked")
    ArrayList<Integer>[] cost = (ArrayList<Integer>[]) new ArrayList[n];

    for (int i = 0; i < n; i++) {
      adj[i] = new ArrayList<Integer>();
      cost[i] = new ArrayList<Integer>();
    }

    for (int i = 0; i < m; i++) {
      int x = scanner.nextInt();
      int y = scanner.nextInt();
      int w = scanner.nextInt();
      adj[x - 1].add(y - 1);
      cost[x - 1].add(w);
    }

    int x = scanner.nextInt() - 1;
    int y = scanner.nextInt() - 1;
    System.out.println(distance(adj, cost, x, y));

    scanner.close();
  }

  private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
    VertexData[] dataArray = new VertexData[adj.length];

    for (int i = 0; i < dataArray.length; i++) {
      dataArray[i] = new VertexData(i, Integer.MAX_VALUE);
    }

    PriorityQueue<VertexData> pQueue = new PriorityQueue<>(new Comparator<VertexData>() {
      @Override
      public int compare(VertexData data1, VertexData data2) {
        if (data1.getDistance() < data2.getDistance()) {
          return -1;
        }

        if (data1.getDistance() > data2.getDistance()) {
          return 1;
        }

        return 0;
      }
    });

    // initialize the priority queue
    dataArray[s].setDistance(0);
    pQueue.offer(dataArray[s]);

    // Dijkstra's algorithm
    while (!pQueue.isEmpty()) {
      VertexData uData = pQueue.poll();
      int u = uData.getVertex();

      for (int i = 0, n = adj[u].size(); i < n; i++) {
        int v = adj[u].get(i);
        int edgeWeight = cost[u].get(i);
        VertexData vData = dataArray[v];

        if (uData.getDistance() == Integer.MAX_VALUE) {
          continue;
        }

        if (vData.getDistance() > uData.getDistance() + edgeWeight) {
          pQueue.remove(vData);
          vData.setDistance(uData.getDistance() + edgeWeight);
          pQueue.offer(vData);
        }
      }
    }

    int result = dataArray[t].getDistance();

    if (result == Integer.MAX_VALUE) {
      result = -1;
    }

    return result;
  }

  private static class VertexData {
    private final int vertex;
    private int distance;

    public VertexData(int vertex, int distance) {
      this.vertex = vertex;
      this.distance = distance;
    }

    public int getVertex() {
      return vertex;
    }

    public int getDistance() {
      return distance;
    }

    public void setDistance(int distance) {
      this.distance = distance;
    }
  }
}
