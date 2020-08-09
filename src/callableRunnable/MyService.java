package callableRunnable;

import java.util.concurrent.*;


public class MyService<V> {
    //    protected BlockingQueue<V> taskQueue;
//    protected Thread consumerThread;
    private TaskWrapper<V> task;
    private ExecutorService executorService;
    private Future<V> future;

    public MyService(Runnable runnable,  TaskType type) {
        this.task = new TaskWrapper<>(runnable, null , type);
        this.StartAndRunTask();
        this.task.run();
    }

    public MyService(Callable<V> callable, TaskType type) {
        this.task = new TaskWrapper<>(callable, type);
        this.StartAndRunTask();
    }


//    public <V> Future<V> apply(final Runnable runnable,final V v, BiFunction<Runnable,V,RunnableFuture<V>> runnableTFunction) throws InterruptedException {
//        final RunnableFuture<V> runnableFuture = runnableTFunction.apply(runnable,v);
//        this.myApply(runnableFuture);
//        return runnableFuture;
//    }
//
//    public<V> Future<V> apply(final Callable<V> callable,Function<Callable<V>,RunnableFuture<V>> runnableTFunction) throws InterruptedException {
//        final RunnableFuture<V> runnableFuture = runnableTFunction.apply(callable);
//        this.myApply(runnableFuture);
//        return runnableFuture;
//    }

    private void StartAndRunTask() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(this.task);
        this.executorService.shutdown();
    }

    public V get() throws ExecutionException, InterruptedException {
        return this.task.get();
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.task.get(timeout, unit);
    }

    public boolean isDone() {
        return this.task.isDone();
    }

    public void cancel(boolean mayInterruptIfRunning) {
        this.task.cancel(mayInterruptIfRunning);
    }

    public boolean isCancelled() {
        return this.task.isCancelled();
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
