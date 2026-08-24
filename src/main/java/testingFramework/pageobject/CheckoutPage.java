package testingFramework.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponent.AbstractComponent;

public class CheckoutPage extends AbstractComponent {

	WebDriver driver;

	@FindBy(css = "[placeholder='Select Country']")
	WebElement countrySelector;

	@FindBy(css = ".action__submit")
	WebElement submitButton;

	@FindBy(css = ".ta-item")
	WebElement country;
	
	By results = By.cssSelector(".ta-results");

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void selectCountry(String countryName) {
		Actions action = new Actions(driver);
		action.sendKeys(countrySelector, countryName).build().perform();
		waitForElementToAppear(results);
		country.click();
	}
	
	public ConfirmationPage submitOrder() {
		submitButton.click();
		ConfirmationPage confirmationPage = new ConfirmationPage(driver);
		return confirmationPage;
	}

}
