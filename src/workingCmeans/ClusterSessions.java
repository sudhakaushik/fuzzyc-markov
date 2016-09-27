package workingCmeans;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.StringTokenizer;

public class ClusterSessions {
	
	String val=null;
	String line1 = null;
	final String INPUT_FILE = new String("/Users/sudhakaushik/Desktop/Eclipse_work/PROJECT/session2.txt");
	public void printSessions(String clust,String outputFile) {
		try{
				PrintStream out = new PrintStream(new FileOutputStream(outputFile));
				System.setOut(out);
				
				StringTokenizer st = new StringTokenizer(clust," ");
				
				
				while (st.hasMoreTokens()) {
					int count1= -1;
					
					String val=String.valueOf(st.nextToken());
					int temp = Integer.parseInt(val);
					
					FileReader freader = new FileReader(INPUT_FILE);
					BufferedReader br = new BufferedReader(freader);
					
					while((line1=br.readLine())!=null){
						count1++;
						if(temp==count1){
							StringTokenizer stLine = new StringTokenizer(line1," ");
							int len = stLine.countTokens();
							if(len <= 3)
								break;
							String info = line1;
							System.out.println(info);
						}
					}
				}
				out.close();
				
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot open file");
		}
	}
	
}