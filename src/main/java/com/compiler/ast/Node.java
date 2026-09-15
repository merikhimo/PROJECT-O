import java.util.ArrayList;
import java.util.List;

public class Node {
    public String type;
    public List<Node> children = new ArrayList<>();

    public Node(String type) {
        this.type = type;
    }

    public void addChild(Node child) {
        if (child != null) children.add(child);
    }
}
