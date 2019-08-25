package package_tree.message;

import package_tree.message.commands.Command;

public class MessageHandler {

    public Response handle(String message) {
        MessageParser parser = new MessageParser();
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
