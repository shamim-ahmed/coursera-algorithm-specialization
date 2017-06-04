import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

public class check_brackets {
  enum SymbolType {
    LEFT_PARENTHESIS, LEFT_CURLY_BRACE, LEFT_BRACKET
  }

  private static final String SUCCESS_STR = "Success";
  private static final int INVALID_INDEX = -1;

  public static void main(String[] args) throws IOException {
    try (InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream)) {
      String text = reader.readLine();
      Stack<Symbol> symbolStack = new Stack<>();
      boolean result = true;
      int mismatchIndex = INVALID_INDEX;

      for (int i = 0, n = text.length(); i < n; i++) {
        char c = text.charAt(i);

        if (c == '(' || c == '{' || c == '[') {
          pushSymbol(symbolStack, c, i);
        } else if (c == ')' || c == '}' || c == ']') {
          result = checkTopSymbol(symbolStack, c);

          if (!result) {
            mismatchIndex = findMismatchIndex(symbolStack, c, i);
            break;
          }
        }
      }

      if (result && !symbolStack.isEmpty()) {
        result = false;
        mismatchIndex = getIndexOfFirstSymbol(symbolStack);
      }

      if (result) {
        System.out.println(SUCCESS_STR);
      } else {
        System.out.println(mismatchIndex);
      }
    }
  }

  private static void pushSymbol(Stack<Symbol> symbolStack, char c, int i) {
    if (c == '(') {
      symbolStack.push(new Symbol(SymbolType.LEFT_PARENTHESIS, i + 1));
    } else if (c == '{') {
      symbolStack.push(new Symbol(SymbolType.LEFT_CURLY_BRACE, i + 1));
    } else if (c == '[') {
      symbolStack.push(new Symbol(SymbolType.LEFT_BRACKET, i + 1));
    }
  }

  private static boolean checkTopSymbol(Stack<Symbol> symbolStack, char c) {
    if (symbolStack.empty()) {
      return false;
    }

    Symbol topSymbol = symbolStack.peek();
    boolean result = false;

    if (c == ')' && topSymbol.getType() == SymbolType.LEFT_PARENTHESIS) {
      result = true;
      symbolStack.pop();
    } else if (c == '}' && topSymbol.getType() == SymbolType.LEFT_CURLY_BRACE) {
      result = true;
      symbolStack.pop();
    } else if (c == ']' && topSymbol.getType() == SymbolType.LEFT_BRACKET) {
      result = true;
      symbolStack.pop();
    }

    return result;
  }

  private static int findMismatchIndex(Stack<Symbol> symbolStack, char c, int i) {
    int result = INVALID_INDEX;

    if (c == ')' || c == '}' || c == ']') {
      result = i + 1;
    } else {
      // in this case, there is at least one item in stack
      result = getIndexOfFirstSymbol(symbolStack);
    }

    return result;
  }

  private static int getIndexOfFirstSymbol(Stack<Symbol> symbolStack) {
    if (symbolStack.isEmpty()) {
      return INVALID_INDEX;
    }

    Symbol symbol = null;

    while (!symbolStack.isEmpty()) {
      symbol = symbolStack.pop();
    }

    return symbol.getPosition();
  }

  private static class Symbol {
    private final SymbolType type;
    private final int position;

    public Symbol(SymbolType type, int position) {
      this.type = type;
      this.position = position;
    }

    public SymbolType getType() {
      return type;
    }

    public int getPosition() {
      return position;
    }
  }
}
