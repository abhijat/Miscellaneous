package Algorithms;

import java.io.*;
import static org.apache.commons.lang3.StringUtils.isAlpha;

public class BTree {

    public static final int ORDER = 7;
    private BTreeNode root;

    public BTree() {
        root = new BTreeNode();
        root.setLeaf(true);
        root.setNumKeys(0);
    }

    public BTreeNode getRoot() {
        return root;
    }

    public void insert(String key) {
        if (root.isFull()) {
            BTreeNode old = root;
            BTreeNode tmp = new BTreeNode();
            tmp.setLeaf(false);
            tmp.setNumKeys(0);
            tmp.setChild(old, 0);
            root = tmp;
            split(root, 0, old);
            insertNonFull(root, key);
        } else {
            insertNonFull(root, key);
        }
    }

    private void split(BTreeNode parent, int index, BTreeNode child) {
        BTreeNode newChild = new BTreeNode();
        newChild.setLeaf(child.isLeaf());
        newChild.setNumKeys(ORDER-1);

        // Copy the higher ORDER-1 keys into new child
        for (int i = 0; i < ORDER-1; i++) {
            newChild.setKey(
                    child.getKey(i+ORDER), i
            );
        }

        // Copy the higher ORDER children if needed
        if (!newChild.isLeaf()) {
            for (int i = 0; i < ORDER; i++) {
                newChild.setChild(
                        child.getChild(i + ORDER), i
                );
            }
        }

        child.setNumKeys(ORDER-1);
        for (int i = parent.getNumKeys(); i > index; i--) {
            parent.setChild(
                    parent.getChild(i), i+1
            );
        }
        parent.setChild(newChild, index+1);

        //ERROR POSSIBLE HERE
        for (int i = parent.getNumKeys()-1; i >= index; i--) {
            parent.setKey(
                    parent.getKey(i), i+1
            );
        }
        parent.setKey(child.getKey(ORDER-1), index);
        parent.setNumKeys(parent.getNumKeys() + 1);
    }

    private void insertNonFull(BTreeNode node, String key) {
        int i = node.getNumKeys()-1;
        if (node.isLeaf()) {
            while (i >= 0 && node.getKey(i).compareTo(key) > 0) {
                node.setKey(
                        node.getKey(i), i+1
                );
                i--;
            }
            node.setKey(key, i+1);
            node.setNumKeys(node.getNumKeys()+1);
        } else {
            while (i >= 0 && node.getKey(i).compareTo(key) > 0) {
                i--;
            }
            i++;
            BTreeNode child = node.getChild(i);
            if (child.isFull()) {
                split(node, i, child);
                if (node.getKey(i).compareTo(key) < 0) {
                    i++;
                }
            }
            insertNonFull(node.getChild(i), key);
        }
    }

    public boolean search(String key) {
        return search(root, key);
    }

    private boolean search(BTreeNode node, String key) {
        int i = 0;
        while (i < node.getNumKeys() && key.compareTo(node.getKey(i)) > 0) {
            i++;
        }
        if (i < node.getNumKeys() && key.equals(node.getKey(i))) {
            return true;
        }
        if (node.isLeaf()) {
            return false;
        }
        return search(node.getChild(i), key);
    }

    public void showTree(BTreeNode node, String sep) {
        System.out.println(sep + node);
        if (node.isLeaf()) {
            return;
        } else {
            for (BTreeNode child: node.getChildren()) {
                showTree(child, sep + "\t");
            }
        }
    }

    public static void main(String[] args) {
        BTree tree = new BTree();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(
                            new File("c:/Users/abhijat/Downloads/sample.txt")
                    )
            ));
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                for (String s: tmp.split("\\s+")) {
                    if (isAlpha(s) && !tree.search(s.toLowerCase())) {
                        tree.insert(s.toLowerCase());
                    }
                }
            }
        } catch (IOException ex) {}
        tree.showTree(tree.getRoot(), "");
    }
}
