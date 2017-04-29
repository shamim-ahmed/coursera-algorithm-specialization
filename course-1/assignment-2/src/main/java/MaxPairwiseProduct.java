import java.util.*;
import java.io.*;

public class MaxPairwiseProduct {
  private static final int INVALID_VALUE = -1;

  static long getMaxPairwiseProduct(long[] numbers) {
    int n = numbers.length;
    int p = INVALID_VALUE;
    int q = INVALID_VALUE;
    long max = INVALID_VALUE;

    // identify the index of the biggest number
    for (int i = 0; i < n; ++i) {
      if (numbers[i] > max) {
        max = numbers[i];
        p = i;
      }
    }

    // identify the index of the second biggest number
    max = INVALID_VALUE;

    for (int i = 0; i < n; ++i) {
      if (numbers[i] > max && i != p) {
        max = numbers[i];
        q = i;
      }
    }

    return numbers[p] * numbers[q];
  }

  public static void main(String[] args) {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    long[] numbers = new long[n];

    for (int i = 0; i < n; i++) {
      numbers[i] = scanner.nextLong();
    }

    System.out.println(getMaxPairwiseProduct(numbers));
  }

  static class FastScanner {
    BufferedReader br;
    StringTokenizer st;

    FastScanner(InputStream stream) {
      try {
        br = new BufferedReader(new InputStreamReader(stream));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    String next() {
      while (st == null || !st.hasMoreTokens()) {
        try {
          st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }

    long nextLong() {
      return Long.parseLong(next());
    }
  }
}
