import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class InverseBWT {

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

  String inverseBWT(String bwt) {
    final int n = bwt.length();
    char[] bwtArray = bwt.toCharArray();

    Map<Character, Integer> indexMap = new HashMap<>();
    indexMap.put('$', 0);
    indexMap.put('A', 1);
    indexMap.put('C', 2);
    indexMap.put('G', 3);
    indexMap.put('T', 4);

    // Compute the permutation required to sort the bwt string.
    int[] permutation = computePermutation(bwtArray, indexMap);
    char[] sortedArray = new char[n];

    for (int i = 0; i < n; i++) {
      sortedArray[permutation[i]] = bwtArray[i];
    }

    // compute the inverse permutation that will allow us to
    // move between the bwt string and sorted string
    int[] inversePermutation = computeInversePermutation(permutation);
    StringBuilder resultBuilder = new StringBuilder();

    int p = 0;

    for (int i = 0; i < n; i++) {
      int q = inversePermutation[p];
      resultBuilder.append(sortedArray[q]);
      p = q;
    }

    return resultBuilder.toString();
  }

  // Compute the permutation required to sort the given bwt string.
  // This method uses ideas from Counting sort algorithm.
  private int[] computePermutation(char[] bwtArray, Map<Character, Integer> indexMap) {
    int[] countArray = new int[indexMap.size()];

    for (int i = 0; i < bwtArray.length; i++) {
      int k = indexMap.get(bwtArray[i]);
      countArray[k]++;
    }

    int sum = 0;
    int[] positionArray = new int[countArray.length];

    for (int i = 0; i < positionArray.length; i++) {
      positionArray[i] = sum;
      sum += countArray[i];
    }

    int[] resultArray = new int[bwtArray.length];

    for (int i = 0; i < bwtArray.length; i++) {
      int k = indexMap.get(bwtArray[i]);
      resultArray[i] = positionArray[k];
      positionArray[k]++;
    }

    return resultArray;
  }

  // invert a given permutation
  private int[] computeInversePermutation(int[] permutation) {
    int[] result = new int[permutation.length];

    for (int i = 0; i < result.length; i++) {
      result[permutation[i]] = i;
    }

    return result;
  }

  static public void main(String[] args) throws IOException {
    new InverseBWT().run();
  }

  public void run() throws IOException {
    FastScanner scanner = new FastScanner();
    String bwt = scanner.next();
    System.out.println(inverseBWT(bwt));
  }
}
