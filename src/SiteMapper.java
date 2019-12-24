import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by User on 12 Дек., 2019
 */
public class SiteMapper extends RecursiveTask<Node> {
    final String exclude1 = "/";
    private String url;
    private int deep;
    private String baseDomen;
    private HashSet<String> hashSet;

    public SiteMapper(HashSet<String> hashSet, String baseDomen, String url, int deep) {
        this.url = url;
        this.deep = deep;
        this.baseDomen = baseDomen;
        this.hashSet = hashSet;
    }

    @Override
    protected Node compute() {
        Node node = new Node(url, deep);

        if (!hashSet.add(url)){
            return node;
        }

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.getMessage();
        }

        if (doc == null) {
            return null;
        }

//        System.out.println(url);
        Elements elements = doc.select("a");

        List<SiteMapper> taskList = new ArrayList<>();


        for (Element element : elements) {

            String href = element.attr("href");
            try {
                if (href != null && !href.equals(exclude1) && href != "") {
                    if (href.charAt(0) == '/') {
                        href = baseDomen + href.substring(1);
                    } else if (href.indexOf(url) == -1) {
                        continue;
                    }
                    SiteMapper task = new SiteMapper(hashSet, baseDomen, href, deep + 1);
                    task.fork();
                    taskList.add(task);

                }

            } catch (IllegalArgumentException e) {
                System.out.println(href);
                e.getStackTrace();
            }
        }

        if (taskList.size() > 0) {
            Node tmpNode;
            node.childNodes = new ArrayList<Node>();
            for (SiteMapper task : taskList) {
                tmpNode = task.join();
                if (tmpNode != null) {
                    node.childNodes.add(tmpNode);
                }
            }
        }

        return node;
    }
}
