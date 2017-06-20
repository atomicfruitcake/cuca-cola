package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import pageObjects.PageObjectImpl;


public abstract class WebDriverHooks {

    private final PageObjectImpl page = new PageObjectImpl();

   @After
    public void embedScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            page.takeScreenshot(scenario.getName());
        }
    }
}
