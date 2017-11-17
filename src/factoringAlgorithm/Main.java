package factoringAlgorithm;

import java.math.BigInteger;

public class Main {
	
	private static QuadraticSieve trial;
	private static String longNumber = "92434447339770015548544881401";
	private static BigInteger number = new BigInteger(longNumber); 
	
	// 211014499133641692110753
	// 92434447339770015548544881401
	
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		trial = new QuadraticSieve(number,1020,1000);
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.println("Elapsed Time:" + elapsedTime/1000 + " seconds");
	}

}
