import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Evacuation {
  private static final int INVALID_EDGE_ID = -1;
  private static FastScanner in;

  public static void main(String[] args) throws IOException {
    in = new FastScanner();

    FlowGraph graph = readGraph();
    System.out.println(maxFlow(graph, 0, graph.size() - 1));
  }

  private static int maxFlow(FlowGraph flowGraph, int fromVertex, int toVertex) {
    boolean done = false;

    while (!done) {
      List<Integer> path = findMinPath(flowGraph, fromVertex, toVertex);

      // if no path can be found, then we are done
      if (path.size() == 0) {
        done = true;
      } else {
        // find the minimum flow along the path found
        // and add it to every edge on the path
        int f = findMinFlow(flowGraph, path);

        for (Integer edgeId : path) {
          flowGraph.addFlow(edgeId, f);
        }
      }
    }

    // find the value of maximum flow from the output flow of the source
    int result = 0;

    for (int edgeId : flowGraph.getIds(fromVertex)) {
      Edge edge = flowGraph.getEdge(edgeId);
      result += edge.flow;
    }

    return result;
  }

  // Find a minimum path (in terms of number of edges) from the source to the sink.
  // We are using breadth-first search for this purpose.
  private static List<Integer> findMinPath(FlowGraph flowGraph, int fromVertex, int toVertex) {
    // an array to store the id of incoming edge for each vertex
    int[] incomingEdgeIds = new int[flowGraph.size()];

    for (int i = 0; i < incomingEdgeIds.length; i++) {
      incomingEdgeIds[i] = INVALID_EDGE_ID;
    }

    Queue<Integer> queue = new LinkedList<>();
    queue.offer(fromVertex);
    boolean pathFound = false;

    while (!queue.isEmpty() && !pathFound) {
      int vertexId = queue.poll();
      List<Integer> edgeIdList = flowGraph.getIds(vertexId);

      // check each edge going out of the current vertex
      for (int edgeId : edgeIdList) {
        Edge edge = flowGraph.getEdge(edgeId);

        // check for loops
        if (edge.from == edge.to) {
          continue;
        }

        if (edge.flow < edge.capacity && incomingEdgeIds[edge.to] == INVALID_EDGE_ID) {
          incomingEdgeIds[edge.to] = edgeId;
          queue.offer(edge.to);

          // check if we have reached the destination
          if (edge.to == toVertex) {
            pathFound = true;
            break;
          }
        }
      }
    }

    // if no path is found, then return an empty list
    if (!pathFound) {
      return Collections.emptyList();
    }

    // trace the path from sink to source, and save the edge ids
    List<Integer> resultList = new ArrayList<>();
    int prevEdgeId = incomingEdgeIds[toVertex];
    resultList.add(prevEdgeId);
    Edge prevEdge = flowGraph.getEdge(prevEdgeId);

    while (prevEdge.from != fromVertex) {
      prevEdgeId = incomingEdgeIds[prevEdge.from];
      resultList.add(prevEdgeId);
      prevEdge = flowGraph.getEdge(prevEdgeId);
    }

    Collections.reverse(resultList);
    return resultList;
  }

  // find the value of the minimum flow along the given path
  private static int findMinFlow(FlowGraph flowGraph, List<Integer> path) {
    int minFlow = Integer.MAX_VALUE;

    for (Integer edgeId : path) {
      Edge edge = flowGraph.getEdge(edgeId);
      int f = edge.capacity - edge.flow;

      if (f < minFlow) {
        minFlow = f;
      }
    }

    return minFlow;
  }

  static FlowGraph readGraph() throws IOException {
    int vertex_count = in.nextInt();
    int edge_count = in.nextInt();
    FlowGraph graph = new FlowGraph(vertex_count);

    for (int i = 0; i < edge_count; ++i) {
      int from = in.nextInt() - 1, to = in.nextInt() - 1, capacity = in.nextInt();
      graph.addEdge(from, to, capacity);
    }
    return graph;
  }

  static class Edge {
    int from, to, capacity, flow;

    public Edge(int from, int to, int capacity) {
      this.from = from;
      this.to = to;
      this.capacity = capacity;
      this.flow = 0;
    }
  }

  /*
   * This class implements a bit unusual scheme to store the graph edges, in order to retrieve the
   * backward edge for a given edge quickly.
   */
  static class FlowGraph {
    /* List of all - forward and backward - edges */
    private List<Edge> edges;

    /* These adjacency lists store only indices of edges from the edges list */
    private List<Integer>[] graph;

    @SuppressWarnings("unchecked")
    public FlowGraph(int n) {
      this.graph = (ArrayList<Integer>[]) new ArrayList[n];

      for (int i = 0; i < n; ++i) {
        this.graph[i] = new ArrayList<>();
      }

      this.edges = new ArrayList<>();
    }

    public void addEdge(int from, int to, int capacity) {
      /*
       * Note that we first append a forward edge and then a backward edge, so all forward edges are
       * stored at even indices (starting from 0), whereas backward edges are stored at odd indices.
       */
      Edge forwardEdge = new Edge(from, to, capacity);
      Edge backwardEdge = new Edge(to, from, 0);
      graph[from].add(edges.size());
      edges.add(forwardEdge);
      graph[to].add(edges.size());
      edges.add(backwardEdge);
    }

    public int size() {
      return graph.length;
    }

    public List<Integer> getIds(int from) {
      return graph[from];
    }

    public Edge getEdge(int id) {
      return edges.get(id);
    }

    public void addFlow(int id, int flow) {
      /*
       * To get a backward edge for a true forward edge (i.e id is even), we should get id + 1 due
       * to the described above scheme. On the other hand, when we have to get a "backward" edge for
       * a backward edge (i.e. get a forward edge for backward - id is odd), id - 1 should be taken.
       *
       * It turns out that id ^ 1 works for both cases. Think this through!
       */
      edges.get(id).flow += flow;
      edges.get(id ^ 1).flow -= flow;
    }
  }

  static class FastScanner {
    private BufferedReader reader;
    private StringTokenizer tokenizer;

    public FastScanner() {
      reader = new BufferedReader(new InputStreamReader(System.in));
      tokenizer = null;
    }

    public String next() throws IOException {
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        tokenizer = new StringTokenizer(reader.readLine());
      }
      return tokenizer.nextToken();
    }

    public int nextInt() throws IOException {
      return Integer.parseInt(next());
    }
  }
}
