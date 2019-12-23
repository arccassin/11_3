import java.io.File;
import java.io.FileWriter;

/**
 * Created by User on 17 Дек., 2019
 */
public class Document {
    private Node root;

    public Document(Node root) {
        this.root = root;
    }

    private void recursiveWriteNode(Appendable builder, Node node) {
        try {
            if (node == null)
                return;
            for (int i = 0; i < node.deep; i++) {
                builder.append("\t");
            }
            builder.append(node.value);
            builder.append('\n');
            if (node.childNodes != null) {
                for (Node childnode : node.childNodes) {
                    recursiveWriteNode(builder, childnode);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        recursiveWriteNode(builder, root);

        return builder.toString();
    }

    public void WriteToFile(String path) {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            recursiveWriteNode(fw, root);
            fw.flush();
            fw.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}

