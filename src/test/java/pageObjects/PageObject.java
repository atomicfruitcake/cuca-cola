package pageObjects;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;

import drivers.DriverFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * @author atomicfruitcake
 */
public abstract class PageObject {

    private static final Logger LOGGER = Logger.getLogger(PageObject.class.getName());

    protected final WebDriver driver;

    private List<WebElement>[] listsOfElementsToWaitForOnLoad;

    protected PageObject() {
        this.driver = DriverFactory.getInstance().getDriver();
    }

    public String getObservedPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public void navigateToURL(String url) {
        driver.get(url);
    }

    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    public void takeScreenshot(String name) {
        Date dDate = new Date();
        SimpleDateFormat fullFormat = new SimpleDateFormat(
                "yyyy.MM.dd_HHmmss");
        File screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenshot, new File("screenshots\\" + name
                    + "_" + fullFormat.format(dDate) + ".png"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    public void switchtab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public void switchTabs() {
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(0));
        driver.close();
        driver.switchTo().window(tabs2.get(1));
    }

    public void removeUnusedTabs() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(originalHandle);
    }

    private void waitForElement(WebElement element) {
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        LOGGER.info("Waiting for WebElement " + element.getText() + " to be displayed");
        element = new WebDriverWait(driver, 60)
                .until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(element.isDisplayed());
    }

    @SafeVarargs
    public final void waitForPageLoad(List<WebElement>... listsOfElementsToWaitForOnLoad) {
        this.listsOfElementsToWaitForOnLoad = listsOfElementsToWaitForOnLoad;
        for (WebElement element : listOfElementsToWaitForOnLoad(listsOfElementsToWaitForOnLoad)) {
            waitForElement(element);
        }
    }

    @SafeVarargs
    private final List<WebElement> listOfElementsToWaitForOnLoad(List<WebElement>... listsOfElementsToWaitForOnLoad) {
        this.listsOfElementsToWaitForOnLoad = listsOfElementsToWaitForOnLoad;
        List<WebElement> concatenatedListOfAllElementsToWaitFor = new ArrayList<>();
        for (List<WebElement> webElementList : listsOfElementsToWaitForOnLoad) {
            concatenatedListOfAllElementsToWaitFor.addAll(webElementList);
        }
        return concatenatedListOfAllElementsToWaitFor;
    }
}
