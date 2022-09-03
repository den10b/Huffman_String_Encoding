public class Node {

    private final int amount;
    private Character value;
    private Node leftChild;
    private Node rightChild;

    public Node(Character value, int amount) {
        this.value = value;
        this.amount = amount;
    }

    public Node(Node leftChild, Node rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.amount = rightChild.amount + leftChild.amount;
    }

    public Character getChar() {
        return value;
    }

    public int getAmount() {
        return amount;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }
}
