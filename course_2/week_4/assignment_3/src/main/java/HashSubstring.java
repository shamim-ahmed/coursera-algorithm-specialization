import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class HashSubstring {
  private static final int PRIME = 1000000007;
  private static final int MULTIPLIER = 263;

  private static FastScanner in;
  private static PrintWriter out;

  public static void main(String[] args) throws IOException {
    in = new FastScanner();
    out = new PrintWriter(new BufferedOutputStream(System.out));

    Data input = readInput();
    List<Integer> occurrenceList = getOccurrences(input);
    printOccurrences(occurrenceList);

    out.close();
  }

  private static Data readInput() throws IOException {
    String pattern = in.next();
    String text = in.next();

    return new Data(pattern, text);
  }

  private static void printOccurrences(List<Integer> occurrenceList) throws IOException {
    for (Integer idx : occurrenceList) {
      out.print(idx);
      out.print(" ");
    }
  }

  private static List<Integer> getOccurrences(Data input) {
    String pattern = input.getPattern();
    char[] patternChars = pattern.toCharArray();
    String text = input.getText();
    char[] textChars = text.toCharArray();
    int patternLength = pattern.length();
    int textLength = text.length();
    List<Integer> occurrenceList = new ArrayList<Integer>();

    long pHash = getHashValueFor(patternChars, 0, patternLength);
    long[] hashValues = computeHashes(textChars, textLength, patternLength);

    for (int i = 0; i <= textLength - patternLength; i++) {
      if (hashValues[i] != pHash) {
        continue;
      }

      if (isEqual(textChars, i, patternChars)) {
        occurrenceList.add(i);
      }
    }

    return occurrenceList;
  }

  private static long[] computeHashes(char[] textChars, int textLength, int patternLength) {
    final int n = textLength - patternLength + 1;
    long[] hashValues = new long[n];

    for (int i = 0; i < n; i++) {
      hashValues[i] = getHashValueFor(textChars, i, patternLength);
    }
    
    return hashValues;
  }

  private static long getHashValueFor(char[] charArray, int i, int patternLength) {
    long hc = 0;
    long x = 1L;

    for (int j = i; j < i + patternLength; j++) {
      hc = (hc + ((long) charArray[j]) * x) % PRIME;
      x = (x * MULTIPLIER) % PRIME;
    }

    return hc;
  }

  private static boolean isEqual(char[] textChars, int startIndex, char[] patternChars) {
    boolean result = true;

    for (int i = 0; i < patternChars.length; i++) {
      if (textChars[startIndex + i] != patternChars[i]) {
        result = false;
        break;
      }
    }

    return result;
  }

  private static class Data {
    private final String pattern;
    private final String text;

    public Data(String pattern, String text) {
      this.pattern = pattern;
      this.text = text;
    }

    public String getPattern() {
      return pattern;
    }

    public String getText() {
      return text;
    }
  }

  private static class FastScanner {
    private BufferedReader reader;
    private StringTokenizer tokenizer;

    public FastScanner() {
      reader = new BufferedReader(new InputStreamReader(System.in));
      tokenizer = null;
    }

    public String next() throws IOException {
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        tokenizer = new StringTokenizer(reader.readLine());
      }

      return tokenizer.nextToken();
    }
  }
}
