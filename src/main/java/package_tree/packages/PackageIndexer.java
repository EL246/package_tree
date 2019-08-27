package package_tree.packages;

import java.util.HashMap;
import java.util.Map;

public class PackageIndexer {
    private static Map<String, Package> packages;

    static {
        packages = new HashMap<>();
    }

    public static boolean containsPackage(String packageName) {
        return packages.containsKey(packageName);
    }

    public static synchronized boolean isDependedOn(String packageName) {
        // checks if other packages are dependent on this package
        for (Package indexed : packages.values()) {
            if (indexed.isDependentOn(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static synchronized boolean dependenciesExist(Package pkg) {
        if (pkg.getDependencies().size() == 0) return true;
        for (String dependency : pkg.getDependencies()) {
            if (!packages.containsKey(dependency)) {
                return false;
            }
        }
        return true;
    }

    public static synchronized boolean add(Package pkg) {
        // check if dependencies exist, if missing return false
        if (!dependenciesExist(pkg)) {
            return false;
        }
        //add package to index, or update dependencies
        packages.put(pkg.getName(), pkg);
        return true;
    }

    public static synchronized boolean delete(String packageName) {
        // if other packages are dependent on this package, unable to remove
        if (isDependedOn(packageName)) {
            return false;
        }

        // remove package from packages Map
        packages.remove(packageName);
        return true;
    }

    static Map<String, Package> getPackages() {
        return packages;
    }
}
