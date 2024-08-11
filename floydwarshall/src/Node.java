import java.util.*;

// A node of a graph for the Spring 2018 ICS 340 program
public class Node {

    String name; // The name of the Node
    String val; // The value of the Node
    String abbrev; // The abbreviation for the Node
    ArrayList<Edge> outgoingEdges; // List of edges coming out from the node
    ArrayList<Edge> incomingEdges; // List of edges going into the node
    boolean visited = false;
    private String type;

    // Constructor creates the Node
    public Node(String theAbbrev) {
        setAbbrev(theAbbrev);
        val = null;
        name = null;
        outgoingEdges = new ArrayList<Edge>();
        incomingEdges = new ArrayList<Edge>();
    }

    // Gets the node abbreviation
    public String getAbbrev() {
        return abbrev;
    }

    // Gets the node name
    public String getName() {
        return name;
    }

    // Gets the node value
    public String getVal() {
        return val;
    }

    // Gets the list of outgoing edges
    public ArrayList<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    // Gets the list of incoming edges
    public ArrayList<Edge> getIncomingEdges() {
        return incomingEdges;
    }

    // Sets the abbreviation for the node
    public void setAbbrev(String theAbbrev) {
        abbrev = theAbbrev;
    }

    // Sets the name for the node
    public void setName(String theName) {
        name = theName;
    }

    // Sets the value for the node
    public void setVal(String theVal) {
        val = theVal;
    }

    // Adds the outgoing edge for the node
    public void addOutgoingEdge(Edge e) {
        outgoingEdges.add(e);
    }

    // Adss the incoming edge for the node
    public void addIncomingEdge(Edge e) {
        incomingEdges.add(e);
    }

    public boolean isVisited() {
        return visited;
    }

    // Add a method to mark the node as visited
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
