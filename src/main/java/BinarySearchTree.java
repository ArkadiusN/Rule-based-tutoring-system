import java.util.ArrayList;

public class BinarySearchTree {
    // Variables
    Node root;
    //List used to pass data in a format of a tree;
    ArrayList<String> treeFormatRuleList = new ArrayList<>();

    // Helper method
    protected void resultEmptyTree() {
        System.out.println("\nEmpty tree.");
    }

    /**
     * Add a new value to BST
     * @param val vale to be inserted.
     **/
    protected void insert(int val) {
        Node newNode = new Node(val);
        if (root == null) {
            root = newNode;
        } else {
            root.insert(newNode);
        }
    }

    /**
     * If there exist a root in the tree save in
     * the list for a tree format.
     * @return the list of the rules in the format of a tree.
     */
    protected ArrayList<String> inOrder() {
        System.out.println();
        if (root != null) {
            treeFormatRuleList.addAll(root.printInTreeFormat(NodeType.RootNode));
        } else {
            resultEmptyTree();
        }
        System.out.println();
        return treeFormatRuleList;
    }

    /**
     * Show binary tree components in pre-order form.
     **/
    protected void preOrder() {
        System.out.println();
        if (root != null) {
            root.printPreOrder(NodeType.RootNode);
        } else {
            resultEmptyTree();
        }
        System.out.println();
    }

    /**
     * Show binary tree components in post-order form.
     **/
    protected void postOrder() {
        System.out.println();
        if (root != null) {
            root.printPostOrder(NodeType.RootNode);
        } else {
            resultEmptyTree();
        }
        System.out.println();
    }

    /**
     * Remove node with 'val' from BST.
     **/
    protected void remove(int val) {
        if (root == null) {
            resultEmptyTree();
        } else {
            root.remove(val);
        }
        System.out.println();
    }

    /**
     * Search if node with 'val' exists in BST.
     **/
    protected boolean search(int val) {
        if (root == null) {
            resultEmptyTree();
            return false;
        } else {
            //Keep on checking depending on what the 'val' is.
            return root.isValIn(root, val) != null;
        }
    }
}// End of Class
