package package_tree.message;

import package_tree.message.commands.Command;
import package_tree.packages.PackageManager;

public class MessageHandler {
    private PackageManager packageManager;

    public MessageHandler() {
        this.packageManager = new PackageManager();
    }

    public synchronized Response handle(String message) {
        MessageParser parser = new MessageParser();
        Command command;
        try {
            command = parser.parse(message);
        } catch (ParseException e) {
            return Response.ERROR;
        }
        if (!command.execute(packageManager)) {
            return Response.ERROR;
        }
        return Response.OK;
    }
}
