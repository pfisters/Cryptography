package factoringAlgorithm;

import java.math.BigInteger;
import java.util.Vector;

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
	 * The factor base for the algorithm depending on F All primes that are
	 * strictly smaller than F
	 */
	private int[] primes;

	/*
	 * The M matrix for the Quadratic Sieve
	 */
	private int[][] M;

	/*
	 * Vector containing the values of r^2
	 */
	private BigInteger[] r;
	
	/*
	 * The M matrix for the Quadratic Sieve mod2
	 */
	private int[][] binaryM;

	/*
	 * The M matrix for the Quadratic Sieve mod2
	 */
	private int[][] modifiedBinaryM;
	
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

	/*
	 * Solutions vector
	 */
	private Vector<int[]> solutions = new Vector<>();
	
	/*
	 * Both factors of the prime number
	 */
	private BigInteger p_final;
	private BigInteger q_final;

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
		this.modifiedBinaryM = new int[L][F];
		this.r = new BigInteger[L];
		this.n = 0;
		this.newR = new int[F];
		this.newbinaryR = new int[F];
		generateM();
		binaryGaussianElimination();
		solutionsAssembly();
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
		System.out.println("Primes:");
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
		System.out.println("M:");
		for (int i = 0; i < L; i += 1) {
			System.out.print("|");
			for (int j = 0; j < F; j += 1) {
				System.out.print(M[i][j]);
				if (j < F - 1)
					System.out.print(",");
			}
			System.out.println("|");
		}
	}

	/*
	 * Print binary M
	 */
	public void printBinaryM() {
		System.out.println("Binary M:");
		for (int i = 0; i < L; i += 1) {
			System.out.print("|");
			for (int j = 0; j < F; j += 1) {
				System.out.print(binaryM[i][j]);
				if (j < F - 1)
					System.out.print(",");
			}
			System.out.println("|");
		}
	}

	/*
	 * Print modified binary M
	 */
	public void printModifiedBinaryM() {
		System.out.println("Modified Binary M:");
		for (int i = 0; i < L; i += 1) {
			System.out.print("|");
			for (int j = 0; j < F; j += 1) {
				System.out.print(modifiedBinaryM[i][j]);
				if (j < F - 1)
					System.out.print(",");
			}
			System.out.println("|");
		}
	}
	
	/*
	 * Print solutions vector
	 */
	public void printSolutions() {
		System.out.println("Solutions:");
		for (int i = 0; i < solutions.size(); i++) {
			System.out.print("|");
			for (int j = 0; j < solutions.elementAt(i).length; j++) {
				System.out.print(solutions.elementAt(i)[j]);
				if (j < solutions.elementAt(i).length - 1)
					System.out.print(",");
			}
			System.out.println("|");
		}
	}

	// ==========================================================
	// PRIVATE METHODS
	// ==========================================================

	/*
	 * Generate the first F primes
	 */
	private void generatePrimes() {
		int n = 0, i;
		for (i = 2; n < F; i += 1) {
			if (isPrime(i)) {
				primes[n] = i;
				n += 1;
			}
		}
	}

	/*
	 * Determines whether a number is prime
	 * INPUT: 	int num
	 * OUTPUT:	boolean: 	true => num is prime
	 * 						false=> num is not prime
	 */
	private boolean isPrime(int num) {
		if (num < 2)
			return false;
		if (num == 2)
			return true;
		if (num % 2 == 0)
			return false;
		for (int i = 3; i * i <= num; i += 2) 
			if (num % i == 0) return false;
		
		return true;
	}


	/*
	 * Computes the Matrix M from the script
	 */
	private void generateM() {
		// Initialize the size of the square to be parsed
		int N = 1;
		// While there is not enough equations
		while (n < L) {
			n += addRSquared(computeR(N, N));
			for (int k = 1; k < N; k += 1) {
				if (n < L) n += addRSquared(computeR(k, N));
				if (n < L) n += addRSquared(computeR(N, k));
			}
			N += 1;
		}
	}

	/*
	 * Compute r by formula (1) given in the project description
	 */
	private BigInteger computeR(int k, int j) {
		return squareRoot(((BigInteger.valueOf(k)).multiply(this.number))).add(BigInteger.valueOf(j));
	}

	/*
	 * Computes Square Root for the BigInteger class
	 * INPUT: 	BigInteger x
	 * OUTPUT:	Floor rounded square root of x 
	 */
	private BigInteger squareRoot(BigInteger x) {
		BigInteger right = x;
		BigInteger left = BigInteger.ZERO;
		BigInteger mid;
		// while right - left > 1
		while (right.subtract(left).compareTo(BigInteger.ONE) > 0) {
			mid = (right.add(left)).shiftRight(1);
			// if mid^2 > x
			if ((mid.multiply(mid)).compareTo(x) > 0)
				right = mid;
			else
				left = mid;
		}
		return left;
	}
	
	/*
	 * Method that determines whether a number is F smooth
	 * The primes are stored in the array primes
	 */
	private boolean isFSmooth(BigInteger r) {
		// Reset the temporary R and binary R
		resetR();
		BigInteger rsquared = (r.multiply(r)).mod(this.number);
		// generate new R
		for (int i = 0; i < primes.length; i += 1) {
			// if r^2 mod primes[i] == 0
			while (rsquared.mod(BigInteger.valueOf(primes[i])).compareTo(BigInteger.ZERO) == 0) {
				rsquared = rsquared.divide(BigInteger.valueOf(primes[i]));
				newR[i] += 1;
				newbinaryR[i] ^= 1;
			}
		}

		// if r == 1 -> return true, else false
		if (rsquared.compareTo(BigInteger.ONE) == 0) { return true;
		} else { return false;}
	}

	/*
	 * Reset the temporary new row vectors to the matrices M and binaryM
	 */
	private void resetR() {
		this.newR = new int[F]; this.newbinaryR = new int[F];
	}

	/*
	 * Method indicating whether the input is already contained in M
	 */
	private boolean isContainedInM() {
		for (int i = 0; i < L; i += 1) {
			int k = 0;
			for (int j = 0; j < F; j += 1) {
				if (newbinaryR[j] != binaryM[i][j]) {
					break;
				} else {
					k += 1;
				}
			}
			if (k == F) return true;
		}
		return false;
	}

	/*
	 * Private method to add r^2 to system of equations
	 * INPUT: 	new possible r
	 * OUTPUT: 	1 if r was added
	 * 			0 else
	 */
	private int addRSquared(BigInteger r) {
		if (isFSmooth(r) && !isContainedInM()) {
			M[n] = newR;
			binaryM[n] = newbinaryR;
			this.r[n] = r;
			return 1;
		} else {
			return 0;
		}
	}

	/*
	 * Algorithm for the binary Gaussian Elimination
	 */
	private void binaryGaussianElimination() {

		int[][] A = binaryM;
		int m = 	A[0].length; // F
		int n =	 	A.length;	 // L
		boolean[] marked = new boolean[n];

		for (int j = 0; j < m; j++) {
			// Search for A_ij in column j
			int row = -1;
			for (int i = 0; i < n; i++) {
				if (A[i][j] == 1) {
					row = i;
					marked[i] = true;
					break;
				}
			}

			// if found, then look for another 1 in the same row
			if (row != -1) {
				for (int k = 0; k < m; k++) {
					// if A_row_k == 1 add column j to column k
					if (A[row][k] == 1 && j != k) {
						for (int l = 0; l < n; l++) {
							A[l][k] ^= A[l][j];
						}
					}
				}
			}

		}

		// add the solutions to the solutions vector
		for (int i = 0; i < marked.length; i++) {
			if (marked[i] == false){
				int[] solVec = new int[L];
				solVec[i] = 1;
				// find the column
				for(int j = 0; j < A[i].length; j++) {
					if (A[i][j] == 1) {
						// find the row
						for (int k = 0; k < A.length; k ++) {
							if(A[k][j] == 1 && marked[k] == true) solVec[k] = 1;
						}
					}
				}
				solutions.add(solVec);
			}
		}
		
		this.modifiedBinaryM = A;
	}

	/*
	 * Assembling solutions
	 */
	private void solutionsAssembly() {
		for (int i = 0; i < solutions.size(); i ++) {
			BigInteger x = BigInteger.ONE;
			int[] prim = new int[F];
			BigInteger y = BigInteger.ONE;
			for(int j = 0; j < solutions.elementAt(i).length; j++) {
				if (solutions.elementAt(i)[j] == 1) {
					x = x.multiply(r[j]);
					for(int k = 0; k < M[i].length; k++) {
						prim[k] += M[j][k];
					}
					
				}
			}
			
			for(int j = 0; j< prim.length; j++) {
				BigInteger base = BigInteger.valueOf(primes[j]);
				int power = prim[j] / 2;
				y = y.multiply(base.pow(power));
			}
			
			y = y.mod(number);
			x = x.mod(number);
			
			BigInteger result = this.number.gcd(y.subtract(x));

			if (result.equals(this.number) || result.equals(BigInteger.ONE)) {
				continue;
			} else {
				this.p_final = result;
				this.q_final = this.number.divide(result);
				if(this.number.equals(p_final.multiply(q_final))) {
					System.out.println("The solution is:");
					System.out.println("p:" + p_final.toString());
					System.out.println("q:" + q_final.toString());
					break;
				}
			}
		}
	}
	
	

}
