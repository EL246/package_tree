package package_tree.message.parser;

import package_tree.message.CommandCreator;
import package_tree.message.CommandType;
import package_tree.message.commands.Command;

public class MessageParser {

    public Command parse(String message) throws ParseException {
        String[] tokens = message.split("\\|",-1);
        if (tokens.length != 3) {
            throw new ParseException("invalid syntax");
        }
        String commandName = tokens[0];
        String packageName = tokens[1];
        String[] dependencyList = tokens[2].equals("") ? new String[]{} : tokens[2].split(",");

        CommandType commandType;
        try {
            commandType = CommandType.valueOf(commandName);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid command type specified");
        }

        return CommandCreator.createCommand(commandType, packageName, dependencyList);
    }
}
