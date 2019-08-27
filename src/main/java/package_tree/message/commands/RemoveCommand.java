package package_tree.message.commands;

import package_tree.packages.PackageIndexer;

public class RemoveCommand extends Command {
    public RemoveCommand(String packageName) {
        super(packageName);
    }

    @Override
    public boolean execute() {
        String packageName = getPackageName();

        // if package doesn't exist, return true
        if (!PackageIndexer.containsPackage(packageName)) {
            return true;
        }

        //  delete package
        return PackageIndexer.delete(packageName);
    }
}