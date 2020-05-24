package tests;

import operations.FermatPrimality;
import operations.MillerRabinPrimality;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

public class PrimesTest {
	
    static final int NUMBER_OF_TESTS = 10;
    static final int NUMBER_OF_BITS = 512;

    String[] primesBig = {"3", "3613", "7297",
            "226673591177742970257407", "2932031007403" };
    String[] nonPrimesBig = { "3341", "2932021007403",
            "226673591177742970257405" };

    final int[] primesSmall = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
            43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
            101, 103, 107, 109, 113, 127, 131, 137, 139, 149,
            151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199,
            271, 331, 397, 547, 631, 919, 1657, 1801, 1951, 2269, 2437,
            2791, 3169, 3571, 4219, 4447, 5167, 5419, 6211, 7057, 7351,
            8269, 9241, 10267, 11719, 12097, 13267, 13669, 16651, 19441, 19927, 22447, 23497};

    final int[] notPrimesSmall = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22,
            24, 25, 26, 27, 28, 30, 32, 33, 34, 35, 36, 38,
            39, 40, 42, 44, 45, 46, 48, 49, 50, 51, 52, 54};

    @Test
    public void primesAlgoTest() {
    	
        Random random = new Random();
        
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
        	
            BigInteger testNumber = BigInteger.probablePrime(NUMBER_OF_BITS, random);
            Assert.assertTrue(FermatPrimality.isProbablePrime(testNumber,50));
            Assert.assertTrue(MillerRabinPrimality.isProbablePrime(testNumber,50));
        }

        for (int i = 0; i < primesSmall.length; ++i) {
        	
            Assert.assertTrue(FermatPrimality.isProbablePrime(BigInteger.valueOf(primesSmall[i]),50));
            Assert.assertTrue(MillerRabinPrimality.isProbablePrime(BigInteger.valueOf(primesSmall[i]),50));
        }

        for (int i = 0; i < notPrimesSmall.length; ++i) {
        	
            Assert.assertFalse(FermatPrimality.isProbablePrime(BigInteger.valueOf(notPrimesSmall[i]),50));
            Assert.assertFalse(MillerRabinPrimality.isProbablePrime(BigInteger.valueOf(notPrimesSmall[i]),50));
        }

        for (int i = 0; i < primesBig.length; ++i) {
        	
            Assert.assertTrue(FermatPrimality.isProbablePrime(new BigInteger(primesBig[i]),50));
            Assert.assertTrue(MillerRabinPrimality.isProbablePrime(new BigInteger(primesBig[i]),50));
        }
        for (int i = 0; i < nonPrimesBig.length; ++i) {
        	
            Assert.assertFalse(FermatPrimality.isProbablePrime(new BigInteger(nonPrimesBig[i]),50));
            Assert.assertFalse(MillerRabinPrimality.isProbablePrime(new BigInteger(nonPrimesBig[i]),50));
        }
    }
}
