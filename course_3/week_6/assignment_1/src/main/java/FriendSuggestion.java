import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class FriendSuggestion {
  private static class Impl {
    // Number of nodes
    int n;
    // adj[0] and cost[0] store the initial graph, adj[1] and cost[1] store the reversed graph.
    // Each graph is stored as array of adjacency lists for each node. adj stores the edges,
    // and cost stores their costs.
    ArrayList<Integer>[][] adj;
    ArrayList<Integer>[][] cost;
    // distance[0] and distance[1] correspond to distance estimates in the forward and backward
    // searches.
    long[][] distance;
    // Two priority queues, one for forward and one for backward search.
    ArrayList<PriorityQueue<Entry>> queueList;
    // visited[v] == true iff v was visited either by forward or backward search.
    boolean[] visited;
    // List of all the nodes which were visited either by forward or backward search.
    ArrayList<Integer> workset;
    final Long INFINITY = Long.MAX_VALUE / 4;
    
    Entry[] entries;
    
    Impl(int n) {
      this.n = n;
      visited = new boolean[n];
      Arrays.fill(visited, false);
      workset = new ArrayList<Integer>();
      distance = new long[][] {new long[n], new long[n]};
      
      for (int i = 0; i < n; ++i) {
        distance[0][i] = distance[1][i] = INFINITY;
      }
      
      entries = new Entry[n];
      for (int i = 0; i < n; i++) {
        entries[i] = new Entry(INFINITY, i);
      }

      queueList = new ArrayList<PriorityQueue<Entry>>();
      queueList.add(new PriorityQueue<Entry>(n));
      queueList.add(new PriorityQueue<Entry>(n));
    }

    // Reinitialize the data structures before new query after the previous query 
    void clear() {
      for (int v : workset) {
        distance[0][v] = distance[1][v] = INFINITY;
        visited[v] = false;
      }
      
      for (int i = 0; i < n; i++) {
        entries[i].cost = INFINITY;
      }
      
      workset.clear();
      queueList.get(0).clear();
      queueList.get(1).clear();
    }

    // Returns the distance from s to t in the graph.
    Long query(int s, int t) {
      clear();
      
      // initialize for forward search
      distance[0][s] = 0L;
      entries[s].cost = 0L;
      PriorityQueue<Entry> forwardQueue = queueList.get(0);
      forwardQueue.offer(entries[s]);
      
      // initialize for backward search
      distance[1][t] = 0L;
      entries[t].cost = 0L;
      PriorityQueue<Entry> backwardQueue = queueList.get(1);
      backwardQueue.offer(entries[t]);
      
      boolean done = false;
      
      while (!done && !forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
        done = process(forwardQueue, 0);
        
        if (!done) {
          done = process(backwardQueue, 1);
        }
      }  
      
      long result = INFINITY;
      
      for (int v : workset) {
        if (result > distance[0][v] + distance[1][v]) {
          result = distance[0][v] + distance[1][v];
        }
      }

      return result == INFINITY ? -1 : result;
    }
    
    boolean process(PriorityQueue<Entry> pQueue, int side) {
      Entry entry = pQueue.poll();
      int u = entry.node;

      if (visited[u]) {
        return true;
      }
      
      visited[u] = true;
      workset.add(u);

      for (int i = 0, len = adj[side][u].size(); i < len; i++) {
        int v = adj[side][u].get(i);
        int edgeWeight = cost[side][u].get(i);

        if (distance[side][v] > distance[side][u] + edgeWeight) {
          distance[side][v] = distance[side][u] + edgeWeight;
          Entry adjEntry = entries[v];
          pQueue.remove(adjEntry);
          adjEntry.cost = distance[side][v];
          pQueue.offer(adjEntry);
        }
      }

      return false;
    }

    class Entry implements Comparable<Entry> {
      long cost;
      int node;

      public Entry(long cost, int node) {
        this.cost = cost;
        this.node = node;
      }

      public int compareTo(Entry other) {
        return cost < other.cost ? -1 : cost > other.cost ? 1 : 0;
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int m = in.nextInt();
    Impl bidij = new Impl(n);
    bidij.adj = (ArrayList<Integer>[][]) new ArrayList[2][];
    bidij.cost = (ArrayList<Integer>[][]) new ArrayList[2][];
    
    for (int side = 0; side < 2; ++side) {
      bidij.adj[side] = (ArrayList<Integer>[]) new ArrayList[n];
      bidij.cost[side] = (ArrayList<Integer>[]) new ArrayList[n];
      
      for (int i = 0; i < n; i++) {
        bidij.adj[side][i] = new ArrayList<Integer>();
        bidij.cost[side][i] = new ArrayList<Integer>();
      }
    }

    for (int i = 0; i < m; i++) {
      int x = in.nextInt();
      int y = in.nextInt();
      int c = in.nextInt();
      bidij.adj[0][x - 1].add(y - 1);
      bidij.cost[0][x - 1].add(c);
      bidij.adj[1][y - 1].add(x - 1);
      bidij.cost[1][y - 1].add(c);
    }

    int t = in.nextInt();

    for (int i = 0; i < t; i++) {
      int u, v;
      u = in.nextInt();
      v = in.nextInt();
      System.out.println(bidij.query(u - 1, v - 1));
    }
    
    in.close();
  }
}
