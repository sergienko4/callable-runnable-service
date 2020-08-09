package matrixTcpServer;

import java.util.ArrayList;
import java.util.Collection;

public class CrossMatrix extends MatrixBase {
    public CrossMatrix(int[][] oArray) {
        super(oArray);
    }

    @Override
    public Collection<Index> getAdjacentIndices(final Index index) {
        Collection<Index> list = new ArrayList<>();
        int extracted = -1;
        try {
            extracted = primitiveMatrix[index.row + 1][index.column];
            list.add(new Index(index.row + 1, index.column));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            extracted = primitiveMatrix[index.row][index.column + 1];
            list.add(new Index(index.row, index.column + 1));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            extracted = primitiveMatrix[index.row - 1][index.column];
            list.add(new Index(index.row - 1, index.column));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            extracted = primitiveMatrix[index.row][index.column - 1];
            list.add(new Index(index.row, index.column - 1));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int r = index.row - 1;
            int c = index.column + 1;
            extracted = primitiveMatrix[r][c];
            list.add(new Index(r, c));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int r = index.row - 1;
            int c = index.column - 1;
            extracted = primitiveMatrix[r][c];
            list.add(new Index(r, c));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int r = index.row + 1;
            int c = index.column + 1;
            extracted = primitiveMatrix[r][c];
            list.add(new Index(r, c));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            int r = index.row + 1;
            int c = index.column - 1;
            extracted = primitiveMatrix[r][c];
            list.add(new Index(r, c));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }


        return list;
    }


}
