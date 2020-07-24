import java.util.concurrent.*;
import java.util.function.Function;


public class MyService<V extends Runnable> {
    //    protected BlockingQueue<V> taskQueue;
//    protected Thread consumerThread;
    private TaskWrapper<V> task;
    private ExecutorService executorService;
    private Future<V> future;

    public MyService(Runnable runnable, TaskType type) {
        this.task = new TaskWrapper<V>(runnable, type);
        this.StartAndRunTask();
    }

    public MyService(Callable<V> callable, TaskType type) {
        this.task = new TaskWrapper<V>(callable, type);
        this.StartAndRunTask();
    }

    private void StartAndRunTask() {
        executorService = Executors.newSingleThreadExecutor();
        this.future = (Future<V>) executorService.submit(this.task);
        this.executorService.shutdown();
    }

    public V get() throws ExecutionException, InterruptedException {
        return this.future.get();
        //return this.task.get();
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.future.get(timeout, unit);
//        return this.task.get(timeout, unit);
    }

    public boolean isDone() {
        return this.future.isDone();
//        return this.task.isDone();
    }

    public void cancel(boolean mayInterruptIfRunning) throws InterruptedException {
        this.future.cancel(mayInterruptIfRunning);
       /* if (mayInterruptIfRunning) {
            isStopNow = true;
            this.consumerThread.interrupt();

        } else {
            // wait till it completes the running
            if (this.consumerThread.isAlive()) {
                this.consumerThread.join();
            }
        }*/
    }

    public boolean isCancelled() {
        return this.future.isCancelled();
    }

    public static void throwIfNull(Object... arguments) throws NullPointerException {
        for (Object argument : arguments) {
            if (argument == null) {
                throw new NullPointerException("arg is null");
            }
        }
    }
}
