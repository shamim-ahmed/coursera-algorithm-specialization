import java.util.*;

public class ShortestPaths {

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

    for (int i = 0; i < n; i++) {
      distance[i] = Long.MAX_VALUE;
      // we assume initially that there is a shortest path to each vertex
      shortest[i] = 1;
    }

    distance[s] = 0;
    reachable[s] = 1;

    // relax all edges n - 1 times
    for (int k = 0; k < n - 1; k++) {
      for (int i = 0; i < n; i++) {
        for (int idx = 0, len = adj[i].size(); idx < len; idx++) {
          int j = adj[i].get(idx);
          int weight = cost[i].get(idx);

          if (distance[i] == Long.MAX_VALUE) {
            continue;
          }

          if (distance[j] > distance[i] + weight) {
            distance[j] = distance[i] + weight;
            reachable[j] = 1;
          }
        }
      }
    }

    // Perform one additional relaxation step to detect if there is any negative cycle.
    // If such a cycle is found, then there is no shortest path from the source
    // to a vertex on the negative cycle and any vertex reachable from the negative cycle.
    for (int i = 0; i < n; i++) {
      for (int idx = 0, len = adj[i].size(); idx < len; idx++) {
        int j = adj[i].get(idx);
        int weight = cost[i].get(idx);

        if (distance[i] == Long.MAX_VALUE) {
          continue;
        }

        if ((distance[j] > distance[i] + weight) && shortest[j] == 1) {
          processCycle(j, adj, shortest);
        }
      }
    }

    // if a vertex is non-reachable, then there is no shortest path to it
    for (int i = 0; i < n; i++) {
      if (reachable[i] == 0) {
        shortest[i] = 0;
      }
    }
  }

  private static void processCycle(int v, ArrayList<Integer>[] adj, int[] shortest) {
    Set<Integer> visited = new HashSet<>();
    Stack<Integer> stack = new Stack<>();
    stack.push(v);

    // perform depth first search of the graph
    // starting from node v
    while (!stack.isEmpty()) {
      int i = stack.pop();

      visited.add(i);
      shortest[i] = 0;

      for (int j : adj[i]) {
        if (!visited.contains(j)) {
          stack.push(j);
        }
      }
    }
  }
}
