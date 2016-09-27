package workingCmeans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.*;
import variables.GlobalVar;

public class FuzzyCMeans {
		/*final int SESSION_NUM = 152;
		final static int PAGES = 73;
		final static int CLUSTER_NUMBER = 3;*/
		final static int SIZE = 50;
		static int pcount=0;
	//	static int ccount=0;
        Vector[] patterns;
        int c;
        double m;
        
        MembershipMatrix mm;
		
        public FuzzyCMeans(Vector[] patterns, int c, double m) {
                this.patterns = patterns;
                this.c = c;
                this.m = m;
        }
        
        public List<Vector> randomClusters(){
        	List<Vector> randomC = new ArrayList<>();
        	int[] sesIndex = new int[c];
        	int j;
        	Random randomGenerator = new Random();
        	for(int i = 0; i < c; i++){
        		while(true){
	        		int rand = randomGenerator.nextInt(GlobalVar.SESSION_NUM);
	        		for(j = 0; j < i; j++){
	        			if(sesIndex[j] == rand || patterns[rand].equals(patterns[sesIndex[j]]))
	        				break;
	        		}
	        		if(j == i){
	        			sesIndex[j] = rand;
	        			System.out.print(rand + " ");
	        			randomC.add(new Vector(patterns[rand].values));
	        			break;
	        		}
        		}
        	}
        	System.out.println();
        	//randomC.add(new Vector(patterns[0].values));
        	//randomC.add(new Vector(patterns[10].values));
        	return randomC;
        }
        
        public void partition() throws FileNotFoundException {
            
			
			//PrintStream pS = new PrintStream(new FileOutputStream("D:/project/clustering.txt"));
			//System.setOut(pS);
        	
        	// 1. choose an arbitrary membership matrix
        	mm = new MembershipMatrix(patterns.length, c);
                
            // 2. calculate cluster centers
            List<Vector> newC = randomClusters();//calculateC();
            boolean zeroFlag = false;
            List<Vector> oldC;
            double exponent = 2.0 / (m - 1); 
            for (@SuppressWarnings("unused")
			int m = 0; ; m++) {
            	List<Vector> sessionC = new ArrayList<>();
            	for(int i = 0; i < newC.size(); i++){
            		double[] val = new double[GlobalVar.PAGES];
            		Vector v = newC.get(i);
            		for(int j = 0; j < (v.values).length; j++){
            			val[j] = Math.round(v.values[j]);
            		}
            		sessionC.add(new Vector(val));
            	}
            		
            	
            	oldC = newC;
      
                 // 3. update membership matrix
                for (int i = 0; i < patterns.length; i++) { // loop over patterns
                	for (int j = 0; j < c; j++) { // loop over clusters
                       	zeroFlag = false;
                        double denominator = 0;
                        double xi_cj_distance = Vector.euclideanDistance(patterns[i], sessionC.get(j));
                        if(xi_cj_distance != 0){
                        	for (int r = 0; r < c; r++) {
                        		double xi_cr_distance = Vector.euclideanDistance(patterns[i], sessionC.get(r));
                        		if(xi_cr_distance != 0){
                        			double ratio = Math.pow(xi_cj_distance / xi_cr_distance, exponent);
                        			denominator += ratio;
                        		}
                        		else{
                        			zeroFlag = true;
                        			break;
                        		}
                        	}
                        	if(zeroFlag == false)
                        		mm.matrix[i][j] = 1.0 / denominator;
                        	else
                        		mm.matrix[i][j] = 0;
                        }
                        else{
                        	mm.matrix[i][j] = 1;
                        }
                    }
                }
                newC = calculateC();
                        
                      /* for(int i = 0; i < c; i++){
                        	Vector v = newC.get(i);
                        	for(int j = 0; j < v.values.length; j++){
                        		if(v.values[j] < 0.5)
                        			v.values[j] = 0;
                        		else
                        			v.values[j] = 1;
                        	}
                        }*/
                  if (newC.equals(oldC))
                	  break;
              	pcount++;

            }
            System.out.println(newC);
        }
        
        /**
         * calculates cluster centers with current clustering configurations.
         */
        protected List<Vector> calculateC() {
        	 
        	//	ccount++;
                List<Vector> C = new ArrayList<Vector>();
                for (int j = 0; j < c; j++) {
                        double[] values = new double[this.patterns[0].getDimensionCount()];
                        // we need to iteration to get each dimension
                        for (int dimIndex = 0; dimIndex < values.length; dimIndex++) {
                                double nominator = 0;
                                for (int i = 0; i < patterns.length; i++) {
                                        nominator += Math.pow(mm.matrix[i][j], m) * patterns[i].getDimension(dimIndex); 
                                }
                                
                                double denominator = 0;
                                for (int i = 0; i < patterns.length; i++) {
                                        denominator += Math.pow(mm.matrix[i][j], m);
                                }
                                
                                values[dimIndex] = nominator / denominator;
                        }
                        C.add(new Vector(values));
                }
                /*Vector v;
                for(int i=0;i<2;i++){
                	v = C.get(i);
                }*/
                return C;
        }
        
