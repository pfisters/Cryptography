package factoringAlgorithm;

import java.math.BigInteger;

/*
 * Class that implements the Quadratic Sieve Algorithm
 */
public class QuadraticSieve {
	
	
	// ============================= =============================
	// PROPERTIES
	// ==========================================================
	
	/*
	 * Number to be factored: N
	 */
	private BigInteger number;
	
	/*
	 * The number of equations for the linear system
	 */
	private int L;
	
	/*
	 * The number of primes used for the factorization
	 */
	private int F;
	/*
	 * The factor base for the algorithm depending on F
	 * All primes that are strictly smaller than F
	 */
	private int[] primes;
	
	/*
	 * The M matrix for the Quadratic Sieve
	 */
	private int[][] M;

	/*
	 * The M matrix for the Quadratic Sieve mod2
	 */
	private int[][] binaryM;
	
	/*
	 * The current number of equations in M and binary M
	 */
	private int n;

	/*
	 * A possible new R, only used for intermediate results
	 */
	private int[] newR;
	
	/*
	 * A possible new binary R, only used for intermediate results 
	 */
	private int[] newbinaryR;
	
	// ==========================================================
	// CONSTRUCTORS
	// ==========================================================
	
	/*
	 * Public Constructor
	 */
	public QuadraticSieve(BigInteger number, int L, int F) {
		this.number = number;
		this.L = L;
		this.F = F;
		this.primes = new int[F];
		generatePrimes();
		this.M = new int[L][F];
		this.binaryM = new int[L][F];
		this.n = 0;
		this.newR = new int[F];
		this.newbinaryR = new int[F];
		
	}
	
	// ==========================================================
	// ACCESSORS
	// ==========================================================
	
	/*
	 * Public accessor to access the number N
	 */
	public BigInteger getNumber() {
		return number;
	}
	
	/*
	 * Public accessor to the factor base
	 */
	public int[] getFactorbase() {
		return primes;
	}
	
	// ==========================================================
	// PUBLIC METHODS
	// ==========================================================
	
	/*
	 * Print the number to be factorized
	 */
	public void printNumber() {
		System.out.println(number.toString());
	}
	
	/*
	 * Print the primes
	 */
	public void printPrimes() {
		System.out.print("[");
		for (int i = 0; i < primes.length; i += 1) {
			System.out.print(primes[i] + ",");
		}
		System.out.println("]");

	}
	
	/*
	 * Print the new R
	 */
	public void printNewR() {
		System.out.print("[");
		for (int i = 0; i < newR.length; i += 1) {
			System.out.print(newR[i] + ",");
		}
		System.out.println("]");

	}
	
	/*
	 * Print the new binary R
	 */
	public void printNewBinaryR() {
		System.out.print("[");
		for (int i = 0; i < newbinaryR.length; i += 1) {
			System.out.print(newbinaryR[i] + ",");
		}
		System.out.println("]");

	}
	
	/*
	 * Print M
	 */
	public void printM() {
		for ( int i = 0; i < L; i += 1) {
			System.out.print("|");
			for (int j = 0; j < F; j += 1) {
				System.out.print(M[i][j]);
				if (j < F - 1) System.out.print(",");
			}
			System.out.println("|");
		}
	}
	
	/*
	 * Print binary M
	 */
	public void printBinaryM() {
		for ( int i = 0; i < L; i += 1) {
			System.out.print("|");
			for (int j = 0; j < F; j += 1) {
				System.out.print(binaryM[i][j]);
				if (j < F - 1) System.out.print(",");
			}
			System.out.println("|");
		}
	}
	
	// ==========================================================
	// PRIVATE METHODS
	// ==========================================================
	
	/*
	 * Generate primes upto F
	 */
	private void generatePrimes() {
		//System.out.println("Generate Primes");
		int n = 0,i;
		for(i=2; n < F; i+= 1) {
			if (isPrime(i)){
				primes[n] = i;
				n += 1;
			}
		}
	}
	
