package package_tree.message.commands;

import package_tree.message.ParseException;
import package_tree.packages.PackageManager;

public class IndexCommand extends Command{
    private final String[] dependencies;

    public IndexCommand(String packageName, String[] dependencies) {
        super(packageName);
        this.dependencies = dependencies;
    }

    @Override
    public boolean execute() throws ParseException {
        return PackageManager.getInstance().index(getPackageName(),dependencies);
    }

    public String[] getDependencies() {
        return dependencies;
    }
}
