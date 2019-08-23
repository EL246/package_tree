package package_tree.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import package_tree.message.commands.Command;
import package_tree.message.commands.IndexCommand;
import package_tree.message.commands.QueryCommand;
import package_tree.message.commands.RemoveCommand;

import java.util.Arrays;

public class MessageParserTest {

    public static String[] invalidMessages() {
        return new String[]{"abcd", "", "INDEX | package | dep", "INDEX|",
                "QUERY|", "REMOVE|", "REMOVE|package|dep1,dep2|abc"};
    }

    public static String[] validMessages() {
        return new String[]{"INDEX|package|dep1,dep2", "QUERY|package|dep1,dep2", "REMOVE|package|dep1,dep2"};
    }

    @ParameterizedTest
    @MethodSource("invalidMessages")
    public void throwParseExceptionOnInvalidMessage(String message) {
        MessageParser messageParser = new MessageParser();
        Assertions.assertThrows(ParseException.class, () -> {
            messageParser.parse(message);
        });
    }

    @Test
    public void returnIndexCommandForIndexMessage() throws ParseException {
        String indexMessage = "INDEX|package|dep1,dep2";
        MessageParser messageParser = new MessageParser();
        Command command = messageParser.parse(indexMessage);
        Assertions.assertTrue(command instanceof IndexCommand);
    }

    @Test
    public void returnQueryCommandForQueryMessage() throws ParseException {
        String indexMessage = "QUERY|package|dep1,dep2";
        MessageParser messageParser = new MessageParser();
        Command command = messageParser.parse(indexMessage);
        Assertions.assertTrue(command instanceof QueryCommand);
    }

    @Test
    public void returnRemoveCommandForRemoveMessage() throws ParseException {
        String indexMessage = "REMOVE|package|dep1,dep2";
        MessageParser messageParser = new MessageParser();
        Command command = messageParser.parse(indexMessage);
        Assertions.assertTrue(command instanceof RemoveCommand);
    }

    @Test
    public void parseDependenciesIntoList() throws ParseException {
        String indexMessage = "INDEX|package|dep1,dep2";
        MessageParser messageParser = new MessageParser();
        IndexCommand command = (IndexCommand) messageParser.parse(indexMessage);
        String[] result = new String[]{"dep1","dep2"};
        Assertions.assertTrue(Arrays.equals(result,command.getDependencies()));
    }
}
