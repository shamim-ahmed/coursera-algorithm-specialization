import java.util.*;

public class FibonacciHuge {
  private static long getFibonacciHuge(long n, int m) {
    int periodLength = findPeriodLength(m);
    int r = (int) (n % periodLength);

    return fibonacciMod(r, m);
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
      len += 1;

      int z = (x + y) % m;
      x = y;
      y = z;

      if (x == 0 && y == 1) {
        done = true;
      }
    }

    return len;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    long n = scanner.nextLong();
    int m = scanner.nextInt();

    System.out.println(getFibonacciHuge(n, m));
    scanner.close();
  }
}

