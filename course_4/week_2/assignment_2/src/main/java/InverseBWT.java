import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {
  private static final char END_MARKER = '$';

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

  String inverseBWT(String bwt) {
    final int n = bwt.length();
    char[] bwtArray = bwt.toCharArray();

    char[] sortedArray = new char[n];
    System.arraycopy(bwtArray, 0, sortedArray, 0, n);
    Arrays.sort(sortedArray);

    StringBuilder[] matrix = new StringBuilder[n];

    for (int i = 0; i < n; i++) {
      matrix[i] = new StringBuilder();
      matrix[i].append(sortedArray[i]);
    }

    for (int i = 1; i < n; i++) {
      copyAsColumn(matrix, bwtArray);
      CustomComparator comparator = new CustomComparator(i + 1);
      Arrays.sort(matrix, comparator);
    }

    String result = null;

    for (int i = 0; i < n; i++) {
      if (matrix[i].charAt(n - 1) == END_MARKER) {
        result = matrix[i].toString();
      }
    }

    return result;
  }

  private void copyAsColumn(StringBuilder[] matrix, char[] bwtArray) {
    for (int i = 0; i < bwtArray.length; i++) {
      matrix[i].insert(0, bwtArray[i]);
    }
  }

  static public void main(String[] args) throws IOException {
    new InverseBWT().run();
  }

  public void run() throws IOException {
    FastScanner scanner = new FastScanner();
    String bwt = scanner.next();
    System.out.println(inverseBWT(bwt));
  }

  private static class CustomComparator implements Comparator<StringBuilder> {
    private final int sequenceLength;

    public CustomComparator(int sequenceLength) {
      this.sequenceLength = sequenceLength;
    }

    @Override
    public int compare(StringBuilder sb1, StringBuilder sb2) {
      String str1 = sb1.substring(0, sequenceLength);
      String str2 = sb2.substring(0, sequenceLength);

      return str1.compareTo(str2);
    }
  }
}
