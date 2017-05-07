import java.util.*;

public class FibonacciSumLastDigit {
  private static int getLastDigitOfFibonacciSum(long n) {
    if (n <= 1L) {
      return (int) n;
    }

    int period = findPeriodLength(10);
    int r = (int) ((n + 2L) % period);
    int s = fibonacciMod(r, 10);

    return (s - 1 + 10) % 10;
  }

  private static int fibonacciMod(int n, int m) {
    if (n <= 1) {
      return n;
    }

    int x = 0;
    int y = 1;

    for (int i = 2; i <= n; i++) {
      int z = (x + y) % m;
      x = y;
      y = z;
    }

    return y;
  }

  private static int findPeriodLength(int m) {
    int len = 0;
    int x = 0;
    int y = 1;
    boolean done = false;

    while (!done) {
      int z = (x + y) % m;
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
    long n = scanner.nextLong();
    long s = getLastDigitOfFibonacciSum(n);

    System.out.println(s);
    scanner.close();
  }
}

