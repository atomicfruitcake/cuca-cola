package config;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.jetbrains.annotations.Nullable;

abstract class EnvironmentSwitch {

    @Nullable
    private static String readLineFromFile(String filename, int lineNumber) {
        try {
            return Files.readAllLines(Paths.get(filename)).get(lineNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * The settings file should be in a secure location and used only to store Test credentials
     */
    private static String getRemoteSettings(int entryNo) {
        return readLineFromFile("settings", entryNo);
    }

    static String getUsername() {
        return Security.encrypt(getRemoteSettings(0), config.Properties.SALT);
    }

    static String getPassword() {
        return Security.encrypt(getRemoteSettings(1), Properties.SALT);
    }

}
