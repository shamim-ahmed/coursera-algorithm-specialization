import java.util.*;

public class FibonacciPartialSum {
  private static int getLastDigitOfFibonacciPartialSum(long m, long n) {
    if (m == 0) {
      // the first Fibonacci number is 0, so we can ignore it while calculating sum
      m = 1;
    }

    if (n <= 1L) {
      return (int) n;
    }

    int s1 = getLastDigitOfFibonacciSum(m - 1);
    int s2 = getLastDigitOfFibonacciSum(n);

    return (s2 - s1 + 10) % 10;
  }

  private static int getLastDigitOfFibonacciSum(long p) {
    int periodLength = findPeriodLength(10);
    int r = (int) ((p + 2) % periodLength);
    int s = getFibonacciMod(r, 10);

    return (s - 1 + 10) % 10;
  }

  // the value of dividend must be greater than or equal to 2
  private static int getFibonacciMod(int n, int dividend) {
    if (n <= 1) {
      return n;
    }

    int x = 0;
    int y = 1;

    for (int i = 2; i <= n; i++) {
      int z = (x + y) % dividend;
      x = y;
      y = z;
    }

    return y;
  }

  private static final int findPeriodLength(int num) {
    int len = 0;
    int x = 0;
    int y = 1;
    boolean done = false;

    while (!done) {
      int z = (x + y) % num;
      x = y;
      y = z;
      len++;

      if (x == 0 && y == 1) {
        done = true;
      }
    }

    return len;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    long m = scanner.nextLong();
    long n = scanner.nextLong();

    System.out.println(getLastDigitOfFibonacciPartialSum(m, n));
    scanner.close();
  }
}

