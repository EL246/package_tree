package package_tree.message;

import package_tree.message.commands.Command;
import package_tree.message.parser.MessageParser;
import package_tree.message.parser.ParseException;

public class MessageHandler {

    public static Response handle(String message) {
        Command command;
        try {
            command = MessageParser.parse(message);
            if (!command.execute()) {
                return Response.FAIL;
            }
        } catch (ParseException e) {
            return Response.ERROR;
        }

        return Response.OK;
    }
}
