import java.util.*;
import java.io.*;

public class SuffixArray {
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

  public class Suffix implements Comparable<Suffix> {
    String suffix;
    int start;

    Suffix(String suffix, int start) {
      this.suffix = suffix;
      this.start = start;
    }

    @Override
    public int compareTo(Suffix other) {
      return suffix.compareTo(other.suffix);
    }
  }

  // Build suffix array of the string text and
  // return an int[] result of the same length as the text
  // such that the value result[i] is the index (0-based)
  // in text where the i-th lexicographically smallest
  // suffix of text starts.
  public int[] computeSuffixArray(String text) {
    List<Suffix> suffixList = new ArrayList<>();
    
    for (int i = 0, n = text.length(); i < n; i++) {
      Suffix suffix = new Suffix(text.substring(i), i);
      suffixList.add(suffix);
    }
    
    Collections.sort(suffixList);
    int[] resultList = new int[suffixList.size()];
    int k = 0;
    
    for (Suffix suffix : suffixList) {
      resultList[k++] = suffix.start;
    }
    
    return resultList;
  }


  static public void main(String[] args) throws IOException {
    new SuffixArray().run();
  }

  public void print(int[] x) {
    for (int a : x) {
      System.out.print(a + " ");
    }
    System.out.println();
  }

  public void run() throws IOException {
    FastScanner scanner = new FastScanner();
    String text = scanner.next();
    int[] SuffixArray = computeSuffixArray(text);
    print(SuffixArray);
  }
}
