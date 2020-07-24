import java.util.concurrent.*;


public class MyService<V> {
    //    protected BlockingQueue<V> taskQueue;
//    protected Thread consumerThread;
    private TaskWrapper<V> task;
    private ExecutorService executorService;
    private Future<V> future;

    public MyService(Runnable runnable, TaskType type) {
        this.task = new TaskWrapper<>(runnable, type);
        this.StartAndRunTask();
    }

    public MyService(Callable<V> callable, TaskType type) {
        this.task = new TaskWrapper<>(callable, type);
        this.StartAndRunTask();
    }

    private void StartAndRunTask() {
        executorService = Executors.newSingleThreadExecutor();
        this.future = (Future<V>) executorService.submit(this.task);
        this.executorService.shutdown();
    }

    public V get() throws ExecutionException, InterruptedException {
        return this.task.get();
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.task.get(timeout, unit);
    }

    public boolean isDone() {
        return this.future.isDone();
    }

    public void cancel(boolean mayInterruptIfRunning) {
        this.future.cancel(mayInterruptIfRunning);
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

    @Override
    public String toString() {
        return task.toString();
    }
}
