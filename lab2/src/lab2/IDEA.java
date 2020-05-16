package lab2;

import java.util.*;

public class IDEA {
	
	static final private int MOD_MULT = 65537;	// 2^16 + 1
	static final private int MOD_ADD = 65536;	// 2^16
	
	public static String encrypt(String input, String key) throws Exception{
		if (key.length() != 16)
			throw new Exception("The algorithm requires 128-bit key");
		
		int[][] keys = getKeyTable(key);
		
		while (input.length() % 8 != 0)
			input += '#';
		
		String res = "";
		String binInput = toBinary(input);
		for (int i = 0; i < binInput.length() / 64; i++){
			String binBlock = binInput.substring(i*64,(i+1)*64);
			String encryptedBlock = encryptBlock(binBlock, keys);
			
			res += encryptedBlock;
		}
		
		return res;
	}
	
	public static String decrypt(String input, String key) throws Exception{
		if (key.length() != 16)
			throw new Exception("The algorithm requires 128-bit key");
		
		int[][] keys = getKeyTableForDecryption(getKeyTable(key));
		
		String res = "";
		String binInput = toBinary(input);
		for (int i = 0; i < binInput.length() / 64; i++){
			String binBlock = binInput.substring(i*64,(i+1)*64);
			String decryptedBlock = encryptBlock(binBlock, keys);
			
			res += decryptedBlock;
		}
		
		return res;		
	}
	
	protected static String toBinary(String input) throws Exception{
		String res = "";
		for (int i = 0; i < input.length(); i++){
			String tmp = Integer.toBinaryString(input.charAt(i));
			if (tmp.length() > 8)
				throw new Exception("Unacceptable characters");
			while (tmp.length() < 8)
				tmp = '0' + tmp;
			
			res = res + tmp;
		}
		
		return res;
	}
	
	protected static int[] convertToNumbers(String bin_input, int size_in_bits){
		
//		while (bin_input.length() % size_in_bits != 0)
//			bin_input += '0';
		
		int n = bin_input.length() / size_in_bits;
 		int[] res = new int[n];
		for (int i = 0; i < n; i++){
			String bin_key = bin_input.substring(i*size_in_bits, (i+1)*size_in_bits);
			res[i] = Integer.parseInt(bin_key, 2);
		}
		
		return res;
	}
	
	protected static String leftCycledShift(String input, int d){
		return input.substring(d) + input.substring(0, d);
	}
	
	protected static String encryptBlock(String binBlock, int[][] keys){
		int[] subblocks = convertToNumbers(binBlock, 16);
		
		for (int i = 0; i < 8; i++){
			subblocks = goRound(subblocks, keys[i]);
		}
		
		subblocks = outputConversion(subblocks, keys[8]);
		
		String res = "";
		for (int i = 0; i < 4; i++){
			
			
			String bin = Integer.toBinaryString(subblocks[i]);
			while (bin.length() < 16)
				bin = '0' + bin;
			
			int[] numbers256 = convertToNumbers(bin, 8);
			res += (char)numbers256[0];
			res += (char)numbers256[1];
		}
		
		return res;
	}
	
	protected static int[] goRound(int[] blocks, int[] keys){
		
		int a = modMult(blocks[0], keys[0]);
		int b = modAdd(blocks[1], keys[1]);
		int c = modAdd(blocks[2], keys[2]);
		int d = modMult(blocks[3], keys[3]);
		int e = a ^ c;
		int f = b ^ d;
		
		int res[] = new int[4];
		res[0] = a ^ modMult(keys[5], modAdd(f, modMult(e, keys[4])));
		res[1] = c ^ modMult(keys[5], modAdd(f, modMult(e, keys[4])));
		res[2] = b ^ modAdd(modMult(e, keys[4]), modMult(keys[5], modAdd(f, modMult(e, keys[4]))));
		res[3] = d ^ modAdd(modMult(e, keys[4]), modMult(keys[5], modAdd(f, modMult(e, keys[4]))));
		
		return res;
	}
	
	protected static int[] outputConversion(int[] blocks, int[] keys){
		int res[] = new int[4];
		
		res[0] = modMult(blocks[0], keys[0]);
		res[1] = modAdd(blocks[1], keys[1]);
		res[2] = modAdd(blocks[2], keys[2]);
		res[2] = modMult(blocks[3], keys[3]);
		
		return res;
	}
	
	protected static int modMult(int a, int b){
		return a*b % MOD_MULT;
	}
	
	protected static int modAdd(int a, int b){
		return (a+b) % MOD_ADD;
	}
	
	protected static int[] gcd (int a, int b) {
		if (a == 0) {
			int res[] = new int[3];
			res[0] = b;
			res[1] = 0;
			res[2] = 1;

			return res;
		}

		int d[] = gcd (b%a, a);
		
		int res[] = new int[3];
		res[0] = d[0];
		res[1] = d[2] - (b / a) * d[1];
		res[2] = d[1];
		
		return res;
	}
	
	protected static int multInversion(int a){
		int[] gcdRes = gcd(MOD_MULT, a);
		return (gcdRes[2] + MOD_MULT) % MOD_MULT;
	}
	
	protected static int addInversion(int a){
		return MOD_ADD - a;
	}
	
	protected static int[][] getKeyTable(String key) throws Exception{
		String binKey = toBinary(key);
		int[][] res = new int[9][6];
		
		int n = 0;
		for (int i = 0; i < 7; i++){
			int raw[] = convertToNumbers(binKey, 16);
			for (int j = 0; j < 8; j++){
				res[n/6][n%6] = raw[j];
				n++;
				
				if (n == 52) break;
			}	
			binKey = leftCycledShift(binKey, 25);
		}
		
		return res;
	}
	
	protected static int[][] getKeyTableForDecryption(int[][] keyTable){
		
		int res[][] = new int[9][6];
		
		// 0 and 3 columns
		for (int i = 0; i < 9; i++){
			res[i][0] = multInversion(keyTable[8 - i][0]);
			res[i][3] = multInversion(keyTable[8 - i][3]);
		}
		
		// 4 and 5 columns
		for (int i = 0; i < 8; i++){
			res[i][4] = keyTable[7 - i][4];
			res[i][5] = keyTable[7 - i][5];
		}		
		
		// 1 and 2 columns
		for (int i = 1; i < 8; i++){
			res[i][1] = addInversion(keyTable[8 - i][2]);
			res[i][2] = addInversion(keyTable[8 - i][1]);
		}
		res[0][1] = addInversion(keyTable[8][1]);
		res[0][2] = addInversion(keyTable[8][2]);
		res[8][1] = addInversion(keyTable[0][1]);
		res[8][2] = addInversion(keyTable[0][2]);
		
		return res;
	}
}
