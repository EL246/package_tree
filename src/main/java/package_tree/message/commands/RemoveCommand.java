package package_tree.message.commands;

import package_tree.packages.PackageIndexer;

public class RemoveCommand extends Command {
    public RemoveCommand(String packageName) {
        super(packageName);
    }

    @Override
    public boolean execute() {
        String packageName = getPackageName();

        // Delete package.
        return PackageIndexer.delete(packageName);
    }
}