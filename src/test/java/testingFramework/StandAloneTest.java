package testingFramework;

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

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {

		String productName = "ZARA COAT 3";

		// open Chrome Driver
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		// add implicity wait on global level to avoi issues
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// maximise the browser
		driver.manage().window().maximize();

		// connect to shop site
		driver.get("https://rahulshettyacademy.com/client/");

		// login using account credentials:
		driver.findElement(By.id("userEmail")).sendKeys("mattiacavalieri@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("R1verside.2025!");

		// click on "Login" button
		driver.findElement(By.id("login")).click();

		// let's add explicit wait to allow the products to be loaded
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		// let's grab alla the products displayed in the dashboard
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

		// let's iterate through all of the products to identify the product "ZARA COAT
		// 3" using Java streams
		WebElement selectedProduct = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);

		// click on "Add To Cart" button to add the selected product
		selectedProduct.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		// verify that after the loading animation, the product has been added to cart
		// we use explicit wait to wait for the toast message to appear
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

		// wait for the animated icon to disappear
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		// click on "Cart" button to see the Cart page
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

		// Once landend on Cart page let's scan all of the products to see if there is
		// the right product
		List<WebElement> productsInCart = driver.findElements(By.cssSelector(".cartSection h3"));
		// we use anyMatch to find any product that matches with our product name and
		// store it in a boolean variable
		Boolean match = productsInCart.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		// eventually we use an assertion to validate the test
		Assert.assertTrue(match);

		// go to Checkout
		driver.findElement(By.cssSelector(".totalRow button")).click();

		// Select Country and place Order
		// Using actions we insert "Italy" in country field
		Actions action = new Actions(driver);
		action.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "Italy").build()
				.perform();

		// we use explicit wait to let the list to be shown
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

		// click on the suggested country to confirm selection
		driver.findElement(By.cssSelector(".ta-item")).click();

		// click on Place Order
		driver.findElement(By.cssSelector(".action__submit")).click();

		// grab the order number
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		// close the browser
		driver.quit();
	}

}
