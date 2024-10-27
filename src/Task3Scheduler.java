import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Task3Scheduler {
    public static void main(String[] args) {
        List<int[]> jobs = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get("dir/task3-input.txt"));
            for (String line : lines) {
                String[] parts = line.split(" ");
                int jobId = Integer.parseInt(parts[0]);
                int processingTime = Integer.parseInt(parts[1]);
                int arrivalTime = Integer.parseInt(parts[2]);
                jobs.add(new int[]{jobId, processingTime, arrivalTime});
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        jobs.sort(Comparator.comparingInt(a -> a[2]));
        Queue<int[]> jobQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        int currentTime = 0;
        int totalCompletionTime = 0;
        List<Integer> executionOrder = new ArrayList<>();
        int jobIndex = 0;

        while (jobIndex < jobs.size() || !jobQueue.isEmpty()) {
            while (jobIndex < jobs.size() && jobs.get(jobIndex)[2] <= currentTime) {
                jobQueue.add(jobs.get(jobIndex++));
            }

            if (jobQueue.isEmpty()) {
                currentTime = jobs.get(jobIndex)[2];
            } else {
                int[] job = jobQueue.poll();
                executionOrder.add(job[0]);
                currentTime += job[1];
                totalCompletionTime += (currentTime - job[2]);
            }
        }

        double averageCompletionTime = totalCompletionTime / (double) executionOrder.size();

        System.out.println("Execution order: " + executionOrder);
        System.out.printf("Average completion time: %.1f\n", averageCompletionTime);
    }
}