	/*
	 * Determines whether a number is prime
	 */
	private boolean isPrime(int num) {
		//System.out.println("isPrime");
		if (num < 2) return false;
		if (num == 2) return true;
		if (num % 2 == 0) return false;
		for (int i = 3; i*i <= num; i += 2) {
			if (num % i == 0) return false;
		}
		return true;
	}
	
	/*
	 * Computes Square Root for the BigInteger class
	 */
	private BigInteger squareRoot(BigInteger x) {
		//System.out.println("squareRoot");
		BigInteger right = x;
		BigInteger left = BigInteger.ZERO;
		BigInteger mid;
		while (right.subtract(left).compareTo(BigInteger.ONE) > 0) {
			mid = (right.add(left)).shiftRight(1);
			if (mid.multiply(mid).compareTo(x) > 0)
				right = mid;
			else
				left = mid;
		}
		return left;
	}
	
	/*
	 * Private method to compute M
	 */
	void generateM() {
		System.out.println("Generate M");
		int N = 1;
		while (n < L) {
			System.out.println("N:" + N);
			n += addRSquared(computeR(N,N));
		
			for(int k = 1; k < N; k+=1 ) {
				System.out.println("N:" + N +",k:" + k);
				if (n < L) n += addRSquared(computeR(k,N));
				if (n < L) n += addRSquared(computeR(N,k));
			}
			N += 1;
			System.out.println("n:" + n);
		}		
	}
	
	/*
	 * Compute r by formula (1) given in the project description
	 */
	private BigInteger computeR(int k,int j) {
		//System.out.println("computeR:" + k + "," + j);
		return squareRoot(((BigInteger.valueOf(k)).multiply(number))).add(BigInteger.valueOf(j));
	}
	
	/*
	 * Private method indicating whether a BigInteger is F smooth
	 * Also, the coefficients are stored in the temporary new row vector newR
	 */
	private boolean isFSmooth(BigInteger r) {
		//System.out.println("IsFSmooth");
		// Reset the temporary R and binary R
		resetR();
		BigInteger rsquared = (r.multiply(r)).mod(number);
		// generate new R
		for(int i = 0; i < primes.length; i += 1) {
			// if r^2 mod primes[i] == 0
			while (rsquared.mod(BigInteger.valueOf(primes[i])).compareTo(BigInteger.ZERO) == 0) {
				rsquared = rsquared.divide(BigInteger.valueOf(primes[i]));
				newR[i] += 1;
			}
		}
		//printNewR();
		
		// generate the new binary R
		for(int i = 0; i < newR.length; i += 1) {
			newbinaryR[i] = newR[i] % 2;
		}
		//printNewBinaryR();
		
		// if r == 1 -> return true, else false
		if (rsquared.compareTo(BigInteger.ONE) == 0){
			//System.out.println("return true");
			return true;
		} else {
			//System.out.println("return false");
			return false;
		}
	}
	
	/*
	 * Reset the temporary new row vector to the Matrix M
	 */
	private void resetR() {
		//System.out.println("resetR");
		newR = new int[F];
		newbinaryR = new int[F];
	}
	
	/*
	 * Private method indicating whether the input is already contained in M
	 */
	private boolean isContainedInM() {
		//System.out.println("isContainedInM");
		for (int i = 0; i < L;i += 1) {
			int k = 0;
			for (int j = 0; j < F; j += 1) {
				if (newbinaryR[j] == binaryM[i][j]) {
					k += 1;
				}
			}
			if (k == F) return true;
		}
		return false;
	}
	
	/*
	 * Private method to add r^2 to system of equations
	 * return 1 if it was added, zero else
	 */
	private int addRSquared(BigInteger r) {
		//System.out.println("addRSquared");
		if (isFSmooth(r) && !isContainedInM()){
			M[n] = newR;
			binaryM[n] = newbinaryR;
			System.out.println("New R found");
			printNewR();
			printNewBinaryR();
			return 1;
		} else {
			return 0;
		}
	}
	
	/*
	 * Implementation of the Gaussian elimination
	 */
	private int[] GaussJordanElimination(int[][] A, int[] b) {
		
		
		
		return new int[F];
	}
	

}
