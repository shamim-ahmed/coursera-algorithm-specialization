import java.io.IOException;
import java.util.Scanner;

public class EnergyValues {
  private static final double ZERO_CHECK_LIMIT = 0.000001;

  public static void main(String[] args) throws IOException {
    Equation equation = readEquation();
    double[] solution = solveEquation(equation);
    printColumn(solution);
  }

  static Equation readEquation() throws IOException {
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

  private static void solveUsingGuassianElimination(double[][] a, double[] b) {
    int size = a.length;

    for (int step = 0; step < size; step++) {
      // find the row containing pivot element
      int k = findMaxRow(a, step);

      if (k != step) {
        swapRows(a, b, step, k);
      }

      // check if pivot element is too small
      if (Math.abs(a[step][step]) < ZERO_CHECK_LIMIT) {
        throw new RuntimeException("Leading coefficient is too small");
      }

      // normalize the row containing pivot element
      double coeff = a[step][step];

      for (int j = step; j < size; j++) {
        a[step][j] /= coeff;
      }

      b[step] /= coeff;

      // subtract multiples of pivot row from other rows
      // so that the pivot element is the only non-zero element in its column
      for (int i = 0; i < size; i++) {
        if (i == step) {
          continue;
        }

        double mf = a[i][step];

        for (int j = step; j < size; j++) {
          a[i][j] -= a[step][j] * mf;
        }

        b[i] -= b[step] * mf;
      }
    }
  }

  private static int findMaxRow(double[][] a, int step) {
    int maxRow = step;

    for (int i = step + 1; i < a.length; i++) {
      if (Math.abs(a[i][step]) > Math.abs(a[maxRow][step])) {
        maxRow = i;
      }
    }

    return maxRow;
  }

  private static void swapRows(double[][] a, double[] b, int i, int j) {
    double[] temp = a[i];
    a[i] = a[j];
    a[j] = temp;

    double val = b[i];
    b[i] = b[j];
    b[j] = val;
  }

  private static double[] solveEquation(Equation equation) {
    double a[][] = equation.a;
    double b[] = equation.b;

    solveUsingGuassianElimination(a, b);
    return b;
  }

  private static void printColumn(double column[]) {
    int size = column.length;
    for (int raw = 0; raw < size; ++raw)
      System.out.printf("%.20f\n", column[raw]);
  }

  private static class Equation {
    Equation(double a[][], double b[]) {
      this.a = a;
      this.b = b;
    }

    double a[][];
    double b[];
  }
}
