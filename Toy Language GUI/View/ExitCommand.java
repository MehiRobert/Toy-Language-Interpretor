package View;

public class ExitCommand extends Commands{

    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {

        System.exit(0);
    }
}
