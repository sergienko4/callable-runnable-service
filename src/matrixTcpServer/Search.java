package matrixTcpServer;

import matrixTcpServer.Index;
import matrixTcpServer.MatrixAsGraph;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Search<T> {

    private Map<Index, LinkedHashSet<Index>> map;
    private ArrayList<LinkedList<Index>> paths;

    private MatrixAsGraph graph;
    private Index source;
    private Index target;


    public Search(MatrixAsGraph graph) {

        this.map = new HashMap<>();
        this.paths = new ArrayList<LinkedList<Index>>();
        this.graph = graph;

        initVertex();
    }

    private void initVertex() {
        for (int i = 0; i < graph.matrix.primitiveMatrix.length; i++) {
            for (int j = 0; j < this.graph.matrix.primitiveMatrix[i].length; j++) {
                Index index = new Index(i, j);
                if (graph.matrix.getValue(index) > 0) {
                    GraphNode<Index> node = new GraphNode<>(index);
                    Collection<GraphNode<Index>> reachableNodes = graph.getReachableNodes(node);
                    // link
                    for (GraphNode<Index> graphNode : reachableNodes) {
                        addTwoWayVertex(graphNode.getData(), node.getData());
                    }
                }
            }
        }
    }

    public Index getSource() {
        return source;
    }

    public void setSource(Index source) {
        this.source = source;
    }

    public Index getTarget() {
        return target;
    }

    public void setTarget(Index target) {
        this.target = target;
    }

    public void search(Index source, Index target, boolean allPaths) {
        this.source = source;
        this.target = target;
        LinkedList<Index> visited = new LinkedList<>();
        visited.add(this.source);
        depthFirst(visited, allPaths);

        // sort from the shorter to the longest
        this.paths.sort((o1, o2) -> {
            return o1.size() - o2.size();
        });
    }

    public void printPath(boolean allPath) {
        // check if has any paths
        if (this.paths.size() < 1) {
            System.out.println("No path available ");
        } else {
            if (allPath) {
                for (LinkedList<Index> item : this.paths) {
                    printPath(item);
                }
            } else {
                printPath(this.paths.get(0));
            }
        }
    }

    private void depthFirst(@NotNull LinkedList<Index> visited, boolean allPaths) {
        LinkedList<Index> nodes = adjacentNodes(visited.getLast());
        for (Index node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(target)) {
                visited.add(node);
                savePath(visited);
                visited.removeLast();
                // stop search
                if (allPaths == false)
                    return;
                break;
            }
        }
        for (Index node : nodes) {
            if (visited.contains(node) || node.equals(target)) {
                continue;
            }
            visited.addLast(node);
            depthFirst(visited, allPaths);
            visited.removeLast();
        }
    }

    private void savePath(LinkedList<Index> visited) {
        LinkedList<Index> cloned = (LinkedList<Index>) visited.clone();
        this.paths.add(cloned);
    }

    private void printPath(LinkedList<Index> visited) {
        for (Index node : visited) {
            System.out.print(node);
            System.out.print(" ");
        }
        System.out.println();
    }

    private void addEdge(Index node1, Index node2) {
        LinkedHashSet<Index> adjacent = map.get(node1);
        if (adjacent == null) {
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
    }

    private void addTwoWayVertex(Index node1, Index node2) {
        addEdge(node1, node2);
        addEdge(node2, node1);
    }

    private boolean isConnected(Index node1, Index node2) {
        Set adjacent = map.get(node1);
        if (adjacent == null) {
            return false;
        }
        return adjacent.contains(node2);
    }

    public LinkedList<Index> adjacentNodes(Index last) {
        LinkedHashSet<Index> adjacent = map.get(last);
        if (adjacent == null) {
            return new LinkedList();
        }
        return new LinkedList<>(adjacent);
    }


}
