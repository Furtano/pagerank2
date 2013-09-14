/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classen_Beleg_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author chris
 */
@Data
public class PageRank {

    // Factors
    public static double daempfungsfaktor = 0.95;
    public static double teleportationsrate = 0.05;
    // break value
    public static double delta = 0.04;

    /**
     * *
     * Sets once the Initial Page Rank of the nodes in the List to 1/N
     *
     * @param nodeList List of nodes which are important
     */
    public static void setInitialPageRank(List<Node> nodeList) {

        double initialRank = 1 / Double.valueOf(nodeList.size());
        for (Node node : nodeList) {
            // initial page Rank =  1/N
            System.out.println(initialRank);
            node.setOldPageRank(initialRank);
        }
    }

    /**
     * *
     * Parses the Incoming Links for all Nodes into a Node Structure
     *
     * @param nodes List of Links
     */
    public static void getIncomingLinksFromNodeList(List<Node> nodes) {

        // Go through every node and look what are the Incoming Links
        int i = 0;
        for (Node searchedNode : nodes) {
            int z = 0;
            for (Node actualNode : nodes) {


                List<String> links = actualNode.getOutgoingLinks();

//                System.out.println("LINKS @ NODE " + actualNode.getName());

                for (String a : links) {
//                    System.out.println("@@@ " + a + " @@@");
                }

                for (String link : links) {
                    // If Link Node Name equals the Outgoing Link in another Node
                    //MAYBE?: AND Link doesnt Link itself (!node2.getName().equals(link))
                    if (searchedNode.getName().equals(link)) {
//                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!S-Node " + searchedNode.getName() + " A-Node " + actualNode.getName() + " GET-LINK: " + link);
                        nodes.get(i).addIncomingLink(actualNode.getName());
                        // Link is needed only one times in the node
                        break;
                    }
                }


                z++;
            }
            i++;
        }
    }

    /**
     * *
     * Reads the Nodes which where crawled before by crawler4j into a Node
     * Structure.
     *
     * @param filename
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<Node> readNodesFromFile(String filename) throws FileNotFoundException, IOException {
        List<Node> nodeList = new ArrayList();
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        Node newNode;
        int i = 0;
        do {
            line = br.readLine();
            System.out.println(line);
            if (line != null) {




                // New Node!
                if (line.contains("+")) {

//                    System.out.println(">>>>>>>> " + line + "<<<<<<<<");
                    newNode = new Node();
                    newNode.setName(line.replace("+++++++", ""));
                    Boolean nodeAlreadyExistent = false;
                    for (Node node : nodeList) {
                        if (node.getName().equals(line.replace("+++++++", ""))) {
                            nodeAlreadyExistent = true;
                            break;
                        }
                    }
                    // Node doesnt Exist, CREATE ONE
                    if (!nodeAlreadyExistent) {
//                        System.out.println("NEW NODE");
                        nodeList.add(newNode);

                    }
                    i++;

                } // New Link
                else {

                    String tokens[] = line.split(", ");
                    for (String token : tokens) {
//                        System.out.println("token: " + token);
                        Boolean nodeAlreadyExistent = false;

                        // Check if Node is already existent
                        for (Node node : nodeList) {
                            if (node.getName().equals(token)) {
                                nodeAlreadyExistent = true;
                                break;
                            }
                        }

                        // Link is not already a Node
                        if (!nodeAlreadyExistent) {

                            // Add as new Node
                            Node link = new Node();
                            link.setName(token);
                            nodeList.add(link);

                            // Add Link
//                            System.out.println("NEW NODE -> NEW LINK" + token);
                            nodeList.get(i - 1).addOutgoingLink(token);
                        } // Link is already a Node
                        else {
                            // Add Link
//                            System.out.println("NO NEW NODE -> NEW LINK" + token);
                            nodeList.get(i - 1).addOutgoingLink(token);
                        }

                    }

                }
            }
        } while (line != null);

        return nodeList;
    }

    /**
     * *
     * Calculates the transition prohability for every incoming Link in the node
     * List. Focus on Inllink because Pages without Inlinks we dont are
     * interistet in.
     *
     * In PR-Algorithm this method is only called 1x!
     *
     * @param nodeList List of Webpages
     */
    public static void calcTransitionProhability(List<Node> nodeList) {

        for (Node node : nodeList) {
            double transitionProhability = 0;
            // Node with Outlinks
            List<String> outlinks = node.getOutgoingLinks();
            List<String> inlinks = node.getIncomingLinks();
            int index = 0;
            for (String link : inlinks) {

                if (outlinks.size() > 0) {
//                    System.out.println("DEBUG: NORMAL");
                    transitionProhability = 1f / outlinks.size();
                } // Without Outlinks (Dangeling)
                else {
//                    System.out.println("DEBUG: DANGLING");
                    transitionProhability = 1f / nodeList.size();
                }
//                System.out.println("DEBUG: TR" + transitionProhability + "@" + node.getName() + "<-" + link);
                node.addTransitionProbability(index, transitionProhability);
                index++;
            }



        }

    }

