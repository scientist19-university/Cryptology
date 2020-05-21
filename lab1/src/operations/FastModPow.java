package operations;

import java.math.BigInteger;

public class FastModPow {
	
    public static BigInteger modPow(BigInteger x, BigInteger exponent, BigInteger m) {
    	
        if (m.compareTo(BigInteger.ZERO) <= 0)
            throw new ArithmeticException("non-positive modulo");
        if (exponent.compareTo(BigInteger.ZERO) < 0)
            return modPow(x.modInverse(m), exponent.negate(), m);
        if (exponent.equals(BigInteger.ONE))
            return x.mod(m);

        BigInteger s = BigInteger.ONE;
        BigInteger t = x;
        BigInteger u = exponent;

        while (!u.equals(BigInteger.ZERO)) {
        	
            if (u.and(BigInteger.ONE).equals(BigInteger.ONE))
                s = s.multiply(t).mod(m);
            u = u.shiftRight(1);
            t = t.multiply(t).mod(m);
        }
        return s;
    }
}
