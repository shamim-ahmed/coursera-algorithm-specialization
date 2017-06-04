import java.util.*;
import java.io.*;

public class tree_height {
  private static final int INVALID_INDEX = -1;

  private static class FastScanner {
    private StringTokenizer tok = new StringTokenizer("");
    private BufferedReader in;

    public FastScanner() {
      in = new BufferedReader(new InputStreamReader(System.in));
    }

    public String next() throws IOException {
      while (!tok.hasMoreElements()) {
        tok = new StringTokenizer(in.readLine());
      }
      
      return tok.nextToken();
    }

    public int nextInt() throws IOException {
      return Integer.parseInt(next());
    }
  }

  private static class Node {
    private Node parent;
    private int level;
    private final List<Node> childNodes;

    public Node() {
      childNodes = new ArrayList<>();
    }

    public Node getParent() {
      return parent;
    }

    public void setParent(Node parent) {
      this.parent = parent;
    }
    
    public int getLevel() {
      return level;
    }

    public void setLevel(int level) {
      this.level = level;
    }

    public List<Node> getChildNodes() {
      return childNodes;
    }

    public void addChild(Node child) {
      childNodes.add(child);
    }
  }

  private static class TreeHeight {
    private Node nodeArray[];
    private Node root;

    public void read() throws IOException {
      FastScanner in = new FastScanner();
      final int n = in.nextInt();
      nodeArray = new Node[n];

      for (int i = 0; i < n; i++) {
        nodeArray[i] = new Node();
      }

      for (int i = 0; i < n; i++) {
        int parentIndex = in.nextInt();
        Node node = nodeArray[i];

        if (parentIndex == INVALID_INDEX) {
          node.setParent(null);
          node.setLevel(1);
          root = node;
        } else {
          Node p = nodeArray[parentIndex];
          node.setParent(p);
          p.addChild(node);
        }
      }
    }

    public int computeHeight() {
      int treeHeight = 0;
      Stack<Node> nodeStack = new Stack<>();
      nodeStack.push(root);

      while (!nodeStack.isEmpty()) {
        Node node = nodeStack.pop();
        int lvl = node.getLevel();
        
        if (lvl > treeHeight) {
          treeHeight = lvl;
        }

        List<Node> childNodes = node.getChildNodes();
        int n = childNodes.size();

        for (int i = n - 1; i >= 0; i--) {
          Node child = childNodes.get(i);
          child.setLevel(lvl + 1);
          nodeStack.push(child);
        }
      }

      return treeHeight;
    }
  }

  public static void main(String[] args) throws IOException {
    new Thread(null, new Runnable() {
      public void run() {
        try {
          new tree_height().run();
        } catch (IOException e) {
        }
      }
    }, "1", 1 << 26).start();
  }

  public void run() throws IOException {
    TreeHeight tree = new TreeHeight();
    tree.read();
    System.out.println(tree.computeHeight());
  }
}
