import java.util.*;

//Class represents the Graph
public class Graph {
	//List of the nodes
	ArrayList<Node> nodeList;
	
	//List of the edges between nodes
	ArrayList<Edge> edgeList;
	
	//Constructor initializes the list
	public Graph() {
		nodeList = new ArrayList<Node>();
		edgeList = new ArrayList<Edge>();
	}
	
	//Gets the list of the nodes
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	//Gets the list of the edges
	public ArrayList<Edge> getEdgeList() {
		return edgeList;
	}
	
	//Adds the node to the list
	public void addNode(Node n) {
		nodeList.add(n);
	}

	
	//Adds the edge to the list
	public void addEdge(Edge e) {
		edgeList.add(e);
	}

}
