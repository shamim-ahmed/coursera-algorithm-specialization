import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
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

  String BWT(String text) {
    StringBuilder result = new StringBuilder();
    final int n = text.length();
    StringBuilder[] matrix = new StringBuilder[n];
    matrix[0] = new StringBuilder(text);

    for (int i = 1; i < n; i++) {
      StringBuilder sb = new StringBuilder(matrix[i - 1].toString());
      char c = sb.charAt(n - 1);
      sb.deleteCharAt(n - 1);
      sb.insert(0, c);
      matrix[i] = sb;
    }

    Arrays.sort(matrix, new Comparator<StringBuilder>() {
      @Override
      public int compare(StringBuilder sb1, StringBuilder sb2) {
        return sb1.toString().compareTo(sb2.toString());
      }
    });
    
    for (int i = 0; i < n; i++) {
      char c = matrix[i].charAt(n - 1);
      result.append(c);
    }

    return result.toString();
  }

  static public void main(String[] args) throws IOException {
    new BurrowsWheelerTransform().run();
  }

  public void run() throws IOException {
    FastScanner scanner = new FastScanner();
    String text = scanner.next();
    System.out.println(BWT(text));
  }
}
