import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JobQueue {
  private Job[] jobArray;
  private PriorityQueue<WorkerThread> workerQueue = new PriorityQueue<>();

  private FastScanner in;
  private PrintWriter out;

  public static void main(String[] args) throws IOException {
    new JobQueue().solve();
  }

  private void readData() throws IOException {
    int numberOfWorkers = in.nextInt();

    for (int i = 0; i < numberOfWorkers; i++) {
      WorkerThread worker = new WorkerThread(i);
      workerQueue.offer(worker);
    }

    int numberOfJobs = in.nextInt();
    jobArray = new Job[numberOfJobs];

    for (int i = 0; i < jobArray.length; i++) {
      long duration = in.nextLong();
      Job job = new Job(duration);
      jobArray[i] = job;
    }
  }

  private void assignJobs() {
    int i = 0;

    while (i < jobArray.length) {
      Job job = jobArray[i];
      WorkerThread worker = workerQueue.poll();

      // update current job with worker id and start time
      job.setAssignedWorker(worker.getId());
      long currentTime = worker.getFinishTIme();
      job.setStartTime(currentTime);
      
      // update the worker with new finish time
      long newFinishTime = currentTime + job.getDuration();
      worker.setFinishTIme(newFinishTime);

      // enqueue the worker again for future jobs
      workerQueue.offer(worker);
      i++;
    }
  }

  private void writeResponse() {
    for (int i = 0; i < jobArray.length; i++) {
      Job job = jobArray[i];
      out.print(String.format("%d %d%n", job.getAssignedWorker(), job.getStartTime()));
    }
  }

  public void solve() throws IOException {
    in = new FastScanner();
    out = new PrintWriter(new BufferedOutputStream(System.out));
    readData();
    assignJobs();
    writeResponse();
    out.close();
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

    public long nextLong() throws IOException {
      return Long.parseLong(next());
    }
  }

  private static class WorkerThread implements Comparable<WorkerThread> {
    private final int id;
    private long finishTIme;

    public WorkerThread(int n) {
      id = n;
      finishTIme = 0L;
    }

    public int getId() {
      return id;
    }

    public long getFinishTIme() {
      return finishTIme;
    }

    public void setFinishTIme(long finishTIme) {
      this.finishTIme = finishTIme;
    }

    @Override
    public int compareTo(WorkerThread otherWorker) {
      if (finishTIme < otherWorker.finishTIme) {
        return -1;
      }

      if (finishTIme > otherWorker.finishTIme) {
        return 1;
      }

      if (id < otherWorker.id) {
        return -1;
      }

      if (id > otherWorker.id) {
        return 1;
      }

      return 0;
    }
  }

  private static class Job {
    public static final int INVALID_WORKER_ID = -1;

    private final long duration;
    private long startTime;
    private int assignedWorker;

    public Job(long t) {
      duration = t;
      startTime = -1L;
      assignedWorker = INVALID_WORKER_ID;
    }

    public long getDuration() {
      return duration;
    }

    public long getStartTime() {
      return startTime;
    }

    public void setStartTime(long startTime) {
      this.startTime = startTime;
    }

    public int getAssignedWorker() {
      return assignedWorker;
    }

    public void setAssignedWorker(int assignedWorker) {
      this.assignedWorker = assignedWorker;
    }
  }
}