        public String printResults() {
                StringBuilder sb = new StringBuilder("");
                sb.append("pattern \t\t cluster memberships" + "\n");
                
                DecimalFormat format = new DecimalFormat("#.###");
                
                for (int i = 0; i < patterns.length; i++) { // loop over patterns
                        sb.append(/* (i) + */"\n"); // + patterns[i] + ": ");
                        sb.append(i + " ");
                        for (int j = 0; j < c; j++) { // loop over clusters
                        	
                        	sb.append(format.format(mm.matrix[i][j]) + "\t");
                        }
                }
                
                return sb.toString();
        }
        
        public void grouping() throws FileNotFoundException{
        	PrintStream pS = new PrintStream("clustering.txt");
			System.setOut(pS);
        	final double THRESHOLD = (double)1/c;
        	//final double EQUAL_BELONGINGNESS = (double)1/c;
        	double max = 0;
        	
        	int[][] cluster = new int[c][GlobalVar.SESSION_NUM];
        	int[] countC = new int[c];
        	for(int i = 0; i < c; i++){
        		countC[i] = 0;
        	}
        	for(int i = 0; i < GlobalVar.SESSION_NUM; i++){
        		max = 0;
        		int[] clusterIndex = new int[c];
        		for(int j = 0; j < c; j++){
        			if(((mm.matrix[i][j] >= THRESHOLD)) && (max <= mm.matrix[i][j])){
        				max = mm.matrix[i][j];
        				clusterIndex[j] = 1;
        			}
        		}
        		if(max > 0){
        			for(int j = 0; j < c; j++){
	        			if(clusterIndex[j] == 1){
		        			cluster[j][countC[j]] = i;
		        			countC[j]++;
	        			}
        			}
       			}
       		}
        	/*int[] prevArr = new int[SIZE];
        	//int[] curArr = new int[SIZE];
        	  
        	for(int i=0;i<c;i++){
        		int[] curArr = new int[countC[i]];
        		for(int j=0;j<countC[i];j++){
        			curArr[j]=cluster[i][j];	
        		}
        		if(Arrays.equals(prevArr, curArr)||curArr.length<CLUSTER_LENGTH){
        			//partition();
        			break;
        		}
        		else
        			prevArr = Arrays.copyOf(curArr, countC[i]);
        	}
        	  
        	*/
        	
        	for(int i = 0; i < c; i++){
        		/*if(countC[i] < CLUSTER_LENGTH)
        			continue;*/
        		for(int j = 0; j < countC[i]; j++)
        			pS.print(cluster[i][j] + " ");
        		pS.println();
        	}
        }
        
        public static void main3(/*String[] args*/) throws FileNotFoundException {
        	try {
        		FileReader fr = new FileReader("/Users/sudhakaushik/Desktop/Eclipse_work/PROJECT/subOutput.txt");
        		BufferedReader br1 = new BufferedReader(fr);
        		String c = br1.readLine();
        		GlobalVar.initializeClusternumber(Integer.parseInt(c));
        		PrintStream out = new PrintStream(new FileOutputStream("Clusters.txt"));
        		System.setOut(out);
                FuzzyCMeans fuzzyCMeans = new FuzzyCMeans(AbstractClusteringAlgorithm.loadPatterns(GlobalVar.PAGES, "/Users/sudhakaushik/Desktop/Eclipse_work/PROJECT/SPVM.csv", ","),GlobalVar.CLUSTER_NUMBER, 2);
                fuzzyCMeans.partition();
                System.out.println(fuzzyCMeans.printResults());
                fuzzyCMeans.grouping();
				final String INPUT_FILE = new String("/Users/sudhakaushik/Desktop/Eclipse_work/PROJECT/clustering.txt");
		    	FileReader freader = new FileReader(INPUT_FILE);
		    	BufferedReader br = new BufferedReader(freader);
		    	String[] outputFiles = { "clusterSessions1.txt","clusterSessions2.txt","clusterSessions3.txt","clusterSessions4.txt","clusterSessions5.txt"};
		    	for(int i = 0; i < GlobalVar.CLUSTER_NUMBER; i++){
		    		try {
		    			ClusterSessions C = new ClusterSessions();
		    			String line=br.readLine();
		    			if(line == null)
		    				continue;
		    			C.printSessions(line,outputFiles[i]);
		    		} catch (IOException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		    			
		    	}
				PrintStream cc = new PrintStream("count.txt");
				cc.println(pcount);
			/*	cc.println(ccount);*/

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
}    
