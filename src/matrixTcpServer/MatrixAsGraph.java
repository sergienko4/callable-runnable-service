package matrixTcpServer;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class MatrixAsGraph implements Traversable<Index> {
    public final CrossMatrix matrix;
    private Index index;

    MatrixAsGraph(@NotNull final int[][] matrix, @NotNull Index index) {
        this(new Matrix(matrix), index);
    }

    MatrixAsGraph(@NotNull final Matrix matrix, @NotNull Index index) {
        this(new CrossMatrix(matrix.primitiveMatrix), index);
    }

    MatrixAsGraph(@NotNull final CrossMatrix matrix, @NotNull Index index) {
        this.matrix = matrix;
        this.index = index;
    }

    @NotNull
    public Index getIndex() {
        return index;
    }

    @NotNull
    @Override
    public GraphNode<Index> getOrigin() throws NullPointerException {
//        if (this.index == null) throw new NullPointerException("initIndex is not initialized");
        return new GraphNode<>(this.index);
    }

    @NotNull
    @Override
    public Collection<GraphNode<Index>> getReachableNodes(@NotNull final GraphNode<Index> s) {
        return this.matrix.getReachables(s.getData()).stream()
                .map(neighbor -> new GraphNode<>(neighbor, s)).collect(Collectors.toList());
    }

    public void search(Index target, boolean allPaths) {
        Search search = new Search(this);
        search.search(this.getIndex(), target, allPaths);
        search.printPath(allPaths);
    }

    public void search(Index start, Index target, boolean allPaths) {
        Search search = new Search(this);
        search.search(start, target, allPaths);
        search.printPath(allPaths);
    }
}
