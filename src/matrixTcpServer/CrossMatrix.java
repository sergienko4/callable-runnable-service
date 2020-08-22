package matrixTcpServer;

import java.util.*;

public class CrossMatrix extends Matrix {
    public CrossMatrix(int[][] oArray) {
        super(oArray);
    }

    @Override
    public Collection<Index> getAdjacentIndices(final Index index) {
        Collection<Index> list = new ArrayList<>();
        int extracted = -1;
        var tempList = super.getAdjacentIndices(index);

        if (tempList.size() > 0) {
            list.addAll(tempList);
        }

        try {
            int r = index.row - 1;
            int c = index.column + 1;
            extracted = super.getValue(new Index(r, c));
            list.add(new Index(r, c));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int r = index.row - 1;
            int c = index.column - 1;
            extracted = super.getValue(new Index(r, c));
            list.add(new Index(r, c));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int r = index.row + 1;
            int c = index.column + 1;
            extracted = super.getValue(new Index(r, c));
            list.add(new Index(r, c));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int r = index.row + 1;
            int c = index.column - 1;
            extracted = super.getValue(new Index(r, c));
            list.add(new Index(r, c));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }


        return list;
    }



//    public void getPath(Index source, Index target) {
//
//        var reachables = getReachables(source);
//        List<HashSet<Index>> indexList = Collections.synchronizedList(new ArrayList<>());
//        HashSet<Index> seenIndexes = new HashSet<>();
//
////        Traverse<Index> algorithm = new TraverseLogic<>();
//        final int[][] matrix = this.primitiveMatrix;
//        CrossMatrix graph = new CrossMatrix(matrix);
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                final Index index = new Index(i, j);
//                if (matrix[index.row][index.column] == 1 && !seenIndexes.contains(index)) {
////                    graph.setIndex(index);
//                    final AbstractList<Index> list = algorithm.traverse(graph);
//
//                    HashSet<Index> hashSet = new HashSet<>(list);
//                    indexList.add(hashSet);
//                    seenIndexes.addAll(hashSet);
//                }
//            }
//        }
//
//    }

}
