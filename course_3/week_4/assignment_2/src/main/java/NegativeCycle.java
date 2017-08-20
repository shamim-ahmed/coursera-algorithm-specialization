import java.util.ArrayList;
import java.util.Scanner;

public class NegativeCycle {

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

    System.out.println(negativeCycle(adj, cost));
    scanner.close();
  }

  private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
    int[] distance = new int[adj.length];

    for (int i = 0; i < distance.length; i++) {
      distance[i] = Integer.MAX_VALUE;
    }

    // set distance for source vertex
    distance[0] = 0;
    
    for (int i = 0; i < adj.length - 1; i++) {
      relax(adj, cost, distance);
    }

    return canRelax(adj, cost, distance) ? 1 : 0;
  }

  private static void relax(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int[] distance) {
    for (int u = 0; u < adj.length; u++) {
      for (int i = 0, len = adj[u].size(); i < len; i++) {
        int v = adj[u].get(i);
        int edgeWeight = cost[u].get(i);

        if (distance[u] == Integer.MAX_VALUE) {
          continue;
        }

        if (distance[v] > distance[u] + edgeWeight) {
          distance[v] = distance[u] + edgeWeight;
        }
      }
    }
  }

  private static boolean canRelax(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost,
      int[] distance) {
    boolean result = false;

    for (int u = 0; u < adj.length && !result; u++) {
      for (int i = 0, len = adj[u].size(); i < len; i++) {
        int v = adj[u].get(i);
        int edgeWeight = cost[u].get(i);

        if (distance[u] == Integer.MAX_VALUE) {
          continue;
        }

        if (distance[v] > distance[u] + edgeWeight) {
          result = true;
          break;
        }
      }
    }

    return result;
  }
}
