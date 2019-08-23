package package_tree.message;

import package_tree.message.commands.Command;

public class MessageParser {

    public Command parse(String message) throws ParseException {
        String[] tokens = message.split("\\|");
        if (tokens.length < 2 || tokens.length > 3) {
            throw new ParseException("invalid syntax");
        }
        String commandName = tokens[0];
        String packageName = tokens[1];
        //check for whitespace
        if (packageName.split("\\W",2).length > 1) {
            throw new ParseException("contains whitespace");
        }
        String[] dependencyList = tokens.length > 2 ? tokens[2].split(",") : new String[]{};

        CommandType commandType;
        try {
            commandType = CommandType.valueOf(commandName);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid command type specified");
        }

        return CommandCreator.createCommand(commandType, packageName, dependencyList);

    }
}
