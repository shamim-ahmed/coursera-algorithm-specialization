import java.util.*;

public class FibonacciSumLastDigit {
  private static int getLastDigitOfFibonacciSum(long n) {
    if (n == 0L) {
      return 0;
    }

    int x = 0;
    int y = 1;
    int sum = 1;

    for (long i = 2L; i <= n; i++) {
      int z = (x + y) % 10;
      sum = (sum + z) % 10;
      x = y;
      y = z;
    }

    return sum;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    long n = scanner.nextLong();
    long s = getLastDigitOfFibonacciSum(n);

    System.out.println(s);
    scanner.close();
  }
}

