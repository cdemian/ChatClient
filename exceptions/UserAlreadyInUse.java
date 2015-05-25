package exceptions;

public class UserAlreadyInUse extends Exception{

	
	private static final long serialVersionUID = 1L;
	public UserAlreadyInUse(){
		super();
	}
	public UserAlreadyInUse(String message){
		super(message);
	}
}
