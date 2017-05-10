import java.util.*;

public class CoveringSegments {

  private static List<Integer> optimalPoints(Segment[] segments) {
    PriorityQueue<Segment> segmentQueue = new PriorityQueue<>();

    for (Segment sgmt : segments) {
      segmentQueue.offer(sgmt);
    }

    List<Integer> points = new ArrayList<>();

    while (!segmentQueue.isEmpty()) {
      Segment sgmt = segmentQueue.poll();
      int p = sgmt.getEnd();
      points.add(p);

      while (!segmentQueue.isEmpty() && segmentQueue.peek().getStart() <= p) {
        segmentQueue.poll();
      }
    }

    return points;
  }

  private static class Segment implements Comparable<Segment> {
    private final int start;
    private final int end;

    public int getStart() {
      return start;
    }

    public int getEnd() {
      return end;
    }

    public Segment(int start, int end) {
      this.start = start;
      this.end = end;
    }

    @Override
    public int compareTo(Segment otherSegment) {
      int e = otherSegment.getEnd();

      if (end < e) {
        return -1;
      }

      if (end > e) {
        return 1;
      }

      return 0;
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    Segment[] segments = new Segment[n];

    for (int i = 0; i < n; i++) {
      int start, end;
      start = scanner.nextInt();
      end = scanner.nextInt();
      segments[i] = new Segment(start, end);
    }

    List<Integer> points = optimalPoints(segments);
    System.out.println(points.size());

    for (Integer point : points) {
      System.out.print(point + " ");
    }

    scanner.close();
  }
}
