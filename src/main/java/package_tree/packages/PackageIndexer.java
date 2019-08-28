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
        // Add package to index, or update dependencies.
        packages.put(pkg.getName(), pkg);
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

        // Remove package from packages Map.
        packages.remove(packageName);
        writeLock.unlock();
        return true;
    }

    // Must be called under lock.
    private static boolean containsPackage(String packageName) {
        return packages.containsKey(packageName);
    }

    // Must be called under lock.
    private static boolean isDependedOn(String packageName) {
        // Check if other packages are dependent on this package.
        for (Package indexed : packages.values()) {
            if (indexed.isDependentOn(packageName)) {
                return true;
            }
        }
        return false;
    }

    // Must be called under lock.
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
