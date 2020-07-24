import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;
import java.util.function.Function;

public class TaskWrapper<V> extends FutureTask<V> implements Comparable<TaskWrapper<V>> {
    protected TaskType taskType;
    public FutureTask<V> target;

    public TaskWrapper(@NotNull Callable<V> callable, TaskType type) {
        super(callable);
        this.initData(new FutureTask<V>(callable), type);
    }

    public TaskWrapper(@NotNull Runnable runnable, TaskType type) {
        super(runnable, null);
        this.initData(new FutureTask<V>(runnable, null), type);
    }

    private void initData(@NotNull FutureTask<V> target, TaskType type) {
        this.target = target;
        this.taskType = type;
    }


    public int getPriority() {
        return this.taskType.getPriority();
    }

    //    @Override
//    public void run() {
//        if (this.target != null && this.isRunning == false) {
//            this.target.run();
//        }
//    }
//
//    @Override
//    public boolean cancel(boolean mayInterruptIfRunning) {
//        return this.target.cancel(mayInterruptIfRunning);
//    }
//
//    @Override
//    public boolean isCancelled() {
//       return  this.target.isCancelled();
//    }
//
//    @Override
//    public boolean isDone() {
////        return  super.isDone();
//        return this.target.isDone();
//    }
//
//    @Override
//    public V get() throws InterruptedException, ExecutionException {
//        V value = null;
//        if (this.target.isDone()) {
//            value = this.target.get();
//        }
//        return value;
//    }
//
//    @Override
//    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
//        return null;
//    }
//
    @Override
    public String toString() {
        String p = Integer.toString(this.getPriority());
        return "task type: " + taskType.toString() + " priority: " + p + "\n";
    }

    @Override
    public int compareTo(TaskWrapper taskWrapper) {
        return Integer.compare(this.getPriority(), taskWrapper.getPriority());
    }
}
