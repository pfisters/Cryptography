package factoringAlgorithm;

import java.math.BigInteger;

public class Main {
	
	private static QuadraticSieve trial;
	private static long longNumber = 31741649;
	private static BigInteger number = BigInteger.valueOf(longNumber); 
	private static BigInteger five	= BigInteger.valueOf(5);
	
	// 211014499133641692110753
	
	public static void main(String[] args) {
		
		trial = new QuadraticSieve(number,25,20);
		trial.generateM();
		//trial.printNumber();
		//trial.printPrimes();
		trial.printM();
		//trial.isFSmooth(number);
				
	}

}
