import java.util.*;
import java.io.*;

public class MajorityElement {
  private static int getMajorityElement(int[] a, int left, int right) {
    Map<Integer, Integer> valueMap = new HashMap<>();

    for (int i = 0; i < a.length; i++) {
      Integer val = a[i];
      Integer count = valueMap.get(val);

      if (count == null) {
        count = 1;
      } else {
        count += 1;
      }

      valueMap.put(val, count);
    }

    int result = 0;
    int threshold = a.length / 2;

    for (Integer count : valueMap.values()) {
      if (count > threshold) {
        result = 1;
        break;
      }
    }

    return result;
  }

  public static void main(String[] args) {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];

    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }

    System.out.println(getMajorityElement(a, 0, a.length));
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
  }
}

