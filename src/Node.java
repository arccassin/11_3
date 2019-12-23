import java.util.ArrayList;

/**
 * Created by User on 13 Дек., 2019
 */
public class Node {
    public String value;
    public ArrayList<Node> childNodes;
    public int deep;

    public Node(String value, int deep) {
        this.value = value;
        this.deep = deep;
    }
}
