import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class ScrapeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer count = (Integer) session.getAttribute("visitCount");
        if (count == null) count = 0;
        session.setAttribute("visitCount", ++count);

        String url = request.getParameter("url");
        String[] options = request.getParameterValues("option");

        Document doc = Jsoup.connect(url).get();
        List<String> results = new ArrayList<>();

        if (options != null) {
            for (String opt : options) {
                if (opt.equals("title")) {
                    results.add("Title: " + doc.title());
                } else if (opt.equals("links")) {
                    Elements links = doc.select("a[href]");
                    for (int i = 0; i < Math.min(5, links.size()); i++) {
                        results.add("Link: " + links.get(i).attr("abs:href"));
                    }
                } else if (opt.equals("images")) {
                    Elements imgs = doc.select("img[src]");
                    for (int i = 0; i < Math.min(3, imgs.size()); i++) {
                        results.add("Image: " + imgs.get(i).attr("abs:src"));
                    }
                }
            }
        }

        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h3>You have visited this page " + count + " times.</h3>");
        response.getWriter().println("<table border='1'>");
        for (String r : results) {
            response.getWriter().println("<tr><td>" + r + "</td></tr>");
        }
        response.getWriter().println("</table>");
        response.getWriter().println("<form method='get' action='DownloadCSVServlet'><button>Download as CSV</button></form>");
        response.getWriter().println("</body></html>");
    }
}
