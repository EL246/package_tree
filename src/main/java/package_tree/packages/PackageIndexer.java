package package_tree.packages;

import java.util.HashMap;
import java.util.Map;

public class PackageIndexer {
    private static Map<String, Package> packages;

    static {
        packages = new HashMap<>();
    }

    public static synchronized boolean query(String packageName) {
        return containsPackage(packageName);
    }

    public static synchronized boolean delete(String packageName) {
        // If package doesn't exist, return true.
        if (!containsPackage(packageName)) {
            return true;
        }

        // If other packages are dependent on this package, unable to remove.
        if (isDependedOn(packageName)) {
            return false;
        }

        // Remove package from packages Map.
        packages.remove(packageName);
        return true;
    }

    public static synchronized boolean add(Package pkg) {
        // Check if dependencies exist, if missing return false.
        if (!dependenciesExist(pkg)) {
            return false;
        }
        // Add package to index, or update dependencies.
        packages.put(pkg.getName(), pkg);
        return true;
    }

    private static boolean containsPackage(String packageName) {
        return packages.containsKey(packageName);
    }

    private static boolean isDependedOn(String packageName) {
        // Checks if other packages are dependent on this package.
        for (Package indexed : packages.values()) {
            if (indexed.isDependentOn(packageName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean dependenciesExist(Package pkg) {
        if (pkg.getDependencies().size() == 0) return true;
        for (String dependency : pkg.getDependencies()) {
            if (!packages.containsKey(dependency)) {
                return false;
            }
        }
        return true;
    }

    static Map<String, Package> getPackages() {
        return packages;
    }
}
