import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class MergingTables {
  private final InputReader reader;
  private final OutputWriter outWriter;
  private int maximumNumberOfRows = -1;

  public static void main(String[] args) {
    InputReader reader = new InputReader(System.in);
    OutputWriter outWriter = new OutputWriter(System.out);
    new MergingTables(reader, outWriter).run();
    outWriter.flush();
  }

  public MergingTables(InputReader reader, OutputWriter outWriter) {
    this.reader = reader;
    this.outWriter = outWriter;
  }

  public void run() {
    int numberOfTables = reader.nextInt();
    int numberOfMerges = reader.nextInt();
    Table[] tables = new Table[numberOfTables + 1];

    for (int i = 1; i <= numberOfTables; i++) {
      int numberOfRows = reader.nextInt();
      tables[i] = new Table(numberOfRows);
      maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
    }

    for (int i = 0; i < numberOfMerges; i++) {
      int destinationIndex = reader.nextInt();
      int sourceIndex = reader.nextInt();
      merge(tables[destinationIndex], tables[sourceIndex]);
      outWriter.printf("%d\n", maximumNumberOfRows);
    }
  }

  private void merge(Table destination, Table source) {
    Table realDestination = destination.findRealParent();
    Table realSource = source.findRealParent();

    if (realDestination == realSource) {
      return;
    }

    int sumOfRowNumbers = realDestination.getNumberOfRows() + realSource.getNumberOfRows();

    // merge two tables and update number of rows
    // use rank heuristic
    int realDestinationRank = realDestination.getRank();
    int realSourceRank = realSource.getRank();

    if (realDestinationRank < realSourceRank) {
      realDestination.setParent(realSource);
      realSource.setNumberOfRows(sumOfRowNumbers);
    } else {
      realSource.setParent(realDestination);
      realDestination.setNumberOfRows(sumOfRowNumbers);
    }

    if (realDestinationRank == realSourceRank) {
      realDestination.setRank(realDestinationRank + 1);
    }

    // update maximumNumberOfRows
    maximumNumberOfRows = Math.max(maximumNumberOfRows, sumOfRowNumbers);
  }

  private static class Table {
    private int numberOfRows;
    private int rank;
    private Table parent;

    public Table(int numberOfRows) {
      this.numberOfRows = numberOfRows;
      rank = 0;
      parent = this;
    }

    public int getNumberOfRows() {
      return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
      this.numberOfRows = numberOfRows;
    }

    public int getRank() {
      return rank;
    }

    public void setRank(int rank) {
      this.rank = rank;
    }

    public Table getParent() {
      return parent;
    }

    public void setParent(Table parent) {
      this.parent = parent;
    }

    public Table findRealParent() {
      Table p = parent;
      Table q = p.getParent();

      List<Table> tempList = new ArrayList<>();

      while (p != q) {
        tempList.add(p);
        p = q;
        q = p.getParent();
      }

      // path compression
      for (Table t : tempList) {
        t.setParent(p);
      }

      return p;
    }
  }

  private static class InputReader {
    private BufferedReader reader;
    private StringTokenizer tokenizer;

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
  }

  private static class OutputWriter {
    private PrintWriter writer;

    public OutputWriter(OutputStream stream) {
      writer = new PrintWriter(stream);
    }

    public void printf(String format, Object... args) {
      writer.print(String.format(Locale.ENGLISH, format, args));
    }

    public void flush() {
      writer.flush();
    }
  }
}
