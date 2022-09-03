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
        MinHeap myLargeHeap = new MinHeap(charlist.size() + 1);
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



}

