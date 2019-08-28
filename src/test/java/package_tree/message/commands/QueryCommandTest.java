package package_tree.message.commands;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import package_tree.packages.PackageIndexer;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PackageIndexer.class})
class QueryCommandTest {

    @BeforeClass
    public static void setup() {
        PowerMockito.mockStatic(PackageIndexer.class);
        // a exists in PackageManager
        when(PackageIndexer.query("a")).thenReturn(true);
        // b does not exist in PackageIndexer
        when(PackageIndexer.query("b")).thenReturn(false);
    }

    @Test
    public void executeReturnsTrueOnExistingPackage() {
        QueryCommand queryCommand = new QueryCommand("a");
        assertTrue(queryCommand.execute());
    }

    @Test
    public void executeReturnsFalseOnNonExistingPackage() {
        QueryCommand queryCommand = new QueryCommand("b");
        assertFalse(queryCommand.execute());
    }
}