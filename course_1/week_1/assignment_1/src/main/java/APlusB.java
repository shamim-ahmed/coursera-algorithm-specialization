import java.util.Scanner;

public class APlusB {
  public static void main(String... args) {
    Scanner scanner = new Scanner(System.in);

    int m = scanner.nextInt();
    int n = scanner.nextInt();
    int result = m + n;

    System.out.println(result);
    scanner.close();
  }
}
