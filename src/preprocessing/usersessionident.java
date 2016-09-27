package preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import variables.GlobalVar;

public class usersessionident {

	final int USERAGENT_LOC = 12;
	final int LINK_LOC = 11;
	final int TIME_LOC = 4;
	final static int TIME_INTERVAL = 30;
	final int SIZE = 2000;
	final int REF_LOC = 7;
	static boolean first_line = true; 
	static int hr, min, sec;
	static int numOfSessions = 0;
	
	List<String> ipAddress =  new ArrayList<>();
	List<String> arrUA =  new ArrayList<>();
	
	public void userIdentification(){
		 // Location at which user agent is found when split using blank spaces
		String ipAd, userAgent = "",temp;
		File file = new File("/Users/sudhakaushik/Desktop/Eclipse_work/PROJECT/preprocess.txt");
		int i = 0;
		try {
			BufferedReader br  = new BufferedReader(new FileReader(file));
			String line = null;
			
			int count;
			String ip;
			while((line = br.readLine()) != null){
				userAgent = "";
				count = 1;
				StringTokenizer st = new StringTokenizer(line," ");
			
				ipAd = new String(st.nextToken());
				count++;
				while(count != USERAGENT_LOC){
					count++;
					temp = st.nextToken();
				}
				while(st.hasMoreTokens()){
					userAgent += " " + st.nextToken();
				}
				
				
					if(ipAddress.contains(ipAd) && arrUA.contains(userAgent))
							continue;
				
				ipAddress.add(ipAd);
				arrUA.add(userAgent);
			}
			PrintStream out = new PrintStream(new FileOutputStream("user.txt"));
			System.setOut(out);
			for(i=0;i<ipAddress.size();i++){
				System.out.println(ipAddress.get(i) + " " + arrUA.get(i));
			}
			out.close();
			userVisitedPagesGroup();
			session();

		} catch (Exception e) {
			e.printStackTrace();		
		}
	}
	
