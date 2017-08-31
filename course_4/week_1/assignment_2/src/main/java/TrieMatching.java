import java.io.*;
import java.util.*;

class Node {
  public static final int Letters = 4;
  public static final int NA = -1;
  public int next[];

  Node() {
    next = new int[Letters];
    Arrays.fill(next, NA);
  }

  boolean isLeaf() {
    boolean result = true;

    for (int i = 0; i < next.length; i++) {
      if (next[i] != NA) {
        result = false;
        break;
      }
    }

    return result;
  }
}

public class TrieMatching implements Runnable {
  int letterToIndex(char letter) {
    switch (letter) {
      case 'A':
        return 0;
      case 'C':
        return 1;
      case 'G':
        return 2;
      case 'T':
        return 3;
      default:
        assert (false);
        return Node.NA;
    }
  }

  List<Integer> solve(String text, List<String> patterns) {
    List<Integer> resultList = new ArrayList<Integer>();
    List<Node> trie = buildTrie(patterns);

    for (int i = 0, len = text.length(); i < len; i++) {
      String str = text.substring(i, len);

      if (matchString(str, trie)) {
        resultList.add(i);
      }
    }

    return resultList;
  }

  boolean matchString(String str, List<Node> trie) {
    Node currentNode = trie.get(0);
    boolean result = false;

    for (char c : str.toCharArray()) {
      int i = letterToIndex(c);
      int k = currentNode.next[i];

      // we have a mismatch
      if (k == Node.NA) {
        break;
      }

      // we have a match
      // move to the next node
      currentNode = trie.get(k);

      // check if we have reached leaf node
      if (currentNode.isLeaf()) {
        result = true;
        break;
      }
    }

    return result;
  }

  List<Node> buildTrie(List<String> patterns) {
    List<Node> trie = new ArrayList<>();
    Node root = new Node();
    trie.add(root);

    for (String pattern : patterns) {
      Node currentNode = root;

      for (char c : pattern.toCharArray()) {
        int i = letterToIndex(c);
        int k = currentNode.next[i];

        if (k == Node.NA) {
          Node node = new Node();
          trie.add(node);
          currentNode.next[i] = trie.size() - 1;
          currentNode = node;
        } else {
          currentNode = trie.get(k);
        }
      }
    }

    return trie;
  }

  public void run() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String text = in.readLine();
      int n = Integer.parseInt(in.readLine());
      List<String> patterns = new ArrayList<String>();

      for (int i = 0; i < n; i++) {
        patterns.add(in.readLine());
      }

      List<Integer> ans = solve(text, patterns);

      for (int j = 0; j < ans.size(); j++) {
        System.out.print("" + ans.get(j));
        System.out.print(j + 1 < ans.size() ? " " : "\n");
      }
    } catch (Throwable e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    new Thread(new TrieMatching()).start();
  }
}
