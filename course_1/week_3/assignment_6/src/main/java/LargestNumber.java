import java.util.*;

public class LargestNumber {
  private static String largestNumber(String[] strValues) {
    Arrays.sort(strValues, new CustomComparator());
    StringBuilder resultBuilder = new StringBuilder();

    for (int i = strValues.length - 1; i >= 0; i--) {
      resultBuilder.append(strValues[i]);
    }

    return resultBuilder.toString();
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    String[] a = new String[n];

    for (int i = 0; i < n; i++) {
      a[i] = scanner.next();
    }

    System.out.println(largestNumber(a));
    scanner.close();
  }

  private static class CustomComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
      int result = str1.compareTo(str2);

      if (result == 0) {
        return result;
      }

      String concat12 = str1.concat(str2);
      String concat21 = str2.concat(str1);
      result = concat12.compareTo(concat21);

      return result;
    }
  }
}
