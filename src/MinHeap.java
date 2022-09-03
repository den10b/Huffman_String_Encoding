public class MinHeap {
    private final Node[] heapArray;
    private final int maxSize;
    private int currentSize;

    public MinHeap(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        heapArray = new Node[maxSize];
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void insertNode(Character value, int amount) {
        if (currentSize == maxSize) return;
        Node newNode = new Node(value, amount);
        heapArray[currentSize] = newNode;
        displaceUp(currentSize++);
    }

    public void insertParentNode(Node leftChild, Node rightChild) {
        if (currentSize == maxSize) return;

        Node newNode = new Node(leftChild, rightChild);
        heapArray[currentSize] = newNode;
        displaceUp(currentSize++);
    }

    public Node removeNode(int index) {
        if (index >= 0 && currentSize > index) {
            Node root = heapArray[index];
            heapArray[index] = heapArray[--currentSize];
            heapArray[currentSize] = null;
            displaceDown(index);
            return root;
        }
        return null;
    }

    private void displaceUp(int index) {
        int parentIndex = (index - 1) / 2;
        Node bottom = heapArray[index];
        while (index > 0 && heapArray[parentIndex].getAmount() > bottom.getAmount()) {
            heapArray[index] = heapArray[parentIndex];
            index = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
        }
        heapArray[index] = bottom;
    }

    private void displaceDown(int index) {
        int smallerChild;
        Node top = heapArray[index];
        while (index < currentSize / 2) {
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;

            if (rightChild < currentSize && heapArray[leftChild].getAmount() > heapArray[rightChild].getAmount()) {
                smallerChild = rightChild;
            } else smallerChild = leftChild;

            if (top.getAmount() <= heapArray[smallerChild].getAmount()) break;

            heapArray[index] = heapArray[smallerChild];
            index = smallerChild;
        }
        heapArray[index] = top;
    }


}
