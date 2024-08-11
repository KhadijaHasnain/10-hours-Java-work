

// Edge between two nodes
public class Edge {
    //The distance between the two points
    int dist;
    Node tail; //The head node
    Node head; //The tail node

    //Constructor creates an edge between two nodes
    public Edge(Node tailNode, Node headNode, int dist) {
        setDist(dist);
        setTail(tailNode);
        setHead(headNode);
    }

    //Gets the tail node
    public Node getTail() {
        return tail;
    }

    //Gets the head node
    public Node getHead() {
        return head;
    }

    //Gets the distance between the two nodes
    public int getDist() {
        return dist;
    }

    //Sets the tail node
    public void setTail(Node n) {
        tail = n;
    }

    //Sets the head node
    public void setHead(Node n) {
        head = n;
    }

    //Sets the distance between the two nodes
    public void setDist(int i) {
        dist = i;
    }
}
