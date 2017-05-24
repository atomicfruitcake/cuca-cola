package runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty","json:target/cucumber/cTests/CucumberTestReport.json",
                "junit:target/cucumber/cTests/CucumberTestReport.xml",
                "html:target/cucumber/cTests/CucumberTestReport.html",
        },
        features= {"src/test/features/"},
        tags = {"@Tests"}
)
public class TestsRunner {
}
