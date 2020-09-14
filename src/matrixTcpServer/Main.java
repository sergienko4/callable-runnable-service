package matrixTcpServer;

import java.util.LinkedHashSet;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int[][] source1 = {
                {1, 1, 0, 0, 0},
                {0, 0, 1, 1, 0},
                {0, 0, 0, 0, 0}
        };
        int[][] source2 = {
                {1, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {1, 0, 1, 0, 0}
        };
        int[][] source3 = {
                {1, 1, 0, 1, 1},
                {0, 0, 0, 1, 1},
                {1, 1, 0, 1, 1}
        };
        //Task 4
        Matrix crossMatrix1 = new CrossMatrix(source3);
        Battleship battleship1 = new Battleship(crossMatrix1);
        System.out.println("Task 4 CrossMatrix:");
        battleship1.validation();

        Matrix matrix4 = new Matrix(source3);
        Battleship battleship2 = new Battleship(matrix4);
        System.out.println("Task 4 Matrix:");
        battleship2.validation();


//
//        //Task 3
//        CrossMatrix crossMatrix3 = new CrossMatrix(source2);
//        MatrixAsGraph matrixAsGraph3 = new MatrixAsGraph(crossMatrix3, new Index(0, 0));
//        System.out.println("Task 3:");
//        matrixAsGraph3.searchShortest(new Index(2, 2), new Index(0, 2));

//        //Task 2
//        CrossMatrix crossMatrix2 = new CrossMatrix(source2);
//        MatrixAsGraph matrixAsGraph2 = new MatrixAsGraph(crossMatrix2, new Index(0, 0));
//        System.out.println("Task 2:");
//        matrixAsGraph2.search(new Index(2, 2), new Index(0, 2));

//        //Task 1
//        CrossMatrix crossMatrix = new CrossMatrix(source1);
//        Map<Index, LinkedHashSet<Index>> map = crossMatrix.getGroups();
//        System.out.println("Task 1:");
//        map.values().stream().forEach(list -> {
//            System.out.println(list.toString() + "\n");
//        });
    }
}
