package wordladder;

import java.io.BufferedReader;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.util.*;

public class Wordladder {
	static void create_dict(Set<String> dict,String path) throws IOException {
		FileInputStream inputStream = new FileInputStream(path);  
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));   
        String str = null;  
        while((str = bufferedReader.readLine()) != null)  
        {    
            dict.add(str);
        }       
        //close  
        inputStream.close();  
        bufferedReader.close();
    }

	static Stack<String> WordLadder(String start, String end, Set<String> dict) {
		if (!dict.contains(start)) { 
			System.out.println("The start word is not in the dictionary."); 
			Stack<String> emptystack = null;
		    return emptystack;
		}
		if (!dict.contains(end)) { 
			System.out.println("The end word is not in the dictionary."); 
			Stack<String> emptystack = null;
		    return emptystack;
		}
		if (start.length() != end.length()) { 
			System.out.println("The length of the start word and the end one is not equal."); 
			Stack<String> emptystack = null;
		    return emptystack;
		}
		int length = start.length();
		Queue<Stack<String>> ladderqueue = new LinkedList<Stack<String>>();
		Stack<String> wordladder = new Stack<String>();
		wordladder.push(start);
		ladderqueue.offer(wordladder);
		Set<String> possible_List = new LinkedHashSet<String>();
		Iterator<String> it = dict.iterator();  
        while (it.hasNext()) {
        	String temp = it.next();
        	if (temp.length() == length) {
    			possible_List.add(temp);
    		}
        }
        while (!ladderqueue.isEmpty()) {
    		Stack<String> tempstack = ladderqueue.poll();
    		String word = tempstack.peek();
    		for (int i = 0; i < length; ++i) {
    			char oldchar = word.charAt(i);
    			for (char c = 'a'; c < 'z'; ++c) {
    				if (c == oldchar)
    					continue;
    				StringBuilder temp1 = new StringBuilder(word);
    				temp1.replace(i, i+1, String.valueOf(c));
    				String word_replace = temp1.toString();
    				if (possible_List.contains(word_replace)) { 
    					if (word_replace.equals(end)) {
    						possible_List.remove(word_replace);
    						Stack<String> result = tempstack;
    						result.push(word_replace);
    						return result;
    					}
    					possible_List.remove(word_replace);
    					Stack<String> result = (Stack<String>) tempstack.clone();
    					result.push(word_replace);
    					ladderqueue.offer(result);
    				}
    			}
    		}
    	}
        System.out.println("No path from the start word to the end one."); 
        Stack<String> emptystack = null;
    	return emptystack;
	}
	
	public static void main(String[] args) throws IOException {
		try {
			Scanner input = new Scanner(System.in);
			System.out.print("Please input the path of the dictionary:");
			String path = input.next();
			if (path.equals("q")) {
				input.close();
				return;
			}
			Set<String> words = new LinkedHashSet<String>();
			create_dict(words,path);
			System.out.print("Please input the start word:");
			String start = input.next();
			System.out.print("Please input the end word:");
			String end = input.next();
			input.close();
			Stack<String> result = WordLadder(start, end, words);
			if (result != null) {
				for (String x : result) { 
					System.out.println(x); 
				}
			}	
		}
		catch(IOException e) {
			System.out.print("Wrong path.");
		};
    }
}
