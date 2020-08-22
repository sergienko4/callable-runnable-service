package matrixTcpServer;

import java.util.Collection;

public interface Traversable<T> {
    public GraphNode<T> getOrigin();
    Collection<GraphNode<T>> getReachableNodes(final GraphNode<T> source);
}
