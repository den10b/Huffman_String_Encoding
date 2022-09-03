import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String S = in.nextLine();

        Map<Character, String> finmap = Huffman(S);
        StringBuilder finStr = new StringBuilder();
        for (Character character : S.toCharArray()) {
            finStr.append(finmap.get(character));
        }

        System.out.println(finmap.size() + " " + finStr.length());
        for (var entry : finmap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println(finStr);

    }

    public static void inTree(HashMap<Character, String> finCharList, Node currNode, String currCode) {
        if (currNode.getLeftChild() != null) {
            inTree(finCharList, currNode.getLeftChild(), currCode + "0");
            inTree(finCharList, currNode.getRightChild(), currCode + "1");
        } else finCharList.put(currNode.getChar(), currCode);
    }

    public static HashMap<Character, String> Huffman(String inputString) {
        HashMap<Character, Integer> charlist = new HashMap<>();
        for (Character myChar : inputString.toCharArray()) {
            if (charlist.containsKey(myChar)) {
                charlist.put(myChar, charlist.get(myChar) + 1);
            } else charlist.put(myChar, 1);

        }
        if (charlist.size() == 1) {
            HashMap<Character, String> finCharList = new HashMap<>();
            for (var entry : charlist.entrySet()) finCharList.put(entry.getKey(), "0");
            return finCharList;
        }
        Heap myLargeHeap = new Heap(charlist.size() + 1);
        for (var myChar : charlist.entrySet()) {
            myLargeHeap.insertNode(myChar.getKey(), myChar.getValue());
        }
        while (myLargeHeap.getCurrentSize() > 1) {
            Node leftMin = myLargeHeap.removeNode(0);
            Node rightMin = myLargeHeap.removeNode(0);
            myLargeHeap.insertParentNode(leftMin, rightMin);
        }
        HashMap<Character, String> finCharList = new HashMap<>();
        inTree(finCharList, myLargeHeap.removeNode(0), "");
        return finCharList;

    }

    public static class Node {

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

    public static class Heap {
        private final Node[] heapArray;
        private final int maxSize;
        private int currentSize;

        public Heap(int maxSize) {
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
}

