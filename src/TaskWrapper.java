import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;
import java.util.function.Function;

public class TaskWrapper<V> extends FutureTask<V> implements Comparable<TaskWrapper<V>> {
    protected TaskType taskType;
    public FutureTask<V> target;

    public TaskWrapper(@NotNull Callable<V> callable, TaskType type) {
        super(callable);
        this.initData(new FutureTask<>(callable), type);
    }

    public TaskWrapper(@NotNull Runnable runnable, TaskType type) {
        super(runnable, null);
        this.initData(new FutureTask<>(runnable, null), type);
    }

    private void initData(@NotNull FutureTask<V> target, TaskType type) {
        this.target = target;
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
}
