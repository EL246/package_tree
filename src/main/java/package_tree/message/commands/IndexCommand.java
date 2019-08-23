package package_tree.message.commands;

import package_tree.packages.PackageManager;

public class IndexCommand extends Command{
    private String[] dependencies;

    public IndexCommand(String packageName, String[] dependencies) {

        super(packageName);
        this.dependencies = dependencies;
    }

    @Override
    public boolean execute(PackageManager packageManager) {
        return packageManager.index(getPackageName(),dependencies);
    }

    public String[] getDependencies() {
        return dependencies;
    }
}
