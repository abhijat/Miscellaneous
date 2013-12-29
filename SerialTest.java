package JavaIO;

import EssentialAlgorithms.BtreeNode;

import java.io.*;
import java.util.Arrays;

public class SerialTest {

    private static final int padding = 1400;
    public static long insert(Object o, RandomAccessFile file) throws IOException {
        long len = file.length();
        file.seek(len);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput oos = new ObjectOutputStream(bos);
        oos.writeObject(o);
        byte[] b = bos.toByteArray();
        oos.close();
        file.writeInt(b.length);
        file.write(b);
        byte[] zeros = new byte[1400-b.length];
        Arrays.fill(zeros, (byte) 0);
        file.write(b);
        return len;
    }

    public static void update(Object o, RandomAccessFile file,
                              long offset) throws IOException {
        file.seek(offset);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput oos = new ObjectOutputStream(bos);
        oos.writeObject(o);
        byte[] b = bos.toByteArray();
        oos.close();
        file.writeInt(b.length);
        file.write(b);
        return;
    }

    public static Object get(long position, RandomAccessFile file)
            throws IOException {
        file.seek(position);
        int size = file.readInt();
        System.out.println("The object found is "
                + size + " bytes long");
        byte[] bytes = new byte[size];
        file.readFully(bytes);
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        ObjectInput in = new ObjectInputStream(bin);
        Object o = null;
        try {
            o = in.readObject();
        } catch (ClassNotFoundException ex) {}
        return o;
    }

    public static long[] testInsert(RandomAccessFile file)
            throws IOException {
        BtreeNode node1 = new BtreeNode();
        node1.setKey("a", 0);
        node1.setNumKeys(1);
        BtreeNode node2 = new BtreeNode();
        node2.setKey("b", 0);
        node2.setNumKeys(1);
        BtreeNode node3 = new BtreeNode();
        node3.setKey("c", 0);
        node3.setNumKeys(1);
        long pos = insert(node1, file);
        long pos2 = insert(node2, file);
        long pos3 = insert(node3, file);
        System.out.println(pos);
        System.out.println(pos2);
        System.out.println(pos3);
        long[] positions = {pos, pos2, pos3};
        return positions;
    }

    public static void testGet(RandomAccessFile file)
            throws IOException {
        BtreeNode b1 = (BtreeNode) get(0, file);
        System.out.println(b1);
        BtreeNode b2 = (BtreeNode) get(514, file);
        System.out.println(b2);
        BtreeNode b3 = (BtreeNode) get(1028, file);
        System.out.println(b3);
    }

    public static void testUpdate(RandomAccessFile file)
            throws IOException{
        BtreeNode b2 = (BtreeNode) get(1028, file);
        b2.setKey("I shall not stand for this boohaki", 1);
        b2.setNumKeys(2);
        update(b2, file, 1028);
        //System.out.println(b1);
        //BtreeNode b2 = (BtreeNode) get(514, file);
        //System.out.println(b2);
    }

    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile(
                new File("/Users/abhijat/tmp/dbfile"), "rw"
        );
        //testInsert(file);
        //testUpdate(file);
        testGet(file);
    }
}
