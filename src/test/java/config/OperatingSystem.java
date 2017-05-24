package config;

public abstract class OperatingSystem {

    private static String OS = null;

    private static String getOperatingSystemName() {
        if (OS == null) {
            OS = System.getProperty("os.name").toLowerCase();
        }
        return OS;
    }

    public static boolean isWindows() {
        return (getOperatingSystemName().indexOf("win") >= 0);
    }

    public static boolean isMacOS() {
        return (getOperatingSystemName().indexOf("mac") >= 0);
    }
}

