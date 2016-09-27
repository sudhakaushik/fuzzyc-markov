package kmarkov;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import variables.GlobalVar;


public class MainClass{
	static int K_VALUE = GlobalVar.K_VALUE;
	static int PAGES = GlobalVar.PAGES;
	static int CLUSTER_NUMBER = GlobalVar.CLUSTER_NUMBER;
	//final static int MAX_SIZE = 200;
	public static void main4(/*String[] args*/) throws FileNotFoundException {
		/*String line = null;
		
		final String INPUT_FILE = new String("clustering.txt");
		FileReader freader = new FileReader(INPUT_FILE);
		BufferedReader br = new BufferedReader(freader);*/
		
		//TransitionMatrix.computePermutation();
		
		//String[] fileNames = { "cluster1.txt","cluster2.txt","cluster3.txt","cluster4.txt","cluster5.txt"};
		/*String[] clusterSessions = { "clusterSessions1.txt","clusterSessions2.txt","clusterSessions3.txt","clusterSessions4.txt","clusterSessions5.txt"};
		for(int i = 0; i < CLUSTER_NUMBER; i++){
			try {
				//PrintStream out = new PrintStream(fileNames[i]);
				//out.println("start");
				ClusterSession C = new ClusterSession();
				//TransitionMatrix transMatrix = new TransitionMatrix(outputFiles[i],fileNames[i]);
				line=br.readLine();
				if(line == null)
					continue;
				C.printSessions(line,clusterSessions[i]);
				//float[][] transitionMatrix = new float[MAX_SIZE][];
				//transMatrix.computeTransMatrix(transMatrix);
				//transMatrix.displayTransitionMatrix(fileNames[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
		
		//TransitionMatrix.computePermutation();
		try {
			int k = K_VALUE;
			while(k > 1){
				Combination cg = new Combination(k);
				int[] num = new int[k];
				cg.calculateCombinations(1,(PAGES - 2),num,0);
				int[][] combinationArray = cg.getCombArray();
				int[] comb = new int[k];
				Integer dig = new Integer(0);
				PermGenerator pg = new PermGenerator();
				for(int i = 0; i < combinationArray.length; i++){
					pg.calculatePerm(combinationArray[i],comb,dig,k);
				}
				k = k - 1;
			}
			if(k == 1){
				String[] clusterSessions = { "clusterSessions1.txt","clusterSessions2.txt","clusterSessions3.txt","clusterSessions4.txt","clusterSessions5.txt"};
				String[] files1stOrder = {"clusterK31.txt","clusterK32.txt","clusterK33.txt","clusterK34.txt","clusterK35.txt"};
				int[] perm = new int[1];
				for(int i = 1; i < PAGES; i++){
					perm[0] = i;
					for(int m = 0; m < CLUSTER_NUMBER; m++){
						TransitionMatrix transMatrix = new TransitionMatrix(clusterSessions[m],files1stOrder[m]);
						transMatrix.transitionMatrixGenerator(perm);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
