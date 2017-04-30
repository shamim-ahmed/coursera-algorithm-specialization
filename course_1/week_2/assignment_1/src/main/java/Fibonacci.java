import java.util.Scanner;

public class Fibonacci {
  private static long calc_fib(int n) {
    if (n <= 1)
      return n;

    long[] values = new long[n + 1];
    values[0] = 0L;
    values[1] = 1L;
    
    for (int i = 2; i <= n; i++) {
      values[i] = values[i - 1] + values[i - 2];
    }
    
    return values[n];
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    
    System.out.println(calc_fib(n));
    in.close();
  }
}
