package package_tree.message.commands;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;
import package_tree.packages.PackageIndexer;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PackageIndexer.class})
class RemoveCommandTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @BeforeClass
    public static void setup() {
        PowerMockito.mockStatic(PackageIndexer.class);
        // a exists in PackageManager, and other packages do not depend on it
        when(PackageIndexer.query("a")).thenReturn(true);
        when(PackageIndexer.delete("a")).thenReturn(true);
        // b does not exist in PackageIndexer
        when(PackageIndexer.query("b")).thenReturn(false);
        when(PackageIndexer.delete("b")).thenReturn(true);
        // c exists in PackageIndexer, but other packages depend on it
        when(PackageIndexer.query("c")).thenReturn(true);
        when(PackageIndexer.delete("c")).thenReturn(false);
    }

    @Test
    public void executeReturnsTrueOnExistingPackage() {
        RemoveCommand removeCommand = new RemoveCommand("a");
        assertTrue(removeCommand.execute());
    }

    @Test
    public void executeReturnsTrueOnNonExistingPackage() {
        RemoveCommand removeCommand = new RemoveCommand("b");
        assertTrue(removeCommand.execute());
    }

    @Test
    public void executeReturnsFalseOnPackageThatIsDependedOn() {
        RemoveCommand removeCommand = new RemoveCommand("c");
        assertFalse(removeCommand.execute());
    }
}