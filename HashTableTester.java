/*
 * Name: Nang Chen
 * Login: cs12saw
 * PID: A14205066
 * Date: 05/28/2017
 * File: HashTableTester.java
 * 
 * This is a tester class that tests the methods in HashTable.java.
 */

package hw8;

import static org.junit.Assert.*;
import org.junit.*;

public class HashTableTester {
	private HashTable table;

	@Before
	public void setUp() {
		table = new HashTable(6);
	}

	@Test
	public void testCtor() {
		assertEquals(0, table.getSize());
		assertFalse(table.contains("six"));
	}

	@Test
	public void testInsert() {
		table.insert("six");
		table.insert("five");
		table.insert("two");
		table.insert("one");
		assertEquals(4, table.getSize());
		assertTrue(table.contains("six"));
		assertFalse(table.contains("a"));
	}

	@Test
	public void testInsertException() {
		try {
			table.insert(null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
		}
	}

	@Test
	public void testDelete() {
		table.insert("six");
		table.insert("five");
		table.insert("two");
		table.insert("one");
		table.insert("nine");
		table.insert("seven");
		table.delete("seven");
		table.delete("six");
		table.delete("five");
		table.delete("two");
		assertEquals(2, table.getSize());
		assertFalse(table.contains("a"));
	}

	@Test
	public void testDeleteException() {
		try {
			table.delete(null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
		}
	}

	@Test
	public void testContains() {
		table.insert("six");
		table.insert("five");
		table.insert("two");
		table.insert("one");
		table.insert("nine");
		table.insert("seven");
		table.insert("eight");
		table.insert("three");
		table.insert("eleven");
		table.insert("sixteen");
		table.insert("thirteen");
		table.insert("twelve");
		assertTrue(table.contains("seven"));
		assertFalse(table.contains("hundred"));
	}

	@Test
	public void testContainsException() {
		try {
			table.contains(null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
		}
	}

	@Test
	public void testGetSize() {
		table.insert("six");
		table.insert("five");
		table.insert("two");
		table.insert("one");
		table.insert("nine");
		table.insert("seven");
		table.insert("eight");
		table.insert("three");
		table.insert("eleven");
		table.insert("sixteen");
		table.insert("thirteen");
		table.insert("twelve");
		assertEquals(12, table.getSize());
	}
}
