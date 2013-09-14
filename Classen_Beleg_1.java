/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classen_Beleg_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
/**
 *
 * @author chris
 */
public class Classen_Beleg_1 {
	private static Logger log;
	public static Indexer indexer;
	
	
    /**
     * @param args the command line arguments
     * @throws ParseException 
     */
	
	public Classen_Beleg_1 () {
		
	}
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
    	indexer = new Indexer("http://mysql12.f4.htw-berlin.de/crawl/");
        // TODO code application logic here

        Controller controller = new Controller();

        try {
            controller.start();
        } catch (Exception ex) {
            Logger.getLogger(Classen_Beleg_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    	System.out.println("Test");


        List<Node> nodeList = new ArrayList<Node>();




        List<Node> nodeList2 = PageRank.readNodesFromFile("//home//chris//workspace//Classen_Beleg_1//crawler123.txt");
        PageRank.getIncomingLinksFromNodeList(nodeList2);

        // Initialize all Nodes with Beginning PR
        PageRank.setInitialPageRank(nodeList2);
        
        System.out.println("d01\td02\td03\td04\td05\td06\td07\td08\t\n");
        System.out.print("Step 0:  ");
        for (Node singleNode : nodeList2) {
            singleNode.printOldPageRank();
            System.out.print("     ");
        }
        
        
        
        
       
        PageRank.calcPageRank(nodeList2);
               

       
        try {
			IndexerItem[] docs = indexer.search("tokens");
			
////			indexer.combinePagerankAndIndex(pr, docs, MyCrawler.nodes);
			for (IndexerItem iitem : docs) {
				System.out.println(iitem.getName() + ":" + iitem.getScore());
			}
			docs = indexer.search("index");
			for (IndexerItem iitem : docs) {
				System.out.println(iitem.getName() + ":" + iitem.getScore());
			}
			docs = indexer.search("classification");
			for (IndexerItem iitem : docs) {
				System.out.println(iitem.getName() + ":" + iitem.getScore());
			}
        }
			catch (IOException e )
			{
				e.printStackTrace();
			}
        
     System.out.println("HHH: " + Crawler.nodeContent.get(7));
        




    }
}
