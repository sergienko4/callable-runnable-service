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

    public void search(Index target, boolean firstPathOnly) {
        search(this.index, target, firstPathOnly);
    }

    public void search(Index start, Index target, boolean firstPathOnly) {
        Search search = new Search(this);
        search.search(start, target, firstPathOnly);

        search.printPath(firstPathOnly);

    }

    public void searchShortest(Index target) {
        this.searchShortest(this.index, target);
    }

    public void searchShortest(Index start, Index target) {
        Search search = new Search(this);
        search.search(start, target, false);
        search.printAllShortest();
    }


//    public static void main(String[] args) {
//
//        int[][] source = {
//                {1, 1, 1, 1, 1},
//                {1, 0, 0, 0, 1},
//                {1, 1, 1, 1, 1}
//        };
//        MatrixAsGraph matrix = new MatrixAsGraph(source, new Index(0, 0));
//        Collection<GraphNode<Index>> list = matrix.getReachableNodes(matrix.getOrigin());
//        Index target = new Index(2, 4);
//        matrix.search(target, false);
//
//        matrix.searchShortest(target);


}

