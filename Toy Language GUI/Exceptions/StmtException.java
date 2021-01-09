package Exceptions;

public class StmtException extends MyException {

    public StmtException(String msg) {
        super(msg);
    }
    @Override
    public String getMessage(){
        return "Statement Exception: " + super.getMessage();
    }
}
