package kmarkov;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import variables.GlobalVar;

public class Combination {
	//final static int K_VALUE = 3;
	//final static int PAGES = 73;
	int combCount = 0;
	int numOfComb;
	int k_value;
	
	int[][] a;
	
	Combination(int k){
		//String n = convertToString(PAGES);
		//String n_r = convertToString(PAGES - K_VALUE);
		//String r = convertToString(K_VALUE);
		//BigInteger factN = factorial(new BigInteger(n));
		//BigInteger factNR = factorial(new BigInteger(n_r));
		//BigInteger factR = factorial(new BigInteger(r));
		//BigInteger numOfCombi = (BigInteger)(factN.divide(factNR.multiply(factR)));
		
		//numOfComb = numOfCombi.intValue();
		int pages = GlobalVar.PAGES;
		int numerator = 1;
		int denominator = 1;
		for(int i = 1; i <= GlobalVar.K_VALUE; i++){
			numerator = numerator * (pages - (GlobalVar.K_VALUE - i));
			denominator = denominator * i;
		}
		numOfComb = (int)(numerator / denominator);
		//numOfComb = (int)((pages)*(pages - 1)*(pages - 2)/6);
		a = new int[numOfComb][k];
		k_value = k;
	}
	
	/*public String convertToString(int num){
		String s = Integer.toString(num);
		return s;
	}*/
	
	/*private long factorial(int n){
		long sum = 1;
		for(int i = 1; i <= n; i++)
			sum = sum * i;
		return sum;
	}*/
	
	/*static BigInteger factorial(BigInteger num) {
        if (num.equals(BigInteger.ONE))
            return BigInteger.ONE;
        else
            return num.multiply(factorial(num.subtract(BigInteger.ONE)));
    }*/

	void calculateCombinations(int start,int end, int[] num, int index){
		for(int i = start; i <= end; i++){
			num[index] = i;
			if(index == (k_value - 1)){
				for(int j = 0; j < k_value; j++)
					a[combCount][j] = num[j];
				combCount++;
				continue;
			}
			calculateCombinations(i+1,end+1,num,index+1);
		}
	}
	
	public int[][] getCombArray(){
		return a;
	}
	
	public void display(){
		PrintStream out;
		try {
			out = new PrintStream("combination.txt");
			System.setOut(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < numOfComb; i++){
			for(int j = 0; j < k_value; j++)
				System.out.print(a[i][j] + " ");
			System.out.println();
		}
	}
	
	/*public static void main(String[] args){
		int[] num = new int[3];
		Combination cg = new Combination();
		cg.calculateCombinations(1,(PAGES - 2),num,0);
		cg.display();
	}*/
}
