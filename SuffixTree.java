package assigment;
import java.util.*;
import java.io.*;
import assigment.*;

public abstract class SuffixTree {
	
		class Node {						// Generic tree node
	}
	
	
	class LeafNode extends Node {		// Leaf node
		int beginIndex;					// suffix that ends here
		LeafNode more;					// More than one suffix can end here
	}
	
	
	class InternalNode extends Node {	// Internal node
		Edge[] child = new Edge[256];	// Child for each char
	}
	

	class Edge {						// Tree edge
		int beginIndex;					// Beginning index
		int endIndex;					// Ending index
		Node node;						// Child node
	}
	
	
	InternalNode root = null;			// Root of the SuffixTree
	String string;						// String that is held in SuffixTree
	
	
	public SuffixTree (String inputString) {
		
	}
	
	
	public SuffixTree () {}
	public abstract int[] findAll (String query);
	public abstract int[] longestPrefix (String query);

	private static void test (SuffixTree tree , String string,String pattern,int val) {
		/*
		 * Method takes the tree and passes on the pattern to findAll or Longest prefix function based on the requirement
		 * val -
		 * 0 - specifies if the exact match has to be found
		 * 1 -specifies that the LONGEST PREFIX HAS TO BE FOUND		 
		 * */
		
		if(val==0){
			System.out.println("Finding all occcurance of - "+pattern);
			length.chars=new StringBuilder();
			int[] a = (tree.findAll(pattern));
			System.out.println("\nPattern occurs at following tales");
			display(a,string,0);
		}
		else{
			int[] b=new int[1];
			System.out.println("\n\nFinding first occurance of longest prefix of "+pattern);
			int[] a = (tree.longestPrefix(pattern));
			System.out.println("\nPattern occurs at following tale");
			b[0]=a[0]; 					//Display only the first occurrance
			display(b,string,val);
		}
		
		
	}
	public static void display(int[] a,String string,int value){
		/*
		 *HELPER Method takes the array a[] of indices matched and displays the context,tale and occurance of the pattern in the DOcument 
		 */ 
		if(value==0){
			for(int val:a){
				int prev=val;
				Object[] keys = hash.h.keySet().toArray();
				Arrays.sort(keys);
				for (Object key :  keys) {
				    if((int)key>val){
				    	System.out.println("\n\nINDEX VALUE- "+val+" TALE NAME-"+hash.h.get(prev));
				    	int i=(int)val;int j=(int)val;
				    	System.out.println("\nThe context is - ");
				    	while(j>0 && string.charAt(j)!='.' && string.charAt(j)!='!' && string.charAt(j)!='?'){
				    		
				    		j--;
				    	}
				    	int k=j+1;
				    	while(k<(int)val){
				    		System.out.print(string.charAt(k));
				    		k++;
				    	}
				    	while(i<string.length() && string.charAt(i)!='.' && string.charAt(i)!='!' && string.charAt(i)!='?'){
				    		System.out.print(string.charAt(i));
				    		i++;
				    	}
				    	
				    	break;
				    }
				    prev=(int) key;
				}
				
			}
		}
		else{
			for(int val:a){
				int prev=val;
				Object[] keys = hash.h.keySet().toArray();
				Arrays.sort(keys);
				ArrayList arr= new ArrayList( hash.h.keySet());
				for (Object key :  keys) {
				    if((int)key>val){
				    	System.out.println("\n\nINDEX VALUE- "+val+" TALE NAME-"+hash.h.get(value));
				    	int i=(int)val;int j=(int)val;
				    	System.out.println("\nThe context is - ");
				    	while(j>0 && string.charAt(j)!='.' && string.charAt(j)!='!' && string.charAt(j)!='?'){
				    		
				    		j--;
				    	}
				    	int k=j+1;
				    	while(k<(int)val){
				    		if(string.charAt(k)!='$')
				    		System.out.print(string.charAt(k));
				    		k++;
				    	}
				    	while(i<string.length() && string.charAt(i)!='.' && string.charAt(i)!='!' && string.charAt(i)!='?'){
				    		if(string.charAt(k)!='$')
				    		System.out.print(string.charAt(i));
				    		i++;
				    	}
				    	
				    	break;
				    }
				    prev=(int) key;
				}
				
			}
			
		}
	}
	static class hash{
		static HashMap<Integer,String> h;
	}
	static class length{
		static StringBuilder chars;
	}
	
