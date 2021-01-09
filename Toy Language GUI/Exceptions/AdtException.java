package Exceptions;

public class AdtException extends MyException{
    public AdtException(String msg) {
        super(msg);
    }
    @Override
    public String getMessage(){
        return "Adt Expression" + super.getMessage();
    }
}
