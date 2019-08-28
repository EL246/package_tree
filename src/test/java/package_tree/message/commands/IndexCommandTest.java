package package_tree.message.commands;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import package_tree.packages.Package;
import package_tree.packages.PackageIndexer;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PackageIndexer.class})
class IndexCommandTest {

    private static Package a = new Package("a",new String[]{"c"});
    private static Package b = new Package("b",new String[]{});
    private static Package d = new Package("d",new String[]{"e"});

    @BeforeClass
    public static void setup() {
        PowerMockito.mockStatic(PackageIndexer.class);
        // a does not exist in PackageIndexer, and depends on c which is already indexed
        when(PackageIndexer.query("a")).thenReturn(false);
        when(PackageIndexer.add(a)).thenReturn(true);
        when(PackageIndexer.query("c")).thenReturn(true);
        // b does not exist in PackageManager, and it does not have any dependencies
        when(PackageIndexer.query("b")).thenReturn(false);
        when(PackageIndexer.add(b)).thenReturn(true);
        // d does not exist in PackageIndexer, and depends on e which is not indexed
        when(PackageIndexer.query("d")).thenReturn(false);
        when(PackageIndexer.add(d)).thenReturn(false);
        when(PackageIndexer.query("e")).thenReturn(false);
    }

    @Test
    public void executeReturnsTrueOnPackageWithoutDependencies() {
        IndexCommand indexCommand = new IndexCommand("a",new String[]{"c"});
        assertTrue(indexCommand.execute());
    }

    @Test
    public void executeReturnsTrueOnPackageWithIndexedDependencies() {
        IndexCommand indexCommand = new IndexCommand("b", new String[]{});
        assertTrue(indexCommand.execute());
    }

    @Test
    public void executeReturnsFalseOnPackageWithUnindexedDependencies() {
        IndexCommand indexCommand = new IndexCommand("d", new String[]{"e"});
        assertFalse(indexCommand.execute());
    }
}