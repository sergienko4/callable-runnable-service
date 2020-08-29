package matrixTcpServer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class MatrixIHandler implements IHandler {

    private Matrix matrix;
    private Index start, end;
    private Battleship battleShip;

    public MatrixIHandler() {
        this.resetParams();
    }

    private void resetParams() {
        this.matrix = null;
        this.start = null;
        this.end = null;
    }

    @Override
    public void handle(InputStream inClient, OutputStream outClient) throws Exception {
        System.out.println("server::start handle");

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outClient);
        ObjectInputStream objectInputStream = new ObjectInputStream(inClient);

        this.resetParams();

        boolean dowork = true;
        while (dowork) {
            switch (objectInputStream.readObject().toString()) {
                case "stop": {
                    dowork = false;
                    break;
                }
                case "matrix": {
                    int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                    this.matrix = new Matrix(primitiveMatrix);
                    this.battleShip = new Battleship(primitiveMatrix);
                    this.matrix.printMatrix();
                    break;
                }
                case "cross-matrix": {
                    int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                    this.matrix = new CrossMatrix(primitiveMatrix);
                    this.matrix.printMatrix();
                    break;
                }
                case "battleship": {
                    int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                    this.battleShip = new Battleship(primitiveMatrix);
                    this.matrix.printMatrix();
                    break;
                }
                case "start Index": {
                    this.start = (Index) objectInputStream.readObject();
                    break;
                }
                case "end Index": {
                    this.end = (Index) objectInputStream.readObject();
                    break;
                }
                case "AdjacentIndices": {
                    // receiving index for getAdjacentIndices
                    Index indexAdjacentIndices = (Index) objectInputStream.readObject();
                    Collection<Index> adjacentIndices = new ArrayList<>();
                    if (this.matrix != null) {
                        adjacentIndices.addAll(this.matrix.getAdjacentIndices(indexAdjacentIndices));
                    }
                    // sending getAdjacentIndices
                    System.out.println("server::getAdjacentIndices:: " + adjacentIndices);
                    objectOutputStream.writeObject(adjacentIndices);
                    break;
                }
                case "Reachables": {
                    // receiving index for getReachables
                    Index indexReachable = (Index) objectInputStream.readObject();
                    Collection<Index> reachable = new ArrayList<>();
                    if (this.matrix != null) {
                        reachable.addAll(this.matrix.getReachables(indexReachable));
                    }
                    // sending getReachables
                    System.out.println("server::getReachables:: " + reachable);
                    objectOutputStream.writeObject(reachable);
                    break;
                }
            }
        }
    }
}