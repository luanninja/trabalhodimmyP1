public class ListaDeProcessos {
    private Node head;
    private Node tail;
    private int size;

    public ListaDeProcessos() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() { return head == null; }
    public int size() { return size; }

    public void addEnd(Processo p) {
        Node novo = new Node(p);
        if (tail == null) {
            head = novo;
            tail = novo;
        } else {
            tail.next = novo;
            tail = novo;
        }
        size++;
    }

    public Processo removeFront() {
        if (head == null) return null;
        Node n = head;
        head = head.next;
        if (head == null) tail = null;
        size--;
        n.next = null;
        return n.processo;
    }

    public boolean removeById(int id) {
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            if (curr.processo.getId() == id) {
                if (prev == null) {
                    head = curr.next;
                    if (head == null) tail = null;
                } else {
                    prev.next = curr.next;
                    if (curr == tail) tail = prev;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public String listarProcessos() {
        StringBuilder sb = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            sb.append(curr.processo.toString());
            if (curr.next != null) sb.append(" -> ");
            curr = curr.next;
        }
        return sb.toString();
    }

    public Processo peekFront() {
        return head == null ? null : head.processo;
    }
}