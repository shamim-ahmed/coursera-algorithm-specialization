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

    char[][] matrix = new char[n][n];

    for (int i = 0; i < n; i++) {
      matrix[i][n - 2] = sortedArray[i];
    }

    for (int i = n - 3; i >= 0; i--) {
      copyAsColumn(matrix, bwtArray, i);
      CustomComparator comparator = new CustomComparator(i, n - i);
      Arrays.sort(matrix, comparator);
    }
    
    copyAsColumn(matrix, bwtArray, n - 1);

    StringBuilder resultBuilder = new StringBuilder();
    resultBuilder.append(String.valueOf(matrix[0], 1, n - 1)).append(END_MARKER);
    return resultBuilder.toString();
  }

  private void copyAsColumn(char[][] matrix, char[] bwtArray, int col) {    
    for (int k = 0; k < bwtArray.length; k++) {
      matrix[k][col] = bwtArray[k];
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

  private static class CustomComparator implements Comparator<char[]> {
    private final int startIndex;
    private final int count;

    public CustomComparator(int startIndex, int count) {
      this.startIndex = startIndex;
      this.count = count;
    }

    @Override
    public int compare(char[] charArray1, char[] charArray2) {
      int result = 0;
      
      for (int i = 0; i < count; i++) {
        result = charArray1[startIndex + i] - charArray2[startIndex + i];
        
        if (result != 0) {
          break;
        }
      }
      
      return result;
    }
  }
}
