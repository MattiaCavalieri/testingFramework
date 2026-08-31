package testingFramework;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import testComponents.BaseTest;
import testingFramework.pageobject.CartPage;
import testingFramework.pageobject.ProductCatalog;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = {"ErrorsHandling"})
	public void loginErrorValidation() throws IOException {
		// wrong password
		landingPage.loginApplication("mattiacavalieri@gmail.com", "R1verside.2022!");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test(groups = {"ErrorsHandling"})
	public void productErrorValidation() throws IOException {
		String productName = "ZARA COAT 3";
		ProductCatalog productCatalog = landingPage.loginApplication("mattiacavalieri@gmail.com", "R1verside.2025!");
		productCatalog.getProductByName(productName);
		productCatalog.addProductToCart(productName);
		CartPage cartPage = productCatalog.goToCartPage();
		cartPage.verifyProductDisplay(productName);
		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
	}
	

}
