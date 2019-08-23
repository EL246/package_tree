package package_tree.message;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MessageHandlerTest {

    public static String[] invalidMessages() {
        return new String[]{"abcd", "", "INDEX | package | dep", "INDEX|",
                "QUERY|", "REMOVE|", "REMOVE|package|dep1,dep2|abc"};
    }

    public static String[] validMessages() {
        return new String[]{"INDEX|package|"};
    }


    @ParameterizedTest
    @MethodSource("validMessages")
    public void returnOkForValidMessage(String message) {
        MessageHandler messageHandler = new MessageHandler();
        assertEquals(Response.OK, messageHandler.handle(message));
    }

    @ParameterizedTest
    @MethodSource("invalidMessages")
    public void returnErrorForInvalidMessage(String message) {
        MessageHandler messageHandler = new MessageHandler();
        assertEquals(Response.ERROR, messageHandler.handle(message));
    }
}
