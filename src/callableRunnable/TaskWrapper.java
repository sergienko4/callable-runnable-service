package callableRunnable;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

public class TaskWrapper<V> implements RunnableFuture<V>, Comparable<TaskWrapper<V>> {
    protected TaskType taskType;
    public RunnableFuture<V> target;


    public TaskWrapper(@NotNull Callable<V> callable, TaskType type) {
        this.taskType = type;
        this.target = new FutureTask<>(callable);
    }

    public TaskWrapper(@NotNull Runnable runnable, V result, TaskType type) {
        this.target = new FutureTask<>(runnable, result);
        this.taskType = type;
    }

    public int getPriority() {
        return this.taskType.getPriority();
    }

    @Override
    public String toString() {
        String p = Integer.toString(this.getPriority());
        return taskType.toString() + " priority: " + p + "\n";
    }

    @Override
    public int compareTo(TaskWrapper taskWrapper) {
        return Integer.compare(this.getPriority(), taskWrapper.getPriority());
    }

    @Override
    public void run() {
        this.target.run();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.target.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return this.target.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.target.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return this.target.get();
    }

    @Override
    public V get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.target.get(timeout, unit);
    }
}
