package package_tree.message.commands;

import package_tree.message.parser.ParseException;

public abstract class Command {
    private final String packageName;

    Command(String packageName) {
        this.packageName = packageName;
    }

    public abstract boolean execute() throws ParseException;

    String getPackageName() {
        return packageName;
    }
}
