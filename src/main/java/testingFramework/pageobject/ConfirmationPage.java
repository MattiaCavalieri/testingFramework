package testingFramework.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponent.AbstractComponent;

public class ConfirmationPage extends AbstractComponent {

	WebDriver driver;

	@FindBy(css = ".hero-primary")
	WebElement confirmationMessage;

	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getConfirmationMessage() {
		return confirmationMessage.getText();
	}

}
