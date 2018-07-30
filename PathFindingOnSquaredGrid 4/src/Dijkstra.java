
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dijkstra {

    private static int N;
    private static ArrayList<Node> nodes;
    private static Graph graph;
    private static Node startNode, endNode;

    enum distanceType {

        MANHATTAN, EUCLIDEAN, CHEBYSHEV
    }

    public Dijkstra() {
        N = PathFindingOnSquaredGrid.getRandomlyGenMatrix().length;
        nodes = new ArrayList<Node>();
        graph = new Graph();
    }

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0.0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
 
            for (Entry< Node, Double> adjacencyPair
                    : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();

                Double edgeWeight = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);

                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static Node getLowestDistanceNode(Set< Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Node node : unsettledNodes) {
            Double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode,
            Double edgeWeigh, Node sourceNode) {

        Double sourceDistance = sourceNode.getDistance();
       
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {

            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);

        }

    }

    public static void addNodes() {
        int numberOfNodes = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (PathFindingOnSquaredGrid.getRandomlyGenMatrix()[i][j]) {
                    //System.out.println((i + 1) + "" + (j + 1));
                    nodes.add(new Node(i + "" + j , i , j));

                    if (i == PathFindingOnSquaredGrid.getAi() && j == PathFindingOnSquaredGrid.getAj()) {
                        startNode = nodes.get(numberOfNodes);
                    } else if (i == PathFindingOnSquaredGrid.getBi() && j == PathFindingOnSquaredGrid.getBj()) {
                        endNode = nodes.get(numberOfNodes);
                    }
                    ++numberOfNodes;
                }
            }
        }

    }

    public static void addDestinations(distanceType distType) {

        int x1, x2, y1, y2;


        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                if (i != j) {
                    x1 = nodes.get(i).getX();
                    y1 = nodes.get(i).getY();

                    x2 = nodes.get(j).getX();
                    y2 = nodes.get(j).getY();

                    if (Math.abs(x2 - x1) <= 1 && Math.abs(y2 - y1) <= 1) {
                        switch (distType) {
                            case MANHATTAN:
                                nodes.get(i).addDestination(nodes.get(j), manhattan(x1, x2, y1, y2));
                                break;
                            case EUCLIDEAN:
                                nodes.get(i).addDestination(nodes.get(j), euclidean(x1, x2, y1, y2));
                                break;
                            case CHEBYSHEV:
                                nodes.get(i).addDestination(nodes.get(j), chebyshev(x1, x2, y1, y2));
                                break;
                            default:
                                System.out.println("Invalid distance type");
                        }
                      
                    }
                }

            }

        }

    }

    public static int manhattan(int x1, int x2, int y1, int y2) {
        if (Math.abs(x1 - x2) + Math.abs(y1 - y2) > 1) {
            return Integer.MAX_VALUE;
        }
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);

    }

    public static double euclidean(int x1, int x2, int y1, int y2) {

        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

    }

    public static double chebyshev(int x1, int x2, int y1, int y2) {

        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));

    }

    public static void addNodesToGraph() {
        for (int i = 0; i < nodes.size(); i++) {

            graph.addNode(nodes.get(i));
    
        }
    }

    public static Node getStartNode() {
        return startNode;
    }

    public static Node getEndNode() {
        return endNode;
    }

    public static void mainDijkstra(int repetitionNumber) {

        addNodes();

        if (repetitionNumber == 0) {
            addDestinations(distanceType.MANHATTAN);

        } else if (repetitionNumber == 1) {
            addDestinations(distanceType.EUCLIDEAN);
        } else if (repetitionNumber == 2) {
            addDestinations(distanceType.CHEBYSHEV);
        }

        addNodesToGraph();

      
        graph = calculateShortestPathFromSource(graph, startNode);

        System.out.println(graph.returnShortestPathtoEnd(endNode));

    }

    public static void mainDijkstra2() {
        Node nodeA = new Node("A", 1, 1);
        Node nodeB = new Node("B", 1, 2);
        Node nodeC = new Node("C", 2, 1);
        Node nodeD = new Node("D", 2, 2);
        Node nodeE = new Node("E", 3, 1);
        Node nodeF = new Node("F", 3, 2);

        nodeA.addDestination(nodeB, 10);
        nodeA.addDestination(nodeC, 15);

        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeF, 15);

        nodeC.addDestination(nodeE, 10);

        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

  
        graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);

        System.out.println(graph.returnShortestPathtoEnd(nodeF));

    }

}
