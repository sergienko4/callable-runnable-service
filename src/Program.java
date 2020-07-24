import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class Program {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Runnable r = () -> {
            System.out.println("Task #1 is running");
            System.out.println("Task #1 is running");

        };

        Callable<Integer> call = () -> {
            return (10 * 10 * 10 * 10) / 10 * 10 / 10 / 10;
        };
        TaskWrapper<Integer> task1 = new TaskWrapper(r, TaskType.IO);
        var ser = new MyService<>(r, TaskType.IO);
        var ser1 = new MyService<Integer>(call, TaskType.COMPUTATIONAL);
        while (!ser.isDone()) {
            System.out.println("Task #1 is not done");
        }

        while (!ser1.isDone()) {
            System.out.println("Task #1 is not done");
        }

        System.out.println("Task #1 is done: " + ser1.get());


    }
}
