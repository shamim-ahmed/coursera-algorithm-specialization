import java.util.*;

public class LCM {
  private static long lcm(long a, long b) {
    return (a * b) / gcd(a, b);
  }

  private static long gcd(long a, long b) {
    long r = a % b;

    while (r != 0L) {
      a = b;
      b = r;
      r = a % b;
    }

    return b;
  }

  public static void main(String... args) {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(lcm(a, b));
    scanner.close();
  }
}
