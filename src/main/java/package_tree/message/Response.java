package package_tree.message;

public enum Response {
    OK("OK\n"),
    FAIL("FAIL\n"),
    ERROR("ERROR\n");

    final String message;

    Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
