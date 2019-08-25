package package_tree.message.commands;

import package_tree.packages.PackageIndexer;

public class IndexCommand extends Command{
    private final String[] dependencies;

    public IndexCommand(String packageName, String[] dependencies) {
        super(packageName);
        this.dependencies = dependencies;
    }

    @Override
    public boolean execute() {
        final PackageIndexer packageIndexer = PackageIndexer.getInstance();
        final String packageName = getPackageName();

        if (!packageIndexer.containsDependencies(dependencies)) {
            return false;
        }

        packageIndexer.addPackage(packageName,dependencies);
        return true;
    }

    public String[] getDependencies() {
        return dependencies;
    }
}
