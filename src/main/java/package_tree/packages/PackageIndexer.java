package package_tree.packages;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PackageIndexer {
    private static Map<String, Package> packages = new HashMap<>();

    // Initialize read-write lock.
    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final Lock readLock = readWriteLock.readLock();
    private static final Lock writeLock = readWriteLock.writeLock();

    public static boolean query(String packageName) {
        boolean exists;

        readLock.lock();
        exists = containsPackage(packageName);
        readLock.unlock();

        return exists;
    }

    public static boolean add(Package pkg) {
        writeLock.lock();
        // Check if dependencies exist, if missing return false.
        if (!dependenciesExist(pkg)) {
            writeLock.unlock();
            return false;
        }

        // If adding an existing package, copy dependsOnMe counter from previous entry
        // and decrease dependsOnMe count of current entry's dependencies.
        if (packages.containsKey(pkg.getName())) {
            // Copy DependsOnMe counter from previous entry.
            pkg.updateDependsOnMe(packages.get(pkg.getName()).getDependsOnMe());
            // Decrease dependsOnMe count of current entry's dependencies
            Package p = packages.get(pkg.getName());
            updateDependentCounter(p, -1);
        }

        // Add new package and update dependsOnMe counters for package dependencies
        packages.put(pkg.getName(), pkg);
        updateDependentCounter(pkg, 1);

        writeLock.unlock();
        return true;
    }

    public static boolean delete(String packageName) {
        readLock.lock();
        // If package does not exist, return true.
        if (!containsPackage(packageName)) {
            readLock.unlock();
            return true;
        }
        readLock.unlock();

        writeLock.lock();
        // If other packages are dependent on this package, unable to remove.
        if (isDependedOn(packageName)) {
            writeLock.unlock();
            return false;
        }

        // Remove package and update dependsOnMe counter of dependencies
        Package p = packages.remove(packageName);
        updateDependentCounter(p, -1);

        writeLock.unlock();
        return true;
    }

    // Must be called under read or write lock.
    private static boolean containsPackage(String packageName) {
        return packages.containsKey(packageName);
    }

    // Must be called under write lock.
    private static boolean isDependedOn(String packageName) {
        // Check if other packages are dependent on this package.
        return packages.get(packageName).getDependsOnMe() != 0;
    }

    // Must be called under write lock.
    private static boolean dependenciesExist(Package pkg) {
        if (pkg.getDependencies().size() == 0) return true;
        for (String dependency : pkg.getDependencies()) {
            if (!packages.containsKey(dependency)) {
                return false;
            }
        }
        return true;
    }

    // Must be called under write lock
    private static void updateDependentCounter(Package pkg, int value) {
        for (String dependency : pkg.getDependencies()) {
            Package myDependency = packages.get(dependency);
            myDependency.updateDependsOnMe(value);
        }
    }

    static Map<String, Package> getPackages() {
        return packages;
    }
}
