package package_tree.message.commands;

import package_tree.packages.PackageIndexer;

public class QueryCommand extends Command {
    public QueryCommand(String packageName) {
        super(packageName);
    }

    @Override
    public boolean execute() {
       return PackageIndexer.getInstance().query(getPackageName());
    }
}
