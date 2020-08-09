package matrixTcpServer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class Matrix extends MatrixBase {
    int[][] primitiveMatrix;

    public Matrix(int[][] oArray) {
        super(oArray);
    }

    public static void main(String[] args) {
        int[][] source = {
                {1, 0, 1},
                {0, 0, 0},
                {1, 0, 1}
        };
        MatrixBase matrix = new CrossMatrix(source);
        matrix.printMatrix();
        System.out.println(matrix.getAdjacentIndices(new Index(1, 1)));
        System.out.println(matrix.getReachables(new Index(1, 1)));
    }
}