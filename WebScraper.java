import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class WebScraper {
    public static void main(String[] args) {
        String url = "https://www.bbc.com";
        Document doc = Jsoup.connect(url).get(); // This line causes compile error

        System.out.println("Title: " + doc.title());

        System.out.println("h1: " + doc.select("h1").text());
        System.out.println("h2: " + doc.select("h2").text());
        System.out.println("h3: " + doc.select("h3").text());
        System.out.println("h4: " + doc.select("h4").text());
        System.out.println("h5: " + doc.select("h5").text());
        System.out.println("h6: " + doc.select("h6").text());

        Elements links = doc.select("a[href]");
        System.out.println("Link 1: " + links.get(0).attr("abs:href") + " - " + links.get(0).text());
        System.out.println("Link 2: " + links.get(1).attr("abs:href") + " - " + links.get(1).text());
        System.out.println("Link 3: " + links.get(2).attr("abs:href") + " - " + links.get(2).text());
    }
}
