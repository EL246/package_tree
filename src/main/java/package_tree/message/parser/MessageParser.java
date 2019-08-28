package package_tree.message.parser;

import package_tree.message.CommandCreator;
import package_tree.message.CommandType;
import package_tree.message.commands.Command;

public class MessageParser {
    private static final int expectedNumArgs = 3;

    public static Command parse(String message) throws ParseException {
        String[] tokens = message.split("\\|",-1);
        if (tokens.length != expectedNumArgs) {
            throw new ParseException("Invalid message length, expected " + expectedNumArgs + " arguments");
        }

        String commandName = tokens[0];
        String packageName = tokens[1];
        validatePackageName(packageName);
        String[] dependencyList = tokens[2].equals("") ? new String[]{} : tokens[2].split(",");

        CommandType commandType = getCommandType(commandName);
        return CommandCreator.createCommand(commandType, packageName, dependencyList);
    }

    private static CommandType getCommandType(String commandName) throws ParseException {
        CommandType commandType;
        try {
            commandType = CommandType.valueOf(commandName);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid command type specified");
        }
        return commandType;
    }

    private static void validatePackageName(String packageName) throws ParseException {
        if (!packageName.matches("^([a-zA-Z_][a-zA-Z0-9+_-]*\\+*)$")) {
            throw new ParseException("Invalid package name specified");
        }
    }
}
