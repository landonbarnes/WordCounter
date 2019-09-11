/**
 * 
 */
import java.util.Iterator;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * WordCounter class to implement counting the number of occurrences in an input string,
 * and returning them in order and packaging them in a JSONArray.
 * @author Landon Barnes
 *
 */
public class WordCounter {

	/**
	 * @param args Single string containing the JSON notation to read para from
	 *             ie: { "para" : "some strings" }
	 */
	public static void main(String[] args) {
		WordCounter count = new WordCounter();
	
		System.out.println(count.getWordCount(args[0]));
		
	}
	
	/**
	 * Splits the string words at each space. Counts each of the occurrence of each word in words.
	 * 
	 * @param  words   String parameter containing paragraph to count words in
	 * @return TreeMap Key is the word and the value is the count of each word in words
	 */
	public static TreeMap<String, Integer> countWords(String words){
		//Ensure we don't get a null value in return
		if(words.length() == 0){
			return new TreeMap<String, Integer>();
		}

		TreeMap<String, Integer> toReturn = new TreeMap<>();
		
		//Remove leading/trailing spaces
		String strippedWords = words.trim();
		
		//Replace any occurrences of 1..n spaces with one space
		strippedWords = strippedWords.replaceAll("\\s+"," ");
		
		//Split on the single spaces remaining
		String[] splitStrings = strippedWords.split(" ");

		/**
		 * For each word in words put/update it to the TreeMap with either
		 * 1. a default value of 0+1 if the key did not exist or 
		 * 2. if the key already exists the key's value + 1
		*/
		for(int i =0; i < splitStrings.length; i++){
			toReturn.put(splitStrings[i],toReturn.getOrDefault(splitStrings[i], 0)+1);
		}

		return toReturn;
	}

	/**
	 * @param  wordCounts TreeMap<String, Integer> containing key/value pairs of the string and it's count
	 * @return JSONArray JSONArray object containing words and their count labeled as "w" and "n"
	 */
	public static JSONArray packageTreeMap(TreeMap<String, Integer> wordCounts){
		
		JSONArray toReturn= new JSONArray();
		
		//Iterate over wordCounts and label the entries as w: and n:
		Iterator<String> it = wordCounts.keySet().iterator();
		JSONObject eachWord;
		while(it.hasNext()){
			eachWord = new JSONObject();

			String key = it.next();
			
			eachWord.put("w", key);
			eachWord.put("n", wordCounts.get(key));
			
			toReturn.add(eachWord);
		}
		return toReturn;
	}

	/**
	 * @param input String containing the JSON to parse the paragraph from
	 * @return JSONArray JSONArray object containing words and their count labeled as "w" and "n"
	 * 		   Will return null if the input is not a valid JSON or has no key "para"
	 */
	public JSONArray getWordCount(String input){
		JSONObject inputJSON;
		
		//Ensure that the input is some parseable JSON. Returns null.
		try {
			inputJSON = (JSONObject) new JSONParser().parse(input);
		} catch (ParseException e) {
			System.out.println("Input argument formatted improperly, input in the format: {\"para\" : \"some strings\"}");
			return null;
		}
		
		String paragraph = (String)inputJSON.get("para");
		//If there is no para then we can't find the paragraph so report and error and return null
		if(paragraph == null){
			System.out.println("Input did not contain the para keyword.");
			return null;
		}
		
		//Utilize previous methods to count the words and return JSONArray
		return packageTreeMap(countWords(paragraph));
		
	}
}
