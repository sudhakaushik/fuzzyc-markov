package variables;

public class GlobalVar {
	public static int K_VALUE = 3;
	public static int PAGES;
	public static int CLUSTER_NUMBER;
	public static int SESSION_NUM;
	
	public static void initailzeSession(int s){
		SESSION_NUM = s;
	}
	
	
	public static void initializePages(int p){
		PAGES = p;
	}
	
	public static void initializeClusternumber(int c){
		CLUSTER_NUMBER = c;
	}
}
