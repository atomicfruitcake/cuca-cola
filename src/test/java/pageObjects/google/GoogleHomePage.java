package pageObjects.google;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObjects.PageObject;

import java.util.List;

public class GoogleHomePage extends PageObject {

	public GoogleHomePage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.ID, using = "lst-ib")
	private WebElement searchBar;
	
	@FindBy(how = How.NAME, using = "btnK")
	private WebElement googleSearchButton;

	@FindBy(how = How.ID, using = "btnI")
	private WebElement imFeelingLuckyButton;

	@FindBy(how = How.XPATH, using = "//*[@class=\"rc\"]")
	private List<WebElement> searchResults;

	public void enterTextInSearchBar(String searchTerm) {
		searchBar.sendKeys(searchTerm);
	}

	public void clickGoogleSearchButton() {
		googleSearchButton.click();
	}

	public void clickImFeelingLuckyButton() {
		imFeelingLuckyButton.click();
	}

	public List<WebElement> getSearchResultsList() {
		return searchResults;
	}

	public String getURL() {
		return "https://www.google.co.uk/";
	}
}
