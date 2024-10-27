import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Task1Scheduler {
    public static void main(String[] args) {
        Queue<int[]> jobQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        try {
            List<String> lines = Files.readAllLines(Paths.get("dir/task1-input.txt"));
            for (String line : lines) {
                String[] parts = line.split(" ");
                int jobId = Integer.parseInt(parts[0]);
                int processingTime = Integer.parseInt(parts[1]);
                jobQueue.add(new int[]{jobId, processingTime});
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        int currentTime = 0;
        int totalCompletionTime = 0;
        List<Integer> executionOrder = new ArrayList<>();

        while (!jobQueue.isEmpty()) {
            int[] job = jobQueue.poll();
            executionOrder.add(job[0]);
            currentTime += job[1];
            totalCompletionTime += currentTime;
        }

        double averageCompletionTime = totalCompletionTime / (double) executionOrder.size();

        System.out.println("Execution order: " + executionOrder);
        System.out.printf("Average completion time: %.1f\n", averageCompletionTime);
    }
}
