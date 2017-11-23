import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* CSE 373 Homework 3 2017 Summer
 * @Author Ran Ju #1621899
 * 		   Zhihua Li # 1322770
 * 
 * TextAssociator represents a collection of associations between words.
 * See write-up for implementation details and hints
 * 
 */
public class TextAssociator {
	private WordInfoSeparateChain[] table;
	private int size;
	
	/* INNER CLASS
	 * Represents a separate chain in the implementation of the hash table.
	 * A WordInfoSeparateChain is a list of WordInfo objects that have all
	 * been hashed to the same index of the TextAssociator.
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;
		
		/* Creates an empty WordInfoSeparateChain without any WordInfo.
		 */
		public WordInfoSeparateChain() {
			this.chain = new ArrayList<WordInfo>();
		}
		
		/* Adds a WordInfo object to the SeparateCahin.
		 * Returns true if the WordInfo was successfully added, false otherwise.
		 */
		public boolean add(WordInfo wi) {
			if(chain.contains(wi)){
				return false;
			}
			chain.add(wi);
			return true;
			// TODO: implement as explained in spec
		}
		
		/* Removes the given WordInfo object from the separate chain.
		 * Returns true if the WordInfo was successfully removed, false otherwise.
		 */
		public boolean remove(WordInfo wi) {
			if(!chain.contains(wi)){
				return false;
			}
			chain.remove(wi);
			return true;
			// TODO: implement as explained in spec
		}
		
		// Returns the size of this separate chain.
		public int size() {
			return chain.size();
		}
		
		// Returns the String representation of this separate chain.
		public String toString() {
			return chain.toString();
		}
		
		// Returns the list of WordInfo objects in this chain.
		public List<WordInfo> getElements() {
			return chain;
		}
	}
	
	
	/* Creates a new TextAssociator without any associations.
	 */
	public TextAssociator() {
		this.table = new WordInfoSeparateChain[7];
		
		//size means how many WordInfos in the table
		this.size = 0;
		for(int i = 0; i < table.length; i++){
			table[i] = new WordInfoSeparateChain();
		}
		//TODO: Implement as explained in spec
	}
	
	
	/* Adds a word with no associations to the TextAssociator.
	 * Returns False if this word is already contained in the TextAssociator,
	 * Returns True if this word is successfully added.
	 */
	public boolean addNewWord(String word) {
		WordInfo wi = new WordInfo(word);
		
		//hash function: get index in the array in each word
		int hashNum = Math.abs(word.hashCode() % table.length);	    
		if (table[hashNum].chain.contains(wi)) return false;
		
		//add new WordInfo into the table and increase the size
		table[hashNum].add(wi);
		size++;
		
		//rehash, if the load factor is larger than 1.0 then double size and find the nearest prime number.
		if ((double)size/(double)table.length > 1.0) {
			int length = table.length * 2 + 1;
			
			//find the closest prime number greater than double old size
			while(!isPrime(length)){
				length++;
			}
			
			WordInfoSeparateChain[] newtable = new WordInfoSeparateChain[length];
			WordInfoSeparateChain[] oldtable = table;
			table = newtable;
			for(int i = 0; i < table.length; i++){
				table[i] = new WordInfoSeparateChain();
			}
			
			//recalculate the index of each WordInfo in the new table 
			//then add the WordInfo and its Association to the new table
			for (int i = 0; i < oldtable.length; i++) {
				for (WordInfo w : oldtable[i].getElements()) {
					addNewWord(w.getWord());
					for (String s : w.getAssociations()) {
						addAssociation(w.getWord(), s);
					}
				}
			}	
		}
		return true;
		//TODO: Implement as explained in spec
	}
	
	/*Create helper function that can decide whether is prime number
	 * returns true if it is prime number otherwise return false;
	 */
	private boolean isPrime(int num){
		if(num <=  2) return true;
		if(num % 2 == 0) return false;
		for (int i = 3; i * i < num; i += 2) {
			if (num % i == 0) {
				return true;
			}
		}
		return false;	
	}
	
	/* Adds an association between the given words. Returns true if association correctly added, 
	 * returns false if first parameter does not already exist in the TextAssociator or if 
	 * the association between the two words already exists.
	 */
	public boolean addAssociation(String word, String association) {
		
		//hash function that get index in the table in each word
		int hashNum = Math.abs(word.hashCode() % table.length);
		
		//return false if it already exist the relationship between word and association
		//add new association and return true if the word already exist and association is not
		for (WordInfo w : table[hashNum].getElements()) {
			if (w.getWord().equals(word)) {
				if (w.getAssociations().equals(association)) {
					return false;
				}
				return w.addAssociation(association);
			}
		} 
		
		//return false if the word not exist;
		return false;
		
		//TODO: Implement as explained in spec
	}
	
	
	/* Remove the given word from the TextAssociator, returns false if word 
	 * was not contained, returns true if the word was successfully removed.
	 * Note that only a source word can be removed by this method, not an association.
	 */
	public boolean remove(String word) {
		
		//get index of each word in the table
		int hashNum = Math.abs(word.hashCode() % table.length);
		
		//walk through every WordInfo that have same index and find the given word then to remove its WordInfo
		for (WordInfo w : table[hashNum].getElements()) {
			if (w.getWord().equals(word)) {
				size --;
				return table[hashNum].remove(w);				
			}
		}
		return false;
		//TODO: Implement as explained in spec
	}
	
	
	/* Returns a set of all the words associated with the given String.
	 * Returns null if the given String does not exist in the TextAssociator.
	 */
	public Set<String> getAssociations(String word) {
		
		//get index of each word in the table
		int hashNum = Math.abs(word.hashCode() % table.length);
		
		//walk through every WordInfo to find the given word then return its association
		for (WordInfo w : table[hashNum].getElements()) {
			if (w.getWord().equals(word)) {
				return w.getAssociations();
			}
		}
		return null;
		//TODO: Implement as explained in spec
	}
	
	
	/* Prints the current associations between words being stored
	 * to System.out
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + size);
		System.out.println("Current table size: " + table.length);
		
		// Walk through every possible index in the table.
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				WordInfoSeparateChain bucket = table[i];
				
				// For each separate chain, grab each individual WordInfo.
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}
	

}
