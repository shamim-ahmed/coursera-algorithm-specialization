import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class CleaningApartment {
  private final InputReader reader;
  private final OutputWriter writer;

  public CleaningApartment(InputReader reader, OutputWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  public static void main(String[] args) {
    InputReader reader = new InputReader(System.in);
    OutputWriter writer = new OutputWriter(System.out);
    new CleaningApartment(reader, writer).run();
    writer.writer.flush();
  }

  class Edge {
    int from;
    int to;
  }

  class ConvertHampathToSat {
    int numberOfVertices;
    Edge[] edges;

    ConvertHampathToSat(int n, int m) {
      numberOfVertices = n;
      edges = new Edge[m];
      for (int i = 0; i < m; ++i) {
        edges[i] = new Edge();
      }
    }

    void printEquisatisfiableSatFormula() {
      boolean[][] adjacencyMatrix = computeAdjacencyMatrix();
      List<List<Integer>> clauseList = new ArrayList<>();

      ensureUniqueVertexPerPosition(clauseList);
      ensureUniquePositionPerVertex(clauseList);
      addClausesForAdjacencyMatrix(adjacencyMatrix, clauseList);

      writer.printf("%d %d\n", clauseList.size(), numberOfVertices * numberOfVertices);
      
      for (List<Integer> clause : clauseList) {
        for (int val : clause) {
          writer.printf("%d ", val);
        }
        
        writer.printf("0\n");
      }
    }

    boolean[][] computeAdjacencyMatrix() {
      boolean[][] adjacencyMatrix = new boolean[numberOfVertices][numberOfVertices];

      for (Edge edge : edges) {
        adjacencyMatrix[edge.from - 1][edge.to - 1] = true;
      }

      return adjacencyMatrix;
    }

    // there will be only one vertex per position
    void ensureUniqueVertexPerPosition(List<List<Integer>> clauseList) {
      for (int k = 0; k < numberOfVertices; k++) {
        List<Integer> clause1 = new ArrayList<>();

        for (int i = 1; i <= numberOfVertices; i++) {
          clause1.add(i + k * numberOfVertices);
        }

        clauseList.add(clause1);

        for (int i = 1; i <= numberOfVertices; i++) {
          for (int j = i + 1; j <= numberOfVertices; j++) {
            List<Integer> clause2 = new ArrayList<>();
            clause2.add(-i - k * numberOfVertices);
            clause2.add(-j - k * numberOfVertices);
            clauseList.add(clause2);
          }
        }
      }
    }

    // there will be only one position for each vertex
    // that is, each vertex will appear once and only once in the path
    void ensureUniquePositionPerVertex(List<List<Integer>> clauseList) {
      for (int i = 1; i <= numberOfVertices; i++) {
        List<Integer> clause1 = new ArrayList<>();

        for (int j = 0; j < numberOfVertices; j++) {
          clause1.add(i + j * numberOfVertices);
        }

        clauseList.add(clause1);

        for (int j = 0; j < numberOfVertices - 1; j++) {
          for (int k = j + 1; k < numberOfVertices; k++) {
            List<Integer> clause2 = new ArrayList<>();
            clause2.add(-i - j * numberOfVertices);
            clause2.add(-i - k * numberOfVertices);
            clauseList.add(clause2);
          }
        }
      }
    }

    // we can generate conditions that must be satisfied depending on the absence of an edge
    // between two vertices
    void addClausesForAdjacencyMatrix(boolean[][] adjacencyMatrix, List<List<Integer>> clauseList) {
      for (int i = 1; i <= numberOfVertices; i++) {
        for (int j = 1; j <= numberOfVertices; j++) {
          if (i != j && adjacencyMatrix[i - 1][j - 1] == false) {
            for (int k = 0; k < numberOfVertices - 1; k++) {
              List<Integer> clause1 = new ArrayList<>();
              clause1.add(-i - k * numberOfVertices);
              clause1.add(-j - (k + 1) * numberOfVertices);
              clauseList.add(clause1);

              List<Integer> clause2 = new ArrayList<>();
              clause2.add(-j - k * numberOfVertices);
              clause2.add(-i - (k + 1) * numberOfVertices);
              clauseList.add(clause2);
            }
          }
        }
      }
    }

  }

  public void run() {
    int n = reader.nextInt();
    int m = reader.nextInt();

    ConvertHampathToSat converter = new ConvertHampathToSat(n, m);
    for (int i = 0; i < m; ++i) {
      converter.edges[i].from = reader.nextInt();
      converter.edges[i].to = reader.nextInt();
    }

    converter.printEquisatisfiableSatFormula();
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
