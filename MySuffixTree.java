package assigment;
import java.util.*;

import assigment.*;
import assigment.SuffixTree.Edge;
import assigment.SuffixTree.InternalNode;
import assigment.SuffixTree.LeafNode;
import assigment.SuffixTree.Node;
import assigment.SuffixTree.length;


public class MySuffixTree extends SuffixTree {
		
		  public static int index (char ch) {
			  /*
			   * Return the index of the character based on ASCII
			   */
		    return (ch == '$')? 0 : (int)ch + 1;
		  }
		  
		 
		  public MySuffixTree (String inputString) {
			  /*
			   * COnstructor  for ST
			   */
		    string = convert(inputString);
		    root = new InternalNode();
		    for (int i = 0; i < string.length(); i++) {
		    	//inserting each suffix in the tree
		      LeafNode leaf = insert(i, root);
		      leaf.beginIndex = i;
		    }
		  }
		  
		 
		  private LeafNode insert (int loc, InternalNode node) {
			  /*
			   * Insert function - construction
			   * takes in the node and location where the node has to be inserted as argument
			   */
					  
		    LeafNode leaf;
		    int endLoc = 1 + string.indexOf('$', loc);    // First '$' after loc
		    int subscript = index(string.charAt(loc));
		    Edge edge = node.child[subscript];HashSet<Integer> s= new HashSet<Integer>();Arrays.sort(s.toArray());Object[] b=s.toArray();Arrays.sort(b);
		    if (edge == null) {
		      // Brand new edge
		      edge = new Edge();
		      edge.beginIndex = loc;
		      edge.endIndex = endLoc;
		      edge.node = new LeafNode();
		      node.child[subscript] = edge;
		      return (LeafNode) edge.node;
		    }
		    String edgeString = string.substring(edge.beginIndex, edge.endIndex);
		    if (string.substring(loc, endLoc).equals(edgeString)) {
		      // Shared leaf node
		      leaf = new LeafNode();
		      leaf.more = (LeafNode) edge.node;
		      edge.node = leaf;
		      return leaf;
		    }
		    // Edge has to be 'broken' at first unmatch
		    int i, j;
		    for (i = edge.beginIndex, j = loc;
		         (i < edge.endIndex) && (string.charAt(i) == string.charAt(j)); 
		         i++, j++) {}
		    if (i >= edge.endIndex) {
		      // Recursively insert the remaining string in the edges node.
		      return insert(j, (InternalNode) edge.node);
		    }
		    InternalNode newNode = new InternalNode();
		    Edge newEdge = new Edge();
		    newEdge.beginIndex = i;
		    newEdge.endIndex = edge.endIndex;
		    newEdge.node = edge.node;
		    edge.endIndex = i;
		    edge.node = newNode;
		    newNode.child[index(string.charAt(i))] = newEdge;
		    leaf = insert(j, newNode);
		    return leaf;
		  }
		
		  
		 
		 
	 private int[] locate (String query, Node node, boolean prefixOK) {
		  /*
		   * Locate function takes in the pattern , and the starting node(=root in the beginning)
		   * prefixOK - TRUE means LCP is found
		   * 			FALSE means the exact pattern is searched for
		   */
		  
		    if (query.length() == 0) return collect(node);
		    if (node instanceof LeafNode) {
		      if (prefixOK) return collect(node);
		      return new int[0];
		    }
		    Edge edge = ((InternalNode)node).child[index(query.charAt(0))];
		    if (edge == null) {
		      if (prefixOK) return collect(node);//if prefix is also okay(LCP)
		      return new int[0];
		    }
		    int queryLength = query.length();
		    int i, q;
		   
		    for (i = edge.beginIndex, q = 0;
		         (q < queryLength) && (i < edge.endIndex) && 
		         (query.charAt(q) == string.charAt(i)); i++, q++) 
		    { 
		    	length.chars.append(string.charAt(i));
		    	System.out.print(string.charAt(i)); 
		    /*
		     * MATCHING THE common prefix in the edge and the pattern
		     */
		   
		    }
		    
		    if (q >= queryLength) return collect(edge.node);
		    else if (i >= edge.endIndex) 
		      return locate(query.substring(q), edge.node, prefixOK);
		    else if (prefixOK) {return collect(edge.node);}
		    
		    else return new int[0];
		    
		    
		  }
		  

		  private int[] collect (Node node) {
			  /*
			   * Finding all the leaf nodes of the matched prefix node to find all the occurances of the pattern/prefix matched
			   *
			   * */
		    Vector v = new Vector();
		    collect(node, v);
		    Object[] elements = v.toArray();
		    int[] result = new int[elements.length];
		    for (int i = 0; i < result.length; i++) 
		      result[i] = ((Integer) elements[i]).intValue();
		    
		    return result;
		  }
		  private void collect (Node node, Vector v) {
		    if (node instanceof LeafNode) {
		      LeafNode leaf = (LeafNode) node;
		      do {
		        v.add(new Integer(leaf.beginIndex));
		        leaf = leaf.more;
		      } while (leaf != null);
		    }
		    else {
		      InternalNode iNode = (InternalNode) node;
		      for (int i = 0; i < 256; i++) {//finding all the leaf nodes
		        if (iNode.child[i] != null) collect(iNode.child[i].node, v);
		      }
		    }
		    return;
		  }
		 
		  
		  public int[] findAll (String query) {
			  /*
			   * FIND all the occurances of the string
			   */
		    return locate(query, root, false);
		  }

		  
		  public int[] longestPrefix (String query) {
			  /*
			   * Find the longest prefix
			   */
					  
			  System.out.print("THE LONGEST PREFIX FOUND IS - ");
			  
			  return locate(query, root, true);
		  }
		  
			
			
		}
		    
		    
		    
		    
		    
		    
		    
		  
		  
		 
		 


