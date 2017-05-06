import java.util.*;

public class FibonacciPartialSum {
  private static int getLastDigitOfFibonacciPartialSum(long m, long n) {
    if (n <= 1L) {
      return (int) n;
    }

    int x = 0;
    int y = 1;

    for (long i = 2L; i <= m; i++) {
      int z = (x + y) % 10;
      x = y;
      y = z;
    }

    int sum = y;

    for (long i = m + 1; i <= n; i++) {
      int z = (x + y) % 10;
      sum = (sum + z) % 10;
      x = y;
      y = z;
    }

    return sum;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    long m = scanner.nextLong();
    long n = scanner.nextLong();

    System.out.println(getLastDigitOfFibonacciPartialSum(m, n));
    scanner.close();
  }
}

