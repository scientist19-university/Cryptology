import org.junit.Test;
import org.junit.Assert;

public class MD5Tests {

    @Test
    public void testHashGeneral() {

    	MD5 md5 = new MD5();
    	
    	Assert.assertEquals("D41D8CD98F00B204E9800998ECF8427E", md5.getHash(""));
    	Assert.assertEquals("0CC175B9C0F1B6A831C399E269772661", md5.getHash("a"));
    	Assert.assertEquals("900150983CD24FB0D6963F7D28E17F72", md5.getHash("abc"));
    	Assert.assertEquals("6512BD43D9CAA6E02C990B0A82652DCA", md5.getHash("11"));
    	Assert.assertEquals("202CB962AC59075B964B07152D234B70", md5.getHash("123"));
    	Assert.assertEquals("1BC29B36F623BA82AAF6724FD3B16718", md5.getHash("md5"));
    	Assert.assertEquals("F96B697D7CB7938D525A2F31AAF161D0", md5.getHash("message digest"));
    	Assert.assertEquals("C3FCD3D76192E4007DFB496CCA67E13B", md5.getHash("abcdefghijklmnopqrstuvwxyz"));
    	Assert.assertEquals("D174AB98D277D9F5A5611C2C9F419D9F", md5.getHash("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"));
    }
}