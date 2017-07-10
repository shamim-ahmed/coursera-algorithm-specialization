import java.util.*;
import java.io.*;

public class is_bst {
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

  public class IsBST {
    private static final int INVALID_INDEX = -1;

    class Node {
      int key;
      int left;
      int right;

      Node(int key, int left, int right) {
        this.left = left;
        this.right = right;
        this.key = key;
      }
    }

    int nodes;
    Node[] tree;

    void read() throws IOException {
      FastScanner in = new FastScanner();
      nodes = in.nextInt();
      tree = new Node[nodes];
      for (int i = 0; i < nodes; i++) {
        tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
      }
    }

    boolean isBinarySearchTree() {
      int[] keys = inOrder();
      boolean result = true;

      for (int i = 0; i < keys.length - 1; i++) {
        // all keys are supposed to be distinct
        if (keys[i] >= keys[i + 1]) {
          result = false;
          break;
        }
      }

      return result;
    }

    int[] inOrder() {
      int[] result = new int[nodes];
      Stack<Integer> stack = new Stack<>();
      int i = 0;
      int count = 0;

      while (count < nodes) {
        while (i != INVALID_INDEX) {
          stack.push(i);
          i = tree[i].left;
        }

        i = stack.pop();
        result[count] = tree[i].key;
        count++;
        i = tree[i].right;
      }

      return result;
    }
  }

  static public void main(String[] args) throws IOException {
    new Thread(null, new Runnable() {
      public void run() {
        try {
          new is_bst().run();
        } catch (IOException e) {
        }
      }
    }, "1", 1 << 26).start();
  }

  public void run() throws IOException {
    IsBST tree = new IsBST();
    tree.read();
    if (tree.isBinarySearchTree()) {
      System.out.println("CORRECT");
    } else {
      System.out.println("INCORRECT");
    }
  }
}
