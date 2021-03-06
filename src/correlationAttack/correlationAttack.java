package correlationAttack;

import java.util.*;

public class correlationAttack {

	// ==========================================================
	// PROPERTIES
	// ==========================================================
	
	private String z_string = 
			"0000011101110100011000101101111000100110001101111101000"
			+ "100101001100010010110111110111000111101010001110110010"
			+ "1111011110101101001011001010100000000111111100101000010"
			+ "00000101111011101010011011000";
	
	private int[] z_sequence;
	private int[] z_sequence_reverse;
	private int[] sequence2_13;
	private int[] sequence2_15;
	private int[] sequence2_17;
	
	// ==========================================================
	// CONSTRUCTORS
	// ==========================================================

	/*
	 * Public Constructor
	 */
	public correlationAttack() {
		parseString();
		reverseArray();
		create2_13();
		create2_15();
		create2_17();
	}

	// ==========================================================
	// PUBLIC METHODS
	// ==========================================================

	public static void main(String[] args) {
		
		correlationAttack trial = new correlationAttack();

		//trial.test2_13();
		//trial.test2_15();
		//trial.test2_17();
		
		int u1 = trial.corr(trial.sequence2_13,trial.z_sequence);
		int u2 = trial.corr(trial.sequence2_15,trial.z_sequence);
		int u3 = trial.corr(trial.sequence2_17,trial.z_sequence);
		
		int u1_r = trial.corr(trial.sequence2_13,trial.z_sequence_reverse);
		int u2_r = trial.corr(trial.sequence2_15,trial.z_sequence_reverse);
		int u3_r = trial.corr(trial.sequence2_17,trial.z_sequence_reverse);
		
		int[] z_gen = trial.generateZ(u1, u2, u3);
		int[] z_gen_r = trial.generateZ(u1_r, u2_r, u3_r);
		
		int result = trial.hamming(z_gen, trial.z_sequence);
		int result_r = trial.hamming(z_gen_r,trial.z_sequence_reverse);
		
		System.out.println("The result is: " + result);
		System.out.println("The inverse result is: " + result_r);
		
	}
	
	public void print2_13() {
		
		System.out.println("Sequence 2^13:");
		
		for(int i = 12; i < 1000; i++) {
			int Q12 = this.sequence2_13[i-12];
			int Q11 = this.sequence2_13[i-11];
			int Q10 = this.sequence2_13[i-10];
			int Q9 = this.sequence2_13[i-9];
			int Q8 = this.sequence2_13[i-8];
			int Q7 = this.sequence2_13[i-7];
			int Q6 = this.sequence2_13[i-6];
			int Q5 = this.sequence2_13[i-5];
			int Q4 = this.sequence2_13[i-4];
			int Q3 = this.sequence2_13[i-3];
			int Q2 = this.sequence2_13[i-2];
			int Q1 = this.sequence2_13[i-1];
			int Q0 = this.sequence2_13[i];
			
			System.out.println("["+ Q12+Q11+Q10+Q9+Q8+Q7+Q6+Q5+Q4+Q3+Q2+Q1+Q0+"]");
		}
	}
	
	public void test2_13() {
		
		System.out.println("Test 2^13:");
		
		int o = 0;
		
		for(int i = 12; i < this.sequence2_13.length; i++) {
			
			int Q12 = this.sequence2_13[i-12];
			int Q11 = this.sequence2_13[i-11];
			int Q10 = this.sequence2_13[i-10];
			int Q9 = this.sequence2_13[i-9];
			int Q8 = this.sequence2_13[i-8];
			int Q7 = this.sequence2_13[i-7];
			int Q6 = this.sequence2_13[i-6];
			int Q5 = this.sequence2_13[i-5];
			int Q4 = this.sequence2_13[i-4];
			int Q3 = this.sequence2_13[i-3];
			int Q2 = this.sequence2_13[i-2];
			int Q1 = this.sequence2_13[i-1];
			int Q0 = this.sequence2_13[i];
			
			if( Q0 == 1 & Q1==0 & Q2==0 & Q3==0 
					& Q4==0 & Q5==0 & Q6==0 
					& Q7==0 & Q8==0 & Q9==0 
					& Q10==0 & Q11==0 & Q12==0) {
				o++;
			}
		}
		
		System.out.println("has " + o + " occurences");
	}

