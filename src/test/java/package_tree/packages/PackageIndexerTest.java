package package_tree.packages;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PackageIndexerTest {

    private static Map packages;

    @BeforeAll
    public static void setup() {
        PackageIndexer packageIndexer = mock(PackageIndexer.class);
        packages = packageIndexer.getPackages();
    }

    @Test
    void indexPackageWithoutDependenciesReturnsTrue() {
        Package a = new Package("a", new String[]{});
        boolean result = PackageIndexer.add(a);
        assertTrue(result);
    }

    @Test
    void indexAlreadyIndexedPackageWithoutDependenciesReturnsTrue() {
        Package pkgB = new Package("b", new String[]{});
        packages.put(pkgB.getName(),pkgB);

        boolean result = PackageIndexer.add(pkgB);
        assertTrue(result);
    }

    @Test
    void indexPackageWithIndexedDependenciesReturnsTrue() {
        Package pkgC = new Package("c",new String[]{"b"});
        boolean result = PackageIndexer.add(pkgC);
        assertTrue(result);
    }

    @Test
    void indexPackageWithUnindexedDependenciesReturnsFalse() {
        Package pkgD = new Package("d", new String[]{"e"});
        boolean result = PackageIndexer.add(pkgD);
        assertFalse(result);
    }

    @Test
    void removeUnindexedPackagesReturnsTrue() {
        boolean result = PackageIndexer.delete("f");
        assertTrue(result);
    }

    @Test
    void removeNotDependedOnIndexedPackagesReturnsTrue() {
        packages.put("g",new Package("g"));
        boolean result = PackageIndexer.delete("g");
        assertTrue(result);
    }

    @Test
    void removeDependedOnIndexedPackagesReturnsFalse() {
        Package pkgH = new Package("h",new String[]{"i"});
        Package pkgI = new Package("i",new String[]{});
        PackageIndexer.add(pkgI);
        PackageIndexer.add(pkgH);
//        packages.put(pkgI.getName(), pkgI);
//        packages.put(pkgH.getName(),pkgH);
        boolean result = PackageIndexer.delete("i");
        assertFalse(result);
    }

    @Test
    void queryIndexedPackageReturnsTrue() {
        Package pkgJ = new Package("j", new String[]{});
        packages.put(pkgJ.getName(),pkgJ);
        boolean result = PackageIndexer.query("j");
        assertTrue(result);
    }

    @Test
    void queryUnIndexedPackageReturnsFalse() {
        boolean result = PackageIndexer.query("unindexed");
        assertFalse(result);
    }
}