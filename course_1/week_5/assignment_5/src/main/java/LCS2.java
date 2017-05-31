import java.util.*;

public class LCS2 {

  private static int lcs2(int[] a, int[] b) {
    int[][] table = new int[a.length][b.length];

    for (int i = 1; i < a.length; i++) {
      for (int j = 1; j < b.length; j++) {
        if (a[i] == b[j]) {
          table[i][j] = table[i - 1][j - 1] + 1;
        } else {
          int p = table[i - 1][j];
          int q = table[i][j - 1];

          table[i][j] = max(p, q);
        }
      }
    }
    
    return table[a.length - 1][b.length - 1];
  }
  
  /*private static void printTable(int[][] table) {
    for (int i = 0; i < table.length; i++) {
      for (int j = 0; j < table[i].length; j++) {
        System.out.printf("%4d ", table[i][j]);
      }

      System.out.println();
    }

    System.out.println();
  }*/

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

    System.out.println(lcs2(a, b));
    scanner.close();
  }
}
