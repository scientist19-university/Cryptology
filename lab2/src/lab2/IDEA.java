package lab2;

import java.util.*;

public class IDEA {
	
	static final private int MOD = 65537;
	
	public String encrypt(String input, String key) throws Exception{
		if (key.length() != 16)
			throw new Exception("The algorithm requires 128-bit key");
		
		String binKey = toBinary(key);
		int[][] keys = new int[9][6];
		
		int n = 0;
		for (int i = 0; i < 7; i++){
			int raw[] = convertToNumbers(binKey, 16);
			for (int j = 0; j < 8; j++){
				keys[n/6][n%6] = raw[j];
				n++;
				
				if (n == 52) break;
			}	
			binKey = leftCycledShift(binKey, 25);
		}
		
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
	
	private String toBinary(String input) throws Exception{
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
	
	private int[] convertToNumbers(String bin_input, int size_in_bits){
		
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
	
	private String leftCycledShift(String input, int d){
		return input.substring(d) + input.substring(0, d);
	}
	
	private String encryptBlock(String binBlock, int[][] keys){
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
	
	private int[] goRound(int[] blocks, int[] keys){
		
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
	
	private int[] outputConversion(int[] blocks, int[] keys){
		int res[] = new int[4];
		
		res[0] = modMult(blocks[0], keys[0]);
		res[1] = modAdd(blocks[1], keys[1]);
		res[2] = modAdd(blocks[2], keys[2]);
		res[2] = modMult(blocks[3], keys[3]);
		
		return res;
	}
	
	private int modMult(int a, int b){
		return a*b % MOD;
	}
	
	private int modAdd(int a, int b){
		return (a+b) % MOD;
	}
}
