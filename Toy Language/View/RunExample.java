package View;

import Controller.Controller;
import Exceptions.MyException;

import java.io.IOException;

public class RunExample extends Commands{
    private Controller ctr;
    public RunExample(String key, String description,Controller ctr) {
        super(key, description);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try
        {
            ctr.allStep();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
