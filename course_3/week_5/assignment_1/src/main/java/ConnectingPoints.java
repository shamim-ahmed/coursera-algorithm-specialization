import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ConnectingPoints {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int[] xValues = new int[n];
    int[] yValues = new int[n];

    for (int i = 0; i < n; i++) {
      xValues[i] = scanner.nextInt();
      yValues[i] = scanner.nextInt();
    }

    System.out.printf("%.9f%n", minimumDistance(xValues, yValues));;
    scanner.close();
  }

  private static double minimumDistance(int[] xValues, int[] yValues) {
    final int n = xValues.length;
    Point[] pointArray = new Point[n];
    Map<Point, DisjointSetNode> nodeMap = new HashMap<>();

    for (int i = 0; i < n; i++) {
      pointArray[i] = new Point(xValues[i], yValues[i]);
      DisjointSetNode node = new DisjointSetNode();
      nodeMap.put(pointArray[i], node);
    }

    PriorityQueue<Edge> edgeQueue = new PriorityQueue<>();

    for (int i = 0; i < n - 1; i++) {
      for (int j = i + 1; j < n; j++) {
        Edge edge = new Edge(pointArray[i], pointArray[j]);
        edgeQueue.offer(edge);
      }
    }

    double result = 0.0;
    int edgeCount = 0;

    while (!edgeQueue.isEmpty()) {
      Edge edge = edgeQueue.poll();
      Point point1 = edge.getPoint1();
      Point point2 = edge.getPoint2();
      DisjointSetNode node1 = nodeMap.get(point1);
      DisjointSetNode node2 = nodeMap.get(point2);

      // check if the nodes belong to the same set
      DisjointSetNode root1 = node1.findRoot();
      DisjointSetNode root2 = node2.findRoot();

      if (root1 != root2) {
        result += edge.getLength();
        union(root1, root2);
        edgeCount++;

        // check if the tree is complete
        if (edgeCount == n - 1) {
          break;
        }
      }
    }

    return result;
  }

  // union by rank
  private static void union(DisjointSetNode root1, DisjointSetNode root2) {
    int rank1 = root1.getRank();
    int rank2 = root2.getRank();

    if (rank1 > rank2) {
      root2.setParent(root1);
    } else {
      root1.setParent(root2);

      if (rank1 == rank2) {
        root2.setRank(rank2 + 1);
      }
    }
  }

  static class Edge implements Comparable<Edge> {
    private final Point point1;
    private final Point point2;
    private final double length;

    public Edge(Point point1, Point point2) {
      this.point1 = point1;
      this.point2 = point2;
      length = Point.computeDistance(point1, point2);
    }

    public Point getPoint1() {
      return point1;
    }

    public Point getPoint2() {
      return point2;
    }

    public double getLength() {
      return length;
    }

    @Override
    public int compareTo(Edge otherEdge) {
      return Double.compare(length, otherEdge.length);
    }
  }

  static class DisjointSetNode {
    private DisjointSetNode parent;
    private int rank;

    public DisjointSetNode() {
      parent = this;
      rank = 0;
    }

    public DisjointSetNode getParent() {
      return parent;
    }

    public void setParent(DisjointSetNode parent) {
      this.parent = parent;
    }

    public int getRank() {
      return rank;
    }

    public void setRank(int rank) {
      this.rank = rank;
    }

    public DisjointSetNode findRoot() {
      DisjointSetNode q = this;

      List<DisjointSetNode> nodeList = new ArrayList<>();

      while (q != q.parent) {
        nodeList.add(q);
        q = q.parent;
      }

      // path compression
      for (DisjointSetNode node : nodeList) {
        node.setParent(q);
      }

      return q;
    }
  }

  static class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public static double computeDistance(Point p1, Point p2) {
      return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    @Override
    public int hashCode() {
      return 17 * x + 19 * y + 23;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Point)) {
        return false;
      }

      Point otherPoint = (Point) obj;
      return x == otherPoint.x && y == otherPoint.y;
    }
  }
}
