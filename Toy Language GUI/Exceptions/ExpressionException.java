package Exceptions;

public class ExpressionException extends MyException {
    public ExpressionException(String msg) {super(msg);}

    @Override
    public String getMessage() {return "Expression Exception: " + super.getMessage();}


}
