import java.io.IOException;
import java.util.Scanner;

class Equation {
  Equation(double a[][], double b[]) {
    this.a = a;
    this.b = b;
  }

  double a[][];
  double b[];
}


class Position {
  Position(int row, int column) {
    this.row = row;
    this.column = column;
  }

  int row;
  int column;
}


class EnergyValues {
  static Equation ReadEquation() throws IOException {
    Scanner scanner = new Scanner(System.in);
    int size = scanner.nextInt();

    double a[][] = new double[size][size];
    double b[] = new double[size];
    for (int r = 0; r < size; ++r) {
      for (int c = 0; c < size; ++c) {
        a[r][c] = scanner.nextInt();
      }
      
      b[r] = scanner.nextInt();
    }
    
    scanner.close();
    return new Equation(a, b);
  }

  static Position SelectPivotElement(double a[][], int step) {
    int size = a.length;
    int maxRow = step;
    
    for (int i = step + 1; i < size; i++) {
      if (Math.abs(a[i][step]) > Math.abs(a[maxRow][step])) {
        maxRow = i;
      }
    }
    
    Position pivot_element = new Position(maxRow, step);
    return pivot_element;
  }

  static void SwapLines(double a[][], double b[], boolean used_raws[], Position pivot_element) {
    int size = a.length;

    for (int column = 0; column < size; ++column) {
      double tmpa = a[pivot_element.column][column];
      a[pivot_element.column][column] = a[pivot_element.row][column];
      a[pivot_element.row][column] = tmpa;
    }

    double tmpb = b[pivot_element.column];
    b[pivot_element.column] = b[pivot_element.row];
    b[pivot_element.row] = tmpb;

    boolean tmpu = used_raws[pivot_element.column];
    used_raws[pivot_element.column] = used_raws[pivot_element.row];
    used_raws[pivot_element.row] = tmpu;

    pivot_element.row = pivot_element.column;
  }

  static void ProcessPivotElement(double a[][], double b[], Position pivot_element) {
    int size = a.length;
    int pivotRow = pivot_element.row;
    int pivotColumn = pivot_element.column;
    
    for (int i = pivot_element.row + 1; i < size; i++) {
      double factor = a[i][pivotColumn] / a[pivotRow][pivotColumn];
      b[i] -= b[pivotRow] * factor;
      
      for (int j = pivotColumn; j < size; j++) {
        a[i][j] -= a[pivotRow][j] * factor;
      }
    }
  }

  static void MarkPivotElementUsed(Position pivot_element, boolean used_raws[],
      boolean used_columns[]) {
    used_raws[pivot_element.row] = true;
    used_columns[pivot_element.column] = true;
  }

  static double[] SolveEquation(Equation equation) {
    double a[][] = equation.a;
    double b[] = equation.b;
    int size = a.length;

    boolean[] used_columns = new boolean[size];
    boolean[] used_rows = new boolean[size];
    
    for (int step = 0; step < size; ++step) {
      Position pivot_element = SelectPivotElement(a, step);
      SwapLines(a, b, used_rows, pivot_element);
      ProcessPivotElement(a, b, pivot_element);
      MarkPivotElementUsed(pivot_element, used_rows, used_columns);
    }
    
    for (int i = size - 1; i >= 0; i--) {
      double cf = a[i][i];
      
      if (Double.doubleToLongBits(cf) != 0L) {
        for (int j = i + 1; j < size; j++) {
          b[i] -= b[j] * a[i][j];
        }
      }
      
      b[i] /= cf;
    }

    return b;
  }

  static void PrintColumn(double column[]) {
    int size = column.length;
    for (int raw = 0; raw < size; ++raw)
      System.out.printf("%.20f\n", column[raw]);
  }

  public static void main(String[] args) throws IOException {
    Equation equation = ReadEquation();
    double[] solution = SolveEquation(equation);
    PrintColumn(solution);
  }
}
