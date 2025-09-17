public class Node {
    public Processo processo;
    public Node next;

    public Node(Processo p) {
        this.processo = p;
        this.next = null;
    }
}