	public void test2_15() {
		
		System.out.println("Test 2^15:");
		
		int o = 0;
		
		for(int i = 14; i < this.sequence2_15.length; i++) {
			
			int Q14 = this.sequence2_15[i-14];
			int Q13 = this.sequence2_15[i-13];
			int Q12 = this.sequence2_15[i-12];
			int Q11 = this.sequence2_15[i-11];
			int Q10 = this.sequence2_15[i-10];
			int Q9 = this.sequence2_15[i-9];
			int Q8 = this.sequence2_15[i-8];
			int Q7 = this.sequence2_15[i-7];
			int Q6 = this.sequence2_15[i-6];
			int Q5 = this.sequence2_15[i-5];
			int Q4 = this.sequence2_15[i-4];
			int Q3 = this.sequence2_15[i-3];
			int Q2 = this.sequence2_15[i-2];
			int Q1 = this.sequence2_15[i-1];
			int Q0 = this.sequence2_15[i];
			
			if( Q0 == 1 & Q1==0 & Q2==0 & Q3==0 
					& Q4==0 & Q5==0 & Q6==0 
					& Q7==0 & Q8==0 & Q9==0 
					& Q10==0 & Q11==0 & Q12==0 
					& Q13==0 & Q14==0) {
				o++;
			}
		}
		
		System.out.println("has " + o + " occurences");
	}
	
	public void test2_17() {
		
		System.out.println("Test 2^17:");
		
		int o = 0;
		
		for(int i = 16; i < this.sequence2_17.length; i++) {
			
			int Q16 = this.sequence2_17[i-16];
			int Q15 = this.sequence2_17[i-15];
			int Q14 = this.sequence2_17[i-14];
			int Q13 = this.sequence2_17[i-13];
			int Q12 = this.sequence2_17[i-12];
			int Q11 = this.sequence2_17[i-11];
			int Q10 = this.sequence2_17[i-10];
			int Q9 = this.sequence2_17[i-9];
			int Q8 = this.sequence2_17[i-8];
			int Q7 = this.sequence2_17[i-7];
			int Q6 = this.sequence2_17[i-6];
			int Q5 = this.sequence2_17[i-5];
			int Q4 = this.sequence2_17[i-4];
			int Q3 = this.sequence2_17[i-3];
			int Q2 = this.sequence2_17[i-2];
			int Q1 = this.sequence2_17[i-1];
			int Q0 = this.sequence2_17[i];
			
			if( Q0 == 1 & Q1==0 & Q2==0 & Q3==0 
					& Q4==0 & Q5==0 & Q6==0 
					& Q7==0 & Q8==0 & Q9==0 
					& Q10==0 & Q11==0 & Q12==0 
					& Q13==0 & Q14==0 & Q15==0
					& Q16==0) {
				o++;
			}
		}
		
		System.out.println("has " + o + " occurences");
	}
	
	
	// ==========================================================
	// PRIVATE METHODS
	// ==========================================================

	/*
	 * Convert the String into a integer array
	 */
	private void parseString(){
		
		int length = z_string.length();
		this.z_sequence = new int[length];
		
		for (int i = 0; i < length; i++) {
			z_sequence[i] = Integer.parseInt(String.valueOf(z_string.charAt(i)));
		}
		
	}
	
	/*
	 * Reverse the string
	 */
	private void reverseArray() {
	
		this.z_sequence_reverse = new int[z_sequence.length];
		
		for (int i = 0; i < this.z_sequence_reverse.length; i++) {
			this.z_sequence_reverse[i] = this.z_sequence[this.z_sequence.length-i-1];
		}
	}
	
	/*
	 * Create lfrs with period 2^13-1
	 * Initial state: 0000000000001
	 * Primitive Element: 1+D+D^2 +D^4 +D^6 +D^7 +D^10 +D^11 +D^13,
	 */
	private void create2_13() {
		
		// Initialize the sequence
		this.sequence2_13 = new int[2*8191];
		sequence2_13[12] = 1;
		
		for (int i = 13; i<sequence2_13.length; i++) {
			// define the outputs of the registers for better readability
			int Q2 = sequence2_13[i-2]; //11
			int Q3 = sequence2_13[i-3]; //10
			int Q6 = sequence2_13[i-6]; //7
			int Q7 = sequence2_13[i-7]; //6
			int Q9 = sequence2_13[i-9]; //4
			int Q11 = sequence2_13[i-11]; //2
			int Q12 = sequence2_13[i-12]; //1
			int Q13 = sequence2_13[i-13]; //0
			
			sequence2_13[i] = (Q13 + Q12 + Q11 + Q9 + Q7 + Q6 + Q3 + Q2) % 2;
		}
	}
	
