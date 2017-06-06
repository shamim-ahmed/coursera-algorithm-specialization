import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class process_packages {

  private static final int INVALID_TIME = -1;

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    int bufferSize = scanner.nextInt();

    List<Request> requests = readQueries(scanner);
    processRequests(requests, bufferSize);
    printResult(requests);

    scanner.close();
  }

  private static List<Request> readQueries(Scanner scanner) throws IOException {
    int numberOfRequests = scanner.nextInt();
    List<Request> requests = new ArrayList<Request>();

    for (int i = 0; i < numberOfRequests; ++i) {
      int arrivalTime = scanner.nextInt();
      int processTime = scanner.nextInt();
      requests.add(new Request(arrivalTime, processTime));
    }
    
    return requests;
  }

  private static void processRequests(List<Request> requestList, int capacity) {
    LinkedList<Request> buffer = new LinkedList<>();

    for (Request currentRequest : requestList) {
      int currentTime = currentRequest.getArrivalTime();
      boolean done = false;

      while (!done && !buffer.isEmpty()) {
        // each packet placed in the buffer will have a valid end time
        Request oldRequest = buffer.peek();

        if (oldRequest.getEndTime() > currentTime) {
          done = true;
        } else {
          buffer.poll();
        }
      }

      if (buffer.isEmpty()) {
        currentRequest.setStartTime(currentTime);
        buffer.offer(currentRequest);
      } else if (buffer.size() == capacity) {
        currentRequest.setStartTime(INVALID_TIME);
      } else {
        Request lastQueuedRequest = buffer.peekLast();
        currentRequest.setStartTime(lastQueuedRequest.getEndTime());
        buffer.offer(currentRequest);
      }
    }
  }

  private static void printResult(List<Request> requests) {
    for (Request request : requests) {
      System.out.println(request.getStartTime());
    }
  }

  private static class Request {
    private final int arrivalTime;
    private final int processTime;
    private int startTime;

    public Request(int arrivalTime, int processTime) {
      this.arrivalTime = arrivalTime;
      this.processTime = processTime;

      startTime = INVALID_TIME;
    }

    public int getArrivalTime() {
      return arrivalTime;
    }

    public int getProcessTime() {
      return processTime;
    }

    public int getStartTime() {
      return startTime;
    }

    public void setStartTime(int startTime) {
      this.startTime = startTime;
    }

    public int getEndTime() {
      if (startTime == INVALID_TIME) {
        return INVALID_TIME;
      }

      return startTime + processTime;
    }
  }
}
