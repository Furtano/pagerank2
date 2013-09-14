/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classen_Beleg_1;

/**
 *
 * @author chris
 */
import java.util.List;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Data;

@Data
public class Crawler extends WebCrawler {

    private final static String VISIT_PATTERN = ".*\\.html";
    public List<String> visitedPages = new ArrayList<String>();
    DataHelper dh;
  

    public List<String> getVisitedPages() {
        return visitedPages;
    }

    public Crawler() throws IOException {
     

        dh = new DataHelper();
    }

    public void start() {
        // System.out.println("HAllo");
    }

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        return url.getURL().toLowerCase().matches(VISIT_PATTERN);

    }

    

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        try {




            String url = page.getWebURL().getURL();
//            System.out.println("URL: " + url);

            if (page.getParseData() instanceof HtmlParseData) {
                HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                String text = htmlParseData.getText();
                String html = htmlParseData.getHtml();
                List<WebURL> links = htmlParseData.getOutgoingUrls();

                //			System.out.println("Text length: " + text.length());
                //			System.out.println("Html length: " + html.length());
                //			System.out.println("Number of outgoing links: " + links.size());
                //			System.out.println("HITTING URL: " + page.getWebURL());

                if (!url.equals("http://mysql12.f4.htw-berlin.de/crawl/")) {

                    dh.writeLink("+++++++" + url + "+++++++", true);

//                    System.out.println("URL::::" + url);

                    for (int i = 0; i < links.size(); i++) {

                        if (links.get(i).toString().contains("html")) {

                            if (i == links.size() - 1) {

                                dh.writeLink(links.get(i).toString(), true);

                            } else {
                                System.out.println("++++" + links.get(i).toString());

                                dh.writeLink(links.get(i).toString(), false);

                            }


                        }

                    }

                }


            }

        } catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}