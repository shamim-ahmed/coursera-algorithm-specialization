import java.util.*;

class EditDistance {
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
        int val = Integer.MAX_VALUE;

        // case 1
        int a = resultTable[i][j - 1] + 1;

        if (a < val) {
          val = a;
        }

        // case 2
        int b = resultTable[i - 1][j] + 1;

        if (b < val) {
          val = b;
        }

        if (firstStringChars[i] == secondStringChars[j]) {
          // case 3
          int c = resultTable[i - 1][j - 1];

          if (c < val) {
            val = c;
          }
        } else {
          // case 4
          int d = resultTable[i - 1][j - 1] + 1;

          if (d < val) {
            val = d;
          }
        }

        resultTable[i][j] = val;
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

  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);

    String s = scan.next();
    String t = scan.next();

    System.out.println(editDistance(s, t));
    scan.close();
  }
}
