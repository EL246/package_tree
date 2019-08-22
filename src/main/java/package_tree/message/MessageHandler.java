package package_tree.message;

import package_tree.message.commands.Command;

public class MessageHandler {

    public Response handle(String message) {
        MessageParser parser = new MessageParser();
        Command command;
        try {
            command = parser.parse(message);
        } catch (ParseException e) {
            return Response.ERROR;
        }
        command.execute();
        System.out.println(command);
        return Response.OK;
    }
}
