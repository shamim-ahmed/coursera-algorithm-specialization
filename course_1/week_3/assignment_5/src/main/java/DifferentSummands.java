import java.util.*;

public class DifferentSummands {
  private static List<Integer> optimalSummands(int n) {
    LinkedList<Integer> summands = new LinkedList<Integer>();
    int currentValue = n;
    boolean done = false;

    while (!done) {
      int last;

      if (summands.size() > 0) {
        last = summands.peekLast();
      } else {
        last = 0;
      }

      if (currentValue <= last) {
        summands.pollLast();
        int v = last + currentValue;
        summands.offer(v);
        done = true;
      } else {
        int v = last + 1;
        summands.offer(v);
        currentValue -= v;
      }
    }

    return summands;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();

    List<Integer> summands = optimalSummands(n);
    System.out.println(summands.size());

    for (Integer summand : summands) {
      System.out.print(summand + " ");
    }

    scanner.close();
  }
}
