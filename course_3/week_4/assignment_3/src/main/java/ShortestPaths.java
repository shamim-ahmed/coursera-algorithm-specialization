import java.util.*;

public class ShortestPaths {
  private static final long MAX = 1000000000000000L;

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

    int s = scanner.nextInt() - 1;
    long distance[] = new long[n];
    int reachable[] = new int[n];
    int shortest[] = new int[n];

    for (int i = 0; i < n; i++) {
      distance[i] = Long.MAX_VALUE;
      reachable[i] = 0;
      shortest[i] = 1;
    }

    shortestPaths(adj, cost, s, distance, reachable, shortest);

    for (int i = 0; i < n; i++) {
      if (reachable[i] == 0) {
        System.out.println('*');
      } else if (shortest[i] == 0) {
        System.out.println('-');
      } else {
        System.out.println(distance[i]);
      }
    }

    scanner.close();
  }

  private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s,
      long[] distance, int[] reachable, int[] shortest) {
    final int n = adj.length;
    int[] previous = new int[n];

    for (int i = 0; i < n; i++) {
      distance[i] = MAX;
      previous[i] = -1;
      shortest[i] = 1;
    }

    distance[s] = 0;
    reachable[s] = 1;

    for (int k = 0; k < n - 1; k++) {
      for (int i = 0; i < n; i++) {
        for (int idx = 0, len = adj[i].size(); idx < len; idx++) {
          int j = adj[i].get(idx);
          int weight = cost[i].get(idx);

          if (distance[j] > distance[i] + weight) {
            distance[j] = distance[i] + weight;
            reachable[j] = 1;
            previous[j] = i;
          }
        }
      }
    }

    for (int i = 0; i < n; i++) {
      for (int idx = 0, len = adj[i].size(); idx < len; idx++) {
        int j = adj[i].get(idx);
        int weight = cost[i].get(idx);

        if (distance[j] > distance[i] + weight && shortest[j] != 0) {
          List<Integer> cycle = findCycle(j, previous);

          for (int k : cycle) {
            shortest[k] = 0;
          }
        }
      }
    }
  }

  private static List<Integer> findCycle(int v, int[] previous) {
    List<Integer> resultList = new ArrayList<>();
    resultList.add(v);

    int x = previous[v];

    while (x != -1 && x != v) {
      resultList.add(x);
      x = previous[x];
    }

    return resultList;
  }
}
