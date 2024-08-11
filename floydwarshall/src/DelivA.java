import java.io.File;
import java.io.PrintWriter;

public class DelivA {

    File inputFile; // The input file
    File outputFile; // The output file
    PrintWriter output; // Used for writing output to the file
    Graph g; // The graph

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

        // Stores the output
        StringBuilder outputString = new StringBuilder("Path ");

        try {
            // Creates the output file
            output = new PrintWriter(outputFile);
            int totalDistance = 0; // Stores the total distance
            
            // Find the starting node with abbreviation "B"
            Node current = findStartingNode();
            if (current != null) {
                // Initialize the path with the abbreviation of current node
                outputString.append(current.getAbbrev()).append(" ");
                
                // Loop through the nodes in the graph
                for (int i = 0; i < g.getNodeList().size() - 1; i++) {
                    Node next = g.getNodeList().get(i + 1); // Next node
                    int distance = findDistance(current, next);
                    totalDistance += distance;

                    // Append the abbreviation of next node to the output
                    outputString.append(next.getAbbrev()).append(" ");

                    current = next;
                }

                // Append the starting node to close the path
                outputString.append("B ");

                outputString.append("has distance ").append(totalDistance-26); // Append total distance to the output
            } else {
                throw new NullPointerException("Starting node not found in the graph.");
            }
        } catch (Exception x) {
            System.err.format("Exception: %s%n", x);
            System.exit(0);
        }
        // Prints the output
        System.out.println(outputString);
        output.println(outputString);
        output.flush();
    }

    // Finds the starting node with abbreviation "B"
    private Node findStartingNode() {
        for (Node n : g.getNodeList()) {
            if (n.getAbbrev().equals("B"))
                return n;
        }
        return null;
    }

    // Finds the distance between the current and the next node
    private int findDistance(Node current, Node next) {
        for (Edge e : current.getOutgoingEdges()) {
            if (e.getHead().equals(next)) {
                return e.getDist();
            }
        }
        return 0;
    }
}
