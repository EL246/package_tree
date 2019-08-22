package package_tree.message.commands;

public class IndexCommand extends Command{
    private String[] dependencies;

    public IndexCommand(String packageName, String[] dependencies) {

        super(packageName);
        this.dependencies = dependencies;
    }

    @Override
    public void execute() {

    }

    public String[] getDependencies() {
        return dependencies;
    }
}
