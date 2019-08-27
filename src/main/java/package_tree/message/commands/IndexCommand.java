package package_tree.message.commands;

import package_tree.packages.Package;
import package_tree.packages.PackageIndexer;

public class IndexCommand extends Command {
    private final String[] dependencies;

    public IndexCommand(String packageName, String[] dependencies) {
        super(packageName);
        this.dependencies = dependencies;
    }

    @Override
    public boolean execute() {
        Package pkg = new Package(getPackageName(), dependencies);
        return PackageIndexer.add(pkg);
    }

    public String[] getDependencies() {
        return dependencies;
    }
}
