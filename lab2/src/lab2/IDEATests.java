package lab2;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class IDEATests extends IDEA{

	@Test
	public void testToBinaryMethod() {
		String s = "abc";
		String expected = "011000010110001001100011";
		String actual = "";
		
		try{
			actual = IDEA.toBinary(s);
		}
		catch(Exception ex){
			fail(ex.toString());
		}
		
		assertEquals(expected, actual);
	}	
	
	@Test
	public void testConvertToNumbersMethod() {
		String s = "011000010110001001100011";
		int expected[] = {97, 98, 99};
		int actual[] = {};
		
		try{
			actual = IDEA.convertToNumbers(s, 8);
		}
		catch(Exception ex){
			fail(ex.toString());
		}
		
		Assert.assertArrayEquals(expected, actual);
	}	
	
	@Test
	public void testGetKeyTableMethod(){
		String key = "";
		for (int i = 1; i <= 8; i++){
			key += (char)0;
			key += (char)i;
		}
		
		int row0[] = {1, 2, 3, 4, 5, 6};
		int row1[] = {7, 8, 1024, 1536, 2048, 2560};
		int row8[] = {128, 192, 256, 320, 0, 0};
		
		int[][] actual = {};
		try{
			actual = IDEA.getKeyTable(key);
		}
		catch(Exception ex){
			fail(ex.toString());
		}

		Assert.assertArrayEquals(row0, actual[0]);
		Assert.assertArrayEquals(row1, actual[1]);
		Assert.assertArrayEquals(row8, actual[8]);
	}
	
	@Test
	public void testGetKeyTableForDecryptionMethod(){
		String key = "";
		for (int i = 1; i <= 8; i++){
			key += (char)0;
			key += (char)i;
		}
		
		int row0[] = {65025, 65344, 65280, 26010, 49152, 57345};
		
		int[][] actual = {};
		try{
			actual = IDEA.getKeyTableForDecryption(IDEA.getKeyTable(key));
		}
		catch(Exception ex){
			fail(ex.toString());
		}
		
		Assert.assertArrayEquals(row0, actual[0]);
	}
	
	@Test
	public void testMultInversionMethod(){
		int x = 128;
		int expected = 65025;
		
		int actual = IDEA.multInversion(x);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGoRoundMethod(){
		int blocks1[] = {0, 1, 2, 3};
		int keys1[] = {1, 2, 3, 4, 5, 6};
		int expected1[] = {240, 245, 266, 261};
		int actual1[] = IDEA.goRound(blocks1, keys1);
		
		Assert.assertArrayEquals(expected1, actual1);
		
		int blocks2[] = expected1;
		int keys2[] = {7, 8, 1024, 1536, 2048, 2560};		
		int expected2[] = {8751, 8629, 62558, 59737};
		int actual2[] = IDEA.goRound(blocks2, keys2);
		
		Assert.assertArrayEquals(expected2, actual2);	
	}
	
	@Test
	public void testOutputConversionMethod(){
		int blocks[] = {2596, 152, 60523, 18725};
		int keys[] = {128, 192, 256, 320};
		
		int expected[] = {4603, 60715, 408, 28133};
		int actual[] = IDEA.outputConversion(blocks, keys);
		
		Assert.assertArrayEquals(expected, actual);		
	}
	
	@Test
	public void testEncryptBlockToNumbersMethod(){
		String key = "";
		for (int i = 1; i <= 8; i++){
			key += (char)0;
			key += (char)i;
		}
		int keys[][] = {};
		try{
			keys = IDEA.getKeyTable(key);
		}
		catch(Exception ex){
			fail(ex.toString());
		}
		
		String input = "";
		for (int i = 0; i < 4; i++){
			input += (char)0;
			input += (char)i;
		}
		String binBlock = "";
		try{
			binBlock = IDEA.toBinary(input);
		}
		catch(Exception ex){
			fail(ex.toString());
		}				
		
		int expected[] = {4603, 60715, 408, 28133};
		int actual[] = IDEA.encryptBlockToNumbers(binBlock, keys);
		
		Assert.assertArrayEquals(expected, actual);		
	}
}
