package pageObjects;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import drivers.DriverFactory;

/*
 * @author sambass@deloitte.co.uk
 */
public abstract class Page {

    protected WebDriver driver;

    public String getObservedPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public void navigate(String url) {
        driver.get(url);
    }

    public void takeScreenshot(String name){
        Date dDate = new Date();
        SimpleDateFormat fullFormat = new SimpleDateFormat(
                "yyyy.MM.dd_HHmmss");
        File screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenshot, new File("screenshots\\" + name
                    + "_" + fullFormat.format(dDate) + ".png"));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
    }

    public void switchTabs() {
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(0));
        driver.close();
        driver.switchTo().window(tabs2.get(1));
    }

    public void removeUnusedTabs() {
        String originalHandle = driver.getWindowHandle();
        for(String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(originalHandle);
    }

    public Page() {
        this.driver = DriverFactory.getInstance().getDriver();
    }
}
