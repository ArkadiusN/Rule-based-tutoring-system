package main;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Node {
    //Variables related to main.Node
    Node leftN, rightN = null;
    static int spaces = 0;
    int data;
    ///List used to pass data in a format of a tree;
    ArrayList<String> treeFormatRuleList = new ArrayList<>();

    /** Constructor for new Nodes.
     * @param data data value for node.
     **/
    protected Node(int data) {
        this.data = data;
    }

    /**
     * Display the space and the tree branch as well as the node data.
     * @param prefix used to define the space and type of tree branch.
     * @param node a node that will be used to show the data.
     * @param isLeft to see if the node is left.
     * @return the list with newly added tree branch.
     */
    protected ArrayList<String> print(String prefix, Node node, boolean isLeft) {
        ArrayList<String> arr = new ArrayList<>();
        String str = "";
        if (node != null) {
            str = prefix + (isLeft ? "├── " : "└── ") + node.data;
            arr.add(str);
            treeFormatRuleList.add(arr.get(0));
            System.out.println("Item: " + 0 +  " " + arr.get(0));
            print(prefix + (isLeft ? "│   " : "    "), node.leftN, true);
            print(prefix + (isLeft ? "│   " : "    "), node.rightN, false);
        }
        return treeFormatRuleList;
    }

    /** Prints spacing character the amount of times
     * it is declared by number of spaces variable.
     * Gives an object-valued stream with the result
     * given by given function and prints it.
     **/
    protected void printSpacing(){
        IntStream.range(0, spaces)
                .mapToObj(i -> "  ")
                .forEach(System.out::print);
    }

    /** Prints spacing character the amount of times
     * it is declared by number of spaces variable.
     * Gives an object-valued stream with the result
     * given by given function and prints it.
     **/
    protected void printSpaceAndTreeBranch(){
        print("", this, false);
    }

    /** User enumeration to
     * display the nodes based on what they are.
     * @param ntype show type of the node
     **/
    protected void PrintNodeType(NodeType ntype){
        switch (ntype) {
            case LeftNode -> System.out.print("Left:");
            case RightNode -> System.out.print("Right:");
            case RootNode -> System.out.print("Root:");
        }
    }

    /**
     * Insert based on what the value is.
     * If node on the left/right already has a value
     * use recursive call to go deeper into correct place.
     * @param newN new main.Node that should be inserted to tree.
     **/
    protected void insert(Node newN){
        if (newN.data <= data) {
            if (leftN == null) {
                leftN = newN;
            } else {
                leftN.insert(newN);
            }
        } else {
            if (rightN == null) {
                rightN = newN;
            } else {
                rightN.insert(newN);
            }
        }
    }

    /** Depending on what the value is
     * we are looking for check right on left side
     * of the tree to determine if value exists inside.
     * Use ternary operator to decide where to recursively check.
     * @param root starting point to check if value is in tree
     * @param value value we are looking for inside a tree.
     **/
    protected Node isValIn(Node root, int value) {
        return root == null || root.data == value ? root : root.data < value ?
                isValIn(root.rightN, value) : isValIn(root.leftN, value);
    }

    /**
     * Pass the list with the tree formatted node.
     * @return the list of String tree*/
    protected ArrayList<String> printInTreeFormat (NodeType ntype){
        printSpaceAndTreeBranch();
        return treeFormatRuleList;
    }

    /**
     * Print the nodes with appropriate spacing and naming in pre order.
     * Should display as (B, A, C)  or (Root, Left, Right).
     * @param ntype type of main.Node.
     **/
    protected void printPreOrder (NodeType ntype){
        PrintNodeType(ntype);
        System.out.println(data);
        spaces+=1;
        if(leftN != null){
            printSpacing();
            leftN.printPreOrder(NodeType.LeftNode);
        }
        if(rightN != null){
            printSpacing();
            rightN.printPreOrder(NodeType.RightNode);
        }
        spaces-=1;
    }

    /**
     * Print the nodes with appropriate spacing and naming in post order.
     * Should display as (A, C, B)  or (Left, Right, Root).
     * @param ntype type of main.Node.
     **/
    protected void printPostOrder (NodeType ntype){
        spaces+=1;
        if(ntype == null){
            return;
        }
        if(leftN != null){
            leftN.printPostOrder(NodeType.LeftNode);
        }
        if(rightN != null){
            rightN.printPostOrder(NodeType.RightNode);
        }
        spaces-=1;
        printSpacing();
        PrintNodeType(ntype);
        System.out.println(data);
    }

    /** Find the minimum child of the right node,
     * going for the leftmost child.
     **/
    protected Node rightNodeMinChild()
    {
        Node current = rightN;
        if(current != null){
            while(current.leftN != null){
                current = current.leftN;
            }
        }
        return current;
    }

    /**
     * Remove the node that has specified value,
     * return it if it cannot be removed.
     **/
    protected Node remove(int x){
        if(data == x){
            //Replace node with the leftmost node of the right node.
            Node min = rightNodeMinChild();
            if(min != null){
                data = min.data;
                rightN = rightN.remove(min.data);
            }
            //If there is no replacement, take any left node
            //as a replacement.
            else{
                return leftN;
            }
        }
        //If 'x' is on the left then recursively call on left.
        else if(data > x && leftN != null){
            leftN = leftN.remove(x);
        }
        //If 'x' is on the right then recursively call on right.
        else if (data < x && rightN != null){
            rightN = rightN.remove(x);
        }
        else{
            System.out.printf("%d is not in tree.%n", x);
        }
        return this;
    }
}// End of Class
