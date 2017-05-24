package enums;

public enum Environments {
    LOCAL,
    DOCKER;

    public static Environments getEnvironment(String environment)
            throws IllegalArgumentException {
        for (Environments e : values()) {
            if (e.toString().equalsIgnoreCase(environment)) {
                return e;
            }
        }
        throw environmentNotFound(environment);
    }

    private static IllegalArgumentException environmentNotFound(String outcome) {
        return new IllegalArgumentException(
                ("Invalid environment [" + outcome + "]"));
    }

}
