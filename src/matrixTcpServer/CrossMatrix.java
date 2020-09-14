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
}
