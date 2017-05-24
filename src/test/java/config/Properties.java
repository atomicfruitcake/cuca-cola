package config;

public abstract class Properties {

    protected static final String SALT = "vv*IMkL,oo3?,sq,z#_I#_V^6TL~&kWd71";

    public static final String BROWSER_PROP_KEY = "browser";
    public static final String ERROR_MESSAGES_FILE_PATH = "src/test/resources/StaticData.properties";
    public static final String ENV = "env";
    public static final String OS = "os";
    public static final String CHROMEDRIVER= "src/webdrivers/chromedriver.exe";
    public static final String CHROMEDRIVER_OSX = "src/webdrivers/chromedriver";
    public static final String USERNAME = Security.decrypt(EnvironmentSwitch.getUsername(), SALT);
    public static final String PASSWORD = Security.decrypt(EnvironmentSwitch.getPassword(), SALT);
    public static final String DOCKER_SELENIUM_ADDRESS = "http://localhost:4444/wd/hub";
}
