import java.util.ArrayList;
import java.util.Scanner;

public class Reachability {
  private static int reach(ArrayList<Integer>[] adj, int x, int y) {
    boolean[] visited = new boolean[adj.length];
    explore(x, y, adj, visited);

    return visited[y] ? 1 : 0;
  }

  private static void explore(int s, int t, ArrayList<Integer>[] adj, boolean[] visited) {
    if (visited[t]) {
      return;
    }

    visited[s] = true;

    if (s == t) {
      return;
    }

    for (int v : adj[s]) {
      if (!visited[v]) {
        explore(v, t, adj, visited);
      }
    }
  }


  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int m = scanner.nextInt();

    @SuppressWarnings("unchecked")
    ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];

    for (int i = 0; i < n; i++) {
      adj[i] = new ArrayList<Integer>();
    }

    for (int i = 0; i < m; i++) {
      int x = scanner.nextInt();
      int y = scanner.nextInt();
      adj[x - 1].add(y - 1);
      adj[y - 1].add(x - 1);
    }

    int x = scanner.nextInt() - 1;
    int y = scanner.nextInt() - 1;
    System.out.println(reach(adj, x, y));

    scanner.close();
  }
}
