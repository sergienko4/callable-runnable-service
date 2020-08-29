package matrixTcpServer;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class MatrixAsGraph implements Traversable<Index> {
    public final Matrix matrix;

    private Map<Index, LinkedHashSet<Index>> map;
    private ArrayList<LinkedList<Index>> paths;

    private Index index;
    private Index searchSource;
    private Index searchTarget;

    MatrixAsGraph(@NotNull final Matrix matrix, @NotNull Index index) {
        this.matrix = matrix;
        this.index = index;
        this.searchSource = null;
        this.searchTarget = null;
    }

    public Map<Index, LinkedHashSet<Index>> getMap() {
        this.initVertex();
        return this.map;
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

    public void search(Index target) {
        this.search(target, false);
    }

    public void search(Index target, boolean firstPathOnly) {
        search(this.index, target, firstPathOnly);
        printPath(firstPathOnly);
    }

    public void searchShortest(Index target) {
        this.searchShortest(this.index, target);
    }

    public void searchShortest(Index start, Index target) {
        search(start, target, false);
        printAllShortest();
    }


    public void printPath(boolean firstPathOnly) {
        // check if has any paths
        if (this.paths.size() < 1) {
            System.out.println("No path available ");
        } else {
            if (firstPathOnly == false) {
                for (LinkedList<Index> item : this.paths) {
                    printPath(item);
                }
            } else {
                printPath(this.paths.get(0));
            }
        }
    }

    public void printPath(LinkedList<Index> visited) {
        for (Index node : visited) {
            System.out.print(node);
            System.out.print(" ");
        }
        System.out.println();
    }

    public void printAllShortest() {
        if (this.paths.size() < 1) {
            System.out.println("No path available ");
        }

        int min = this.paths.get(0).size();
        List<LinkedList<Index>> shortest = this.paths.stream().filter(x -> x.size() == min).collect(Collectors.toList());

        // get all shortest paths
        for (LinkedList<Index> item : shortest) {
            printPath(item);
        }
    }

    public LinkedList<Index> adjacentNodes(Index last) {
        LinkedHashSet<Index> adjacent = map.get(last);
        if (adjacent == null) {
            return new LinkedList();
        }
        return new LinkedList<>(adjacent);
    }


    private boolean isConnected(Index node1, Index node2) {
        Set adjacent = map.get(node1);
        if (adjacent == null) {
            return false;
        }
        return adjacent.contains(node2);
    }

    private void initVertex() {
        this.map = new HashMap<>();
        this.paths = new ArrayList<LinkedList<Index>>();

        for (int i = 0; i < this.matrix.primitiveMatrix.length; i++) {
            for (int j = 0; j < this.matrix.primitiveMatrix[i].length; j++) {
                Index index = new Index(i, j);
                if (this.matrix.getValue(index) > 0) {
                    GraphNode<Index> node = new GraphNode<>(index);
                    Collection<GraphNode<Index>> reachableNodes = getReachableNodes(node);
                    if (reachableNodes.size() == 0) {
                        reachableNodes.add(node);
                    }
                    // link
                    for (GraphNode<Index> graphNode : reachableNodes) {
                        addTwoWayVertex(graphNode.getData(), node.getData());
                    }
                }
            }
        }
    }

    private void depthFirst(@NotNull LinkedList<Index> visited, boolean firstPathOnly) {
        LinkedList<Index> nodes = adjacentNodes(visited.getLast());
        for (Index node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(this.searchTarget)) {
                visited.add(node);
                savePath(visited);
                visited.removeLast();
                // stop search
                if (firstPathOnly == true)
                    return;
                break;
            }
        }
        for (Index node : nodes) {
            if (visited.contains(node) || node.equals(this.searchTarget)) {
                continue;
            }
            visited.addLast(node);
            depthFirst(visited, firstPathOnly);
            visited.removeLast();
        }
    }

    private void savePath(LinkedList<Index> visited) {
        LinkedList<Index> cloned = (LinkedList<Index>) visited.clone();
        this.paths.add(cloned);
    }


    private void addTwoWayVertex(Index node1, Index node2) {
        addEdge(node1, node2);
        addEdge(node2, node1);
    }

    private void addEdge(Index node1, Index node2) {
        LinkedHashSet<Index> adjacent = map.get(node1);
        if (adjacent == null) {
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
    }

    private void search(Index source, Index target, boolean firstPathOnly) {
        initVertex();
        this.searchSource = source;
        this.searchTarget = target;
        LinkedList<Index> visited = new LinkedList<>();
        visited.add(this.searchSource);

        depthFirst(visited, firstPathOnly);

        // sort from the shorter to the longest
        this.paths.sort((o1, o2) -> {
            return o1.size() - o2.size();
        });
    }

    public static void main(String[] args) {

        int[][] source = {
                {1, 1, 0, 1, 1},
                {0, 0, 0, 1, 1},
                {1, 1, 0, 1, 1}
        };

        Battleship game = new Battleship(source);
//        CrossMatrix c = new CrossMatrix(source);
//        Collection<Index> g = c.getReachables(new Index(1, 3));
////        System.out.println(g.toString());
//        MatrixAsGraph matrix = new MatrixAsGraph(c, new Index(0, 0));
//
//
//        Collection<GraphNode<Index>> list = matrix.getReachableNodes(matrix.getOrigin());
//        Index target = new Index(2, 4);
//
//        System.out.printf("all\n");
//        matrix.search(target);
//
//
//        System.out.printf("first\n");
//        matrix.search(target, true);
//
//        System.out.printf("shortest\n");
//        matrix.searchShortest(target);

//        matrix.validation();
    }


}

