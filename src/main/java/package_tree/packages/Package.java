package package_tree.packages;

import java.util.HashSet;
import java.util.Set;

class Package {
    private final Set<String> dependencies;
    private final Set<String> children;

    Package() {
        this.dependencies = new HashSet<>();
        this.children = new HashSet<>();
    }

    synchronized void addChild(String name) {
        children.add(name);
    }

    synchronized void addDependency(String name) {
        dependencies.add(name);
    }

    synchronized Set<String> getChildren() {
        return children;
    }

    synchronized Set<String> getDependencies() {
        return dependencies;
    }

    synchronized void removeChild(String name) {
        children.remove(name);
    }
}
