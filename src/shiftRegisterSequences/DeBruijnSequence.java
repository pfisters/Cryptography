package shiftRegisterSequences;

public class DeBruijnSequence {

	// ==========================================================
	// PROPERTIES
	// ==========================================================
	private final int sequenceLength = 10003;
	private int[] sequence24;
	private int[] sequence54;
	private int[] sequence;
		

	// ==========================================================
	// CONSTRUCTORS
	// ==========================================================

	/*
	 * Public Constructor
	 */
	public DeBruijnSequence() {
		this.sequence24 = new int[sequenceLength];
		this.sequence54 = new int[sequenceLength];
		this.sequence = new int[sequenceLength];
		generate24();
		generate54();
		generateSequence();
	}

	// ==========================================================
	// PUBLIC METHODS
	// ==========================================================

	public static void main(String[] args) {
		DeBruijnSequence deBruijn = new DeBruijnSequence();
	}
	
	public void printSequence24() {
		
		System.out.println("Sequence 2^4:");
		
		for(int i = 3; i < 21; i++) {
			
			int Q3 = this.sequence24[i-3];
			int Q2 = this.sequence24[i-2];
			int Q1 = this.sequence24[i-1];
			int Q0 = this.sequence24[i];
			
			System.out.println("[" + Q3 + "," + Q2 + "," + Q1 + "," + Q0 + "]");
		}
		
	}
	
	public void printSequence54() {
		System.out.println("Sequence 5^4:");
		
		for(int i = 3; i < 630; i++) {
			
			int Q3 = this.sequence54[i-3];
			int Q2 = this.sequence54[i-2];
			int Q1 = this.sequence54[i-1];
			int Q0 = this.sequence54[i];
			
			System.out.println("[" + Q3 + "," + Q2 + "," + Q1 + "," + Q0 + "]");
		}
		
	}

	public void printSequenc() {
		System.out.println("Sequence 10^4:");
		
		for(int i = 3; i < this.sequenceLength; i++) {
			
			int Q0 = this.sequence[i];
			System.out.print(Q0);
		}
		
		System.out.print("\n");

	}

	
	// ==========================================================
	// PRIVATE METHODS
	// ==========================================================

	/*
	 * Generate the de Bruijn sequence with period 2^4
	 * primitive element: x^4+x+1
	 * initial state: 0000
	 */
	private void generate24() {
		
		for (int i = 4; i < this.sequenceLength; i++) {
									
			// define the outputs of the registers for better readability
			int Q4 = this.sequence24[i-4];
			int Q3 = this.sequence24[i-3];
			int Q2 = this.sequence24[i-2];
			int Q1 = this.sequence24[i-1];
			
			// define the sequence
			if ((Q4==1) & (Q3==0) & (Q2==0) & (Q1==0)) this.sequence24[i] = 0;
			else if ((Q4==0) & (Q3==0) & (Q2==0) & (Q1==0)) this.sequence24[i] = 1; 
			else this.sequence24[i] = (Q3 + Q4) % 2;

		}
		
	}
	
	/*
	 * Generate the de Bruijn sequence with period 5^4
	 * primitive element: x^4+x^2+2x+2
	 * initial state: 0000
	 */
	private void generate54() {
						
		for( int i = 4; i < this.sequenceLength; i++) {
			// define the outputs of the registers for better readability
			int Q4 = this.sequence54[i-4];
			int Q3 = this.sequence54[i-3];
			int Q2 = this.sequence54[i-2];
			int Q1 = this.sequence54[i-1];
			
			// define the sequence
			if ((Q4==2) & (Q3==0) & (Q2==0) & (Q1==0)) this.sequence54[i] = 0;
			else if ((Q4==0) & (Q3==0) & (Q2==0) & (Q1==0)) this.sequence54[i] = 1; 
			else this.sequence54[i] = (4*Q2+3*Q3+3*Q4) % 5;
			
		}
		
	}
	
	/*
	 * Generate the output sequence
	 * Merge the two sequences according to the Chinese remainder theorem
	 */
	private void generateSequence() {
		
		for (int i = 0; i < this.sequenceLength; i++) {
			this.sequence[i] = (this.sequence24[i] * this.sequence54[i]) % 10;
		}
	}
	
	

}
