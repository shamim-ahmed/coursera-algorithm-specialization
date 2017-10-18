import java.io.*;
import java.util.*;

public class Diet {

  private static final double ZERO_CHECK_LIMIT = 1.0E-6;
  private static final double BIG_NUMBER = 1.0E9;
  private static final double THRESHOLD_VALUE = 999_999_990D;

  BufferedReader br;
  PrintWriter out;
  StringTokenizer st;
  boolean eof;

  int solveDietProblem(int n, int m, double[][] a, double[] b, double[] c, double[] x) {
    Double maxValue = Double.NaN;
    double[] maxSolution = new double[m];

    int[] inputValues = new int[n + m + 1];

    for (int i = 0; i < inputValues.length; i++) {
      inputValues[i] = i;
    }

    List<int[]> combinationList = new ArrayList<>();
    int[] temp = new int[m];
    generateCombinations(inputValues, temp, m, 0, combinationList);

    for (int[] combination : combinationList) {
      double[][] subsetA = new double[m][m];
      double[] subsetB = new double[m];
      selectSubsetOfEquations(combination, a, b, subsetA, subsetB);
      boolean errorFlag = false;

      try {
        solveUsingGuassianElimination(subsetA, subsetB);
      } catch (Exception ex) {
        errorFlag = true;
      }

      if (errorFlag) {
        continue;
      }

      double[] solution = subsetB;
      double[][] tempA = new double[n + 1][m];
      double[] tempB = new double[n + 1];

      int[] complementCombination = findComplement(combination, n, m);
      selectSubsetOfEquations(complementCombination, a, b, tempA, tempB);
      boolean valid = validateSolution(solution, tempA, tempB);

      if (valid) {
        double val = multiply(c, solution);

        if (maxValue.isNaN() || val > maxValue) {
          maxValue = val;
          maxSolution = solution;
        }
      }
    }
   
    if (maxValue.isNaN()) {
      return -1;
    }
    
    if (maxValue > THRESHOLD_VALUE){
      return 1;
    }

    System.arraycopy(maxSolution, 0, x, 0, m);
    return 0;
  }

  private void generateCombinations(int[] inputValues, int[] combination, int len, int pos,
      List<int[]> resultList) {
    if (len == 0) {
      int[] result = new int[combination.length];
      System.arraycopy(combination, 0, result, 0, combination.length);
      resultList.add(result);
      return;
    }

    for (int i = pos; i <= inputValues.length - len; i++) {
      if (pos > 0 && combination[pos - 1] >= inputValues[i]) {
        continue;
      }

      combination[pos] = inputValues[i];
      generateCombinations(inputValues, combination, len - 1, pos + 1, resultList);
    }
  }

  private void selectSubsetOfEquations(int[] combination, double[][] a, double[] b,
      double[][] subsetA, double[] subsetB) {
    int n = a.length;
    int m = a[0].length;

    for (int i = 0; i < combination.length; i++) {
      int k = combination[i];

      if (k < n) {
        System.arraycopy(a[k], 0, subsetA[i], 0, subsetA[i].length);
        subsetB[i] = b[k];
      } else if (k == n + m) {
        subsetA[i] = new double[m];
        Arrays.fill(subsetA[i], 1.0);
        subsetB[i] = BIG_NUMBER;
      } else {
        subsetA[i] = new double[m];
        subsetA[i][k - n] = -1.0;
        subsetB[i] = 0.0;
      }
    }
  }

  private int[] findComplement(int[] combination, int n, int m) {
    int[] result = new int[n + 1];
    int k = 0;
    
    for (int i = 0; i <= n + m; i++) {
      if (Arrays.binarySearch(combination, i) < 0) {
        result[k] = i;
        k++;
      }
    }
    
    return result;
  }

  private boolean validateSolution(double[] solution, double[][] tempA, double[] tempB) {
    boolean result = true;

    for (int i = 0; i < tempA.length; i++) {
      double val = multiply(tempA[i], solution);

      if (val > tempB[i]) {
        result = false;
        break;
      }
    }

    return result;
  }

  private double multiply(double[] p, double[] q) {
    double result = 0.0;

    for (int i = 0; i < p.length; i++) {
      result += p[i] * q[i];
    }

    return result;
  }

  private void solveUsingGuassianElimination(double[][] a, double[] b) throws Exception {
    int size = a.length;

    for (int step = 0; step < size; step++) {
      int k = findMaxRow(a, step);

      if (k != step) {
        swapRows(a, b, step, k);
      }

      if (Math.abs(a[step][step]) < ZERO_CHECK_LIMIT) {
        throw new Exception("Leading coefficient is too small");
      }

      // normalize the current row
      double coeff = a[step][step];

      for (int j = step; j < size; j++) {
        a[step][j] /= coeff;
      }

      b[step] /= coeff;

      // subtract multiples of current row from other rows
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

  private int findMaxRow(double[][] a, int step) {
    int maxRow = step;

    for (int i = step + 1; i < a.length; i++) {
      if (Math.abs(a[i][step]) > Math.abs(a[maxRow][step])) {
        maxRow = i;
      }
    }

    return maxRow;
  }

  private void swapRows(double[][] a, double[] b, int i, int j) {
    double[] temp = a[i];
    a[i] = a[j];
    a[j] = temp;

    double val = b[i];
    b[i] = b[j];
    b[j] = val;
  }

  void solve() throws IOException {
    int n = nextInt();
    int m = nextInt();
    double[][] A = new double[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        A[i][j] = nextInt();
      }
    }
    double[] b = new double[n];
    for (int i = 0; i < n; i++) {
      b[i] = nextInt();
    }
    double[] c = new double[m];
    for (int i = 0; i < m; i++) {
      c[i] = nextInt();
    }
    double[] ansx = new double[m];
    int anst = solveDietProblem(n, m, A, b, c, ansx);
    if (anst == -1) {
      out.printf("No solution\n");
      return;
    }
    if (anst == 0) {
      out.printf("Bounded solution\n");
      for (int i = 0; i < m; i++) {
        out.printf("%.18f%c", ansx[i], i + 1 == m ? '\n' : ' ');
      }
      return;
    }
    if (anst == 1) {
      out.printf("Infinity\n");
      return;
    }
  }

  Diet() throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    out = new PrintWriter(System.out);
    solve();
    out.close();
  }

  public static void main(String[] args) throws IOException {
    new Diet();
  }

  String nextToken() {
    while (st == null || !st.hasMoreTokens()) {
      try {
        st = new StringTokenizer(br.readLine());
      } catch (Exception e) {
        eof = true;
        return null;
      }
    }
    return st.nextToken();
  }

  int nextInt() throws IOException {
    return Integer.parseInt(nextToken());
  }
}
