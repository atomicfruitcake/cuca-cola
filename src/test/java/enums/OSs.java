package enums;


public enum OSs {
    ANY,
    VISTA,
    LINUX;

    public static OSs getOS(String os)
            throws IllegalArgumentException {
        for (OSs e : values()) {
            if (e.toString().equalsIgnoreCase(os)) {
                return e;
            }
        }
        throw environmentNotFound(os);
    }

    private static IllegalArgumentException environmentNotFound(String outcome) {
        return new IllegalArgumentException(
                ("Invalid environment [" + outcome + "]"));
    }

}