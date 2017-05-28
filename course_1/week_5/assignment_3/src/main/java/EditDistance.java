import java.util.*;

public class EditDistance {
  public static int editDistance(String s, String t) {
    final int m = s.length();
    final int n = t.length();
    char[] firstStringChars = convertStringToCharArray(s);
    char[] secondStringChars = convertStringToCharArray(t);
    int[][] resultTable = new int[m + 1][n + 1];

    for (int i = 1; i <= m; i++) {
      resultTable[i][0] = i;
    }

    for (int j = 1; j <= n; j++) {
      resultTable[0][j] = j;
    }

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        int a = resultTable[i][j - 1] + 1;
        int b = resultTable[i - 1][j] + 1;

        if (firstStringChars[i] == secondStringChars[j]) {
          int c = resultTable[i - 1][j - 1];
          resultTable[i][j] = findMinimum(a, b, c);
        } else {
          int d = resultTable[i - 1][j - 1] + 1;
          resultTable[i][j] = findMinimum(a, b, d);
        }
      }
    }

    return resultTable[m][n];
  }

  private static char[] convertStringToCharArray(String s) {
    int len = s.length();
    char[] resultArray = new char[len + 1];
    System.arraycopy(s.toCharArray(), 0, resultArray, 1, len);

    return resultArray;
  }

  private static int findMinimum(int... values) {
    int result = Integer.MAX_VALUE;

    for (int v : values) {
      if (v < result) {
        result = v;
      }
    }

    return result;
  }

  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);

    String s = scan.next();
    String t = scan.next();

    System.out.println(editDistance(s, t));
    scan.close();
  }
}
