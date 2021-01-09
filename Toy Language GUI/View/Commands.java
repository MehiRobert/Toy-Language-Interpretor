package View;

public abstract class Commands {
    private final String key,description;

    public Commands(String key,String description) { this.key = key; this.description = description;}

    public abstract void execute();

    public String getKey() {return this.key;}

    public String getDescription() {return this.description;}

}
