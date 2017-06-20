package drivers;

import static config.Properties.BROWSER_PROP_KEY;
import static config.Properties.CHROMEDRIVER;
import static config.Properties.CHROMEDRIVER_OSX;
import static config.Properties.DOCKER_SELENIUM_ADDRESS;
import static config.Properties.ENV;
import static config.Properties.ERROR_MESSAGES_FILE_PATH;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import resourceData.TestData;
import config.OperatingSystem;
import enums.Browsers;
import enums.Environments;

public class DriverFactory {

    private final Logger LOGGER = Logger.getLogger(DriverFactory.class.getName());

    private WebDriver driver;
    private final DesiredCapabilities capability = new DesiredCapabilities();
    public static Properties userProp;
    public static Properties staticDataProp;

    private DriverFactory() {
    }

    private static final DriverFactory instance = new DriverFactory();

    public static DriverFactory getInstance() {
        return instance;
    }

    private final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>() {
        @Override
        protected WebDriver initialValue() {
            if (driver == null) {
                initializeDriver();
                loadDataFiles();
            }
            return driver;
        }

        private void loadDataFiles() {
            File file = new File(ERROR_MESSAGES_FILE_PATH);

            FileInputStream fileInput = null;
            try {
                fileInput = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            staticDataProp = new Properties();

            try {
                staticDataProp.load(fileInput);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void initializeDriver() {
            LOGGER.info("Initializing driver");
            try {
                Browsers browser;
                if (System.getProperty(BROWSER_PROP_KEY) == null) {
                    LOGGER.info("BROWSER_PROP_KEY was null, setting browser to chrome");
                    browser = Browsers.CHROME;
                } else {
                    browser = Browsers.getBrowser(System
                            .getProperty(BROWSER_PROP_KEY));
                    LOGGER.info("Setting browser to " + BROWSER_PROP_KEY);
                }
                Environments environment;
                if (System.getProperty(ENV) == null) {
                    environment = Environments.LOCAL;
                } else {
                    environment = Environments.getEnvironment(System
                            .getProperty(ENV));
                }

                switch (environment) {
                    case LOCAL:
                        LOGGER.info("Starting driver Locally");
                        switch (browser) {
                            case CHROME:
                                if(OperatingSystem.isMacOS()){
                                    System.setProperty("webdriver.chrome.driver",
                                            CHROMEDRIVER_OSX);
                                }
                                if(OperatingSystem.isWindows()) {
                                    System.setProperty("webdriver.chrome.driver",
                                            CHROMEDRIVER);
                                } else {
                                    System.setProperty("webdriver.chrome.driver",
                                            CHROMEDRIVER);
                                }
                                capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                                driver = new ChromeDriver(capability);
                                setTimeout(60);
                                break;

                            case FIREFOX:
                                driver = new FirefoxDriver();
                                setTimeout(60);
                                break;

                        }
                        break;

                    case DOCKER:
                        LOGGER.info("Starting tests inside docker containers");
                        switch (browser) {
                            case CHROME:
                                LOGGER.info("Setting docker to chrome mode");
                                capability.setBrowserName("chrome");
                                capability.setPlatform(Platform.LINUX);
                                try {
                                    LOGGER.info("Building Chrome RemoteWebDriver at " + DOCKER_SELENIUM_ADDRESS);
                                    driver = new RemoteWebDriver(new URL(DOCKER_SELENIUM_ADDRESS), capability);
                                } catch (MalformedURLException e) {
                                    LOGGER.info("Error build Chrome RemoteWebDriver in Docker at " + DOCKER_SELENIUM_ADDRESS);
                                    e.printStackTrace();
                                }
                                break;
                            case FIREFOX:
                                LOGGER.info("Setting docker to firefox mode");
                                capability.setBrowserName("firefox");
                                capability.setPlatform(Platform.LINUX);

                                try {
                                    LOGGER.info("Building Firefox RemoteWebDriver at " + DOCKER_SELENIUM_ADDRESS);
                                    driver = new RemoteWebDriver(new URL(DOCKER_SELENIUM_ADDRESS), capability);
                                } catch (MalformedURLException e) {
                                    LOGGER.info("Error build Firefox RemoteWebDriver in Docker at " + DOCKER_SELENIUM_ADDRESS);
                                    e.printStackTrace();
                                }
                                break;
                        }
                        break;
                }

            } finally {
                Runtime.getRuntime().addShutdownHook(
                        new Thread(new BrowserCleanup()));
            }
            TestData run = new TestData();
            run.createNewRunNumber();
        }
    };

    private void setTimeout(int timeoutLength) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(timeoutLength, TimeUnit.SECONDS);
    }

    private class BrowserCleanup implements Runnable {
        public void run() {
            removeDriver();
        }
    }

    public WebDriver getDriver() {
        threadLocalDriver.remove();
        return threadLocalDriver.get();
    }

    private void removeDriver() {
        threadLocalDriver.get().quit();
        threadLocalDriver.remove();
        if (driver != null) {
            driver.quit();
        }
    }
}