import java.util.*;
import java.io.*;

public class SuffixTree {
  private static final int INVALID_INDEX = -1;

  static class FastScanner {
    StringTokenizer tok = new StringTokenizer("");
    BufferedReader in;

    FastScanner() {
      in = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() throws IOException {
      while (!tok.hasMoreElements())
        tok = new StringTokenizer(in.readLine());
      return tok.nextToken();
    }

    int nextInt() throws IOException {
      return Integer.parseInt(next());
    }
  }

  static class SuffixTreeNode {
    SuffixTreeNode parent;
    Map<Character, SuffixTreeNode> children;
    int stringDepth;
    int edgeStart;
    int edgeEnd;

    SuffixTreeNode(SuffixTreeNode parent, int stringDepth, int edgeStart, int edgeEnd) {
      this.parent = parent;
      this.children = new LinkedHashMap<>();
      this.stringDepth = stringDepth;
      this.edgeStart = edgeStart;
      this.edgeEnd = edgeEnd;
    }
  }

  // Build a suffix tree of the string text and return a list
  // with all of the labels of its edges (the corresponding
  // substrings of the text) in any order.
  public List<String> computeSuffixTreeEdges(String text) {
    int[] suffixArray = computeSuffixArray(text);
    int[] lcpArray = computeLCPArray(text, suffixArray);

    int n = text.length();
    SuffixTreeNode root = new SuffixTreeNode(null, 0, INVALID_INDEX, INVALID_INDEX);
    SuffixTreeNode currentNode = root;
    int lcpPrev = 0;

    for (int i = 0; i < n; i++) {
      int suffix = suffixArray[i];

      // note that for root, the loop condition will always evaluate to false.
      while (currentNode.stringDepth > lcpPrev) {
        currentNode = currentNode.parent;
      }

      if (currentNode.stringDepth == lcpPrev) {
        currentNode = createNewLeaf(currentNode, text, suffix);
      } else {
        int edgeStart = suffixArray[i - 1] + currentNode.stringDepth;
        int offset = lcpPrev - currentNode.stringDepth;
        SuffixTreeNode midNode = breakEdge(currentNode, text, edgeStart, offset);
        currentNode = createNewLeaf(midNode, text, suffix);
      }

      if (i < n - 1) {
        lcpPrev = lcpArray[i];
      }
    }

    List<String> resultList = findEdgeLabels(root, text);
    return resultList;
  }

  private List<String> findEdgeLabels(SuffixTreeNode root, String text) {
    List<String> resultList = new ArrayList<>();
    Stack<SuffixTreeNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      SuffixTreeNode node = stack.pop();
      String edgeLabel = findLabel(node, text);

      if (edgeLabel != null) {
        resultList.add(edgeLabel);
      }

      List<SuffixTreeNode> childNodes = new ArrayList<>(node.children.values());

      for (int i = childNodes.size() - 1; i >= 0; i--) {
        stack.push(childNodes.get(i));
      }
    }

    return resultList;
  }

  private String findLabel(SuffixTreeNode node, String text) {
    int start = node.edgeStart;
    int end = node.edgeEnd;

    if (start == INVALID_INDEX || end == INVALID_INDEX) {
      return null;
    }

    return text.substring(start, end + 1);
  }

  private SuffixTreeNode createNewLeaf(SuffixTreeNode currentNode, String text, int suffix) {
    int n = text.length();
    int edgeStart = suffix + currentNode.stringDepth;
    SuffixTreeNode leafNode = new SuffixTreeNode(currentNode, n - suffix, edgeStart, n - 1);
    currentNode.children.put(text.charAt(edgeStart), leafNode);

    return leafNode;
  }

  private SuffixTreeNode breakEdge(SuffixTreeNode currentNode, String text, int start, int offset) {
    char startChar = text.charAt(start);
    char midChar = text.charAt(start + offset);
    SuffixTreeNode midNode = new SuffixTreeNode(currentNode, currentNode.stringDepth + offset,
        start, start + offset - 1);

    SuffixTreeNode oldChildNode = currentNode.children.get(startChar);
    midNode.children.put(midChar, oldChildNode);
    oldChildNode.parent = midNode;
    oldChildNode.edgeStart += offset;

    currentNode.children.put(startChar, midNode);

    return midNode;
  }

  private int[] computeSuffixArray(String text) {
    int n = text.length();
    Map<Character, Integer> indexMap = new HashMap<>();
    indexMap.put('$', 0);
    indexMap.put('A', 1);
    indexMap.put('C', 2);
    indexMap.put('G', 3);
    indexMap.put('T', 4);

    int[] orderArray = sortCharacters(text, indexMap);
    int[] classArray = computeClasses(text, orderArray);
    int len = 1;

    while (len < n) {
      // compute new order and class array
      int[] newOrderArray = sortDoubled(text, orderArray, classArray, len);
      int[] newClassArray = updateClasses(text, newOrderArray, classArray, len);

      // update for next iteration
      len = 2 * len;
      orderArray = newOrderArray;
      classArray = newClassArray;
    }

    return orderArray;
  }

