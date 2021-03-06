package package_tree.message.parser;

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
                "QUERY|", "REMOVE|", "REMOVE|package|dep1,dep2|abc", "INDEXA|A|B"};
    }

    @ParameterizedTest
    @MethodSource("invalidMessages")
    public void throwParseExceptionOnInvalidMessage(String message) {
        Assertions.assertThrows(ParseException.class, () -> {
            MessageParser.parse(message);
        });
    }

    @Test
    public void returnIndexCommandOnValidIndexMessage() throws ParseException {
        String indexMessage = "INDEX|package|dep1,dep2";
        Command command = MessageParser.parse(indexMessage);
        Assertions.assertTrue(command instanceof IndexCommand);
    }

    @Test
    public void returnQueryCommandOnValidQueryMessage() throws ParseException {
        String indexMessage = "QUERY|package|dep1,dep2";
        Command command = MessageParser.parse(indexMessage);
        Assertions.assertTrue(command instanceof QueryCommand);
    }

    @Test
    public void returnRemoveCommandOnValidRemoveMessage() throws ParseException {
        String indexMessage = "REMOVE|package|dep1,dep2";
        Command command = MessageParser.parse(indexMessage);
        Assertions.assertTrue(command instanceof RemoveCommand);
    }

    @Test
    public void parseDependenciesIntoList() throws ParseException {
        String indexMessage = "INDEX|package|dep1,dep2";
        IndexCommand command = (IndexCommand) MessageParser.parse(indexMessage);
        String[] result = new String[]{"dep1","dep2"};
        Assertions.assertTrue(Arrays.equals(result,command.getDependencies()));
    }
}
