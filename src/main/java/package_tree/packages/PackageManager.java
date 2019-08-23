package package_tree.packages;

import java.util.HashMap;
import java.util.Map;

public class PackageManager {
    private Map<String, Package> packages;

    public PackageManager() {
        this.packages = new HashMap<>();
    }

    public synchronized boolean index(String packageName, String[] dependencies) {
        // check if any dependencies aren't indexed, return false
        for (String dependency : dependencies) {
            if (!query(dependency)) {
                return false;
            }
        }
        //if package doesn't already exist, create it
        Package pkg;
        if (!packages.containsKey(packageName)) {
            pkg = new Package(packageName);
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
        printCurVals();
        return packages.containsKey(packageName);
    }

    public synchronized void printCurVals(){
        for (String key : packages.keySet()) {
            System.out.println(key);
        }
    }
}