  private int[] sortCharacters(String text, Map<Character, Integer> indexMap) {
    int n = text.length();
    int[] countArray = new int[indexMap.size()];

    for (int i = 0; i < n; i++) {
      char c = text.charAt(i);
      int k = indexMap.get(c);
      countArray[k]++;
    }

    for (int i = 1; i < countArray.length; i++) {
      countArray[i] += countArray[i - 1];
    }

    int[] orderArray = new int[n];

    for (int i = n - 1; i >= 0; i--) {
      char c = text.charAt(i);
      int k = indexMap.get(c);
      countArray[k]--;
      orderArray[countArray[k]] = i;
    }

    return orderArray;
  }

  private int[] computeClasses(String text, int[] orderArray) {
    int n = text.length();
    int[] classArray = new int[n];
    classArray[orderArray[0]] = 0;

    for (int i = 1; i < n; i++) {
      char c1 = text.charAt(orderArray[i]);
      char c2 = text.charAt(orderArray[i - 1]);

      if (c1 != c2) {
        classArray[orderArray[i]] = classArray[orderArray[i - 1]] + 1;
      } else {
        classArray[orderArray[i]] = classArray[orderArray[i - 1]];
      }
    }

    return classArray;
  }

  private int[] sortDoubled(String text, int[] orderArray, int[] classArray, int len) {
    int n = text.length();
    int[] countArray = new int[n];

    for (int i = 0; i < n; i++) {
      countArray[classArray[i]]++;
    }

    for (int i = 1; i < n; i++) {
      countArray[i] += countArray[i - 1];
    }

    int[] newOrder = new int[n];

    for (int i = n - 1; i >= 0; i--) {
      int start = (orderArray[i] - len + n) % n;
      int cl = classArray[start];
      countArray[cl]--;
      newOrder[countArray[cl]] = start;
    }

    return newOrder;
  }

  private int[] updateClasses(String text, int[] newOrderArray, int[] classArray, int len) {
    int n = text.length();
    int[] newClassArray = new int[n];
    newClassArray[newOrderArray[0]] = 0;

    for (int i = 1; i < n; i++) {
      int cur = newOrderArray[i];
      int prev = newOrderArray[i - 1];
      int mid = (cur + len) % n;
      int midPrev = (prev + len) % n;

      if (classArray[cur] != classArray[prev] || classArray[mid] != classArray[midPrev]) {
        newClassArray[cur] = newClassArray[prev] + 1;
      } else {
        newClassArray[cur] = newClassArray[prev];
      }
    }

    return newClassArray;
  }

  private int[] computeLCPArray(String text, int[] suffixArray) {
    int n = text.length();
    int[] lcpArray = new int[n - 1];
    int[] posInOrder = invertSuffixArray(suffixArray);
    int suffix = suffixArray[0];
    int lcp = 0;

    for (int i = 0; i < n; i++) {
      int orderIndex = posInOrder[suffix];

      if (orderIndex == n - 1) {
        lcp = 0;
      } else {
        int nextSuffix = suffixArray[orderIndex + 1];
        int newLcp = computeLcpOfSuffixes(text, suffix, nextSuffix, lcp - 1);
        lcpArray[orderIndex] = newLcp;
        lcp = newLcp;
      }

      suffix = (suffix + 1) % n;
    }

    return lcpArray;
  }

  private int[] invertSuffixArray(int[] suffixArray) {
    int[] invertedArray = new int[suffixArray.length];

    for (int i = 0; i < invertedArray.length; i++) {
      invertedArray[suffixArray[i]] = i;
    }

    return invertedArray;
  }

  private int computeLcpOfSuffixes(String text, int i, int j, int initialValue) {
    int lcp = Math.max(0, initialValue);
    int n = text.length();

    while (i + lcp < n && j + lcp < n) {
      char c1 = text.charAt(i + lcp);
      char c2 = text.charAt(j + lcp);

      if (c1 != c2) {
        break;
      }

      lcp++;
    }

    return lcp;
  }

  static public void main(String[] args) throws IOException {
    new SuffixTree().run();
  }

  public void print(List<String> x) {
    for (String a : x) {
      System.out.println(a);
    }
  }

  public void run() throws IOException {
    FastScanner scanner = new FastScanner();
    String text = scanner.next();
    List<String> edges = computeSuffixTreeEdges(text);
    print(edges);
  }
}
