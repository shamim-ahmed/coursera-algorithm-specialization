import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FractionalKnapsack {
  private static double getOptimalValue(final int capacity, int[] values, int[] weights) {
    double result = 0;
    int availableSpace = capacity;
    final int n = values.length;
    PriorityQueue<Item> itemQueue = new PriorityQueue<>(Collections.reverseOrder());

    for (int i = 0; i < n; i++) {
      Item item = new Item(values[i], weights[i]);
      itemQueue.offer(item);
    }

    while (!itemQueue.isEmpty() && availableSpace > 0) {
      Item item = itemQueue.poll();
      int w = item.getWeight();

      if (w > availableSpace) {
        result += availableSpace * item.getValuePerWeight();
        availableSpace = 0;
      } else {
        result += w * item.getValuePerWeight();
        availableSpace -= w;
      }
    }

    return result;
  }

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int capacity = scanner.nextInt();
    int[] values = new int[n];
    int[] weights = new int[n];

    for (int i = 0; i < n; i++) {
      values[i] = scanner.nextInt();
      weights[i] = scanner.nextInt();
    }

    System.out.printf("%.4f%n", getOptimalValue(capacity, values, weights));
    scanner.close();
  }

  private static class Item implements Comparable<Item> {
    private final int value;
    private final int weight;
    private final double valuePerUnit;

    Item(int value, int weight) {
      this.value = value;
      this.weight = weight;
      this.valuePerUnit = ((double) value) / weight;
    }

    public int getValue() {
      return value;
    }

    public int getWeight() {
      return weight;
    }

    public double getValuePerWeight() {
      return valuePerUnit;
    }

    @Override
    public int compareTo(Item otherItem) {
      long x = Double.doubleToLongBits(valuePerUnit);
      long y = Double.doubleToLongBits(otherItem.valuePerUnit);

      if (x < y) {
        return -1;
      }

      if (x > y) {
        return 1;
      }

      return 0;
    }

    @Override
    public String toString() {
      return String.format("[value = %d, weight = %d, valuePerUnit = %.4f]", value, weight,
          valuePerUnit);
    }
  }
}
