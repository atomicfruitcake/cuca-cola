package pageObjects;


import org.openqa.selenium.support.PageFactory;

/*
 * @author atomicfruitcake
 */
public class PageObjectImpl extends PageObject {

    public PageObjectImpl() {
        PageFactory.initElements(driver, this);
    }
}
