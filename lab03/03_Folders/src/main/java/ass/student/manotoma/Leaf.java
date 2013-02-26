package ass.student.manotoma;

import java.io.File;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class Leaf implements INode{
    
    private File file;

    public Leaf(File file) {
        this.file = file;
    }

    public long size() {
        return file.length();
    }

    @Override
    public String toString() {
        return "Leaf{" + "file=" + file + '}';
    }

}
