import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DelivC {

    File inputFile;
    File outputFile;
    PrintWriter output;
    Graph g;
    String allGoalPaths = "";
    int minimumDist = Integer.MAX_VALUE;
    private static final Set<String> GOAL_NODE_VALUES = new HashSet<>(Arrays.asList("G", "P", "T", "V", "W", "j", "p", "t"));

    public DelivC(File in, Graph gr) {
        inputFile = in;
        g = gr;
        String inputFileName = inputFile.toString();
        String baseFileName = inputFileName.substring(0, inputFileName.length() - 4);
        String outputFileName = baseFileName.concat("_out.txt");
        outputFile = new File(outputFileName);
        if (outputFile.exists()) {
            outputFile.delete();
        }
        try {
            output = new PrintWriter(outputFile);
            DFS(findNode("S"), "", 0);
            output.println(allGoalPaths);
            output.println("Minimum Distance: " + minimumDist);
            output.flush();
        } catch (Exception x) {
            System.err.format("Exception: %s%n", x);
            System.exit(0);
        }
    }

    private void DFS(Node node, String path, int distance) {
        if (node == null || node.isVisited()) {
            return;
        }
        node.setVisited(true);

        path += " " + node.getName();

        if (isGoalNode(node)) {
            allGoalPaths += path + "\n";
            minimumDist = Math.min(minimumDist, distance);
        }

        for (Edge edge : node.getOutgoingEdges()) {
            Node nextNode = edge.getHead();
            if (!nextNode.isVisited()) {
                DFS(nextNode, path, distance + edge.getDist());
            }
        }

        node.setVisited(false); // Reset visited flag for backtracking
    }

    private Node findNode(String value) {
        for (Node n : g.getNodeList()) {
            if (n.getVal().equals(value))
                return n;
        }
        return null;
    }

    private boolean isGoalNode(Node node) {
        return GOAL_NODE_VALUES.contains(node.getVal());
    }

}
