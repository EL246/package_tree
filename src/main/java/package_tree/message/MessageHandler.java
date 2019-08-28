package package_tree.message;

import package_tree.message.commands.Command;
import package_tree.message.parser.MessageParser;
import package_tree.message.parser.ParseException;

public class MessageHandler {
    private MessageParser parser;

    public MessageHandler() {
        this.parser = new MessageParser();
    }

    public Response handle(String message) {
        Command command;
        try {
            command = parser.parse(message);
            if (!command.execute()) {
                return Response.FAIL;
            }
        } catch (ParseException e) {
            return Response.ERROR;
        }

        return Response.OK;
    }
}
