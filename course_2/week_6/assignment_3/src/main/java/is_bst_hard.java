import java.util.*;
import java.io.*;

public class is_bst_hard {
  static class FastScanner {
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

  static class Node {
    private int key;
    private Node left;
    private Node right;
    private Node parent;

    public int getKey() {
      return key;
    }

    public void setKey(int key) {
      this.key = key;
    }

    public Node getLeft() {
      return left;
    }

    public void setLeft(Node left) {
      this.left = left;
    }

    public Node getRight() {
      return right;
    }

    public void setRight(Node right) {
      this.right = right;
    }

    public Node getParent() {
      return parent;
    }

    public void setParent(Node parent) {
      this.parent = parent;
    }
  }

  public static class IsBST {
    int numberOfNodes;
    Node[] tree;

    void read() throws IOException {
      FastScanner in = new FastScanner();
      numberOfNodes = in.nextInt();
      tree = new Node[numberOfNodes];

      for (int i = 0; i < numberOfNodes; i++) {
        int key = in.nextInt();
        int leftIndex = in.nextInt();
        int rightIndex = in.nextInt();

        Node currentNode = findNode(i);
        currentNode.setKey(key);

        Node leftNode = findNode(leftIndex);
        Node rightNode = findNode(rightIndex);
        currentNode.setLeft(leftNode);
        currentNode.setRight(rightNode);

        if (leftNode != null) {
          leftNode.setParent(currentNode);
        }

        if (rightNode != null) {
          rightNode.setParent(currentNode);
        }
      }
    }

    Node findNode(int index) {
      if (index < 0 || index >= numberOfNodes) {
        return null;
      }

      if (tree[index] == null) {
        tree[index] = new Node();
      }

      return tree[index];
    }

    Node findNext(Node currentNode) {
      Node result = null;
      Node w = currentNode.getRight();

      if (w != null) {
        while (w.getLeft() != null) {
          w = w.getLeft();
        }

        result = w;
      } else {
        Node q = currentNode;
        Node p = currentNode.getParent();

        while (p != null && p.getLeft() != q) {
          q = p;
          p = p.getParent();
        }

        result = p;
      }

      return result;
    }

    boolean isBinarySearchTree() {
      Node[] nodeArray = inOrder();
      boolean result = true;

      for (int i = 0; i < nodeArray.length - 1; i++) {
        if (nodeArray[i].getKey() > nodeArray[i + 1].getKey()) {
          result = false;
          break;
        }
      }

      return result;
    }

    private Node[] inOrder() {
      if (numberOfNodes == 0) {
        return new Node[0];
      }
      
      Stack<Node> stack = new Stack<>();
      Node[] resultArray = new Node[numberOfNodes];
      Node currentNode = tree[0];
      int count = 0;

      while (count < numberOfNodes) {
        while (currentNode != null) {
          stack.push(currentNode);
          currentNode = currentNode.getLeft();
        }

        currentNode = stack.pop();
        resultArray[count] = currentNode;
        count++;
        currentNode = currentNode.getRight();
      }

      return resultArray;
    }
  }

  static public void main(String[] args) throws IOException {
    new Thread(null, new Runnable() {
      public void run() {
        try {
          new is_bst_hard().run();
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
