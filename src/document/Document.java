package document;

/** 
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

	private String text;
	
	/** Create a new document from the given text.
	 * Because this class is abstract, this is used only from subclasses.
	 * @param text The text of the document.
	 */
	protected Document(String text)
	{
		this.text = text;
	}
	
	/** Returns the tokens that match the regex pattern from the document 
	 * text string.
	 * @param pattern A regular expression string specifying the 
	 *   token pattern desired
	 * @return A List of tokens from the document text that match the regex 
	 *   pattern
	 */
	protected List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	// This is a helper function that returns the number of syllables
	// in a word.  You should write this and use it in your 
	// BasicDocument class.
	// You will probably NOT need to add a countWords or a countSentences method
	// here.  The reason we put countSyllables here because we'll use it again
	// next week when we implement the EfficientDocument class.
	protected int countSyllables(String word) {
		int debug = 0;
		int numSyllables = 0;
		
		// Separate word into vowels to count up syllables
		String[] tokens = word.split("[^(aA)|(eE)|(iI)|(oO)|(uU)|(yY)]+");
	
		// Count up the numSyllables skip over null elements in the String array
		if (debug == 1) System.out.println("\nWORD = " +word);
		for(String t : tokens) {
			if (debug == 1) System.out.println("TOKEN = " +t);
			if (!t.isEmpty()) {
				numSyllables += 1;
			}
		}
	
		// Check for the Lone E case if we find one then just decrement the numSyllables count
		if(word.charAt(word.length() - 1) == 'e' ||
		   word.charAt(word.length() - 1) == 'E') {
			numSyllables -= 1;
			// Make sure we don't have zero syllables for words like "be"
			if(numSyllables == 0){
				numSyllables = 1;
			}
			// If we found a lone E then check the char preceding it, make sure it's not a vowel
			// if it is then we should add back a count to numSyllables
			else if (word.toLowerCase().charAt(word.length()-2) == 'a' 	 ||
					 word.toLowerCase().charAt(word.length() - 2) == 'e' ||
					 word.toLowerCase().charAt(word.length() - 2) == 'i' ||
					 word.toLowerCase().charAt(word.length() - 2) == 'o' ||
					 word.toLowerCase().charAt(word.length() - 2) == 'u' ||
					 word.toLowerCase().charAt(word.length() - 2) == 'y' 
					 ) 
				numSyllables += 1;
		}
	
		if (debug == 1) System.out.println("numSyllables = " +numSyllables);
		return numSyllables;
	}
	
	/** A method for testing
	 * 
	 * @param doc The Document object to test
	 * @param syllables The expected number of syllables
	 * @param words The expected number of words
	 * @param sentences The expected number of sentences
	 * @return true if the test case passed.  False otherwise.
	 */
	public static boolean testCase(Document doc, int syllables, int words, int sentences)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound 
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound 
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound 
					+ ", expected " + sentences);
			passed = false;
		}
		
		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}
	
	
	/** Return the number of words in this document */
	public abstract int getNumWords();
	
	/** Return the number of sentences in this document */
	public abstract int getNumSentences();
	
	/** Return the number of syllables in this document */
	public abstract int getNumSyllables();
	
	/** Return the entire text of this document */
	public String getText()
	{
		return this.text;
	}
	
	/** return the Flesch readability score of this document */
	public double getFleschScore()
	{
		double numWords = (double) getNumWords();
		double numSentences = (double) getNumSentences();
		double numSyllables = (double) getNumSyllables();
		double fleschScore = 206.835 - (1.015 * numWords/numSentences) - (84.6 * numSyllables/numWords);
		
	    return fleschScore;
	}
	
}
