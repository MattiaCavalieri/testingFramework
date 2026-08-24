package testingFramework;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import testComponents.BaseTest;
import testingFramework.pageobject.CartPage;
import testingFramework.pageobject.CheckoutPage;
import testingFramework.pageobject.ConfirmationPage;
import testingFramework.pageobject.LandingPage;
import testingFramework.pageobject.ProductCatalog;

public class SubmitOrderTest extends BaseTest {
	
	@Test
	public void submitOrder() throws IOException {

		String productName = "ZARA COAT 3";
		LandingPage landingPage = launchApplication();
		// login using account credentials:
		ProductCatalog productCatalog = landingPage.loginApplication("mattiacavalieri@gmail.com", "R1verside.2025!");
		// List<WebElement> products = productCatalog.getProductList();

		// let's iterate through all of the products to identify the product "ZARA COAT
		// 3" using Java streams
		productCatalog.getProductByName(productName);

		// click on "Add To Cart" button to add the selected product
		productCatalog.addProductToCart(productName);

		// verify that after the loading animation, the product has been added to cart
		// we use explicit wait to wait for the toast message to appear
		// wait for the animated icon to disappear
		
		// click on "Cart" button to see the Cart page
		CartPage cartPage = productCatalog.goToCartPage();

		// Once landend on Cart page let's scan all of the products to see if there is
		// the right product
		
		// we use anyMatch to find any product that matches with our product name and
		// store it in a boolean variable
		cartPage.verifyProductDisplay(productName);
		// eventually we use an assertion to validate the test
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);

		// go to Checkout
		CheckoutPage checkoutPage = cartPage.goToCheckout();

		// Select Country and place Order
		// Using actions we insert "Italy" in country field
		// we use explicit wait to let the list to be shown
		// click on the suggested country to confirm selection
		checkoutPage.selectCountry("Italy");
		
		// click on Place Order
		ConfirmationPage confirmationPage =  checkoutPage.submitOrder();

		// grab the order number
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		// close the browser
		driver.quit();
	}

}
