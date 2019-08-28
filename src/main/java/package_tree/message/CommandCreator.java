package package_tree.message;

import package_tree.message.commands.Command;
import package_tree.message.commands.IndexCommand;
import package_tree.message.commands.QueryCommand;
import package_tree.message.commands.RemoveCommand;
import package_tree.message.parser.ParseException;

public class CommandCreator {
    public static Command createCommand(CommandType commandType, String packageName, String[] dependencies) throws ParseException {
        switch (commandType) {
            case INDEX:
                return createIndexCommand(packageName,dependencies);
            case QUERY:
                return createQueryCommand(packageName);
            case REMOVE:
                return createRemoveCommand(packageName);
            default:
                throw new ParseException("Invalid command type specified");
        }
    }

    private static Command createIndexCommand(String packageName, String[] dependencies) {
        return new IndexCommand(packageName,dependencies);
    }

    private static Command createRemoveCommand(String packageName) {
        return new RemoveCommand(packageName);
    }

    private static Command createQueryCommand(String packageName) {
        return new QueryCommand(packageName);
    }


}
