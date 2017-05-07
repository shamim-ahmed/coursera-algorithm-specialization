import java.util.Scanner;

public class Change {
  private static int getChange(final int m) {
    int count = 0;
    int amount = m;

    while (amount > 0) {
      if (amount >= 10) {
        amount -= 10;
      } else if (amount >= 5) {
        amount -= 5;
      } else {
        amount -= 1;
      }

      count++;
    }

    return count;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int m = scanner.nextInt();

    System.out.println(getChange(m));
    scanner.close();
  }
}
