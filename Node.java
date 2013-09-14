/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classen_Beleg_1;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author chris
 */
@Data
public class Node {

	List<String> incomingLinks;
	List<String> outgoingLinks;
	String name;
	double pageRank;
	double oldPageRank;
	// pi (Zwischenschritt)
	double WSK;
	
	String content;
	List<Double> transitionProbability;

	public Node() {
		incomingLinks = new ArrayList<String>();
		outgoingLinks = new ArrayList<String>();
		// Refers to incoming Links, starts at index 0
		transitionProbability = new ArrayList<Double>();
	}

	/***
	 * Adds a outgoing Link to List, checks if Link is already in List and if
	 * then do not add.
	 * 
	 * @param link
	 *            outgoing link
	 */
	public void addOutgoingLink(String link) {
		// Check if Node is already existent
		Boolean linkAlreadyExistent = false;
		for (String linkA : outgoingLinks) {

			if (linkA.equals(link)) {
				linkAlreadyExistent = true;
				break;
			}
		}

		if (!linkAlreadyExistent) {
//			System.out.println("ADD OUTG LINK " + link + " @ " + name);
			outgoingLinks.add(link);
		}
	}

	/***
	 * Adds a incoming Link to List, checks if Link is already in List and if
	 * then do not add.
	 * 
	 * @param link
	 *            incoming link
	 */
	public void addIncomingLink(String link) {
		// Check if Node is already existent
		Boolean linkAlreadyExistent = false;
		for (String linkA : incomingLinks) {

			if (linkA.equals(link)) {
				linkAlreadyExistent = true;
				break;
			}
		}

		if (!linkAlreadyExistent) {
//			System.out.println("ADD INC LINK " + link + " @ " + name);
			incomingLinks.add(link);
		}
	}

	/***
	 * Prints the links for debugging purpose
	 */
	public void printLinks() {
//		System.out.println("NODE:::::::: " + name + " ::::::::::::");
//		System.out.println("Outgoing Links");
		for (String link : outgoingLinks) {
//			System.out.println(link);
		}

//		System.out.println("IncomingLinks Links");
		int i = 0;
		for (String link2 : incomingLinks) {

//			System.out.println(link2);
//			System.out.println("INDEX::: " + i);
//			System.out.println(link2 + "TP: " + transitionProbability.get(i));
			i++;
		}
	}

	public void printPageRank() {
		
		System.out.print((Math.round(pageRank * 10000) / 10000.0) + "     ");

	}

	public void printOldPageRank() {
		System.out.print(Math.round(oldPageRank * 10000) / 10000.0 + "0");

	}

	/**
	 * Print
	 */
	public void printWSK() {
		System.out.print(Math.round(WSK * 1000) / 1000.0);
		// System.out.print(WSK);
	}

	/**
	 * Adds transition probability to the list
	 * 
	 * @param probability
	 *            single transition probability
	 */
	public void addTransitionProbability(int index, double probability) {
		transitionProbability.add(index, probability);
	}
	
	public void addNode(){
		
	}

}
