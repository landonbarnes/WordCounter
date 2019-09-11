import static org.junit.Assert.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.TreeMap;


/**
 * 
 * Test all the functionality in the WordCounter class.
 * 
 * @author Landon Barnes
 *
 *
 */
public class WordCounterTest {

	private TreeMap<String, Integer> expectedTreeMap;

	
	/**
	 * Ensures we start with a fresh TreeMap before each test
	 */
	@Before
	public void indivSetup(){
		 expectedTreeMap = new TreeMap<>();
	}
	
	/**
	 * Test to ensure we remove any leading and trailing spaces otherwise a blank character will be counted
	 */
	@Test
	public void countWordsRemoveLeadTrail() {
		
		expectedTreeMap.put("a", 1);
		TreeMap<String, Integer> actual = WordCounter.countWords((" a "));
		//Test if preceding/trailing strings removed
		assertEquals(actual, expectedTreeMap);
		
	}
	
	/**
	 * Test to ensure we remove any multiple spaces otherwise split will return spaces within a word
	 */
	@Test
	public void countWordsRemoveMultipleSpaces() {
		
		expectedTreeMap.put("a", 2);
		TreeMap<String, Integer> actual = WordCounter.countWords(("a   a "));
		//Test if preceding/trailing strings removed
		assertEquals(actual, expectedTreeMap);
		
	}
	
	/**
	 * Test to ensure we have correctly alphabetized the word counts by inputting out of order data and confirming countWords re-arranges it
	 */
	@Test
	public void countWordsInOrderReturn() {
		String[] alphaOrder = {"a", "b"};
			
		TreeMap<String, Integer> actual = WordCounter.countWords(("b   a "));
		Object[] keys = actual.keySet().toArray();
				
		assertArrayEquals(keys, alphaOrder);
		
	}
	
	/**
	 * Test to ensure we have correctly removed any punctuation
	 */
	@Test
	public void countWordsPunctuationRemoved() {
		expectedTreeMap.put("beta", 1);
		expectedTreeMap.put("alpha", 2);
		expectedTreeMap.put("delta", 3);
		expectedTreeMap.put("gamma", 4);
			
		TreeMap<String, Integer> actual = WordCounter.countWords(("delta gamma. alpha, delta{ gamma beta delta gamma alpha gamma"));
				
		assertEquals(actual, expectedTreeMap);
	}
	
	/**
	 * Test to ensure we have correctly lowercased everything
	 */
	@Test
	public void countWordsAllLower() {
		expectedTreeMap.put("beta", 1);
		expectedTreeMap.put("alpha", 2);
		expectedTreeMap.put("delta", 3);
		expectedTreeMap.put("gamma", 4);
			
		TreeMap<String, Integer> actual = WordCounter.countWords(("delta gamma alpha Delta gamma Beta delta Gamma alpha gamma"));
				
		assertEquals(actual, expectedTreeMap);
	}
	
	/**
	 * Test to ensure the counting of words is correct
	 */
	@Test
	public void countWordsCountCorrect() {
		expectedTreeMap.put("beta", 1);
		expectedTreeMap.put("alpha", 2);
		expectedTreeMap.put("delta", 3);
		expectedTreeMap.put("gamma", 4);
			
		TreeMap<String, Integer> actual = WordCounter.countWords(("delta gamma alpha delta gamma beta delta gamma alpha gamma"));
				
		assertEquals(actual, expectedTreeMap);
		
	}

	/**
	 * Test to verify an empty tree does not cause errors
	 */
	@Test
	public void packageTreeMapEmpty(){
		JSONArray result = WordCounter.packageTreeMap(new TreeMap<String, Integer>());
		assertEquals(result.size(), 0);
	}
	
	/**
	 * Test to verify packaging one JSONObject
	 */
	@Test
	public void packageTreeMapPackageOne(){
		expectedTreeMap.put("beta", 1);
		JSONArray actual = WordCounter.packageTreeMap(expectedTreeMap);
		JSONArray expectedJSONArray = new JSONArray();
		
		JSONObject toAdd = new JSONObject();
		
		toAdd.put("w", "beta");
		toAdd.put("n", 1);
		expectedJSONArray.add(toAdd);
		
		assertEquals(actual, expectedJSONArray);
	}
	
	/**
	 * Test to verify packaging multiple JSONObjects
	 */
	@Test
	public void packageTreeMapPackageMany(){
		expectedTreeMap.put("alpha", 2);
		expectedTreeMap.put("beta", 1);
		
		JSONArray actual = WordCounter.packageTreeMap(expectedTreeMap);
		
		JSONArray expectedJSONArray = new JSONArray();
		
		JSONObject toAdd = new JSONObject();
		toAdd.put("w", "alpha");
		toAdd.put("n", 2);
		expectedJSONArray.add(toAdd);
		
		toAdd = new JSONObject();
		toAdd.put("w", "beta");
		toAdd.put("n", 1);
		expectedJSONArray.add(toAdd);
		
		assertEquals(actual, expectedJSONArray);
	}
	
	/**
	 * Test to verify packaging does not change the order of our JSONObjects in the JSONArray
	 */
	@Test
	public void packageTreeMapPackageInOrder(){
		expectedTreeMap.put("beta", 1);
		expectedTreeMap.put("alpha", 2);
		
		JSONArray actual = WordCounter.packageTreeMap(expectedTreeMap);
		
		JSONArray expectedJSONArray = new JSONArray();
		
		JSONObject toAdd = new JSONObject();
		toAdd.put("w", "alpha");
		toAdd.put("n", 2);
		expectedJSONArray.add(toAdd);
		
		toAdd = new JSONObject();
		toAdd.put("w", "beta");
		toAdd.put("n", 1);
		
		expectedJSONArray.add(toAdd);
		assertEquals(actual, expectedJSONArray);
	}

	/**
	 * Test to verify invalid input does not cause an unhandled exception
	 */
	@Test
	public void getWordCountNotJSON(){
		WordCounter test = new WordCounter();
		assertNull(test.getWordCount("NOTJSON"));		
	}
	
	/**
	 * Test to verify input missing the key "para" is handled
	 */
	@Test
	public void getWordCountNoPara(){
		WordCounter test = new WordCounter();
		assertNull(test.getWordCount("{ \"pra\" : \"beta alpha gamma delta alpha\" }"));		
	}
	
	/**
	 * Test to verify that getWordCount produces correct results with a valid input.
	 */
	@Test
	public void getWordCountTotalTest(){
		WordCounter test = new WordCounter();
		
		JSONArray actual = WordCounter.packageTreeMap(expectedTreeMap);
		JSONArray expectedJSONArray = new JSONArray();
		
		JSONObject toAdd = new JSONObject();
		
		
		toAdd.put("w", "alpha");
		toAdd.put("n", 2);
		expectedJSONArray.add(toAdd);
		toAdd = new JSONObject();
		
		toAdd.put("w", "beta");
		toAdd.put("n", 1);
		expectedJSONArray.add(toAdd);
		toAdd = new JSONObject();

		toAdd.put("w", "delta");
		toAdd.put("n", 1);
		expectedJSONArray.add(toAdd);
		toAdd = new JSONObject();
		
		toAdd.put("w", "gamma");
		toAdd.put("n", 1);
		expectedJSONArray.add(toAdd);
		
		
		assertEquals(expectedJSONArray, test.getWordCount("{ \"para\" : \"beta alpha gamma delta alpha\" }"));		
		
	}
}
