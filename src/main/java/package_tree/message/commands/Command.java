package package_tree.message.commands;

public abstract class Command {
    private String packageName;

    Command(String packageName) {
        this.packageName = packageName;
    }

    public abstract void execute();
}
