import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite {
  private enum Color {
    RED, GREEN
  }

  private static int bipartite(ArrayList<Integer>[] adj) {
    int result = 1;
    Queue<Integer> queue = new LinkedList<>();
    Color[] vertexColors = new Color[adj.length];

    int s = 0;
    vertexColors[s] = Color.RED;
    queue.offer(s);

    while (!queue.isEmpty() && result != 0) {
      int u = queue.poll();
      Color otherColor = vertexColors[u] == Color.RED ? Color.GREEN : Color.RED;

      for (int v : adj[u]) {
        if (vertexColors[v] != null && vertexColors[v] != vertexColors[u]) {
          continue;
        }

        if (vertexColors[v] == vertexColors[u]) {
          result = 0;
          break;
        }

        vertexColors[v] = otherColor;
        queue.offer(v);
      }
    }

    return result;
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

    System.out.println(bipartite(adj));
    scanner.close();
  }
}
