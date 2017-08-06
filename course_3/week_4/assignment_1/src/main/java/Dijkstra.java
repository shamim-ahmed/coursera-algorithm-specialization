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
    return -1;
  }
}
