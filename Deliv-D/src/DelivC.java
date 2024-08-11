import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

// Class DelivC does the work for deliverable DelivC of the Prog340

public class DelivC {

    File inputFile;
    File outputFile;
    PrintWriter output;
    Graph g;
    int event=0;
    String allGoalPaths="";
    int minimumDist = 100000;
    String distances = "";

    public DelivC(File in, Graph gr ) {
        inputFile = in;
        g = gr;
        // Get output file name.
        String inputFileName = inputFile.toString();
        String baseFileName = inputFileName.substring(0, inputFileName.length()-4); // Strip off ".txt"
        String outputFileName = baseFileName.concat( "_out.txt" );
        outputFile = new File( outputFileName );
        if ( outputFile.exists() ) {    // For retests
            outputFile.delete();
        }

		try {
			output = new PrintWriter(outputFile);
		}
		catch (Exception x ) {
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}

		delivc();
        output.flush();
    }

    // depth first search algorithm
	private void delivc() {
		ArrayList<Node> nodes = g.getNodeList();

		// start node
		Node startNode = null;

		// goal nodes
		ArrayList<Node> goals = new ArrayList<>();

		// the path to the goal nodes
		HashMap<Node, ArrayList<Node>> pathMap = new HashMap<>();

		// the distance to the goal nodes
		HashMap<Node, Integer> distanceMap = new HashMap<>();

		// the current route
		ArrayList<Node> pathCurrent = new ArrayList<>();

		// a list of relevant edges for each node in current path
		ArrayList<ArrayList<Edge>> availableEdges = new ArrayList<>();

		// visits to each of the nodes
		HashSet<Node> visitedGlobal = new HashSet<>();

		// the nearest of the found goal nodes
		Node nearestGoal = null;

		// minimum distances to the goal node
		int maiDistance = Integer.MAX_VALUE;

		// steps sequence
		String sequence = "";

		// edges comparator
		Comparator<Edge> comparatorEdge = (edge1, edge2) -> {
			int distance1 = edge1.getDist();
			int distance2 = edge2.getDist();

			if (distance1 < distance2) {
				return -1;
			}
			else if (distance1 > distance2) {
				return 1;
			}
			else {
				String abbrev1 = edge1.getHead().getAbbrev();
				String abbrev2 = edge2.getHead().getAbbrev();

				return abbrev1.compareTo(abbrev2);
			}
		};

		// set initial data
		for (Node node : nodes) {
			String val = node.getVal();

			if (val.equals("S")) { // start
				startNode = node;
			} else if (val.equals("G")) { // goals
				goals.add(node);
				distanceMap.put(node, Integer.MAX_VALUE);
			}
		}

		// the start of the path search
		if (startNode != null && !goals.isEmpty()) {
			// set the start node and the available edges
			{
				// node
				pathCurrent.add(startNode);

				// edges
				ArrayList<Edge> outgoingEdges = startNode.getOutgoingEdges();
				ArrayList<Edge> outgoingEdgesCopy = new ArrayList<>(outgoingEdges);
				outgoingEdgesCopy.sort(comparatorEdge);
				availableEdges.add(outgoingEdgesCopy);
			}

			// continue processing as long as there are available nodes in the list
			while (true) {
				int size = pathCurrent.size();

				if (size == 0) {
					break;
				}

				// add space to steps
				if (!sequence.isEmpty()) {
					sequence += " ";
				}

				// calculate distance for nodes from the active list
				int curentDistance = 0;

				for (int i = 0; i < size - 1; i++) {
					Node source = pathCurrent.get(i);
					Node destination = pathCurrent.get(i + 1);
					ArrayList<Edge> edges = source.getOutgoingEdges();

					for (Edge edge : edges) {
						if (edge.getHead() == destination) {
							curentDistance += edge.getDist();
							break;
						}
					}
				}

				// visited nodes for active list
				HashSet<Node> visitedCurrent = new HashSet<>();

				for (int i = 0; i < size; i++) {
					Node node = pathCurrent.get(i);
					visitedCurrent.add(node);
				}

				// analyze the last node in the list
				int lastIndex = size - 1;
				Node lastNode = pathCurrent.get(lastIndex);

				// set visited for the last node
				visitedGlobal.add(lastNode);

				// check whether the last node is the goal
				if (goals.contains(lastNode)) {
					sequence += "[" + lastNode.getAbbrev() + " @ " + curentDistance + "]";

					// check whether the current distance is a better than the existing one
					int previousDistance = distanceMap.get(lastNode);

					if (curentDistance < previousDistance) {
						// update the distance and make a copy of the current path
						distanceMap.put(lastNode, curentDistance);
						pathMap.put(lastNode, new ArrayList<>(pathCurrent));
					}

					// check if the current distance is the best for all goals
					if (curentDistance < maiDistance) {
						maiDistance = curentDistance;
						nearestGoal = lastNode;
					}

					// delete the current node from list, since there is no need to go further for the goal node
					pathCurrent.remove(lastIndex);
					availableEdges.remove(lastIndex);
				} else {
					// add node to steps
					if (visitedGlobal.contains(lastNode)) {
						sequence += "(" + lastNode.getAbbrev() + ")";
					} else {
						sequence += lastNode.getAbbrev();
					}

					// choose a next node
					Node next = null;

					{
						ArrayList<Edge> nextEdges = availableEdges.get(lastIndex);
						Iterator<Edge> iterator = nextEdges.iterator();

						while (iterator.hasNext()) {
							Edge edge = iterator.next();

							// remove the edge from the list with the minimum distance
							iterator.remove();

							// choose a node for the next step only if the total distance to it is less than the minimum
							if (curentDistance + edge.getDist() < maiDistance) {
								next = edge.getHead();
								break;
							}
						}
					}

					// check whether the next node is found
					if (next != null) {
						// add the next node and edges to the active path
						pathCurrent.add(next);

						ArrayList<Edge> nextEdges = new ArrayList<>();
						ArrayList<Edge> outgoingEdges = next.getOutgoingEdges();

						for (Edge edge : outgoingEdges) {
							if (!visitedCurrent.contains(edge.getHead())) {
								nextEdges.add(edge);
							}
						}

						nextEdges.sort(comparatorEdge);
						availableEdges.add(nextEdges);
					} else {
						// delete the current node from which there are no longer steps to return to the previous one
						pathCurrent.remove(lastIndex);
						availableEdges.remove(lastIndex);
					}
				}
			}
		}

		// print results
		String path = "";

		if (nearestGoal != null) {
			path += "Shortest path is ";
			ArrayList<Node> list = pathMap.get(nearestGoal);

			for (Node node : list) {
				path += node.getAbbrev() + " ";
			}

			int distance = distanceMap.get(nearestGoal);
			path += "with dist " + distance + ".";
		} else {
			path = "Path not found.";
		}

		System.out.println(sequence);
		System.out.println(path);

		output.println(sequence);
		output.println(path);
	}

}


