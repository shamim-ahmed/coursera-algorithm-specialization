import java.util.*;

public class LCS3 {

  private static int lcs3(int[] a, int[] b, int[] c) {
    int[][][] table = new int[a.length][b.length][c.length];

    for (int i = 1; i < a.length; i++) {
      for (int j = 1; j < b.length; j++) {
        for (int k = 1; k < c.length; k++) {
          if (a[i] == b[j] && b[j] == c[k]) {
            table[i][j][k] = table[i - 1][j - 1][k - 1] + 1;
          } else {
            int p = table[i - 1][j][k];
            int q = table[i][j - 1][k];
            int r = table[i][j][k - 1];

            table[i][j][k] = max(p, q, r);
          }
        }
      }
    }

    return table[a.length - 1][b.length - 1][c.length - 1];
  }

  private static int max(int... values) {
    int result = Integer.MIN_VALUE;

    for (int v : values) {
      if (v > result) {
        result = v;
      }
    }

    return result;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int an = scanner.nextInt();
    int[] a = new int[an + 1];

    for (int i = 1; i <= an; i++) {
      a[i] = scanner.nextInt();
    }

    int bn = scanner.nextInt();
    int[] b = new int[bn + 1];

    for (int i = 1; i <= bn; i++) {
      b[i] = scanner.nextInt();
    }

    int cn = scanner.nextInt();
    int[] c = new int[cn + 1];

    for (int i = 1; i <= cn; i++) {
      c[i] = scanner.nextInt();
    }

    System.out.println(lcs3(a, b, c));
    scanner.close();
  }
}
