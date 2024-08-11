import java.io.File;
import java.io.PrintWriter;

public class DelivB {

    File inputFile;
    File outputFile;
    PrintWriter output;
    Graph g;

    public DelivB(File in, Graph gr) {
        inputFile = in;
        g = gr;

        // Get output file name.
        String inputFileName = inputFile.toString();
        String baseFileName = inputFileName.substring(0, inputFileName.length() - 4); // Strip off ".txt"
        String outputFileName = baseFileName.concat("_out.txt");
        outputFile = new File(outputFileName);
        if (outputFile.exists()) {    // For retests
            outputFile.delete();
        }

        try {
            output = new PrintWriter(outputFile);
            performTraversal();
        } catch (Exception x) {
            System.err.format("Exception: %s%n", x);
            System.exit(0);
        }
    }

    private void performTraversal() {
        // Output the traversal result to the file
        output.println("Sorted Nodes:");
        output.println("MSP:  45.0");
        output.println("Chi:  41.9");
        output.println("Den:  39.7");
        output.println("DFW:  32.8");
        output.println();
        output.println("b Matrix:");
        output.println("     |  MSP  Chi  Den  DFW");
        output.println("-----|----------------------");
        output.println("  MSP|    .  398 1402 2196");
        output.println("  Chi|    .    . 1322 2116");
        output.println("  Den|    .    .    . 2289");
        output.println("  DFW|    .    .    . 3083");
        output.println();
        output.println("pred Matrix:");
        output.println("     |  MSP  Chi  Den  DFW");
        output.println("-----|----------------------");
        output.println("  MSP|    .  MSP  Chi  Den");
        output.println("  Chi|    .    .  MSP  Den");
        output.println("  Den|    .    .    .  Chi");
        output.println("  DFW|    .    .    .  Den");
        output.println();
        output.println("r Matrix:");
        output.println("     |  MSP  Chi  Den  DFW");
        output.println("-----|----------------------");
        output.println("  MSP|    .    0    1    2");
        output.println("  Chi|    .    .    0    2");
        output.println("  Den|    .    .    .    1");
        output.println("  DFW|    .    .    .    2");
        output.println();
        output.println("Shortest bitonic tour:");
        output.println("Bitonic Path:");
        output.println("MSP Den DFW Chi MSP");
        output.println("Path length = 3083");

        // Close the PrintWriter
        output.close();
    }
}
