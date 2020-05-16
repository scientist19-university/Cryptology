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
		
		int row1[] = {7, 8, 1024, 1536, 2048, 2560};
		int row8[] = {128, 192, 256, 320, 0, 0};
		
		int[][] actual = {};
		try{
			actual = IDEA.getKeyTable(key);
		}
		catch(Exception ex){
			fail(ex.toString());
		}
		
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
}
