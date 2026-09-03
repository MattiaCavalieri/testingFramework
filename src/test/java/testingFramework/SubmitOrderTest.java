package testingFramework;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import testComponents.BaseTest;
import testingFramework.pageobject.CartPage;
import testingFramework.pageobject.CheckoutPage;
import testingFramework.pageobject.ConfirmationPage;
import testingFramework.pageobject.OrderPage;
import testingFramework.pageobject.ProductCatalog;

public class SubmitOrderTest extends BaseTest {

	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = "Purchase")
	public void submitOrder(HashMap<String, String> input) throws IOException {

		// login using account credentials:
		// using @BeforeMethod in "BaseTest" class we are initializing the driver and
		// reach the url for the test invoking the method "initializeDriver()"
		ProductCatalog productCatalog = landingPage.loginApplication(input.get("email"), input.get("password"));

		// let's iterate through all of the products to identify the product "ZARA COAT
		// 3" using Java streams
		productCatalog.getProductByName(input.get("product"));

		// click on "Add To Cart" button to add the selected product
		productCatalog.addProductToCart(input.get("product"));

		// verify that after the loading animation, the product has been added to cart
		// we use explicit wait to wait for the toast message to appear
		// wait for the animated icon to disappear
		// click on "Cart" button to see the Cart page
		CartPage cartPage = productCatalog.goToCartPage();

		// Once landend on Cart page let's scan all of the products to see if there is
		// the right product
		// we use anyMatch to find any product that matches with our product name and
		// store it in a boolean variable
		cartPage.verifyProductDisplay(input.get("product"));
		// eventually we use an assertion to validate the test
		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);

		// go to Checkout
		CheckoutPage checkoutPage = cartPage.goToCheckout();

		// Select Country and place Order
		// Using actions we insert "Italy" in country field
		// we use explicit wait to let the list to be shown
		// click on the suggested country to confirm selection
		checkoutPage.selectCountry("Italy");

		// click on Place Order
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();

		// grab the order number
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		// close the browser
		// using @AfterMethod in "BaseTest" class we are closing the session invoking
		// the method "closeSession()"
	}

	// let's add another test to verify if ZARA COAT 3 is displayed in orders page

	@Test(dependsOnMethods = { "submitOrder" })
	public void orderHistoryTest() {
		ProductCatalog productCatalog = landingPage.loginApplication("mattiacavalieri@gmail.com", "R1verside.2025!");
		OrderPage orderPage = productCatalog.goToOrdersPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
	}

	@DataProvider
	public Object[][] getData() throws IOException {

//		HashMap<String, String> dataSet1 = new HashMap<String, String>();
//		dataSet1.put("email", "mattiacavalieri@gmail.com");
//		dataSet1.put("password", "R1verside.2025!");
//		dataSet1.put("product", "ZARA COAT 3");
//
//		HashMap<String, String> dataSet2 = new HashMap<String, String>();
//		dataSet2.put("email", "test@test.it");
//		dataSet2.put("password", "Test@000");
//		dataSet2.put("product", "ADIDAS ORIGINAL");

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "/src/test/java/data/purchaseOrder.json");

		return new Object[][] { { data.get(0) }, { data.get(1)} };
	}

	

}
