package package_tree.message.commands;

import package_tree.packages.PackageManager;

public class QueryCommand extends Command {
    public QueryCommand(String packageName) {
        super(packageName);
    }

    @Override
    public boolean execute() {
       return PackageManager.getInstance().query(getPackageName());
    }
}
