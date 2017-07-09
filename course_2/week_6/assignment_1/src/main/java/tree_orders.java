import java.util.*;
import java.io.*;

public class tree_orders {
  private static final int INVALID_VALUE = -1;

  class FastScanner {
    StringTokenizer tok = new StringTokenizer("");
    BufferedReader in;

    FastScanner() {
      in = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() throws IOException {
      while (!tok.hasMoreElements())
        tok = new StringTokenizer(in.readLine());
      return tok.nextToken();
    }

    int nextInt() throws IOException {
      return Integer.parseInt(next());
    }
  }

  public class TreeOrders {
    int n;
    int[] key, left, right;

    void read() throws IOException {
      FastScanner in = new FastScanner();
      n = in.nextInt();
      key = new int[n];
      left = new int[n];
      right = new int[n];

      for (int i = 0; i < n; i++) {
        key[i] = in.nextInt();
        left[i] = in.nextInt();
        right[i] = in.nextInt();
      }
    }

    List<Integer> inOrder() {
      ArrayList<Integer> result = new ArrayList<Integer>();
      inOrderTraversal(0, result);

      return result;
    }

    private void inOrderTraversal(int i, List<Integer> result) {
      if (i == INVALID_VALUE) {
        return;
      }

      inOrderTraversal(left[i], result);
      result.add(key[i]);
      inOrderTraversal(right[i], result);
    }

    List<Integer> preOrder() {
      ArrayList<Integer> result = new ArrayList<Integer>();
      preOrderTraversal(0, result);

      return result;
    }

    private void preOrderTraversal(int i, List<Integer> result) {
      if (i == INVALID_VALUE) {
        return;
      }

      result.add(key[i]);
      preOrderTraversal(left[i], result);
      preOrderTraversal(right[i], result);
    }

    List<Integer> postOrder() {
      ArrayList<Integer> result = new ArrayList<Integer>();
      postOrderTraversal(0, result);

      return result;
    }

    private void postOrderTraversal(int i, List<Integer> result) {
      if (i == INVALID_VALUE) {
        return;
      }

      postOrderTraversal(left[i], result);
      postOrderTraversal(right[i], result);
      result.add(key[i]);
    }
  }

  static public void main(String[] args) throws IOException {
    new Thread(null, new Runnable() {
      public void run() {
        try {
          new tree_orders().run();
        } catch (IOException e) {
        }
      }
    }, "1", 1 << 26).start();
  }

  public void print(List<Integer> x) {
    for (Integer a : x) {
      System.out.print(a + " ");
    }
    System.out.println();
  }

  public void run() throws IOException {
    TreeOrders tree = new TreeOrders();
    tree.read();
    print(tree.inOrder());
    print(tree.preOrder());
    print(tree.postOrder());
  }
}
