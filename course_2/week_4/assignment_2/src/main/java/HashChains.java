import java.io.*;
import java.util.StringTokenizer;

public class HashChains {
  private static final String QUERY_ADD = "add";
  private static final String QUERY_DELETE = "del";
  private static final String QUERY_FIND = "find";
  private static final String QUERY_CHECK = "check";

  private static final String YES_STR = "yes";
  private static final String NO_STR = "no";

  private FastScanner in;
  private PrintWriter out;
  private SimpleHashTable hashTable;

  public static void main(String[] args) throws IOException {
    new HashChains().processQueries();
  }

  private Query readQuery() throws IOException {
    String type = in.next();
    Query query;

    if (type.equals(QUERY_CHECK)) {
      int index = in.nextInt();
      query = new Query(type, index);
    } else {
      String s = in.next();
      query = new Query(type, s);
    }

    return query;
  }

  private void processQuery(Query query) {
    String type = query.getType();

    if (type.equals(QUERY_CHECK)) {
      int index = query.getIndex();
      hashTable.check(index);
    } else {
      String key = query.getKey();

      if (type.equals(QUERY_ADD)) {
        hashTable.add(key);
      } else if (type.equals(QUERY_DELETE)) {
        hashTable.remove(key);
      } else if (type.equals(QUERY_FIND)) {
        boolean found = hashTable.find(key);
        System.out.println(found ? YES_STR : NO_STR);
      }
    }
  }

  public void processQueries() throws IOException {
    in = new FastScanner();
    out = new PrintWriter(new BufferedOutputStream(System.out));

    int bucketCount = in.nextInt();
    hashTable = new SimpleHashTable(bucketCount);
    int queryCount = in.nextInt();

    for (int i = 0; i < queryCount; ++i) {
      Query query = readQuery();
      processQuery(query);
    }

    out.close();
  }

  private static class Query {
    public static final int INVALID_INDEX = -1;

    private final String type;
    private String key;
    private int index = INVALID_INDEX;

    public Query(String type, String key) {
      this.type = type;
      this.key = key;
    }

    public Query(String type, int index) {
      this.type = type;
      this.index = index;
    }

    public String getKey() {
      return key;
    }

    public int getIndex() {
      return index;
    }

    public String getType() {
      return type;
    }
  }

  private static class ListNode {
    private final String key;
    private ListNode previous;
    private ListNode next;

    public ListNode(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }

    public ListNode getPrevious() {
      return previous;
    }

    public void setPrevious(ListNode previous) {
      this.previous = previous;
    }

    public ListNode getNext() {
      return next;
    }

    public void setNext(ListNode next) {
      this.next = next;
    }
  }

  private static class SimpleHashTable {
    private static final long PRIME = 1000000007L;
    private static final long MULTIPLIER = 263L;
    private static final String BLANK_SPACE = " ";

    private final int bucketCount;
    private final ListNode[] nodeArray;

    public SimpleHashTable(int bucketCount) {
      this.bucketCount = bucketCount;
      nodeArray = new ListNode[bucketCount];
    }

    public void add(String key) {
      int index = hashFunction(key);

      if (findNode(key, index) != null) {
        return;
      }

      ListNode head = nodeArray[index];
      ListNode newNode = new ListNode(key);
      nodeArray[index] = newNode;
      newNode.setNext(head);

      if (head != null) {
        head.setPrevious(newNode);
      }
    }

    public void remove(String key) {
      int index = hashFunction(key);
      ListNode node = findNode(key, index);

      if (node == null) {
        return;
      }

      ListNode p = node.getPrevious();
      ListNode q = node.getNext();

      if (p != null) {
        p.setNext(q);
      }

      if (q != null) {
        q.setPrevious(p);
      }

      // special check for removing the head of the linked list
      if (p == null) {
        nodeArray[index] = q;
      }
    }

    public boolean find(String key) {
      ListNode node = findNode(key);
      return node != null;
    }

    public void check(int index) {
      ListNode p = nodeArray[index];

      while (p != null) {
        System.out.print(p.getKey());

        ListNode q = p.getNext();

        if (q != null) {
          System.out.print(BLANK_SPACE);
        }

        p = q;
      }

      System.out.println();
    }

    private ListNode findNode(String key) {
      int index = hashFunction(key);
      return findNode(key, index);
    }

    private ListNode findNode(String key, int index) {
      ListNode p = nodeArray[index];

      while (p != null && !key.equals(p.getKey())) {
        p = p.getNext();
      }

      return p;
    }

    private int hashFunction(String key) {
      char[] charArray = key.toCharArray();
      long hc = 0;
      long x = 1L;

      for (int i = 0; i < charArray.length; i++) {
        hc = (hc + ((long) charArray[i]) * x) % PRIME;
        x *= MULTIPLIER;
      }

      return (int) (hc % bucketCount);
    }
  }

  private static class FastScanner {
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
