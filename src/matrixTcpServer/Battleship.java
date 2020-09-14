package matrixTcpServer;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Battleship {
    private Matrix graph;

    Battleship(@NotNull final int[][] matrix) {
        this.graph = new Matrix(matrix);
    }

    Battleship(@NotNull final Matrix matrix) {
        this.graph = new Matrix(matrix.primitiveMatrix);
    }

    public void validation() {
        AtomicBoolean isValid = new AtomicBoolean(true);
        this.graph.getGroups().forEach((k, v) -> {
            Index maxX = Collections.max(v, (o1, o2) -> o1.column - o2.column);
            Index maxY = Collections.max(v, (o1, o2) -> o1.row - o2.row);
            Index minX = Collections.min(v, (o1, o2) -> o1.column - o2.column);
            Index minY = Collections.min(v, (o1, o2) -> o1.row - o2.row);

            int total = ((maxX.column - minX.column) + 1) * (1 + (maxY.row - minY.row));
            if ((v.size() != total) || v.size() == 1) {
                printError(v);
                isValid.set(false);
            } else {
                System.out.println("Size:" + v.size() + " 👍");
            }
        });
        if (isValid.get()) {
            System.out.println("OK:");
        }
    }

    private void printError(LinkedHashSet<Index> list) {
        System.out.println("Wrong input! 🤦‍");
        System.out.println(list.toString());


    }
}
