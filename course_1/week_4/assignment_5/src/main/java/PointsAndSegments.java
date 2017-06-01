import java.util.Arrays;
import java.util.Scanner;

public class PointsAndSegments {

  private static int[] fastCountSegments(int[] startArray, int[] endArray, int[] pointArray) {
    final int numberOfSegments = startArray.length;
    final int numberOfPoints = pointArray.length;
    Segment[] segmentArray = new Segment[numberOfSegments];
    
    for (int i = 0; i < segmentArray.length; i++) {
      segmentArray[i] = new Segment(startArray[i], endArray[i]);
    }
    
    Arrays.sort(segmentArray);
    int[] countArray = new int[numberOfPoints];
    
    for (int i = 0; i < numberOfPoints; i++) {
      int point = pointArray[i];
      
      for (int j = 0; j < numberOfSegments; j++) {
        Segment segment = segmentArray[j];
        
        if (segment.getLeftEndPoint() > point) {
          break;
        }
        
        if (segment.containsPoint(point)) {
          countArray[i]++;
        }
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

  private static class Segment implements Comparable<Segment> {
    private final int leftEndPoint;
    private final int rightEndPoint;

    public Segment(int leftEndPoint, int rightEndPoint) {
      this.leftEndPoint = leftEndPoint;
      this.rightEndPoint = rightEndPoint;
    }
    
    public int getLeftEndPoint() {
      return leftEndPoint;
    }

    public int getRightEndPoint() {
      return rightEndPoint;
    }

    public boolean containsPoint(int point) {
      return point >= leftEndPoint && point <= rightEndPoint;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Segment)) {
       return false; 
      }
      
      Segment otherSegment = (Segment) obj;
      
      return leftEndPoint == otherSegment.leftEndPoint && rightEndPoint == otherSegment.rightEndPoint;
    }
    
    @Override
    public int hashCode() {
      return 17 * leftEndPoint + rightEndPoint;
    }
    
    @Override
    public int compareTo(Segment otherSegment) {
      if (leftEndPoint < otherSegment.leftEndPoint) {
        return -1;
      }

      if (leftEndPoint > otherSegment.leftEndPoint) {
        return 1;
      }

      return 0;
    }
  }
}

