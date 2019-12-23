
import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        String url = "https://skillbox.ru/";
//        String url = "http://lenta.ru/";

        HashSet<String> hashSet = new HashSet<>();
        Node node =  new ForkJoinPool().invoke(new SiteMapper(hashSet, url, url, 0));
        Document doc = new Document(node);
        doc.WriteToFile("D:\\mapperResult.txt");
        System.out.println(doc);
    }
}
