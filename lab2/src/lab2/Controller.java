package lab2;

public class Controller {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String input = "Hello world!",
			   key = "qwertyuiopasdfgh";
		
		String encr = "";
		try{
			encr = IDEA.encrypt(input, key);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		System.out.println(encr);

		String decr = "";		
		try{
			decr = IDEA.decrypt(encr, key);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		System.out.println(decr);		
	}

}
