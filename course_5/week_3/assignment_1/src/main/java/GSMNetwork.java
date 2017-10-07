import java.io.*;
import java.util.Locale;
import java.util.StringTokenizer;

public class GSMNetwork {
  private final InputReader reader;
  private final OutputWriter writer;

  public GSMNetwork(InputReader reader, OutputWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  public static void main(String[] args) {
    InputReader reader = new InputReader(System.in);
    OutputWriter writer = new OutputWriter(System.out);
    new GSMNetwork(reader, writer).run();
    writer.writer.flush();
  }

  class Edge {
    int from;
    int to;
  }

  class ConvertGSMNetworkProblemToSat {
    final int numberOfColors = 3;
    int numberOfVertices;
    Edge[] edges;

    ConvertGSMNetworkProblemToSat(int n, int m) {
      numberOfVertices = n;
      edges = new Edge[m];
      for (int i = 0; i < m; ++i) {
        edges[i] = new Edge();
      }
    }

    // Imagine a matrix of dimension numberOfColors x numberOfVertices.
    // Each variable holds one position within that matrix.
    // Now form clauses that represent a certain constraint to be satisfied.
    void printEquisatisfiableSatFormula() {
      writer.printf("%d %d\n", numberOfVertices + edges.length * numberOfColors,
          numberOfVertices * numberOfColors);

      ensureEachVertexHasOneColor();
      ensureDifferentColorForAdjacentVertices();
    }

    void ensureEachVertexHasOneColor() {
      StringBuilder resultBuilder = new StringBuilder();

      for (int i = 1; i <= numberOfVertices; i++) {
        for (int j = 0; j < numberOfColors; j++) {
          resultBuilder.append(i + j * numberOfVertices).append(" ");
        }

        resultBuilder.append("0\n");
      }

      writer.printf("%s", resultBuilder.toString());
    }

    void ensureDifferentColorForAdjacentVertices() {
      StringBuilder resultBuilder = new StringBuilder();

      for (int i = 0; i < edges.length; i++) {
        int from = edges[i].from;
        int to = edges[i].to;

        for (int j = 0; j < numberOfColors; j++) {
          resultBuilder.append(-from - j * numberOfVertices).append(" ")
              .append(-to - j * numberOfVertices).append(" 0\n");
        }
      }

      writer.printf("%s", resultBuilder.toString());
    }
  }

  public void run() {
    int n = reader.nextInt();
    int m = reader.nextInt();

    ConvertGSMNetworkProblemToSat converter = new ConvertGSMNetworkProblemToSat(n, m);
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
