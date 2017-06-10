import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
  private int[] data;
  private List<Swap> swaps;

  private FastScanner in;
  private PrintWriter out;

  public static void main(String[] args) throws IOException {
    new BuildHeap().solve();
  }

  private void readData() throws IOException {
    int n = in.nextInt();
    data = new int[n];
    
    for (int i = 0; i < n; ++i) {
      data[i] = in.nextInt();
    }
  }

  private void writeResponse() {
    out.println(swaps.size());
    
    for (Swap swap : swaps) {
      out.println(swap.index1 + " " + swap.index2);
    }
  }

  private void generateSwaps() {
    swaps = new ArrayList<Swap>();
    
    for (int i = data.length / 2; i >= 0; i--) {
      siftDown(data, i);
    }
  }
  
  private int leftChild(int i) {
    return 2*i + 1;
  }
  
  private int rightChild(int i) {
    return 2*i + 2;
  }
  
  private Swap performSwap(int[] values, int i, int j) {
    int temp = values[i];
    values[i] = values[j];
    values[j] = temp;
    
    return new Swap(i, j);
  }
  
  private void siftDown(int[] values, final int i) {
    int k = i;
    
    while (k < values.length) {
      int left = leftChild(k);
      int right = rightChild(k);
      int minIndex = k;
      
      if (left < values.length && values[left] < values[minIndex]) {
        minIndex = left;
      }
      
      if (right < values.length && values[right] < values[minIndex]) {
        minIndex = right;
      }
      
      if (minIndex == k) {
        break;
      }
      
      Swap s = performSwap(values, k, minIndex);
      swaps.add(s);
      k = minIndex;
    }
  }

  public void solve() throws IOException {
    in = new FastScanner();
    out = new PrintWriter(new BufferedOutputStream(System.out));
    
    readData();
    generateSwaps();
    writeResponse();
    out.close();
  }

  static class Swap {
    int index1;
    int index2;

    public Swap(int index1, int index2) {
      this.index1 = index1;
      this.index2 = index2;
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
