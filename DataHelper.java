/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classen_Beleg_1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author chris
 */
public class DataHelper {
    FileWriter writer;
    
    public DataHelper () throws IOException{
       
    }
    public void writeLink (String link, Boolean isLastLink) throws IOException{
        link = link.replace("http://mysql12.f4.htw-berlin.de/crawl/", "");
        link = link.replace(".html", "");
        
         File file = new File("crawler.txt");
        FileWriter writer = new FileWriter(file ,true);
        
        if (!isLastLink){
            writer.write(link + ", ");
        }
        else {
            writer.write(link);
            writer.write(System.getProperty("line.separator"));

        }
        
        writer.flush();
        writer.close();
    }
}
