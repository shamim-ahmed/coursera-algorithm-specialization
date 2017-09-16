import java.util.*;
import java.io.*;

public class SuffixArrayLong {
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

  // Build suffix array of the string text and
  // return an int[] result of the same length as the text
  // such that the value result[i] is the index (0-based)
  // in text where the i-th lexicographically smallest
  // suffix of text starts.
  public int[] computeSuffixArray(String text) {
    int n = text.length();
    Map<Character, Integer> indexMap = new HashMap<>();
    indexMap.put('$', 0);
    indexMap.put('A', 1);
    indexMap.put('C', 2);
    indexMap.put('G', 3);
    indexMap.put('T', 4);

    int[] orderArray = sortCharacters(text, indexMap);
    int[] classArray = computeClasses(text, orderArray);
    int len = 1;

    while (len < n) {
      // compute new order and class array
      int[] newOrderArray = sortDoubled(text, orderArray, classArray, len);
      int[] newClassArray = updateClasses(text, newOrderArray, classArray, len);
      
      // update for next iteration
      len = 2 * len;
      orderArray = newOrderArray;
      classArray = newClassArray;
    }

    return orderArray;
  }

  private int[] sortCharacters(String text, Map<Character, Integer> indexMap) {
    int n = text.length();
    int[] countArray = new int[indexMap.size()];

    for (int i = 0; i < n; i++) {
      char c = text.charAt(i);
      int k = indexMap.get(c);
      countArray[k]++;
    }

    for (int i = 1; i < countArray.length; i++) {
      countArray[i] += countArray[i - 1];
    }

    int[] orderArray = new int[n];

    for (int i = n - 1; i >= 0; i--) {
      char c = text.charAt(i);
      int k = indexMap.get(c);
      countArray[k]--;
      orderArray[countArray[k]] = i;
    }

    return orderArray;
  }

  private int[] computeClasses(String text, int[] orderArray) {
    int n = text.length();
    int[] classArray = new int[n];
    classArray[orderArray[0]] = 0;

    for (int i = 1; i < n; i++) {
      char c1 = text.charAt(orderArray[i]);
      char c2 = text.charAt(orderArray[i - 1]);

      if (c1 != c2) {
        classArray[orderArray[i]] = classArray[orderArray[i - 1]] + 1;
      } else {
        classArray[orderArray[i]] = classArray[orderArray[i - 1]];
      }
    }

    return classArray;
  }

  private int[] sortDoubled(String text, int[] orderArray, int[] classArray, int len) {
    int n = text.length();
    int[] countArray = new int[n];

    for (int i = 0; i < n; i++) {
      countArray[classArray[i]]++;
    }

    for (int i = 1; i < n; i++) {
      countArray[i] += countArray[i - 1];
    }

    int[] newOrder = new int[n];

    for (int i = n - 1; i >= 0; i--) {
      int start = (orderArray[i] - len + n) % n;
      int cl = classArray[start];
      countArray[cl]--;
      newOrder[countArray[cl]] = start;
    }

    return newOrder;
  }

  private int[] updateClasses(String text, int[] newOrderArray, int[] classArray, int len) {
    int n = text.length();
    int[] newClassArray = new int[n];
    newClassArray[newOrderArray[0]] = 0;

    for (int i = 1; i < n; i++) {
      int cur = newOrderArray[i];
      int prev = newOrderArray[i - 1];
      int mid = (cur + len) % n;
      int midPrev = (prev + len) % n;

      if (classArray[cur] != classArray[prev] || classArray[mid] != classArray[midPrev]) {
        newClassArray[cur] = newClassArray[prev] + 1;
      } else {
        newClassArray[cur] = newClassArray[prev];
      }
    }

    return newClassArray;
  }

  static public void main(String[] args) throws IOException {
    new SuffixArrayLong().run();
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
    int[] suffix_array = computeSuffixArray(text);
    print(suffix_array);
  }
}
