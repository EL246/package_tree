package package_tree.message.commands;

import package_tree.packages.PackageManager;

public class RemoveCommand extends Command{
    public RemoveCommand(String packageName) {
        super(packageName);
    }

    @Override
    public boolean execute(PackageManager packageManager) {
        return packageManager.remove(getPackageName());
    }
}
