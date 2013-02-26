package ass.student.manotoma;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class Composite implements INode{
    
    private String dirName = null;
    private List<INode> nodes = new ArrayList<INode>();

    public Composite() {
    }

    public Composite(String dirName) {
        this.dirName = dirName;
    }

    public Composite(List<INode> leafs) {
        this.nodes = leafs;
    }

    public long size() {
        Long size = 0L;
        for (INode node : nodes) {
            size += node.size();
        }
        return size;
    }
    
    public void add(INode comp){
        nodes.add(comp);
    }

    public void add(List<INode> comps){
        nodes.addAll(comps);
    }

    @Override
    public String toString() {
        return "Composite{" + "dirName=" + dirName + ", size=" + nodes.size() + '}';
    }
    
}
