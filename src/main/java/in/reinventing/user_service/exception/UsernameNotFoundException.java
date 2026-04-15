package in.reinventing.user_service.exception;

public class UsernameNotFoundException extends Exception{
    public UsernameNotFoundException(String message){
        super(message);
    }
}