	/*
	 * Create lfrs with period 2^15-1
	 * Initial state: 000000000000001
	 * Primitive Element: 1+D^2 +D^4 +D^6 +D^7 +D^10 +D^11 +D^13 +D^15 ,
	 */
	private void create2_15() {
		
		// Initialize the sequence
		this.sequence2_15 = new int[2*32767];
		sequence2_15[14] = 1;
		
		for (int i = 15; i<sequence2_15.length; i++) {
			// define the outputs of the registers for better readability
			int Q2 = sequence2_15[i-2]; //13
			int Q4 = sequence2_15[i-4]; //11
			int Q5 = sequence2_15[i-5]; //10
			int Q8 = sequence2_15[i-8]; //7
			int Q9 = sequence2_15[i-9]; //6
			int Q11 = sequence2_15[i-11]; //4
			int Q13 = sequence2_15[i-13]; //2
			int Q15 = sequence2_15[i-15]; //0
			
			sequence2_15[i] = (Q15 + Q13 + Q11 + Q9 + Q8 + Q5 + Q4 + Q2) % 2;
		}
	}
	 
	/*
	 * Create lfrs with period 2^17-1
	 * Initial state: 00000000000000001
	 * Primitive Element: 1 +D^2 +D^4 +D^5 +D^8 +D^10 +D^13 +D^16 +D^17,
	 */
	private void create2_17() {
		
		// Initialize the sequence
		this.sequence2_17 = new int[2*131071];
		sequence2_17[16] = 1;
		
		for (int i = 17; i<sequence2_17.length; i++) {
			// define the outputs of the registers for better readability
			int Q1 = sequence2_17[i-1]; //16
			int Q4 = sequence2_17[i-4]; //13
			int Q7 = sequence2_17[i-7]; //10
			int Q9 = sequence2_17[i-9]; //8
			int Q12 = sequence2_17[i-12]; //5
			int Q13 = sequence2_17[i-13]; //4
			int Q15 = sequence2_17[i-15]; //2
			int Q17 = sequence2_17[i-17]; //0
			
			sequence2_17[i] = (Q17 + Q15 + Q13 + Q12 + Q9 + Q7 + Q4 + Q1) % 2;
		}
	}
	

	private int corr(int[] u, int[] z_seq) {
		
		int p_star = z_seq.length;
		int times = 0;
		int index = 0;
		
		for(int i = z_seq.length - 1; i < u.length; i++) {
			int p_star_new = hamming(z_seq, Arrays.copyOfRange(u, i - z_seq.length + 1, i + 1));
			if (p_star_new == p_star) times ++;
			if (p_star_new < p_star){
				p_star = p_star_new;
				times = 1;
				index = i - z_seq.length + 1;
			}
		}
		System.out.println("The index of the highest correlation is of: " + index);
		System.out.println("It appeared " + times + " times.");
		System.out.println("The hamming distance: " + p_star);
		return index;
	}
	
	/*
	 * Hamming distance between two vectors of the same length
	 * Number of different entries of two test vectors of the same length
	 */
	private int hamming(int[] test, int[] u) {
				
		int d = 0;
		
		for (int i = 0; i < test.length; i++) {
			if (test[i] != u[i]) d++;
		}
		return d;
	}
	
	/*
	 * Generate an artificial z based on the indexes given
	 */
	private int[] generateZ(int index_2_13, int index_2_15, int index_2_17) {
		
		int[] z_gen = new int[z_sequence.length];
		
		for (int i = 0; i < z_gen.length; i++) {
		
			int u1 = this.sequence2_13[(index_2_13 + i) % this.sequence2_13.length];
			int u2 = this.sequence2_15[(index_2_15 + i) % this.sequence2_15.length];
			int u3 = this.sequence2_17[(index_2_17 + i) % this.sequence2_17.length];
			
			z_gen[i] = ((u1 + u2 + u3) > 1) ? 1 : 0;
			
		}
		
		return z_gen;
	}
	
	
	
}
