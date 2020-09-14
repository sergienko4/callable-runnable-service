package matrixTcpServer;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Matrix implements Serializable {
    public int[][] primitiveMatrix;

    public Matrix(int[][] oArray) {
        this.primitiveMatrix = oArray;
    }

    public int getValue(Index index) {
        return primitiveMatrix[index.row][index.column];
    }

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
        return list;
    }

    public Collection<Index> getReachables(Index index) {

        ArrayList<Index> filteredIndices = new ArrayList<>();
        this.getAdjacentIndices(index).stream().filter(i -> getValue(i) == 1)
                .map(neighbor -> filteredIndices.add(neighbor)).collect(Collectors.toList());
        return filteredIndices;
    }

    public Map<Index, LinkedHashSet<Index>> getGroups() {
        Map<Index, LinkedHashSet<Index>> map = new HashMap<>();
        LinkedHashSet<Index> checkedIndexesMain = new LinkedHashSet<Index>();

        for (int i = 0; i < this.primitiveMatrix.length; i++) {
            for (int j = 0; j < this.primitiveMatrix[i].length; j++) {
                if (this.primitiveMatrix[i][j] == 0) {
                    continue;
                }
                LinkedHashSet<Index> checkedIndexes = new LinkedHashSet<Index>();

                Index index = new Index(i, j);

                Collection<Index> finalList = new ArrayList<>();
                Stack<Index> reachableIndexes = new Stack<Index>();

                Collection<Index> list = this.getReachables(index);
                list.add(index);

                for (var item : list) {
                    if (reachableIndexes.stream().filter(x -> x.equals(item)).count() == 0
                            &&
                            checkedIndexesMain.stream().filter(x -> x.equals(item) == true).count() == 0) {
                        finalList.add(item);
                    }
                }
                reachableIndexes.addAll(finalList);
                while (!reachableIndexes.isEmpty()) {
                    Index temp = reachableIndexes.pop();
                    if (checkedIndexesMain.stream().filter(x -> x.equals(temp) == true).count() > 0 ||
                            checkedIndexes.stream().filter(x -> x.equals(temp) == true).count() > 0
                    ) {
                        continue;
                    }
                    Collection<Index> tempList = getReachables(temp);
                    // add all to one collection
                    tempList.forEach((value -> {
                        if (checkedIndexesMain.stream().filter(x -> x.equals(value) == true).count() == 0 &&
                                checkedIndexes.stream().filter(x -> x.equals(value) == true).count() == 0 &&
                                reachableIndexes.stream().filter(x -> x.equals(value) == true).count() == 0
                        ) {
                            reachableIndexes.push(value);
                        }
                    }));
                    checkedIndexes.add(temp);
                }

                AtomicLong count = new AtomicLong();
                map.forEach((k, v) -> {
                    count.set(count.get() + v.stream().filter(x -> x.equals(index)).count());
                });
                if (count.get() > 0) {
                    continue;
                }
                map.put(index, checkedIndexes);
                checkedIndexesMain.addAll(checkedIndexes);
            }
        }
        return map;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] row : primitiveMatrix) {
            stringBuilder.append(Arrays.toString(row));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void printMatrix() {
        String out = this.toString();
        System.out.print(out);
    }
}