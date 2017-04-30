import java.util.*;

public class GCD {
  private static int gcd(int a, int b) {
    int r = a % b;

    while (r != 0) {
      a = b;
      b = r;
      r = a % b;
    }

    return b;
  }

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(gcd(a, b));
    scanner.close();
  }
}
