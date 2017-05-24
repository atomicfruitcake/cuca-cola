package pageObjects.google;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Page;

public class GoogleHomePage extends Page {

	public GoogleHomePage() {
		PageFactory.initElements(driver, this);
	}

	private String URL = "https://www.google.co.uk/";
	
	@FindBy(how = How.ID, using = "lst-ib")
	private WebElement searchBar;
	
	@FindBy(how = How.NAME, using = "btnK")
	private WebElement googleSearchButton;

	@FindBy(how = How.ID, using = "btnI")
	private WebElement imFeelingLuckyButton;

	public void enterTextInSearchBar(String searchTerm) {
		searchBar.sendKeys(searchTerm);
	}

	public void clickGoogleSearchButton() {
		googleSearchButton.click();
	}

	public void clickImFeelingLuckyButton() {
		imFeelingLuckyButton.click();
	}

	public String getURL() {
		return URL;
	}
}
