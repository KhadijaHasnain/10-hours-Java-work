import java.io.File;
import java.io.PrintWriter;
import java.util.stream.StreamSupport;

// Class DelivA does the work for deliverable DelivA of the Prog340
public class DelivA {

	File inputFile; //The input file
	File outputFile; //The output file
	PrintWriter output; //Used for writing output to t he file
	Graph g; //The graph

	public DelivA(File in, Graph gr) {
		inputFile = in;
		g = gr;

		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring(0, inputFileName.length() - 4); // Strip off ".txt"
		String outputFileName = baseFileName.concat("_out.txt");
		outputFile = new File(outputFileName);
		if (outputFile.exists()) { // For retests
			outputFile.delete();
		}

		//Stores the output
		String outputString = "Path ";

		try {
			//Creates the output file
			output = new PrintWriter(outputFile); 
			int totalDistance = 0; //Stores the total distance
			//Calculates the total distance and the path
			Node current = findNode("S");
			for (int i = 0; i < g.getNodeList().size(); i++) {

				Node next = g.getNodeList().get(i);

				int distance = findDistance(current, next);
				totalDistance += distance;

				outputString += current.getAbbrev() + " ";

				if (i == g.getNodeList().size() - 1) {
					outputString += next.getAbbrev() + " ";
				}
				current = next;
				//*/
			}

			outputString += "has distance " + totalDistance;//*/

		} catch (Exception x) {
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		//Prints the output
		System.out.println(outputString);
		output.println(outputString);
		output.flush();
	}
	
	//Finds the distance between the current and the next node
	private int findDistance(Node current, Node next) {
		for (Edge e : current.getOutgoingEdges()) {
			if (e.getHead().equals(next)) {
				return e.getDist();
			}
		}
		return 0;
	}

	//Finds the node that has the matching value
	private Node findNode(String value) {
		for (Node n : g.getNodeList()) {
			if(n.getVal().equals(value))
				return n;
		}
		return null;
	}

}
