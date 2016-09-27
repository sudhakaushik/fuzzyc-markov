package kmarkov;

public class Node {
	private int data;
	Node link;
	
	public static boolean checkFirstNode(Node node){
		if(node != null)
			return false;
		else
			return true;
	}
	
	public static Node createFirstNode(int page){
		Node n = new Node();
		n.data = page;
		n.link = null;
		return n;
	}
	
	public void addNode(int page){
		
		Node n = this;
			while(n.link != null){
				n = n.link;
			}
			n.link = new Node();
			n.link.data = page;
			n.link.link = null;
	}
	
	public static void printNodes(Node n){
		while(n != null){
			System.out.print(n.data + " ");
			n = n.link;
		}
		System.out.println();
	}
	
	public static int findPage(Node node,int page){
		int numOfOccurance = 0;
		if(node == null)
			return numOfOccurance;
		while(node != null){
			if(node.data == page)
				numOfOccurance++;
			node = node.link;
		}
		return numOfOccurance;
	}
	
}

