package package_tree.packages;

import java.util.HashMap;
import java.util.Map;


public class PackageManager {
    private static PackageManager instance;
    private final Map<String, Package> packages;


    private PackageManager() {
        this.packages = new HashMap<>();
    }

    public synchronized static PackageManager getInstance() {
        if (instance == null) {
            instance = new PackageManager();
        }
        return instance;
    }

    public synchronized boolean index(String packageName, String[] dependencies) {

        for (String dependency : dependencies) {
            if (!packages.containsKey(dependency)) {
                return false;
            }
        }
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
        return true;
    }

    public synchronized boolean remove(String packageName) {
        // if package doesn't exist, return
        if (!packages.containsKey(packageName)) {
            return true;
        }
        // if package contains children, unable to remove
        Package pkg = packages.get(packageName);
        for (String child : pkg.getChildren()) {
            if (packages.containsKey(child)) {
                return false;
            }
        }
        // remove package from all dependencies' children Sets
        for (String dependency : pkg.getDependencies()) {
            packages.get(dependency).removeChild(packageName);
        }

        // remove package from packages Map
        packages.remove(packageName);

        return true;
    }

    public synchronized boolean query(String packageName) {
        return packages.containsKey(packageName);
    }
}
