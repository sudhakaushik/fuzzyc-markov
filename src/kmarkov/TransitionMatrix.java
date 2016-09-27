package kmarkov;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.FileWriter;
import java.io.BufferedWriter;

import variables.GlobalVar;


public class TransitionMatrix {

	final static int K_VALUE = GlobalVar.K_VALUE;
	final static int PAGES = GlobalVar.PAGES;
	//final static int MAX_SIZE = 200;
	//float[][] transitionMatrix;
	//static int[][] permutation;
	String inputFile;
	String outputFile;
	
	TransitionMatrix(String inputFile, String outputFile){
		//transitionMatrix = new float[permutation.length][PAGES];
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	public void transitionMatrixGenerator(int[] permutation){
		try{
			String line = null;
			int permutationCount = 0;
			int k_value = permutation.length;
			//PrintStream out = new PrintStream(new FileOutputStream(outputFile));
			//System.setOut(out);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
				permutationCount = 0;
				Node nextLink = null;
				FileReader freader = new FileReader(inputFile);
				BufferedReader buffreader = new BufferedReader(freader);
				line = buffreader.readLine();
				while((line) != null){
					int i=0;
					StringTokenizer token = new StringTokenizer(line," ");
					int[] IntegerArray = new int[token.countTokens()];
					while(token.hasMoreTokens()){
						IntegerArray[i]= Integer.parseInt(token.nextToken());
						i++;
					}
					for(int increment=0; increment<(IntegerArray.length-(k_value - 1));increment++){
						int flag=1;
						
						for(int k = 0;k < k_value; k++){
							if(IntegerArray[k+increment]==permutation[k])
								continue;
							else{
								flag=0;
								break;
							}
						}
						if(flag==1){
							permutationCount++;
							if((increment+k_value) >= IntegerArray.length)
								continue;
							else{
								if(Node.checkFirstNode(nextLink)){
									nextLink = Node.createFirstNode(IntegerArray[increment + k_value]);
								}
								else
									nextLink.addNode(IntegerArray[increment + k_value]);
							}
						}
					}
					line = buffreader.readLine();
				}
				//PageProbability[] pageProbArray = new PageProbability[permLength];
				int flagPermPrint = 0;
				for(int k = 1; k <= PAGES; k++){
					int count = Node.findPage(nextLink, k);
					if((permutationCount) != 0){
						float val = (float)count/(permutationCount);
						if(val > 0){
							if(flagPermPrint == 0){
								for(int x = 0; x < k_value; x++)
									pw.print(permutation[x]+" ");
								pw.print(" : ");
								flagPermPrint++;
							}
							pw.print( k + "-" + val + " ");
						}
					}
				}
				if(flagPermPrint > 0)
					pw.println();
			//}
				pw.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("cannot open the file");
		}
	}
	
	
	/*public static void computePermutation(){
		try {
			Combination cg = new Combination();
			int[] num = new int[K_VALUE];
			cg.calculateCombinations(1,(PAGES - 2),num,0);
			int[][] combinationArray = cg.getCombArray();
			//int[] comb = new int[K_VALUE];
			//Integer dig = new Integer(0);
			PermGenerator pg = new PermGenerator();
			//PrintStream out;
			//out = new PrintStream("permutations.txt");
			//System.setOut(out);
			for(int i = 0; i < combinationArray.length; i++){
				pg.calculatePerm(combinationArray[i],comb,dig);
			}
			//out.close();
			//pg.display();
			//permutation = pg.getPermArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/*public void computeTransMatrix(TransitionMatrix tm){
		tm.transitionMatrixGenerator();
	}*/
}
