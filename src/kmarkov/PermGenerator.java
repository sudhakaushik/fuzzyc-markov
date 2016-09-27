package kmarkov;


import java.io.FileNotFoundException;
import variables.GlobalVar;

public class PermGenerator {
	final static int K_VALUE = GlobalVar.K_VALUE;
	final static int PAGES = GlobalVar.PAGES;
	final static int CLUSTER_NUMBER = GlobalVar.CLUSTER_NUMBER;
	String[][] fileNames = {
			{ "clusterK11.txt","clusterK12.txt","clusterK13.txt","clusterK14.txt","clusterK15.txt"},
			{"clusterK21.txt","clusterK22.txt","clusterK23.txt","clusterK24.txt","clusterK25.txt"},
			{"clusterK31.txt","clusterK32.txt","clusterK33.txt","clusterK34.txt","clusterK35.txt"},
			};
	String[] clusterSessions = { "clusterSessions1.txt","clusterSessions2.txt","clusterSessions3.txt","clusterSessions4.txt","clusterSessions5.txt"};
	int count = 0;
	
	public void calculatePerm(int[] num, int[] combi, Integer digits, int k){
		int[] perm = new int[k];
		if(digits == k){
			count++;
			int sum = 0;
			//System.out.print(count + " " );
			for(int i = 0; i < k; i++){
				//System.out.print(combi[i] + " ");
				//sum = (sum * 10) + combi[i];
				perm[i] = combi[i];
			}
			//perm[count-1] = sum;
			for(int m = 0; m < CLUSTER_NUMBER; m++){
				TransitionMatrix transMatrix = new TransitionMatrix(clusterSessions[m],fileNames[K_VALUE - k][m]);
				transMatrix.transitionMatrixGenerator(perm);
			}
			//System.out.println();
			return;
		}
		for(int i = 0; i < k; i++)
		{
			int j = 0;
			boolean valid = true;
			while(j < digits)
			{
				if(num[i] == combi[j])
				{
					valid = false;
					break;
				}
				j++;
			}
			if(valid)
			{
				combi[digits] = num[i];
				digits++;
				calculatePerm(num,combi,digits,k);
				digits--;
			}
		}
	}
		
	
	public static void main(String[] args){
		Combination cg = new Combination(K_VALUE);
		int[] num = new int[K_VALUE];
		cg.calculateCombinations(1,(PAGES - 2),num,0);
		int[][] combinationArray = cg.getCombArray();
		//int[] num = {1,2,3,4,5};
		int[] comb = new int[K_VALUE];
		Integer dig = new Integer(0);
		PermGenerator pg = new PermGenerator();
		try {
			//PrintStream out = new PrintStream("permutations.txt");
			//System.setOut(out);
			for(int i = 0; i < combinationArray.length; i++)
				pg.calculatePerm(combinationArray[i],comb,dig,K_VALUE);
			//pg.display();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}