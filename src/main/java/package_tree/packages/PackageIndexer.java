package package_tree.packages;

import java.util.HashMap;
import java.util.Map;


public class PackageIndexer {
    private final Map<String, Package> packages;


    private PackageIndexer() {
        this.packages = new HashMap<>();
    }

    private static class PackageManagerSingleton{
        private static final PackageIndexer INSTANCE = new PackageIndexer();
    }

    public static PackageIndexer getInstance() {
        return PackageManagerSingleton.INSTANCE;
    }

    public synchronized boolean containsPackage(String packageName) {
        return packages.containsKey(packageName);
    }

    public synchronized boolean packageChildrenExist(String packageName) {
        // if package contains children, unable to remove
        Package pkg = packages.get(packageName);
        for (String child : pkg.getChildren()) {
            if (packages.containsKey(child)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void deletePackage(String packageName) {
        // remove package from all dependencies' children Sets
        Package pkg = packages.get(packageName);
        for (String dependency : pkg.getDependencies()) {
            packages.get(dependency).removeChild(packageName);
        }

        // remove package from packages Map
        packages.remove(packageName);
    }

    public synchronized boolean containsDependencies(String[] dependencies) {
        for (String dependency : dependencies) {
            if (!packages.containsKey(dependency)) {
                return false;
            }
        }
        return true;
    }

    public synchronized void addPackage(String packageName, String[] dependencies) {
        //if package doesn't already exist, create it
        Package pkg;
        if (!packages.containsKey(packageName)) {
            pkg = new Package();
            packages.put(packageName, pkg);
        } else {
            pkg = packages.get(packageName);
        }
        //update dependencies
        for (String dependency : dependencies) {
            packages.get(dependency).addChild(packageName);
            pkg.addDependency(dependency);
        }
    }
}
