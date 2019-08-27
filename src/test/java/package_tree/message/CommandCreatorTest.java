package package_tree.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;
import package_tree.message.commands.Command;
import package_tree.message.commands.IndexCommand;
import package_tree.message.commands.QueryCommand;
import package_tree.message.commands.RemoveCommand;
import package_tree.message.parser.ParseException;

class CommandCreatorTest {

    @MethodSource("invalidMessages")
    public void throwParseExceptionOnInvalidMessage(String message) {
        CommandType commandType = null;
        String pkg = "pkg";
        String[] deps = new String[]{"dep1", "dep2"};
        Assertions.assertThrows(ParseException.class, () -> {
            CommandCreator.createCommand(commandType,pkg,deps);
        });
    }

    @Test
    void createCommandReturnsIndexOnValidIndexMessage() throws ParseException {
        CommandType commandType = CommandType.INDEX;
        String pkg = "pkg";
        String[] deps = new String[]{"dep1", "dep2"};
        Command command = CommandCreator.createCommand(commandType, pkg, deps);
        Assertions.assertTrue(command instanceof IndexCommand);
    }

    @Test
    void createCommandReturnsQueryCommandOnValidQueryMessage() throws ParseException {
        CommandType commandType = CommandType.QUERY;
        String pkg = "pkg";
        String[] deps = new String[]{"dep1", "dep2"};
        Command command = CommandCreator.createCommand(commandType, pkg, deps);
        Assertions.assertTrue(command instanceof QueryCommand);
    }

    @Test
    void createCommandReturnsRemoveCommandOnValidRemoveMessage() throws ParseException {
        CommandType commandType = CommandType.REMOVE;
        String pkg = "pkg";
        String[] deps = new String[]{"dep1", "dep2"};
        Command command = CommandCreator.createCommand(commandType, pkg, deps);
        Assertions.assertTrue(command instanceof RemoveCommand);
    }
}