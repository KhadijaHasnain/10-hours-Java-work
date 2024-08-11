Node.java:

import java.util.*;

// A node of a graph for the Spring 2018 ICS 340 program

public class Node {

String name;

String val; // The value of the Node

String abbrev; // The abbreviation for the Node

ArrayList<Edge> outgoingEdges;

ArrayList<Edge> incomingEdges;

public Node( String theAbbrev ) {

setAbbrev( theAbbrev );

val = null;

name = null;

outgoingEdges = new ArrayList<Edge>();

incomingEdges = new ArrayList<Edge>();

}

public String getAbbrev() {

return abbrev;

}

public String getName() {

return name;

}

public String getVal() {

return val;

}

public ArrayList<Edge> getOutgoingEdges() {

return outgoingEdges;

}

public ArrayList<Edge> getIncomingEdges() {

return incomingEdges;

}

public void setAbbrev( String theAbbrev ) {

abbrev = theAbbrev;

}

public void setName( String theName ) {

name = theName;

}

public void setVal( String theVal ) {

val = theVal;

}

public void addOutgoingEdge( Edge e ) {

outgoingEdges.add( e );

}

public void addIncomingEdge( Edge e ) {

incomingEdges.add( e );

}

// Add toString() method for debug.

@Override

public String toString() {

return "Node [name=" + name + ", val=" + val + ", abbrev=" + abbrev + " outgoingEdges size="

+ outgoingEdges.size() + " incomingEdges size=" + incomingEdges.size() + "]";

}

}

Graph.java:

import java.util.*;

public class Graph {

ArrayList<Node> nodeList;

ArrayList<Edge> edgeList;

public Graph() {

nodeList = new ArrayList<Node>();

edgeList = new ArrayList<Edge>();

}

public ArrayList<Node> getNodeList() {

return nodeList;

}

public ArrayList<Edge> getEdgeList() {

return edgeList;

}

public void addNode( Node n ) {

nodeList.add(n);

}

public void addEdge( Edge e ) {

edgeList.add(e);

}

}

Prog340.java:

import javax.swing.*;

import java.io.*;

import java.util.*;

import java.awt.*;

import java.awt.event.*;

/** ProgramA simply reads a file containing rows of space-separated Strings,

** your assignment is to print out those strings interpreted as a graph.

**/

public class Prog340 extends JPanel implements ActionListener {

private static final long serialVersionUID = 1L; // Keep Eclipse happy.

File inputFile;

File outputFile;

PrintWriter output;

JFileChooser fileChooser;

Graph g;

String[] comboBoxList; // For putting names in Combo Box

/** The main method instantiates a Prog340 class, which includes giving you a choice of things to do.

** The only one active now will be reading the graph file and having you parse it.

**

** @param args

** - Not used

**

** @throws FileNotFoundException

** - Thrown if the file selected is not found. Shouldn't happen with a FileChooser.

**/

public static void main(String[] args) throws FileNotFoundException {

javax.swing.SwingUtilities.invokeLater(new Runnable() {

public void run() {

createAndShowGUI();

}

});

}

/** Create and show the GUI.

** For thread safety, this method should be invoked from the event-dispatching thread.

**/

private static void createAndShowGUI() {

// Create and set up the window

JFrame frame = new JFrame("Prog340");

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Create and set up the content pane.

JComponent newContentPane = new Prog340();

newContentPane.setOpaque(true);; // content panes must be opaque

frame.setContentPane(newContentPane);;

// Display the window.

frame.pack();

frame.setVisible(true);

}

/** The constructor creates a new ProgramA object, and sets up the input and output files.

**/

public Prog340() {

super( new BorderLayout() );

try {

// I like the colorful FileChooser.

UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

System.out.println( "Look and feel set.");

}

catch (Exception e) { // exit on exception.

System.err.format("Exception: %s%n", e);

System.exit(0);

}

fileChooser = new JFileChooser();

fileChooser.setDialogTitle("Choose a file");

// Start looking for files at the currect directory, not home.

fileChooser.setCurrentDirectory(new File("."));

inputFile = null;

g = new Graph();

// Select the action

comboBoxList = new String[5];

comboBoxList[0] = new String("prog340: Select file and read graph");

comboBoxList[1] = new String("Deliv A");

comboBoxList[2] = new String("Deliv B");

comboBoxList[3] = new String("Deliv C");

comboBoxList[4] = new String("exit");

JComboBox<String> actionList = new JComboBox<String>( comboBoxList );

actionList.setName("Action List");

actionList.setSelectedIndex(0);

actionList.addActionListener( this );

add( actionList, BorderLayout.PAGE_START );

}

// Listen to the Combo Box

public void actionPerformed( ActionEvent e ) {

JComboBox cb = (JComboBox)e.getSource();

int actionIndex = cb.getSelectedIndex();

switch( actionIndex ) {

case 0:

g = new Graph();

readGraphInfo( g );

break;

case 1:

DelivA dA = new DelivA( inputFile, g );

break;

case 2:

DelivB dB = new DelivB( inputFile, g );

break;

case 3:

DelivC dC = new DelivC( inputFile, g );

break;

case 4:

System.out.println( "Goodbye");

System.exit(0);

default:

System.out.println( "Invalid choice" );

System.exit(0);

}

}

/** Read the file containing the Strings, line by line, then process each line as it is read.

**/

public void readGraphInfo( Graph g ) {

try {

if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

// Instantiate the selected input and output files.

inputFile = fileChooser.getSelectedFile();

System.out.println( "Chosen file = " + inputFile + "\n");

}

// read text file

Scanner sc = new Scanner( inputFile );

// First line special: It contains "~", and "val", and the nodes with the edges.

String firstLine = sc.nextLine();

String[] splitString = firstLine.split( " +" );

// Ignore first two fields of first line, Every other field is a node.

for ( int i = 2; i < splitString.length; i++ ) {

Node n = new Node( splitString[i] );

g.addNode( n );

}

// Every other line gives the name and value of the Node, and any edges.

int nodeIndex = 0;

ArrayList<Node> nodeList = g.getNodeList();

while ( sc.hasNextLine() ) {

String nextLine = sc.nextLine();

splitString = nextLine.split(" +");

Node n = nodeList.get( nodeIndex );

n.setName( splitString[0] );

n.setVal( splitString[1] );

for ( int i = 2; i < splitString.length; i++ ) {

if ( !splitString[i].equals("~") ) {

Node head = nodeList.get(i-2);

Edge e = new Edge( n, head, splitString[i] );

g.addEdge( e );

n.addOutgoingEdge( e );

head.addIncomingEdge( e );

}

}

nodeIndex++;

}

sc.close();

}

catch (Exception x) {

System.err.format("ExceptionOuter: %s%n", x);

}

}

}