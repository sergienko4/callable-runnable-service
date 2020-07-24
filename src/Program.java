import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class Program {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Runnable runnable = () -> System.out.println("Task #1 is running");

        Callable<Integer> callable = () -> {
            Random rnd = new Random();
            return rnd.nextInt();
        };

        var ser = new MyService<>(runnable, TaskType.IO);
        var ser1 = new MyService<>(callable, TaskType.COMPUTATIONAL);

        while (!ser.isDone()) {
            System.out.println("Task #1 is not done");
        }

        while (!ser1.isDone()) {
            System.out.println("Task #2 is not done");
        }

        var result = ser1.get();
        System.out.println("Task #2 is done: " + result);
        System.out.println("Task #2 info:" + ser1);
    }
}
