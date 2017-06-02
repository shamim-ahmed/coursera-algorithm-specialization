import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class PointsAndSegments {
  private enum Category {SEGMENT_START, POINT, SEGMENT_END};

  private static int[] fastCountSegments(int[] startArray, int[] endArray, int[] pointArray) {
    final int numberOfPoints = pointArray.length;
    int[] countArray = new int[numberOfPoints];
    Position[] positionArray = new Position[startArray.length + endArray.length + pointArray.length];
    HashMap<Position, Integer> indexMap = new HashMap<>();
    
    int k = 0;
    
    for (int val : startArray) {
      positionArray[k] = new Position(val, Category.SEGMENT_START);
      k++;
    }
    
    for (int val : endArray) {
      positionArray[k] = new Position(val, Category.SEGMENT_END);
      k++;
    }
    
    for (int i = 0; i < pointArray.length; i++) {
      positionArray[k] = new Position(pointArray[i], Category.POINT);
      indexMap.put(positionArray[k], i);
      k++;
    }
    
    Arrays.sort(positionArray);
    
    int segmentCount = 0;
    
    for (Position position : positionArray) {
      Category type = position.getType();
      
      if (type == Category.SEGMENT_START) {
        segmentCount++;
      } else if (type == Category.SEGMENT_END) {
        segmentCount--;
      } else {
        int index = indexMap.get(position);
        countArray[index] = segmentCount;
      }
    }

    return countArray;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int m = scanner.nextInt();
    int[] starts = new int[n];
    int[] ends = new int[n];
    int[] points = new int[m];

    for (int i = 0; i < n; i++) {
      starts[i] = scanner.nextInt();
      ends[i] = scanner.nextInt();
    }

    for (int i = 0; i < m; i++) {
      points[i] = scanner.nextInt();
    }

    // use fastCountSegments
    int[] cnt = fastCountSegments(starts, ends, points);

    for (int x : cnt) {
      System.out.print(x + " ");
    }

    scanner.close();
  }
  
  private static class Position implements Comparable<Position> {
    private final int value;
    private final Category type;
    
    public Position(int value, Category type) {
      this.value = value;
      this.type = type;
    }
    
    public int getValue() {
      return value;
    }

    public Category getType() {
      return type;
    }
    
    @Override
    public int compareTo(Position otherPosition) {
      if (value < otherPosition.value) {
        return -1;
      }
      
      if (value > otherPosition.value) {
        return 1;
      }
      
      int ordinal = type.ordinal();
      int otherOrdinal = otherPosition.type.ordinal();
      
      if (ordinal < otherOrdinal) {
        return -1;
      }
      
      if (ordinal > otherOrdinal) {
        return 1;
      }
      
      return 0;
    }
  }
}
