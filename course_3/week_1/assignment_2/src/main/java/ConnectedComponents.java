import java.util.ArrayList;
import java.util.Scanner;

public class ConnectedComponents {
  private static int numberOfComponents(ArrayList<Integer>[] adj) {
    int result = 0;
    boolean[] visited = new boolean[adj.length];

    for (int i = 0; i < adj.length; i++) {
      if (!visited[i]) {
        result++;
        explore(i, adj, visited);
      }
    }

    return result;
  }

  private static void explore(int x, ArrayList<Integer>[] adj, boolean[] visited) {
    if (visited[x]) {
      return;
    }

    visited[x] = true;

    for (int y : adj[x]) {
      if (!visited[y]) {
        explore(y, adj, visited);
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

    System.out.println(numberOfComponents(adj));

    scanner.close();
  }
}
