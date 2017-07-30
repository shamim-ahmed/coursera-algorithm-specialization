import java.util.ArrayList;
import java.util.Scanner;

public class Acyclicity {
  private enum Color {
    WHITE, GRAY, BLACK
  }

  private static int acyclic(ArrayList<Integer>[] adj, Color[] nodeColors) {
    int result = 0;

    for (int i = 0; i < adj.length; i++) {
      if (nodeColors[i] == Color.WHITE) {
        result = visit(i, adj, nodeColors);

        if (result == 1) {
          break;
        }
      }
    }

    return result;
  }

  private static int visit(int i, ArrayList<Integer>[] adj, Color[] nodeColors) {
    nodeColors[i] = Color.GRAY;
    int result = 0;

    for (int j : adj[i]) {
      result = nodeColors[j] == Color.GRAY ? 1 : visit(j, adj, nodeColors);

      if (result == 1) {
        break;
      }
    }

    nodeColors[i] = Color.BLACK;
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
      int x, y;
      x = scanner.nextInt();
      y = scanner.nextInt();
      adj[x - 1].add(y - 1);
    }

    // initialize node colors
    Color[] nodeColors = new Color[n];

    for (int i = 0; i < n; i++) {
      nodeColors[i] = Color.WHITE;
    }

    System.out.println(acyclic(adj, nodeColors));
    scanner.close();
  }
}
