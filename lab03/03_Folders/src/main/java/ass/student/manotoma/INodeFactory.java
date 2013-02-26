package ass.student.manotoma;

import java.io.File;

/**
 * User: Jan Stiborek, jan.stiborek@agents.felk.cvut.cz Date: 2/28/12 Time: 2:14
 * PM
 */
final public class INodeFactory {

    private INodeFactory() {
    }

    public static INode newInstance(File path) {
        if (!path.exists()) {
            System.err.println(String.format("Sorry bro, file with name [%s] was not found.", path.getAbsolutePath()));
            System.exit(0);
        }
        INode root = null;
        if (path.isFile()) {
            root = new Leaf(path);
        } else {
            root = buildTree(new Composite(path.getName()), path.listFiles());
        }
        System.out.println(String.format("Tree builded succesfully on input [%s].", path.getAbsolutePath()));
        return root;
    }

    private static Composite buildTree(Composite root, File[] files) {
        for (File file : files) {
            // is it leaf?
            if (file.isFile()) {
                root.add(new Leaf(file));
            } else {
                // or is it composite?
                Composite newRoot = new Composite(file.getName());
                root.add(newRoot);
                // do recursion..
                buildTree(newRoot, file.listFiles());
            }
        }
        return root;
    }
    
    public static void main(String[] args) {
        INode root = INodeFactory.newInstance(new File("src/test/resources/test-file"));
        System.out.println(root.size());
    }
}
