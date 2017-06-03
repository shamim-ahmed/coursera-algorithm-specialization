import java.io.*;
import java.util.*;

public class Sorting {
  private static Random random = new Random();
  
  private static int[] partition3(int[] a, int left, int right) {
    int pivot = a[right];
    int i = left - 1;
    int j = right;
    int x = left - 1;
    int y = right;
    
    while (true) {
      do {
        i++;
      } while (a[i] < pivot);
      
      do {
        j--;
      } while (a[j] > pivot && j > left);
      
      if (i >= j) {
        break;
      }
      
      swap(a, i, j);
      
      if (a[i] == pivot) {
        x++;
        swap(a, x, i);
      }
      
      if (a[j] == pivot) {
        y--;
        swap(a, y, j);
      }
    }
    
    swap(a, i, right);
    
    int p = i - 1;
    
    for (int k = left; k <= x; k++, p--) {
      swap(a, k, p);
    }
    
    int q = i + 1;
    
    for (int k = right - 1; k >= y; k--, q++) {
      swap(a, k, q);
    } 
    
    int[] result = new int[2];
    result[0] = p;
    result[1] = q;
    
    return result;
  }
  
  private static void randomizedQuickSort(int[] a, int l, int r) {
    if (l >= r) {
      return;
    }

    int k = random.nextInt(r - l + 1) + l;
    int t = a[l];
    a[l] = a[k];
    a[k] = t;

    // use partition3
    int[] m = partition3(a, l, r);
    randomizedQuickSort(a, l, m[0]);
    randomizedQuickSort(a, m[1], r);
  }
  
  private static void swap(int[] a, int i, int j) {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }

  public static void main(String[] args) {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }
    randomizedQuickSort(a, 0, n - 1);
    for (int i = 0; i < n; i++) {
      System.out.print(a[i] + " ");
    }
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
