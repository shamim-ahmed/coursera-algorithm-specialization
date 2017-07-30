import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Toposort {
  private static ArrayList<Integer> toposort(ArrayList<Integer>[] adj) {
    boolean visitFlags[] = new boolean[adj.length];
    ArrayList<Integer> orderList = new ArrayList<Integer>();
    
    for (int i = 0; i < adj.length; i++) {
      if (!visitFlags[i]) {
        dfs(i, adj, visitFlags, orderList);
      }
    }
    
    Collections.reverse(orderList);
    return orderList;
  }

  private static void dfs(int i, ArrayList<Integer>[] adj, boolean[] visitFlags, ArrayList<Integer> orderList) {
    visitFlags[i] = true;
    
    for (int j : adj[i]) {
      if (!visitFlags[j]) {
        dfs(j, adj, visitFlags, orderList);
      }
    }
    
    orderList.add(i);
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
      int x, y;
      x = scanner.nextInt();
      y = scanner.nextInt();
      adj[x - 1].add(y - 1);
    }
    
    ArrayList<Integer> order = toposort(adj);
    
    for (int x : order) {
      System.out.print((x + 1) + " ");
    }
    
    scanner.close();
  }
}
