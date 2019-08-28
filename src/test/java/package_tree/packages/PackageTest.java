package package_tree.packages;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PackageTest {

    @Test
    void isDependentOnReturnsTrueForPackageDependency() {
        String[] deps = new String[]{"dep1","dep2","dep3"};
        Package p = new Package("test", deps);
        boolean result = p.isDependentOn("dep1");
        assertTrue(result);
    }

    @Test
    void isDependentOnReturnsFalseForNonPackageDependency() {
        String[] deps = new String[]{"dep1","dep2","dep3"};
        Package p = new Package("test", deps);
        boolean result = p.isDependentOn("dep5");
        assertFalse(result);
    }

    @Test
    void getDependenciesReturnsSetOfDependencies() {
        String[] deps = new String[]{"dep1","dep2","dep3"};
        Package p = new Package("test", deps);
        Set result = p.getDependencies();
        HashSet expected = new HashSet<String>(Arrays.asList(deps));
        Assertions.assertEquals(expected,result);
    }

    @Test
    void getNameReturnsName() {
        String[] deps = new String[]{"dep1","dep2","dep3"};
        Package p = new Package("test", deps);
        String result = p.getName();
        Assertions.assertEquals("test",result);
    }
}