package edu.buet.cse.demo;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class PreOrderTraversal {
  private static final int INVALID_INDEX = -1;
  
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
    private final int id;
    private Node parent;
    private final List<Node> childNodes;
    
    public Node(int id) {
      this.id = id;
      childNodes = new ArrayList<>();
    }
    
    public int getId() {
      return id;
    }
    
    public Node getParent() {
      return parent;
    }
    
    public void setParent(Node parent) {
      this.parent = parent;
    }

    public List<Node> getChildNodes() {
      return childNodes;
    }
    
    public void addChild(Node child) {
      childNodes.add(child);
    }
  }

  static class TreeHeight {
    int n;
    Node nodeArray[];
    Node root;

    void read() throws IOException {
      FastScanner in = new FastScanner();
      n = in.nextInt();
      nodeArray = new Node[n];
      
      for (int i = 0; i < n; i++) {
        nodeArray[i] = new Node(i);
      }
      
      for (int i = 0; i < n; i++) {
        Node node = nodeArray[i];
        int parentIndex = in.nextInt();
        
        if (parentIndex == INVALID_INDEX) {
          node.setParent(null);
          root = node;
        } else {
          Node p = nodeArray[parentIndex];
          node.setParent(p);
          p.addChild(node);
        }
      }
    }

    void traversePreOrder() {
      Stack<Node> nodeStack = new Stack<>();
      nodeStack.push(root);
      
      while (!nodeStack.isEmpty()) {
        Node node = nodeStack.pop();
        System.out.printf("%3d", node.getId());
        
        List<Node> childNodes = node.getChildNodes();
        int n = childNodes.size();
        
        for (int i = n - 1; i >= 0; i--) {
          Node child = childNodes.get(i);
          nodeStack.push(child);
        }
      }
      
      System.out.println();
    }
  }

  static public void main(String[] args) throws IOException {
    new Thread(null, new Runnable() {
      public void run() {
        try {
          new PreOrderTraversal().run();
        } catch (IOException e) {
        }
      }
    }, "1", 1 << 26).start();
  }

  public void run() throws IOException {
    TreeHeight tree = new TreeHeight();
    tree.read();
    tree.traversePreOrder();
  }
}
