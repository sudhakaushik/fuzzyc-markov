package preprocessing;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import variables.GlobalVar;

public class preprocess {
	
	final int LINK_LOC = 10;
	final int SIZE = 2000;
	final int REF_LOC = 8;
	ArrayList<String> list = new ArrayList<String>();

	public void remove(){
		
		String prevTime = null;
		String curTime = null;
		
		int COUNT=0;
		String line=null;
		String link=null;
		int count;

		final String INPUT_FILE = new String("/Users/sudhakaushik/Desktop/misc/college/project/logfile.txt");
		ArrayList<String> list = new ArrayList<String>();
		List<Integer> list1 = new ArrayList<>();
		ArrayList<String> list2 = new ArrayList<String>();
		try{
			PrintStream out = new PrintStream(new FileOutputStream("preprocess.txt"));
	    	System.setOut(out);
			FileReader freader = new FileReader(INPUT_FILE);
			@SuppressWarnings("resource")
			BufferedReader buffreader = new BufferedReader(freader);
			int cnt=0;
			while((line=buffreader.readLine())!=null){
				COUNT++;
				String urll="";
				
				if(line.contains(".png")||line.contains(".gif")||line.contains(".jpg")||line.contains(".jpeg")||line.contains(".GIF")||line.contains(".JPG")||line.contains(".JPEG")||line.contains(".bmp"))
					continue;
					
				else if(line.contains(".css")||line.contains(".js")||line.contains("POST"))
					continue;
					
				else if(line.contains(" 200 ") == false)
					continue;
				
			/*	else if(line.contains("admintools"))
					continue;*/
				
				else if(line.contains(".ico")||line.contains("yahoo")||line.contains("google")||line.contains("bot")||line.contains("robots.txt")||line.contains("spider"))
					continue;
					
				else if(line.contains("enggresources") == false)
					continue;
					
				else{
					list1.add(COUNT);
					
				char[] linkArray = new char[SIZE];
				char[] resArray = new char[SIZE];
				int i,j,m,len;
				i=0;
				j=0;
				count=0;
				
				StringTokenizer st = new StringTokenizer(line," ");
				count++;
				while(count!=11){
					count++;
					if(count == 5){
						prevTime = curTime;
						curTime = st.nextToken();
					}
				
					else
						st.nextToken();
				}
				

				
				if((prevTime != null) && curTime.equals(prevTime))
					continue;
				
				link=st.nextToken();
				
				
				System.out.println(line);
				
				
					
				char[] array = new char[SIZE];
				char[] arrayRem = new char[SIZE];
				
				array = link.toCharArray();
				len=array.length;
				m=0;
				if(array[len-2]=='/'){
					for(int k=0;k<=len-3;k++){
						arrayRem[m]=array[k];
						m++;
					}
					arrayRem[m++] = ' ';
					String intermediate = new String(arrayRem,0,m);
					link = intermediate;
				}	
					
				if(link.contains("http://www.enggresources")){
					int len2= link.length();
					link= link.substring(1,len-1);
					urll = link;
					if(urll.charAt(urll.length()-1)==' ')
						urll = urll.replace(urll.substring(urll.length()-1), "");
				}
				else if(link.contains("http://enggresources")){
					linkArray=link.toCharArray();
					i=1;
					while(linkArray[i]!='e'){
						resArray[j]=linkArray[i];
						i++;
						j++;
					}	
					int k=1;
					while(k<=3){
						resArray[j]='w';
						j++;
						k++;
					}
					resArray[j]='.';
					j++;
					for(k=8;k<linkArray.length;k++){
						resArray[j]=linkArray[k];
						j++;
					}
						
					String url = new String(resArray,0,j);
					int len2= url.length();
					url=url.substring(0,len2-1);
					urll = url;	
					if(urll.charAt(urll.length()-1)==' ')
						urll = urll.replace(urll.substring(urll.length()-1), "");
				}
				
				else if(link.contains("\"www.enggresources")){			
					char[] http = new char[] {'h','t','t','p',':','/','/'};
					int p=0;
					linkArray=link.toCharArray();
					for(int k=1;k<=http.length;k++){
						resArray[j]=http[p];
						j++;
						p++;
					}
					for(int k=0;k<linkArray.length;k++){
						if(k==0)
							continue;
						else{
							resArray[j]=linkArray[k];
							j++;
						}
					}
					String url = new String(resArray,0,j);
					urll = url;
					if(urll.charAt(urll.length()-1)==' ')
						urll = urll.replace(urll.substring(urll.length()-1), "");
				}
				else
					continue;
		
				if(urll.contains("?")){
					int index = urll.indexOf("?");
					urll = urll.substring(0,index);
				}
				if(list.contains(urll)){
					list2.add(urll);
				}
				else
				{
					list2.add(urll);
					list.add(urll);
				}
				

				
			}
			}
			
			
			PrintStream out1 = new PrintStream(new FileOutputStream("links.txt"));
			System.setOut(out1);
			for(int i=0;i<list.size();i++){
				String url = list.get(i);
				System.out.println(url);
			}
			GlobalVar.initializePages(list.size());

		}catch(IOException e){
			e.printStackTrace();
			System.out.println("cannot open the file");
		}
	}
}