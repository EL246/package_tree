package package_tree.message.commands;

import package_tree.packages.PackageManager;

public abstract class Command {
    private String packageName;

    Command(String packageName) {
        this.packageName = packageName;
    }

    public abstract boolean execute(PackageManager packageManager);

    public String getPackageName() {
        return packageName;
    }
}