	public static void main (String[] args) {
		
		StringBuilder str;
		str=read();
	/*	
	 * read function reads the whole document as one GENERALised suffix tree string
	 *  where each tale is considered as a string ending with
	 *   a '$'  
	 *   */
		/*
		 * QUESTION 1 testing for all the occurances of exact pattern 
		 */
		SuffixTree tree;
		String pattern="wolf";//TEST CASE 1
		String pattern2="cobbler";//TEST CASE 2
		tree=new MySuffixTree(str.toString());
		
		/*
		 * test(tree,argument for the tree,pattern,
		 * val=0 for all occurances || 
		 * val=1 for first occurance of longest prefix)
		*/
		test(tree,str.toString(),pattern,0);
		test(tree,str.toString(),pattern2,0);
		/*
		* QUESTION 2 testing for longest the prefix of pattern
		*/
		ArrayList<String> st=new ArrayList<String>();
		ArrayList<SuffixTree> tales = read_talewise(st);
		ArrayList<Integer> arr=new ArrayList(hash.h.keySet());
		
		pattern="Wolf";//TEST CASE please give the input here
		Iterator<SuffixTree> it=tales.iterator();
		Iterator<String> str_iterator=st.iterator();
		Iterator<Integer> ar=arr.iterator();
		
		while(it.hasNext() && ar.hasNext()){
			length.chars=new StringBuilder(); 
			test(it.next(),str_iterator.next(),pattern,ar.next());//testing for the first occurance of longest prefix of the pattern
			
		}
		/*
		 * Ranking Documents based on Relevance
		 * DEFINITION OF RELEVANCE
		 * INPUT- Query string -A collection of words
		 * Match each word in each document,if the match is not found then match 
		 * the longest prefix and return the same.
		 * The document with highest exact word matches will have a higher relevance 
		 * than the one with partial matching(A prefix of the word matching)
		 * The rank is based on the total number of characters matched in 
		 * the whole query string in each document
		 * RELEVANCE PERCENTAGE - relevance score is calculated as
		 * total characters that matched in the document*100/total length of the query string
		 * THE DOCUMENT MATCHING THE WHOLE QUERY IN THE SAME ORDER HAS TO HAVE A RELEVANCE OF 100%
		 * */
		/*
		 * QUESTION 3 RANKING BASED ON RELEVANCE
		 */
		
		String query = "roaming through a forest, trod"; //TEST CASE
		String[] words = query.split(" ");
		int matched_length;
		int matched_words;
		ArrayList<String> st2=new ArrayList<String>();
		ArrayList<SuffixTree> tales2 = read_talewise(st2);
		ArrayList<Integer> arr2=new ArrayList(hash.h.keySet());
		Iterator<SuffixTree> it2=tales2.iterator();
		Iterator<String> str_iterator2=st2.iterator();
		Iterator<Integer> ar2=arr2.iterator();
		ArrayList<Integer> matches = new ArrayList<Integer>();
		ArrayList<String> tale_matches = new ArrayList<String>();
		
		while(it2.hasNext() && ar2.hasNext()){
			SuffixTree cur=it2.next();
			String current=str_iterator2.next();
			Integer c=ar2.next();
			length.chars=new StringBuilder(); 
			for(String word:words){
				test(cur,current,word,c);
			}
			matches.add(length.chars.length());
			tale_matches.add(hash.h.get(c));
			
			length.chars.delete(0,length.chars.length());
			
		}
		System.out.print("THE LIST OF DOCUMENTS BASED ON RANK\n");
		System.out.print("Relevance score    RANK          TALE      \n");
		int rank=1;
		for(int i=100;i>=0;i--){
			for(int j=0;j<matches.size();j++){
				if((int)matches.get(j)==i){
					System.out.println("Relevance score "+i*100/query.length()+"  "+rank+"   "+tale_matches.get(j));
					rank++;
				}
			}
		}
	}
		public static ArrayList<SuffixTree> read_talewise(ArrayList<String> st){
			/*
			 * read a document talewise and return an Arraylist of suffix trees for each tale.
			 * to be used for questions 2 and 3
			 * */
			FileReader file = null;
			Scanner sc = null;
			try {
				file = new FileReader("AesopTales.txt");
				sc = new Scanner(file);
				sc.useDelimiter("\n");
			} catch (FileNotFoundException e) {
		
				e.printStackTrace();
			}  
		    		
			StringBuilder args=new StringBuilder();
			while(sc.hasNext()){
				String s = sc.next();
				if(s.matches("\\s*"))
					s = s + '$';
				
				args.append(s);
			}
			String[] argu;
			String n=args.toString();
			
			argu = n.split("\\$");
			StringBuilder val=new StringBuilder();
			int i=0;
			hash.h=new HashMap<Integer,String>();
			ArrayList<SuffixTree> all_tales =new ArrayList();
			int value=0;
			for(String each:argu){
				
					
					value += each.length();
					if(each.length()<50){
						if(!(each.matches("\\s*")) && !each.contains(".")){
							hash.h.put(value, each);
							
						}
					}
					
				
					else{
							each=convert(each.trim());
							st.add(each);
							SuffixTree tale;
							tale=new MySuffixTree(each);
							all_tales.add(tale);
					}
			}
			
			return all_tales;
			
			
	}
	public static StringBuilder read(){
		/*
		 * READ AND PROCESS THE DOCUMENT
		 * */
		FileReader file = null;
		Scanner sc = null;
		try {
			file = new FileReader("AesopTales.txt");
			sc = new Scanner(file);
			sc.useDelimiter("\n");
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		}  
	    		
		StringBuilder args=new StringBuilder();
		while(sc.hasNext()){
			String s = sc.next();
			if(s.matches("\\s*"))
				s = s + '$';
			
			args.append(s);
		}
		String[] argu;
		String n=args.toString();
		

		argu = n.split("\\$");
		StringBuilder val=new StringBuilder();
		int i=0;
		hash.h=new HashMap<Integer,String>();
	
		int value=0;
		for(String each:argu){
			
				
				value += each.length();
				if(each.length()<50){
					if(!(each.matches("\\s*"))){
						hash.h.put(value, each);
					}
				}
				
			
			
			each=convert(each.trim());
			val.append(each.trim());
			
		}
		return val;
		
	}
	public static String convert (String string) {
		/*	CONVERT THE string to add a terminal $ if not present.
		 * */
	    if (string.length()-1>=0 && string.charAt(string.length()-1) != '$') string = string + '$';
	    return string;
	  }
	
}



