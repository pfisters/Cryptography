package factoringAlgorithm;

import java.math.BigInteger;

public class Main {
	
	private static QuadraticSieve trial;
	private static long longNumber = 307561;
	private static BigInteger number = BigInteger.valueOf(longNumber); 
	//private static BigInteger five	= BigInteger.valueOf(5);
	//private static int[][] A = {{0,1,1},{2,4,-2},{0,3,15}};
	//private static int[] b = {4,2,36};
	//private static int n = 3;
	
	// 211014499133641692110753
	
	public static void main(String[] args) {
		
		trial = new QuadraticSieve(number,12,10);
		//trial.generateM();
		//trial.printNumber();
		//trial.printPrimes();
		//trial.printM();
		//trial.isFSmooth(number);
		//trial.binaryGaussianElimination();
		//trial.printSolutions();
	}

}
