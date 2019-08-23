package package_tree.packages;

import java.util.HashSet;
import java.util.Set;

public class Package {
    private String name;
    private Set<String> dependencies;
    private Set<String> children;

    public Package(String name) {
        this.name = name;
        this.dependencies = new HashSet<>();
        this.children = new HashSet<>();
    }

    public synchronized void addChild(String name) {
        children.add(name);
    }

    public synchronized void addDependency(String name) {
        dependencies.add(name);
    }

    public synchronized Set<String> getChildren() {
        return children;
    }

    public synchronized Set<String> getDependencies() {
        return dependencies;
    }

    public synchronized void removeChild(String name) {
        children.remove(name);
    }
}
