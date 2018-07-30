
import java.util.HashSet;
import java.util.Set;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tiron
 */
public class Graph {

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public String returnShortestPathtoEnd(Node endNode) {

        String output = "Path = " + endNode.returnShortestpath() + endNode.getName()
                + " Distance=" + endNode.getDistance() + "\n";

        return output;
    }

    @Override
    public String toString() {
        String output = "";
        for (Node node : nodes) {
            output += node.getName() + "-";
        }
        //    System.out.println("nodes:" + nodes.size());
        return output;
    }

}
