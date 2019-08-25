package package_tree.message;

public enum Response {
    OK("OK"),
    FAIL("FAIL"),
    ERROR("ERROR");

    final String message;

    Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
