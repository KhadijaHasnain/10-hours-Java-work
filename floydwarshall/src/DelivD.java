import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

// Class DelivD does the work for deliverable DelivD of the Prog340

public class DelivD {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	public DelivD( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		
		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
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

		//
		floydwarshall();
	}

	private void floydwarshall() {
		ArrayList<Node> nodeList = g.getNodeList();
		int nodeNumber = nodeList.size();
		int[][] distances = new int[nodeNumber][nodeNumber];
		Node[][] predecessors = new Node[nodeNumber][nodeNumber];

		// init distance with Infinity
		for (int i = 0; i < nodeNumber; i++) {
			for (int j = 0; j < nodeNumber; j++) {
				if (i != j) {
					distances[i][j] = Integer.MAX_VALUE;
				}
			}
		}

		// set distance & predecessor
		for (int i = 0; i < nodeNumber; i++) {
			Node sourceNode = nodeList.get(i);
			ArrayList<Edge> edgeList = sourceNode.getOutgoingEdges();

			for (Edge edge : edgeList) {
				Node destinationNode = edge.getHead();
				int distance = edge.getDist();

				for (int j = 0; j < nodeNumber; j++) {
					if (i != j && nodeList.get(j) == destinationNode) {
						distances[i][j] = distance;
						predecessors[i][j] = sourceNode;
					}
				}
			}
		}

		// Floyd–Warshall algorithm
		for (int k = 0; k < nodeNumber; k++) {
			// output
			if (nodeNumber < 8) {
				String currentIteration = "Iteration " + k + "\n" + distanceOutput(nodeList, distances) + "\n" + predecessorOutput(nodeList, predecessors) + "\n";
				System.out.print(currentIteration);
				output.print(currentIteration);
			}

			// determination of distances and predecessors
			for (int i = 0; i < nodeNumber; i++) {
				for (int j = 0; j < nodeNumber; j++) {
					int distanceIJ = distances[i][j];
					int distanceIK = distances[i][k];
					int distanceKJ = distances[k][j];
					boolean isInfinityIK = distanceIK == Integer.MAX_VALUE;
					boolean isInfinityKJ = distanceKJ == Integer.MAX_VALUE;

					if (!isInfinityIK && !isInfinityKJ && distanceIJ > distanceIK + distanceKJ) {
						distances[i][j] = distanceIK + distanceKJ;
						predecessors[i][j] = predecessors[k][j];
					}
				}
			}
		}

		// output
		String finalIteration = "Iteration " + nodeNumber + " (Final)\n" + distanceOutput(nodeList, distances) + "\n" + predecessorOutput(nodeList, predecessors) + "\n";
		System.out.print(finalIteration);
		output.print(finalIteration);

		// detect negative weight cycle
		for (int i = 0; i < nodeNumber; i++) {
			if (distances[i][i] < 0) {
				String s = "The graph has negative weight cycle\n";
				System.out.print(s);
				output.print(s);
				break;
			}
		}

		output.flush();
	}

	/*
		Helper method for outputting a table with distances by all graph nodes
	 */
	private String distanceOutput(ArrayList<Node> nodeList, int[][] distances) {
		String result = "";
		int nodeNumber = nodeList.size();

		// header
		result += "d";

		for (int i = 0; i < nodeNumber; i++) {
			Node node = nodeList.get(i);
			result += String.format("%4s", node.getAbbrev());
		}

		result += "\n";

		// table
		for (int i = 0; i < nodeNumber; i++) {
			Node node = nodeList.get(i);
			result += node.getAbbrev();

			for (int j = 0; j < nodeNumber; j++) {
				int distance = distances[i][j];
				boolean isInfinity = distance == Integer.MAX_VALUE;

				if (!isInfinity) {
					result += String.format("%4d", distance);
				} else {
					result += String.format("%4s", "~");
				}
			}

			result += "\n";
		}

		return result;
	}

	/*
		Helper method for outputting a table with predecessors for all graph nodes
	 */
	private String predecessorOutput(ArrayList<Node> nodeList, Node[][] predecessors) {
		String result = "";
		int nodeNumber = nodeList.size();

		// header
		result += "p";

		for (int i = 0; i < nodeNumber; i++) {
			Node node = nodeList.get(i);
			result += String.format("%4s", node.getAbbrev());
		}

		result += "\n";

		// table
		for (int i = 0; i < nodeNumber; i++) {
			Node sourceNode = nodeList.get(i);
			result += sourceNode.getAbbrev();

			for (int j = 0; j < nodeNumber; j++) {
				Node destinationNode = predecessors[i][j];

				if (destinationNode != null) {
					result += String.format("%4s", destinationNode.getAbbrev());
				} else {
					result += String.format("%4s", "~");
				}
			}

			result += "\n";
		}

		return result;
	}
}


