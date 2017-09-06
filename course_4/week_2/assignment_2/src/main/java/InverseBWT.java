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

    Map<Character, Integer> indexMap = new HashMap<>();
    indexMap.put('$', 0);
    indexMap.put('A', 1);
    indexMap.put('C', 2);
    indexMap.put('G', 3);
    indexMap.put('T', 4);

    int[] permutation = computePermutation(bwtArray, indexMap);

    char[][] matrix = new char[n][n];

    for (int i = 0; i < n; i++) {
      matrix[i][n - 2] = sortedArray[i];
    }

    for (int i = n - 3; i >= 0; i--) {
      copyAsColumn(matrix, bwtArray, i);
      char[][] temp = new char[n][];

      for (int j = 0; j < n; j++) {
        temp[permutation[j]] = matrix[j];
      }

      matrix = temp;
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

  private int[] computePermutation(char[] bwtArray, Map<Character, Integer> indexMap) {
    int[] countArray = new int[indexMap.size()];

    for (int i = 0; i < bwtArray.length; i++) {
      int k = indexMap.get(bwtArray[i]);
      countArray[k]++;
    }

    int sum = 0;
    int[] positionArray = new int[countArray.length];

    for (int i = 0; i < positionArray.length; i++) {
      positionArray[i] = sum;
      sum += countArray[i];
    }

    int[] resultArray = new int[bwtArray.length];

    for (int i = 0; i < bwtArray.length; i++) {
      int k = indexMap.get(bwtArray[i]);
      resultArray[i] = positionArray[k];
      positionArray[k]++;
    }

    return resultArray;
  }

  static public void main(String[] args) throws IOException {
    new InverseBWT().run();
  }

  public void run() throws IOException {
    FastScanner scanner = new FastScanner();
    String bwt = scanner.next();
    System.out.println(inverseBWT(bwt));
  }
}
