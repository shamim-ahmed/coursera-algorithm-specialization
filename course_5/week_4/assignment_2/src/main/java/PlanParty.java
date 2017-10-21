import java.io.*;
import java.util.*;

class Vertex {
  Vertex() {
    this.weight = 0;
    this.children = new ArrayList<Integer>();
  }

  int weight;
  ArrayList<Integer> children;
}


class PlanParty {
  static Vertex[] ReadTree() throws IOException {
    InputStreamReader input_stream = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input_stream);
    StreamTokenizer tokenizer = new StreamTokenizer(reader);

    tokenizer.nextToken();
    int vertices_count = (int) tokenizer.nval;

    Vertex[] tree = new Vertex[vertices_count];

    for (int i = 0; i < vertices_count; ++i) {
      tree[i] = new Vertex();
      tokenizer.nextToken();
      tree[i].weight = (int) tokenizer.nval;
    }

    for (int i = 1; i < vertices_count; ++i) {
      tokenizer.nextToken();
      int from = (int) tokenizer.nval;
      tokenizer.nextToken();
      int to = (int) tokenizer.nval;
      tree[from - 1].children.add(to - 1);
      tree[to - 1].children.add(from - 1);
    }

    return tree;
  }

  static void dfs(Vertex[] tree, int vertex, int parent) {
    for (int child : tree[vertex].children) {
      if (child != parent) {
        dfs(tree, child, vertex);
      }
    }

    int funFactorSum1 = tree[vertex].weight;
    int funFactorSum2 = 0;

    for (int child : tree[vertex].children) {
      if (child != parent) {
        funFactorSum2 += tree[child].weight;

        for (int grandChild : tree[child].children) {
          if (grandChild != child && grandChild != parent && grandChild != vertex) {
            funFactorSum1 += tree[grandChild].weight;
          }
        }
      }
    }

    tree[vertex].weight = Math.max(funFactorSum1, funFactorSum2);
  }

  static int MaxWeightIndependentTreeSubset(Vertex[] tree) {
    int size = tree.length;

    if (size == 0) {
      return 0;
    }

    dfs(tree, 0, -1);

    int maxValue = Integer.MIN_VALUE;

    for (Vertex v : tree) {
      if (v.weight > maxValue) {
        maxValue = v.weight;
      }
    }

    return maxValue;
  }

  public static void main(String[] args) throws IOException {
    // This is to avoid stack overflow issues
    new Thread(null, new Runnable() {
      public void run() {
        try {
          new PlanParty().run();
        } catch (IOException e) {
        }
      }
    }, "1", 1 << 26).start();
  }

  public void run() throws IOException {
    Vertex[] tree = ReadTree();
    int weight = MaxWeightIndependentTreeSubset(tree);
    System.out.println(weight);
  }
}
