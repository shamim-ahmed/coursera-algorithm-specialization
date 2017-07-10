import java.util.*;
import java.io.*;

public class tree_orders {
  private static final int INVALID_INDEX = -1;

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
      List<Integer> result = new ArrayList<>();
      Stack<Integer> stack = new Stack<>();
      int i = 0;
      int count = 0;

      while (count < n) {
        while (i != INVALID_INDEX) {
          stack.push(i);
          i = left[i];
        }

        i = stack.pop();
        result.add(key[i]);
        count++;
        i = right[i];
      }

      return result;
    }

    List<Integer> preOrder() {
      ArrayList<Integer> result = new ArrayList<Integer>();
      Stack<Integer> stack = new Stack<>();
      stack.push(0);
      int count = 0;

      while (count < n) {
        int i = stack.pop();
        result.add(key[i]);
        count++;

        if (right[i] != INVALID_INDEX) {
          stack.push(right[i]);
        }

        if (left[i] != INVALID_INDEX) {
          stack.push(left[i]);
        }
      }

      return result;
    }

    List<Integer> postOrder() {
      ArrayList<Integer> result = new ArrayList<Integer>();
      Stack<Integer> stack = new Stack<>();
      boolean[] flags = new boolean[n];
      stack.push(0);
      int count = 0;

      while (count < n) {
        int i = stack.peek();

        if (isVisited(left[i], flags) && isVisited(right[i], flags)) {
          stack.pop();
          flags[i] = true;
          result.add(key[i]);
          count++;
        } else {
          if (right[i] != INVALID_INDEX) {
            stack.push(right[i]);
          }

          if (left[i] != INVALID_INDEX) {
            stack.push(left[i]);
          }
        }
      }

      return result;
    }
  }

  boolean isVisited(int i, boolean[] flags) {
    return i == INVALID_INDEX || flags[i];
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
