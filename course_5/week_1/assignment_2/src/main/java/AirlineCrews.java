import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class AirlineCrews {
  private static final int INVALID_VERTEX_ID = -1;
  private static final int INVALID_EDGE_ID = -1;
  private static final int CAPACITY = 1;
  private FastScanner in;
  private PrintWriter out;

  public static void main(String[] args) throws IOException {
    new AirlineCrews().solve();
  }

  public void solve() throws IOException {
    in = new FastScanner();
    out = new PrintWriter(new BufferedOutputStream(System.out));
    boolean[][] bipartiteGraph = readData();
    int[] matching = findMatching(bipartiteGraph);
    writeResponse(matching);
    out.close();
  }

  boolean[][] readData() throws IOException {
    int numLeft = in.nextInt();
    int numRight = in.nextInt();
    boolean[][] adjMatrix = new boolean[numLeft][numRight];

    for (int i = 0; i < numLeft; ++i) {
      for (int j = 0; j < numRight; ++j) {
        adjMatrix[i][j] = (in.nextInt() == 1);
      }
    }

    return adjMatrix;
  }

  private int[] findMatching(boolean[][] bipartiteGraph) {
    // construct the flow graph from the given info on bipartite graph
    FlowGraph flowGraph = constructFlowGraph(bipartiteGraph);
    boolean done = false;

    // find the maximum flow from the source to the sink
    while (!done) {
      List<Integer> path = findMinimumPath(flowGraph, 0, flowGraph.size() - 1);

      if (path.size() == 0) {
        done = true;
      } else {
        int f = findMinFlow(path, flowGraph);

        for (int edgeId : path) {
          flowGraph.addFlow(edgeId, f);
        }
      }
    }

    // We have computed the maximum flow.
    // Now find the edges in the flow that match a vertex in the left set with a vertex in the
    // right set.
    int n = bipartiteGraph.length;
    int[] result = new int[n];
    Arrays.fill(result, INVALID_VERTEX_ID);

    for (int edgeId : flowGraph.getIds(0)) {
      Edge edge = flowGraph.getEdge(edgeId);

      if (edge.flow == 0) {
        continue;
      }

      int u = edge.to;
      int v = findMatchingVertex(u, flowGraph, n);
      result[u - 1] = v;
    }

    return result;
  }

  // For the given flow graph, find the minimum path in terms of the number of edges.
  // We use breadth first search to find the minimum path.
  private List<Integer> findMinimumPath(FlowGraph flowGraph, int fromVertex, int toVertex) {
    int[] incomingEdgeIds = new int[flowGraph.size()];
    Arrays.fill(incomingEdgeIds, INVALID_EDGE_ID);

    Queue<Integer> queue = new LinkedList<>();
    queue.offer(fromVertex);
    boolean pathFound = false;

    while (!queue.isEmpty() && !pathFound) {
      int vertexId = queue.poll();
      List<Integer> edgeIdList = flowGraph.getIds(vertexId);

      // consider the outgoing edges for the current vertex
      for (int edgeId : edgeIdList) {
        Edge edge = flowGraph.getEdge(edgeId);

        // check for loop
        if (edge.from == edge.to) {
          continue;
        }

        if (edge.flow < edge.capacity && incomingEdgeIds[edge.to] == INVALID_EDGE_ID) {
          incomingEdgeIds[edge.to] = edgeId;
          queue.offer(edge.to);

          // check if we have reached the sink
          if (edge.to == toVertex) {
            pathFound = true;
            break;
          }
        }
      }
    }

    // if a path is not found, return an empty list
    if (!pathFound) {
      return Collections.emptyList();
    }

    // find the list of edge ids for the minimum path
    List<Integer> path = new ArrayList<>();
    int prevEdgeId = incomingEdgeIds[toVertex];
    path.add(prevEdgeId);
    Edge prevEdge = flowGraph.getEdge(prevEdgeId);

    while (prevEdge.from != fromVertex) {
      prevEdgeId = incomingEdgeIds[prevEdge.from];
      path.add(prevEdgeId);
      prevEdge = flowGraph.getEdge(prevEdgeId);
    }

    Collections.reverse(path);
    return path;
  }

  // find the minimum flow value for a given path
  private int findMinFlow(List<Integer> path, FlowGraph flowGraph) {
    int minFlow = Integer.MAX_VALUE;

    for (int edgeId : path) {
      Edge edge = flowGraph.getEdge(edgeId);
      int f = edge.capacity - edge.flow;

      if (f < minFlow) {
        minFlow = f;
      }
    }

    return minFlow;
  }

  // Construct the flow graph from the information given on the bipartite graph.
  // Note that we need to add a source, a sink and the associated edges.
  private static FlowGraph constructFlowGraph(boolean[][] bipartiteGraph) {
    int n = bipartiteGraph.length;
    int m = bipartiteGraph[0].length;
    // flowGraph has two extra vertices for source and sink
    FlowGraph flowGraph = new FlowGraph(n + m + 2);

    // add edges from source to left vertex set, each with capacity 1
    for (int i = 0; i < n; i++) {
      flowGraph.addEdge(0, i + 1, CAPACITY);
    }

    // add edges between the left and right vertex set, each with capacity 1
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (bipartiteGraph[i][j]) {
          flowGraph.addEdge(i + 1, n + 1 + j, CAPACITY);
        }
      }
    }

    // add edges from the right vertex set to the sink, each with capacity 1
    for (int i = 0; i < m; i++) {
      flowGraph.addEdge(n + 1 + i, n + m + 1, CAPACITY);
    }

    return flowGraph;
  }

  // Given a vertex u in the left set, find the matching vertex in the right set
  private int findMatchingVertex(int u, FlowGraph flowGraph, int n) {
    int v = INVALID_VERTEX_ID;

    for (int edgeId : flowGraph.getIds(u)) {
      Edge edge = flowGraph.getEdge(edgeId);

      if (edge.flow == CAPACITY) {
        // we need this transformation because of the way we number the vertices
        v = edge.to - n - 1;
        break;
      }
    }

    return v;
  }

  private void writeResponse(int[] matching) {
    for (int i = 0; i < matching.length; ++i) {
      if (i > 0) {
        out.print(" ");
      }
      if (matching[i] == -1) {
        out.print("-1");
      } else {
        out.print(matching[i] + 1);
      }
    }
    out.println();
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
