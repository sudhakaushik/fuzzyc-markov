package sessionpageview;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.StringTokenizer;
import variables.GlobalVar;

public class CSVcreation {
	
	//final static int PAGES = 73;
	public static void main2(/*String[] args*/){
		
		try {
			FileReader file;
			PrintStream out = new PrintStream("SPVM.csv");
			System.setOut(out);
			String line = null;
			int[] array = new int[GlobalVar.PAGES];
			int i;
			file = new FileReader("/Users/sudhakaushik/Desktop/Eclipse_work/PROJECT/session.txt");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(file);
			while((line = br.readLine()) != null){
				for(i = 0;i < GlobalVar.PAGES; i++){
					array[i] = 0;
				}
				StringTokenizer st = new StringTokenizer(line," ");
				while(st.hasMoreTokens()){
					int index = Integer.parseInt(st.nextToken());
					array[index-1] = 1;
				}
				for(i = 0;i < GlobalVar.PAGES;i++){
					if(i != (GlobalVar.PAGES - 1))
						System.out.print(array[i] + ",");
					else
						System.out.print(array[i]);
				}
				System.out.print("\n");
			}
			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
}