    /**
     * Calcs the pi (Wahrscheinlichkeit) for every single Node. This is a
     * Ziwschenschritt.
     *
     * @param nodeList
     */
    public static void calcWSK(List<Node> nodeList) {
        int i = 0;
        // GO THROUGH EVERY NODE (MAKE CALC OF EVERY NODE)!
        for (Node node : nodeList) {

            double WSK = 0;


            // INC. LINKS OF NODE
            List<String> incomingLinks = node.getIncomingLinks();

            int z = 0;
            // O N L Y !!! EVERY NODE WHICH IS IMPORTANT
            for (String link : incomingLinks) {
                // FIND THE NODE WHICH THROWS THE OUTLINK [[INLINK SEARCH]]
                for (Node n : nodeList) {
                    if (n.getName().equals(link)) {
                        // SELF LINK
//                        System.out.println("Node " + node.getName() + " FOR " + n.getName() + "NPR: " + n.getPageRank() + " TR" + node.getTransitionProbability().get(z));
                        WSK += n.getPageRank() * node.getTransitionProbability().get(z);
                    }
                }


                z++;
            }
//            System.out.println("WWSSKK: " + WSK);
            // SET WSK for NODE
            node.setPageRank(WSK);
            i++;
        }

    }

    public static void calcPageRank(List<Node> nodeList) {
        Boolean pageRankIsFinish = false;
        int step = 1;
        do {
//            System.out.println("RUNNNNN");
            // Calc PR for Node 1 to X
            for (Node node : nodeList) {

            	//                System.out.println("CALC PR FOR NODE: " + node.getName());
                // green on Lecture!
                double sum1 = 0f;
                // yellow on Lecture!
                double sum2 = 0f;
                List<String> incomingLinks = node.getIncomingLinks();
                List<String> outgoingLinks = node.getOutgoingLinks();


                // CALC Sum 1
                // CALC SUM 2
                int z = 0;
                for (String link : incomingLinks) {
                    for (Node node3 : nodeList) {
                    	double hehe = node3.getOutgoingLinks().size();
                        if (node3.getName() == link) {
//                            System.out.println("INC " + link);
                            sum1 += node3.getOldPageRank() / (hehe * 1.00);
//                            System.out.println("[SUM1] Node: " + node3.getName() + " OLD: " + node3.getOldPageRank() + " SIZE: " + outgoingLinks.size());
                        }
                    }
                }
                // CALC SUM 2
                for (Node node2 : nodeList) {

                    // DEAD END !!!
                    if (node2.getOutgoingLinks().isEmpty()) {
                        sum2 += node2.getOldPageRank() / (nodeList.size() * 1.00);

//                        System.out.println("[SUM 2] Node: " + node2.getName() + " WSK: " + node2.getOldPageRank() + " SIZE: " + nodeList.size());
                    }

                }

                node.setPageRank(daempfungsfaktor * (sum1 + sum2) + (teleportationsrate / nodeList.size()* 1.00));
//                System.out.println("----------------");
//                System.out.println("Node::: " + node.getName() + "PR: " + node.getOldPageRank());
//                System.out.println("----------------");

            }

            /**
             * BREAK RULE:
             */
            double calc = 0f;
            for (Node check : nodeList) {
                calc += Math.abs(check.getPageRank() - check.getOldPageRank());
//                System.out.println(check.getPageRank() + " - " + check.getOldPageRank() + "=" + "OMEGA: " + calc);
                
            }
            
            if ( calc <= delta){
                pageRankIsFinish = true;
            }
            System.out.println("");
            System.out.print("Step " + step + ":  ");
            for (Node node : nodeList) {
            	node.setOldPageRank(node.getPageRank());
                
                node.printPageRank();

            }


            step++;
        } while (!pageRankIsFinish);
        
        
    }
}
