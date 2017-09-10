import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BWMatching {

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

  // Preprocess the Burrows-Wheeler Transform bwt of some text
  // and compute as a result:
  // * starts - for each character C in bwt, starts[C] is the first position
  // of this character in the sorted array of
  // all characters of the text.
  // * occ_count_before - for each character C in bwt and each position P in bwt,
  // occ_count_before[C][P] is the number of occurrences of character C in bwt
  // from position 0 to position P inclusive.
  private void PreprocessBWT(String bwt, Map<Character, Integer> starts,
      Map<Character, int[]> occ_counts_before) {
    int n = bwt.length();
    char[] symbols = {'$', 'A', 'C', 'G', 'T'};

    for (char c : symbols) {
      occ_counts_before.put(c, new int[n + 1]);
    }

    // Compute the permutation required to sort the bwt string.
    int[] permutation = computePermutation(bwt, symbols);
    char[] sortedArray = new char[n];

    for (int i = 0; i < sortedArray.length; i++) {
      sortedArray[permutation[i]] = bwt.charAt(i);
    }

    for (int i = 0; i < sortedArray.length; i++) {
      char c = sortedArray[i];

      if (starts.get(c) == null) {
        starts.put(c, i);
      }
    }

    for (int i = 0; i < n; i++) {
      char c = bwt.charAt(i);

      for (Map.Entry<Character, int[]> entry : occ_counts_before.entrySet()) {
        char key = entry.getKey();
        int[] valueArray = entry.getValue();

        if (key == c) {
          if (i == 0) {
            valueArray[i + 1] = 1;
          } else {
            valueArray[i + 1] = valueArray[i] + 1;
          }
        } else {
          if (i != 0) {
            valueArray[i + 1] = valueArray[i];
          }
        }
      }
    }
  }

  // Compute the number of occurrences of string pattern in the text
  // given only Burrows-Wheeler Transform bwt of the text and additional
  // information we get from the preprocessing stage - starts and occ_counts_before.
  int CountOccurrences(String pattern, String bwt, Map<Character, Integer> starts,
      Map<Character, int[]> occ_counts_before) {
    int top = 0;
    int bottom = bwt.length() - 1;
    int i = pattern.length() - 1;
    int result = 0;

    while (top <= bottom) {
      if (i >= 0) {
        char c = pattern.charAt(i);
        i--;

        Integer firstOccurrence = starts.get(c);
        
        if (firstOccurrence == null) {
          result = 0;
          break;
        }
        
        int[] occurrenceCounts = occ_counts_before.get(c);

        if (occurrenceCounts[bottom + 1] - occurrenceCounts[top] >= 1) {
          top = firstOccurrence + occurrenceCounts[top];
          bottom = firstOccurrence + occurrenceCounts[bottom + 1] - 1;
        } else {
          result = 0;
          break;
        }
      } else {
        result = bottom - top + 1;
        break;
      }
    }

    return result;
  }

  // Compute the permutation required to sort the given bwt string.
  // This method uses ideas from Counting sort algorithm.
  private int[] computePermutation(String bwt, char[] symbols) {
    int n = bwt.length();
    Map<Character, Integer> indexMap = new HashMap<>();

    for (int i = 0; i < symbols.length; i++) {
      indexMap.put(symbols[i], i);
    }

    int[] countArray = new int[symbols.length];

    for (int i = 0; i < n; i++) {
      char c = bwt.charAt(i);
      int k = indexMap.get(c);
      countArray[k]++;
    }

    int sum = 0;
    int[] positionArray = new int[countArray.length];

    for (int i = 0; i < positionArray.length; i++) {
      positionArray[i] = sum;
      sum += countArray[i];
    }

    int[] resultArray = new int[n];

    for (int i = 0; i < resultArray.length; i++) {
      char c = bwt.charAt(i);
      int k = indexMap.get(c);
      resultArray[i] = positionArray[k];
      positionArray[k]++;
    }

    return resultArray;
  }

  static public void main(String[] args) throws IOException {
    new BWMatching().run();
  }

  public void print(int[] x) {
    for (int a : x) {
      System.out.print(a + " ");
    }
    System.out.println();
  }

  public void run() throws IOException {
    FastScanner scanner = new FastScanner();
    String bwt = scanner.next();
    // Start of each character in the sorted list of characters of bwt,
    // see the description in the comment about function PreprocessBWT
    Map<Character, Integer> starts = new HashMap<Character, Integer>();
    // Occurrence counts for each character and each position in bwt,
    // see the description in the comment about function PreprocessBWT
    Map<Character, int[]> occ_counts_before = new HashMap<Character, int[]>();
    // Preprocess the BWT once to get starts and occ_count_before.
    // For each pattern, we will then use these precomputed values and
    // spend only O(|pattern|) to find all occurrences of the pattern
    // in the text instead of O(|pattern| + |text|).
    PreprocessBWT(bwt, starts, occ_counts_before);
    int patternCount = scanner.nextInt();
    String[] patterns = new String[patternCount];
    int[] result = new int[patternCount];
    for (int i = 0; i < patternCount; ++i) {
      patterns[i] = scanner.next();
      result[i] = CountOccurrences(patterns[i], bwt, starts, occ_counts_before);
    }
    print(result);
  }
}
