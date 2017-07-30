import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class StronglyConnected {
  private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
    // construct the data structure for the reverse graph
    ArrayList<Integer>[] reverseAdj = computeReverseGraph(adj);

    // perform dfs on the reverse graph
    ArrayList<Integer> orderList = new ArrayList<>();
    boolean[] visitFlagsForRG = new boolean[reverseAdj.length];

    for (int i = 0; i < reverseAdj.length; i++) {
      if (!visitFlagsForRG[i]) {
        dfs(i, reverseAdj, visitFlagsForRG, orderList);
      }
    }

    Collections.reverse(orderList);

    // now perform dfs on the original graph
    // the order is determined by orderList
    int count = 0;
    boolean[] visitFlags = new boolean[adj.length];

    for (int i : orderList) {
      if (!visitFlags[i]) {
        dfs(i, adj, visitFlags, null);
        count++;
      }
    }

    return count;
  }

  private static void dfs(int i, ArrayList<Integer>[] adj, boolean[] visited,
      ArrayList<Integer> orderList) {
    visited[i] = true;

    for (int j : adj[i]) {
      if (!visited[j]) {
        dfs(j, adj, visited, orderList);
      }
    }

    if (orderList != null) {
      orderList.add(i);
    }
  }

  private static ArrayList<Integer>[] computeReverseGraph(ArrayList<Integer>[] adj) {
    @SuppressWarnings("unchecked")
    ArrayList<Integer>[] reverseAdj = (ArrayList<Integer>[]) new ArrayList[adj.length];

    for (int i = 0; i < reverseAdj.length; i++) {
      reverseAdj[i] = new ArrayList<Integer>();
    }

    for (int i = 0; i < adj.length; i++) {
      for (int j : adj[i]) {
        reverseAdj[j].add(i);
      }
    }

    return reverseAdj;
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
    }

    System.out.println(numberOfStronglyConnectedComponents(adj));
    scanner.close();
  }
}
