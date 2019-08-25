package package_tree.message.commands;

import package_tree.packages.PackageIndexer;

public class RemoveCommand extends Command {
    public RemoveCommand(String packageName) {
        super(packageName);
    }

    @Override
    public boolean execute() {
        final PackageIndexer packageIndexer = PackageIndexer.getInstance();
        final String packageName = getPackageName();

        // if package doesn't exist, return
        if (!packageIndexer.containsPackage(packageName)) {
            return true;
        }

        // if package contains children, unable to remove
        if (packageIndexer.packageChildrenExist(packageName)) {
            return false;
        }

        //  delete package
        packageIndexer.deletePackage(packageName);

        return true;
    }
}