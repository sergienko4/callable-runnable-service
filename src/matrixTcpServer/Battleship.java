package matrixTcpServer;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Battleship {
    private MatrixAsGraph graph;
    private Map<Index, LinkedHashSet<Index>> map;
    private Map<Index, LinkedHashSet<Index>> connected;

    Battleship(@NotNull final int[][] matrix) {
        this(new Matrix(matrix));
    }

    Battleship(@NotNull final Matrix matrix) {
        this.graph = new MatrixAsGraph(matrix, new Index(0, 0));
        validation();
    }

    public void validation() {
        // map the data
        this.map = this.graph.getMap();
        this.connected = new HashMap<>();

        // map connection points;
        map.forEach((key, list) -> {
            LinkedHashSet<Index> clonedListWithKey = (LinkedHashSet<Index>) list.clone();
            clonedListWithKey.add(key);

            if (connected.isEmpty()) {
                connected.put(key, clonedListWithKey);
            } else {
                AtomicBoolean isFound = new AtomicBoolean(false);
                connected.forEach((yKey, yValue) -> {
                    long hasTheKey = yValue.stream().filter(x -> x.equals(key)).count();

                    if (hasTheKey == 0) {
                        final Set<Index> set = new HashSet<>(list.size());
                        for (final Index a : list)
                            set.add(a);

                        for (final Index b : yValue)
                            if (set.contains(b)) {
                                hasTheKey = 1;
                                break;
                            }
                    }

                    if (hasTheKey > 0) {
                        isFound.set(true);
                        yValue.addAll(clonedListWithKey);
                        return;
                    }
                });
                if (isFound.get() == false) {
                    connected.put(key, clonedListWithKey);
                }
            }


        });
        AtomicBoolean isValid = new AtomicBoolean(true);
        // validation of the size - shape
        connected.forEach((key, list) -> {
            Index maxX = Collections.max(list, (o1, o2) -> o1.column - o2.column);
            Index maxY = Collections.max(list, (o1, o2) -> o1.row - o2.row);
            Index minX = Collections.min(list, (o1, o2) -> o1.column - o2.column);
            Index minY = Collections.min(list, (o1, o2) -> o1.row - o2.row);

            int total = ((maxX.column - minX.column) + 1) * (1 + (maxY.row - minY.row));

            if (total != list.size() || total == 1) {
                printError(list);
                isValid.set(false);
            }
        });

        if (isValid.get()) {
            System.out.printf("the number of ships is:" + connected.size());
        }
    }

    private void printError(LinkedHashSet<Index> list) {
        System.out.println("Wrong input!");
    }
}
