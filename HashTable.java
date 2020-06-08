package hw8;

import java.text.DecimalFormat;
import java.util.*;

public class HashTable implements IHashTable {

	// You will need a HashTable of LinkedLists.

	private int nelems; // Number of element stored in the hash table
	private int expand; // Number of times that the table has been expanded
	private int collision; // Number of collisions since last expansion
	private String statsFileName; // FilePath for the file to write statistics
									// upon every rehash
	private boolean printStats = false; // Boolean to decide whether to write
										// statistics to file or not after
										// rehashing
	private final float threshold = 0.67f;
	private LinkedList<String> table[];

	/**
	 * Constructor for hash table
	 * 
	 * @param Initial
	 *            size of the hash table
	 */
	@SuppressWarnings("unchecked")
	public HashTable(int size) {
		this.nelems = 0;
		this.expand = 0;
		table = new LinkedList[size];
		for (int i = 0; i < size; i++) {
			table[i] = new LinkedList<String>();
		}
		this.collision = 0;
		this.statsFileName = null;
	}

	/**
	 * Constructor for hash table
	 * 
	 * @param Initial
	 *            size of the hash table
	 * @param File
	 *            path to write statistics
	 */
	@SuppressWarnings("unchecked")
	public HashTable(int size, String fileName) {
		this.nelems = 0;
		this.expand = 0;
		table = new LinkedList[size];
		for (int i = 0; i < size; i++) {
			table[i] = new LinkedList<String>();
		}
		this.collision = 0;
		this.statsFileName = fileName;
		this.printStats = true;
	}

	/**
	 * Insert the string value into the hash table
	 * 
	 * @param value
	 *            value to insert
	 * @throws NullPointerException
	 *             if value is null
	 * @return true if the value was inserted, false if the value was already
	 *         present
	 */
	@Override
	public boolean insert(String value) {
		if (value == null) {
			throw new NullPointerException();
		} else {
			//get the relative indexes need to be hashed
			int hashVal = hashFunc(value);
			if (!table[hashVal].contains(value)) {
				nelems++;
				//add the strings to the relative indexes
				table[hashVal].add(value);
				float loadFactor = (float) nelems / table.length;
				//record collisions
				if(!table[hashVal].isEmpty()){
					collision++;
				}
				//rehash and print stats if load factor is 
				//larger than threshold
				if (loadFactor >= threshold) {
					expand++;
					if (printStats) {
						printStatistics();
						rehash();
					}
				}
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Delete the given value from the hash table
	 * 
	 * @param value
	 *            value to delete
	 * @throws NullPointerException
	 *             if value is null
	 * @return true if the value was deleted, false if the value was not found
	 */
	@Override
	public boolean delete(String value) {
		if (value == null) {
			throw new NullPointerException();
		} else {
			int hashVal = hashFunc(value);
			if (table[hashVal].contains(value)) {
				//remove the string from specific indexes
				table[hashVal].remove(value);
				nelems--;
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Check if the given value is present in the hash table
	 * 
	 * @param value
	 *            value to look up
	 * @throws NullPointerException
	 *             if value is null
	 * @return true if the value was found, false if the value was not found
	 */
	@Override
	public boolean contains(String value) {
		if (value == null) {
			throw new NullPointerException();
		} else {
			int hashVal = hashFunc(value);
			if (table[hashVal].contains(value)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Print the contents of the hash table. Print nothing if table is empty
	 * 
	 * Example output for this function:
	 * 
	 * 0: 1: 2: marina, fifty 3: table 4:
	 * 
	 */
	@Override
	public void printTable() {
		for (int i = 0; i < table.length; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < table[i].size(); j++) {
				System.out.print(table[i].get(j));
				//if there are more than one elements in the specific index
				if (j >= 0 && j < table[i].size() - 1 && table[i].size() > 1) {
					System.out.print(", ");
				}
			}
			System.out.print("\n");
		}
	}

	/**
	 * Return the number of elements currently stored in the hashtable
	 * 
	 * @return nelems
	 */
	@Override
	public int getSize() {
		return nelems;
	}

	// rehash() to expand and rehash the items into the table when load factor
	// goes over the threshold (0.67).
	@SuppressWarnings("unchecked")
	private void rehash() {
		int oldCap = table.length;
		int newCap = 2 * oldCap;
		LinkedList<String> oldTable[] = table;
		//replace the old table with two times larger new table
		table = new LinkedList[newCap];
		for(int i = 0; i<table.length; i++){
			table[i] = new LinkedList<String>();
		}
		//set the data to 0 after rehashing
		nelems = 0;
		collision = 0;
		//insert the original key with hash function to the new table
		for(int i = 0; i<oldTable.length; i++){
			for(int j = 0; j<oldTable[i].size();j++){
				String key = oldTable[i].get(j);
				insert(key);
			}
		}
	}

	// printStatistics() to print the statistics after each expansion.
	// This method will be called from insert/rehash only if printStats=true
	private void printStatistics() {
		int longestChain = 0;
		//get the longest chain of the table right before rehashing
		for(int i = 0;i<table.length;i++){
			 if(longestChain < table[i].size()){
				 longestChain = table[i].size();
			 }
		}
		//set the load factor to two decimal places
		DecimalFormat dec = new DecimalFormat();
		dec.setMaximumFractionDigits(2);
			System.out.println(expand + " resizes, " + "load factor " + 
		dec.format((float) nelems / table.length) + ", " + collision 
		+ " collisions, " + longestChain + " longest chain");
	}

	// my hash function
	private int hashFunc(String value) {
		int hashVal = 0;
		for (int i = 0; i < value.length(); i++) {
			int letter = value.charAt(i);
			hashVal = (hashVal * 27 + letter) % table.length;
		}
		return hashVal;
	}
}
