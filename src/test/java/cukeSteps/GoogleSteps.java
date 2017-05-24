package cukeSteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.ContextHelper;
import pageObjects.google.GoogleHomePage;

public class GoogleSteps {

    ContextHelper helper;

    public GoogleSteps(ContextHelper helper) {
        this.helper = helper;
    }

    private GoogleHomePage googleHomePage = new GoogleHomePage();

    @Given("^I navigate to the google home page$")
    public void i_navigate_to_the_google_home_page() {
        googleHomePage.navigate(googleHomePage.getURL());
    }

    @And("^I search for \"([^\"]*)\"$")
    public void i_search_for(String searchTerm) {
        googleHomePage.enterTextInSearchBar(searchTerm);

    }

    @When("^I click the search button$")
    public void i_click_the_search_button() {
        googleHomePage.clickGoogleSearchButton();
    }

    @Then("^I see my search results$")
    public void i_see_my_search_results() {

    }

}