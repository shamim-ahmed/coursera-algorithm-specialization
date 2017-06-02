import java.util.*;

public class Inversions {
  private static final int[] EMPTY_ARRAY = new int[0];
  private static long inversionCount;

  private static int[] mergeSort(int[] a, int left, int right) {
    if (left > right) {
      return EMPTY_ARRAY;
    }

    if (left == right) {
      return new int[] {a[left]};
    }

    int mid = (left + right) / 2;
    int[] p = mergeSort(a, left, mid);
    int[] q = mergeSort(a, mid + 1, right);
    int[] result = merge(p, q);

    return result;
  }

  private static int[] merge(int[] a, int[] b) {
    int[] result = new int[a.length + b.length];
    int i = 0, j = 0, k = 0;

    while (i < a.length && j < b.length) {
      if (a[i] <= b[j]) {
        result[k] = a[i];
        i++;
      } else {
        result[k] = b[j];
        inversionCount += (a.length- i);
        j++;
      }

      k++;
    }

    while (i < a.length) {
      result[k] = a[i];
      i++;
      k++;
    }

    while (j < b.length) {
      result[k] = b[j];
      j++;
      k++;
    }

    return result;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];

    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }

    inversionCount = 0L;
    mergeSort(a, 0, a.length - 1);
    System.out.println(inversionCount);
    scanner.close();
  }
}
