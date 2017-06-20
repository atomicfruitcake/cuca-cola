package cukeSteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.ContextHelper;
import org.junit.Assert;
import pageObjects.google.GoogleHomePage;

public class GoogleSteps {

    private final ContextHelper helper;
    private String searchTermFromGherkinFeature;

    public GoogleSteps(ContextHelper helper) {
        this.helper = helper;
    }

    private final GoogleHomePage googleHomePage = new GoogleHomePage();

    @Given("^I navigate to the google home page$")
    public void i_navigate_to_the_google_home_page() {
        googleHomePage.navigateToURL(googleHomePage.getURL());
    }

    @And("^I search for \"([^\"]*)\"$")
    public void i_search_for(String searchTerm) {
        searchTermFromGherkinFeature = searchTerm;
        googleHomePage.enterTextInSearchBar(searchTerm);
    }

    @When("^I click the search button$")
    public void i_click_the_search_button() {
        googleHomePage.clickGoogleSearchButton();
    }

    @Then("^I see my search results$")
    public void i_see_my_search_results() {
        googleHomePage.waitForPageLoad(googleHomePage.getSearchResultsList());
        Assert.assertTrue(googleHomePage.getCurrentURL()
                .contains("https://www.google.co.uk/search?q="
                        + searchTermFromGherkinFeature
                        + "&oq="
                        + searchTermFromGherkinFeature)
        );
    }

}