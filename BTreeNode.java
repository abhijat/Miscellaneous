package Algorithms;

public class BTreeNode {

    private boolean leaf;
    private String[] keys;
    private BTreeNode[] children;
    private int numKeys;

    public BTreeNode() {
        leaf = false;
        keys = new String[2*BTree.ORDER - 1];
        children = new BTreeNode[2*BTree.ORDER];
        numKeys = 0;
    }

    public void setKey(String key, int index) {
        if (index >= keys.length) {
            System.out.println("Attempt to add key past array boundary: "
                    + key);
        } else {
            keys[index] = key;
        }
    }

    public String getKey(int index) {
        return keys[index];
    }

    public String[] getKeys() {
        String[] keysCopy = new String[getNumKeys()];
        System.arraycopy(
                keys, 0, keysCopy, 0, keysCopy.length
        );
        return keysCopy;
    }

    public void setChild(BTreeNode node, int index) {
        if (index >= children.length) {
            System.out.println("Attempt to add child past array boundary: "
                    + node.hashCode());
        } else {
            children[index] = node;
        }
    }

    public BTreeNode getChild(int index) {
        if (index > numKeys) {
            System.out.println("Attempt to access child: "
                    + index + " when child size is " + (numKeys+1));
        }
        return children[index];
    }

    public BTreeNode[] getChildren() {
        BTreeNode[] childrenCopy = new BTreeNode[getNumKeys()+1];
        System.arraycopy(
                children, 0, childrenCopy, 0, childrenCopy.length
        );
        return childrenCopy;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }

    public boolean isFull() {
        return numKeys == keys.length;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("Keys: ");
        for (String key: getKeys()) {
            repr.append(key).append(" . ");
        }
        return repr.toString();
    }

}