	public void userVisitedPagesGroup(){
		String reqFile = null;
		String link = null;
		String userAgent = "";
		String ipAd = "";
		String line = "";
		int count = 0;
		try{
			PrintStream out = new PrintStream("userGroups.txt");
			System.setOut(out);
			for(int i = 0;i < ipAddress.size(); i++){
				FileReader fr = new FileReader("/Users/sudhakaushik/Desktop/Eclipse_work/PROJECT/preprocess.txt");
				BufferedReader br = new BufferedReader(fr);
				while((line = br.readLine()) != null){
					StringTokenizer st = new StringTokenizer(line," ");
					userAgent = "";
					count = 1;
					ipAd = new String(st.nextToken());
					count++;
					while(count != USERAGENT_LOC){
						count++;
						if(count == 8){
							reqFile = new String(st.nextToken());
							//reqFile = reqFile.substring(0, (reqFile.length() - 1));
						}
						else if(count == 12)
							link = st.nextToken();
						else
							st.nextToken();
					}
					
					if(link.contains(reqFile)){
						int begIndex = link.length() - reqFile.length() - 1;
						String cutFile = new String(link.substring(begIndex, (link.length() - 1)));
						if(reqFile.equals(cutFile))
							continue;
					}
					
					while(st.hasMoreTokens()){
						userAgent += " " + st.nextToken();
					}
					if(ipAd.equals(ipAddress.get(i)) && userAgent.equals(arrUA.get(i)))
						System.out.println(line);
				}
			}
			out.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int lineIndex(String str){
		int index = 0;
		String line1 = null;
		String link = str;
		
		try {
			
			int i,j,m,len;
			//int k=1;
			i=0;
			j=0;
			
			char[] linkArray = new char[SIZE];
			char[] resArray = new char[SIZE];
			
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
			
			if(link.contains("http://www.enggresources"))
			{
				int len2= link.length();
				link= link.substring(1,len-1);
				if(link.charAt(link.length()-1)==' ')
					link = link.replace(link.substring(link.length()-1), "");
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
				String urll = new String(resArray,0,j);
				link = urll;
				int len2= link.length();
				link = link.substring(0,len2-1);	
				if(link.charAt(link.length()-1)==' ')
					link = link.replace(link.substring(link.length()-1), "");
			}
			
			else if(link.contains("\"www.enggresources")){
				
				char[] http = new char[] {'h','t','t','p',':','/','/'};
				int n=0;
				linkArray=link.toCharArray();
				
				for(int k=1;k<=http.length;k++){
					resArray[j]=http[n];
					j++;
					n++;
				}
				for(int k=0;k<linkArray.length;k++){
					if(k==0)
						continue;
					else{
						resArray[j]=linkArray[k];
						j++;
					}
				}
				String urll = new String(resArray,0,j);
				link = urll;
				if(link.charAt(link.length()-1)==' ')
					link = link.replace(link.substring(link.length()-1), "");
			}
			if(link.contains("?")){
				int ind = link.indexOf("?");
				link = link.substring(0,ind);
			}
						
			FileReader frIndex = new FileReader("links.txt");
			BufferedReader br = new BufferedReader(frIndex);
			while((line1 = br.readLine()) != null){
				index++;
				if(line1.equals(link))
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		  }
		return index;
	}
	
		
	public static void initializeSessionTime(){
		hr = 0;
		min = 0; //file starts from 36 min session--29 to 39, 39 to 49
		sec = 0;
	}
			
	public static void sessionTime(int hh, int mm, int ss, int index,String cTime,long difference,int rootid){	
		
		if(((hh == hr && mm < ((min + TIME_INTERVAL) )) || (hh == hr && mm == (( min + TIME_INTERVAL )) && ss <= sec ) || ((( min + TIME_INTERVAL )>60) && hh == hr+1 && mm < ((min + TIME_INTERVAL) % 60 ))) && (difference < 10) && (rootid != index)){ //first iteration min=29 time_interval=10 hr=0
			System.out.print(index + " " );
			return;
		}
		else if((hh == hr && mm >= (min + TIME_INTERVAL)) || (hh != hr ) || (rootid == index) ){         //separate into sessions
			if(first_line){
				System.out.print(index + " " );
				first_line = false;
			}
			else
				System.out.print( "\n" + index + " " );
			numOfSessions++;
			if(hh == hr){ 
				min = mm;
			    sec = ss;
			}
			else{
				hr = hh;
				min = mm;
				sec = ss;
			}
		}
	}
	
	public void session(){
		
		String userAgent = "";
		String ipAd = "";
		String line = "";
		String link = "";
		String time = "";
		int prevIndex = 0;
		int index =0;
		int count = 0;
		String time1 = "";
		int flag=0;
		int refindref=0;
		String root = "^http://www.enggresources.com$";
		int rootid = 0;

//	    Queue<Integer> queue = new Queue<Integer>(capacity);

		try {
			
			PrintStream ps = new PrintStream("uservispages.txt");
			PrintStream ts = new PrintStream("staytime.txt");			
			PrintStream out = new PrintStream("session.txt");
			System.setOut(out);
			
		//	System.out.println(root);
			rootid =  lineIndex(root);
		//	System.out.println(rootid);

		//	System.out.println(rootid);
			
			for(int p = 0;p < ipAddress.size(); p++){
				FileReader fr = new FileReader("/Users/sudhakaushik/Desktop/Eclipse_work/PROJECT/userGroups.txt");
				BufferedReader br = new BufferedReader(fr);							
				initializeSessionTime();

				while((line = br.readLine()) != null){
					
					StringTokenizer st = new StringTokenizer(line," ");
					userAgent = "";
					count = 1;
					ipAd = new String(st.nextToken());
					count++;
					while(count != USERAGENT_LOC){
						if(count == LINK_LOC ){
							link = st.nextToken();
						}
						else if(count == TIME_LOC ){
							time = st.nextToken();			
						}
						else
							st.nextToken();
						count++;
					}
					
					while(st.hasMoreTokens()){
						userAgent += " " + st.nextToken();
					}
					
					if(ipAd.equals(ipAddress.get(p)) && userAgent.equals(arrUA.get(p))){

						index = lineIndex(link);
						
						if(prevIndex==index)
							continue;
						ps.print(index + " ");
												
						String cTime = time.substring(13, 21);
						String pTime = time.substring(1,21);
					//	ts.print(pTime + " ");
						String time2 = pTime;
						StringTokenizer stime = new StringTokenizer(cTime,":"); //separate into tokens using : as delimiter
			 			int hh = Integer.parseInt(stime.nextToken()); //separate the tokens into hour
						int mm = Integer.parseInt(stime.nextToken()); // min
						int ss = Integer.parseInt(stime.nextToken()); // sec
												
						if(flag == 0){
							time1 = pTime;
							flag = 1;
						}

						SimpleDateFormat format = new SimpleDateFormat("d/MMM/YYYY:HH:mm:ss");
						Date date1 = format.parse(time1);
						Date date2 = format.parse(time2);
						long difference = date2.getTime() - date1.getTime(); 
					    difference = TimeUnit.MINUTES.convert(difference,TimeUnit.MILLISECONDS);
					    
						sessionTime(hh,mm,ss,index,cTime,difference,rootid);

						ts.print(prevIndex + " " );
						ts.println(difference);
						
						time1 = pTime;
						prevIndex = index;

					}
				}
				ps.println(); 
				flag = 0;
			}
			out.close();
			GlobalVar.initailzeSession(numOfSessions);
		}catch (Exception e) {
				e.printStackTrace();
		}
	}	
}
