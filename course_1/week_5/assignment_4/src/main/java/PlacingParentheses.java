import java.util.Scanner;

public class PlacingParentheses {
  private static long getMaximValue(String expr) {
    final int n = expr.length() / 2;
    int[] digitArray = new int[n + 2];
    char[] operatorArray = new char[n + 1];

    char[] exprChars = expr.toCharArray();

    int p = 1;
    int q = 1;

    for (int k = 0; k < exprChars.length; k++) {
      if (k % 2 == 0) {
        digitArray[p] = exprChars[k] - '0';
        p++;
      } else {
        operatorArray[q] = exprChars[k];
        q++;
      }
    }

    long[][] minTable = new long[n + 2][n + 2];
    long[][] maxTable = new long[n + 2][n + 2];

    for (int i = 1; i <= n + 1; i++) {
      minTable[i][i] = digitArray[i];
      maxTable[i][i] = digitArray[i];
    }

    for (int s = 1; s <= n; s++) {
      for (int i = 1; i <= n - s + 1; i++) {
        int j = s + i;
        computeMinAndMax(i, j, operatorArray, minTable, maxTable);
      }
    }

    return maxTable[1][n + 1];
  }

  /*private static void printTable(long[][] table, int n) {
    for (int i = 0; i <= n + 1; i++) {
      for (int j = 0; j <= n + 1; j++) {
        System.out.printf("%4d ", table[i][j]);
      }

      System.out.println();
    }

    System.out.println();
  }*/

  private static void computeMinAndMax(int i, int j, char[] operatorArray, long[][] minTable,
      long[][] maxTable) {
    long minValue = Long.MAX_VALUE;
    long maxValue = Long.MIN_VALUE;

    for (int k = i; k < j; k++) {
      long a = eval(maxTable[i][k], maxTable[k + 1][j], operatorArray[k]);
      long b = eval(maxTable[i][k], minTable[k + 1][j], operatorArray[k]);
      long c = eval(minTable[i][k], maxTable[k + 1][j], operatorArray[k]);
      long d = eval(minTable[i][k], minTable[k + 1][j], operatorArray[k]);

      long x = findMaximum(a, b, c, d);
      long y = findMinimum(a, b, c, d);

      if (x > maxValue) {
        maxValue = x;
      }

      if (y < minValue) {
        minValue = y;
      }
    }

    maxTable[i][j] = maxValue;
    minTable[i][j] = minValue;
  }

  private static long eval(long a, long b, char op) {
    if (op == '+') {
      return a + b;
    } else if (op == '-') {
      return a - b;
    } else if (op == '*') {
      return a * b;
    } else {
      assert false;
      return 0;
    }
  }

  private static final long findMinimum(long... values) {
    long result = Long.MAX_VALUE;

    for (long v : values) {
      if (v < result) {
        result = v;
      }
    }

    return result;
  }

  private static final long findMaximum(long... values) {
    long result = Long.MIN_VALUE;

    for (long v : values) {
      if (v > result) {
        result = v;
      }
    }

    return result;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String exp = scanner.next();
    System.out.println(getMaximValue(exp));
    scanner.close();
  }
}
