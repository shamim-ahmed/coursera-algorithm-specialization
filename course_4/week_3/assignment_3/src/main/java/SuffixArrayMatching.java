import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class SuffixArrayMatching {
  private static final int INVALID_INDEX = -1;

  class fastscanner {
    StringTokenizer tok = new StringTokenizer("");
    BufferedReader in;

    fastscanner() {
      in = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() throws IOException {
      while (!tok.hasMoreElements())
        tok = new StringTokenizer(in.readLine());
      return tok.nextToken();
    }

    int nextint() throws IOException {
      return Integer.parseInt(next());
    }
  }


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

  public List<Integer> findOccurrences(String pattern, String text, int[] suffixArray) {
    List<Integer> resultList = new ArrayList<>();

    int low = 0;
    int high = suffixArray.length - 1;
    int suffixIndex = INVALID_INDEX;

    while (low <= high) {
      int mid = (low + high) / 2;
      int i = suffixArray[mid];
      int res = matches(text, i, pattern);

      if (res == 0) {
        suffixIndex = mid;
        break;
      } else {
        if (res < 0) {
          // search in the upper interval of suffix array
          low = mid + 1;
        } else {
          // search in the lower interval of suffix array
          high = mid - 1;
        }
      }
    }

    if (suffixIndex != INVALID_INDEX) {
      resultList.add(suffixArray[suffixIndex]);
      int k = suffixIndex + 1;

      while (k < suffixArray.length) {
        int res = matches(text, suffixArray[k], pattern);

        if (res != 0) {
          break;
        }

        resultList.add(suffixArray[k]);
        k++;
      }

      k = suffixIndex - 1;
      while (k >= 0) {
        int res = matches(text, suffixArray[k], pattern);

        if (res != 0) {
          break;
        }

        resultList.add(suffixArray[k]);
        k--;
      }
    }

    return resultList;
  }

  private int matches(String text, int startIndex, String pattern) {
    int textLength = text.length();
    int patternLength = pattern.length();
    int result = 0;

    for (int j = 0; j < patternLength; j++) {
      if (startIndex + j >= textLength) {
        result = -1;
        break;
      }

      char c1 = text.charAt(startIndex + j);
      char c2 = pattern.charAt(j);

      if (c1 != c2) {
        if (c1 < c2) {
          result = -1;
        } else {
          result = 1;
        }

        break;
      }
    }

    return result;
  }

  static public void main(String[] args) throws IOException {
    new SuffixArrayMatching().run();
  }

  public void print(boolean[] x) {
    for (int i = 0; i < x.length; ++i) {
      if (x[i]) {
        System.out.print(i + " ");
      }
    }
    System.out.println();
  }

  public void run() throws IOException {
    fastscanner scanner = new fastscanner();
    String text = scanner.next() + "$";
    int[] suffixArray = computeSuffixArray(text);
    int patternCount = scanner.nextint();
    boolean[] occurs = new boolean[text.length()];
    for (int patternIndex = 0; patternIndex < patternCount; ++patternIndex) {
      String pattern = scanner.next();
      List<Integer> occurrences = findOccurrences(pattern, text, suffixArray);
      for (int x : occurrences) {
        occurs[x] = true;
      }
    }
    print(occurs);
  }
}
