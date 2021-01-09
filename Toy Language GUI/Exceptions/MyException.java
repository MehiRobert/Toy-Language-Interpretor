package Exceptions;

public class MyException extends Exception{

        public MyException(String msg) {super(msg);}
        public String getMessage()
        {
            return super.getMessage();
        }

}
