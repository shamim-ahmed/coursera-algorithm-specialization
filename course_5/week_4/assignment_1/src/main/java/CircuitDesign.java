import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

public class CircuitDesign {
  private final InputReader reader;
  private final OutputWriter writer;

  public CircuitDesign(InputReader reader, OutputWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  public static void main(String[] args) {
    InputReader reader = new InputReader(System.in);
    OutputWriter writer = new OutputWriter(System.out);
    new CircuitDesign(reader, writer).run();
    writer.writer.flush();
  }

  class Clause {
    int firstVar;
    int secondVar;
  }

  class TwoSatisfiability {
    int numVars;
    Clause[] clauses;
    List<Set<Integer>> sccList = new ArrayList<>();

    TwoSatisfiability(int n, int m) {
      numVars = n;
      clauses = new Clause[m];
      for (int i = 0; i < m; ++i) {
        clauses[i] = new Clause();
      }
    }

    void initialize() {
      @SuppressWarnings("unchecked")
      List<Integer>[] adj = (List<Integer>[]) new ArrayList[2 * numVars];
      @SuppressWarnings("unchecked")
      List<Integer>[] adjReverse = (List<Integer>[]) new ArrayList[2 * numVars];

      constructimplicationGraph(adj, adjReverse);

      List<Integer> orderList = new ArrayList<>();
      boolean[] visitedFlags = new boolean[2 * numVars];
      depthFirstSearchWithOrder(adjReverse, visitedFlags, 0, orderList);

      Collections.reverse(orderList);

      visitedFlags = new boolean[2 * numVars];

      for (int order : orderList) {
        if (!visitedFlags[order]) {
          Set<Integer> scc = new HashSet<>();
          depthFirstSearchWithStronglyConnectedComponent(adj, visitedFlags, order, scc);
          sccList.add(scc);
        }
      }
    }

    void constructimplicationGraph(List<Integer>[] adj, List<Integer>[] adjReverse) {
      for (Clause clause : clauses) {
        int x, compX, y, compY;

        if (clause.firstVar > 0) {
          x = clause.firstVar - 1;
          compX = x + numVars;
        } else {
          x = -clause.firstVar - 1 + numVars;
          compX = x - numVars;
        }

        if (clause.secondVar > 0) {
          y = clause.secondVar - 1;
          compY = y + numVars;
        } else {
          y = -clause.secondVar - 1 + numVars;
          compY = y - numVars;
        }

        adj[compX].add(y);
        adj[compY].add(x);
        adjReverse[y].add(compX);
        adjReverse[x].add(compY);
      }
    }

    void depthFirstSearchWithOrder(List<Integer>[] adjArray, boolean[] visitedFlags, int u,
        List<Integer> orderList) {
      visitedFlags[u] = true;

      for (int v : adjArray[u]) {
        if (!visitedFlags[v]) {
          depthFirstSearchWithOrder(adjArray, visitedFlags, v, orderList);
        }
      }

      orderList.add(u);
    }

    void depthFirstSearchWithStronglyConnectedComponent(List<Integer>[] adjArray,
        boolean[] visitedFlags, int u, Set<Integer> scc) {
      scc.add(u);

      for (int v : adjArray[u]) {
        if (!visitedFlags[v]) {
          visitedFlags[v] = true;
          depthFirstSearchWithStronglyConnectedComponent(adjArray, visitedFlags, v, scc);
        }
      }
    }

    boolean isSatisfiable(int[] result) {
      for (Set<Integer> scc : sccList) {
        for (int x : scc) {
          if (scc.contains(x + numVars) || scc.contains(x - numVars)) {
            return false;
          }
        }
      }

      for (int i = 0; i < result.length; i++) {
        result[i] = -1;
      }

      for (Set<Integer> scc : sccList) {
        for (int i : scc) {
          int x;
          boolean complemented;

          if (i >= numVars) {
            x = i - numVars;
            complemented = true;
          } else {
            x = i;
            complemented = false;
          }

          if (result[x] == -1) {
            if (complemented) {
              result[x] = 1;
            } else {
              result[x] = 0;
            }
          }
        }
      }

      return true;
    }
  }

  public void run() {
    int n = reader.nextInt();
    int m = reader.nextInt();

    TwoSatisfiability twoSat = new TwoSatisfiability(n, m);

    for (int i = 0; i < m; ++i) {
      twoSat.clauses[i].firstVar = reader.nextInt();
      twoSat.clauses[i].secondVar = reader.nextInt();
    }

    twoSat.initialize();
    int result[] = new int[n];

    if (twoSat.isSatisfiable(result)) {
      writer.printf("SATISFIABLE\n");

      for (int i = 1; i <= n; ++i) {
        if (result[i - 1] == 1) {
          writer.printf("%d", -i);
        } else {
          writer.printf("%d", i);
        }

        if (i < n) {
          writer.printf(" ");
        } else {
          writer.printf("\n");
        }
      }
    } else {
      writer.printf("UNSATISFIABLE\n");
    }
  }

  static class InputReader {
    public BufferedReader reader;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
      reader = new BufferedReader(new InputStreamReader(stream), 32768);
      tokenizer = null;
    }

    public String next() {
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        try {
          tokenizer = new StringTokenizer(reader.readLine());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return tokenizer.nextToken();
    }

    public int nextInt() {
      return Integer.parseInt(next());
    }

    public double nextDouble() {
      return Double.parseDouble(next());
    }

    public long nextLong() {
      return Long.parseLong(next());
    }
  }

  static class OutputWriter {
    public PrintWriter writer;

    OutputWriter(OutputStream stream) {
      writer = new PrintWriter(stream);
    }

    public void printf(String format, Object... args) {
      writer.print(String.format(Locale.ENGLISH, format, args));
    }
  }
}
