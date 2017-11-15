/*
 * Name: Nang Chen
 * Login: cs12saw
 * PID: A14205066
 * Date: 05/21/2017
 * File: SpellChecker.java
 * 
 * This is a class that implements methods to imitate
 * a dictionary for spell checking.
 */

package hw8;

import java.util.*;
import java.io.*;

public class SpellChecker {
	
	/**
	 * This is a integrated method to populate the hash table, add 
	 * add the related words to the linked list and print the relative
	 * words for our input.
	 * @param table
	 * @param inputList
	 * @param dic
	 * @param input
	 * @return true if the input file is found, else false
	 */
	@SuppressWarnings("unchecked")
	public static boolean populateHashTable
	(HashTable table, LinkedList<String> inputList, String dic, String input) {
		File dicFile = new File(dic);
		File inputFile = new File(input);
		try {
			Scanner dicScanner = new Scanner(dicFile);
			Scanner inputScanner = new Scanner(inputFile);
			//populate the hash table with the dictionaries
			while (dicScanner.hasNextLine()) {
				table.insert(dicScanner.nextLine().trim().toLowerCase());
			}
			//populate the linked list with the input files
			while (inputScanner.hasNextLine()) {
				inputList.add(inputScanner.nextLine().trim().toLowerCase());
			}
			//close the scanners
			dicScanner.close();
			inputScanner.close();
			//make a new array type linked list
			LinkedList<String>[] list = new LinkedList[inputList.size()];
			for (int i = 0; i < list.length; i++) {
				list[i] = new LinkedList<String>();
			}
			for (int i = 0; i < inputList.size(); i++) {
				//if the word is in the dictionary
				if (table.contains(inputList.get(i))) {
					list[i].add("ok");
				} else {
					//if there is one single wrong character 
					for (int j = 1; j < inputList.get(i).length() - 1; j++) {
						for (char c = 'a'; c <= 'z'; c++) {
							StringBuilder sb = new StringBuilder();
							sb.append(inputList.get(i).substring(0, j));
							sb.append(c);
							sb.append(inputList.get(i).substring
									(j + 1, inputList.get(i).length()));
							String s = sb.toString();
							if (table.contains(s)) {
								list[i].add(s);
							}
						}
					}

					//case of the wrong character in the front of the word
					for (char c = 'a'; c <= 'z'; c++) {
						StringBuilder sb = new StringBuilder();
						sb.append(c);
						sb.append(inputList.get(i).substring
								(1, inputList.get(i).length()));
						String s = sb.toString();
						if (table.contains(s)) {
							list[i].add(s);
						}
					}

					//case of the wrong character in the end of the word
					for (char c = 'a'; c <= 'z'; c++) {
						StringBuilder sb = new StringBuilder();
						sb.append(inputList.get(i).substring
								(0, inputList.get(i).length() - 1));
						sb.append(c);
						String s = sb.toString();
						if (table.contains(s)) {
							list[i].add(s);
						}
					}

					//if there's a missing character in the word
					for (int j = 0; j <= inputList.get(i).length(); j++) {
						for (char c = 'a'; c <= 'z'; c++) {
							StringBuilder sb = new StringBuilder();
							sb.append(inputList.get(i));
							sb.insert(j, c);
							String s = sb.toString();
							if (table.contains(s) && !list[i].contains(s)) {
								list[i].add(s);
							}
						}
					}

					//if there's an extra character in the word
					for (int j = 0; j < inputList.get(i).length(); j++) {
						StringBuilder sb = new StringBuilder();
						sb.append(inputList.get(i));
						sb.deleteCharAt(j);
						String s = sb.toString();
						if (table.contains(s)) {
							list[i].add(s);
						}
					}

					//if there's two adjacent characters swapped in the word
					for (int j = 0; j < inputList.get(i).length() - 1; j++) {
						StringBuilder sb = new StringBuilder();
						sb.append(inputList.get(i));
						char ch = sb.charAt(j);
						sb.deleteCharAt(j);
						sb.insert(j + 1, ch);
						String s = sb.toString();
						if (table.contains(s)) {
							list[i].add(s);
						}
					}

					//if there should be space between the word to create two
					for (int j = 1; j < inputList.get(i).length(); j++) {
						String s = inputList.get(i).substring(0, j);
						String str = inputList.get(i).substring
								(j, inputList.get(i).length());
						if (table.contains(s) && table.contains(str)) {
							s = s.concat(" " + str);
							list[i].add(s);
						}
					}

				}
			}
			//if the word does not exist after all the cases
			for (int i = 0; i < inputList.size(); i++) {
				if (list[i].isEmpty()) {
					list[i].add("not found");
				}
			}
			
			//print all the suggestions
			for (int i = 0; i < list.length; i++) {
				System.out.print(inputList.get(i) + ": ");
				for (int j = 0; j < list[i].size(); j++) {
					System.out.print(list[i].get(j));
					if (j != list[i].size() - 1 && list[i].size() != 1) {
						System.out.print(", ");
					}
				}
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			System.out.println("\nFile not found!!");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		args = new String[]{"test.dict.txt","5.txt"};
		if (args.length < 2) {
			System.err.println("Invalid number of arguments passed");
			return;
		}
		//make a hash table of size of 5
		HashTable table = new HashTable(5);
		LinkedList<String> inputList = new LinkedList<String>();
		//the first argument would be dictionary and the second would be input
		String dic = args[0];
		String input = args[1];
		// Create my hash table from file
		boolean check = populateHashTable(table, inputList, dic, input);
		if (check == false) {
			System.out.println("\nUnable to create hash table");
		}
	}
}
