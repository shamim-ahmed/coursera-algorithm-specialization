import java.util.*;

public class Knapsack {
  static int optimalWeight(int capacity, int n, int[] weightArray) {
    int[] valueArray = Arrays.copyOf(weightArray, weightArray.length);
    int[][] resultTable = new int[capacity + 1][n + 1];

    for (int w = 1; w <= capacity; w++) {
      for (int i = 1; i <= n; i++) {
        resultTable[w][i] = resultTable[w][i - 1];

        if (weightArray[i] <= w) {
          int val = resultTable[w - weightArray[i]][i - 1] + valueArray[i];

          if (val > resultTable[w][i]) {
            resultTable[w][i] = val;
          }
        }
      }
    }

    return resultTable[capacity][n];
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int capacity, n;
    capacity = scanner.nextInt();
    n = scanner.nextInt();
    int[] weightArray = new int[n + 1];
    weightArray[0] = 0;

    for (int i = 1; i <= n; i++) {
      weightArray[i] = scanner.nextInt();
    }

    System.out.println(optimalWeight(capacity, n, weightArray));
    scanner.close();
  }
}
