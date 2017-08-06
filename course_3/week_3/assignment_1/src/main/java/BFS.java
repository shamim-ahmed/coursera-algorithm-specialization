import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BFS {
  private static int distance(ArrayList<Integer>[] adj, int s, int t) {
    int[] distance = new int[adj.length];

    for (int i = 0; i < adj.length; i++) {
      distance[i] = Integer.MAX_VALUE;
    }

    distance[s] = 0;
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(s);

    boolean found = false;

    while (!queue.isEmpty() && !found) {
      int u = queue.poll();

      for (int v : adj[u]) {
        if (distance[v] == Integer.MAX_VALUE) {
          distance[v] = distance[u] + 1;
          queue.offer(v);

          if (v == t) {
            found = true;
            break;
          }
        }
      }
    }

    return distance[t] == Integer.MAX_VALUE ? -1 : distance[t];
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
    System.out.println(distance(adj, x, y));

    scanner.close();
  }
}
