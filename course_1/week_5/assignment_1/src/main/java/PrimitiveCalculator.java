import java.util.*;

public class PrimitiveCalculator {
  enum Operation {
    MULTIPLY_BY_TWO, MULTIPLY_BY_THREE, INCREMENT_BY_ONE
  }

  private static List<Integer> optimal_sequence(int n) {
    List<Integer> sequence = new ArrayList<Integer>();

    if (n == 1) {
      sequence.add(n);
      return sequence;
    }

    int[] operationCountArray = new int[n + 1];
    Operation[] operationArray = new Operation[n + 1];

    operationCountArray[1] = 0;
    operationArray[1] = Operation.INCREMENT_BY_ONE;

    for (int i = 2; i <= n; i++) {
      int val = Integer.MAX_VALUE;
      Operation op = null;

      if (i % 2 == 0) {
        int x = operationCountArray[i / 2] + 1;

        if (x < val) {
          val = x;
          op = Operation.MULTIPLY_BY_TWO;
        }
      }

      if (i % 3 == 0) {
        int y = operationCountArray[i / 3] + 1;

        if (y < val) {
          val = y;
          op = Operation.MULTIPLY_BY_THREE;
        }
      }

      int z = operationCountArray[i - 1] + 1;

      if (z < val) {
        val = z;
        op = Operation.INCREMENT_BY_ONE;
      }

      operationCountArray[i] = val;
      operationArray[i] = op;
    }

    int k = n;

    while (k >= 1) {
      sequence.add(k);

      switch (operationArray[k]) {
        case MULTIPLY_BY_TWO:
          k /= 2;
          break;
        case MULTIPLY_BY_THREE:
          k /= 3;
          break;
        case INCREMENT_BY_ONE:
          k -= 1;
          break;
        default:
          break;
      }
    }

    Collections.reverse(sequence);

    return sequence;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();

    List<Integer> sequence = optimal_sequence(n);
    System.out.println(sequence.size() - 1);

    for (Integer x : sequence) {
      System.out.print(x + " ");
    }

    scanner.close();
  }
}
