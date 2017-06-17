import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class PhoneBook {
  private enum QueryType {
    ADD, DELETE, FIND
  };

  private static final String QUERY_ADD = "add";
  private static final String QUERY_DELETE = "del";
  private static final String QUERY_FIND = "find";
  private static final String RESULT_NOT_FOUND = "not found";

  private FastScanner in = new FastScanner();
  private Map<Integer, String> contactMap = new HashMap<>();

  public static void main(String[] args) {
    new PhoneBook().processQueries();
  }

  public void processQueries() {
    int queryCount = in.nextInt();

    for (int i = 0; i < queryCount; ++i) {
      Query query = readQuery();
      processQuery(query);
    }
  }

  private void processQuery(Query query) {
    QueryType type = query.getType();
    int number = query.getNumber();
    String name = query.getName();

    if (type == QueryType.ADD) {
      contactMap.put(number, name);
    } else if (type == QueryType.DELETE) {
      contactMap.remove(number);
    } else if (type == QueryType.FIND) {
      String response = contactMap.get(number);

      if (response == null) {
        response = RESULT_NOT_FOUND;
      }

      writeResponse(response);
    }
  }

  private Query readQuery() {
    String typeStr = in.next();
    int number = in.nextInt();

    Query result = null;

    if (typeStr.equals(QUERY_ADD)) {
      String name = in.next();
      result = new Query(QueryType.ADD, number, name);
    } else if (typeStr.equals(QUERY_DELETE)) {
      result = new Query(QueryType.DELETE, number);
    } else if (typeStr.equals(QUERY_FIND)) {
      result = new Query(QueryType.FIND, number);
    }

    return result;
  }

  private void writeResponse(String response) {
    System.out.println(response);
  }

  private static class Query {
    private final QueryType type;
    private final int number;
    private final String name;

    public Query(QueryType type, int number, String name) {
      this.type = type;
      this.number = number;
      this.name = name;
    }

    public Query(QueryType type, int number) {
      this(type, number, null);
    }

    public QueryType getType() {
      return type;
    }

    public int getNumber() {
      return number;
    }

    public String getName() {
      return name;
    }
  }

  private static class FastScanner {
    BufferedReader br;
    StringTokenizer st;

    FastScanner() {
      br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
      while (st == null || !st.hasMoreTokens()) {
        try {
          st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }
  }
}
