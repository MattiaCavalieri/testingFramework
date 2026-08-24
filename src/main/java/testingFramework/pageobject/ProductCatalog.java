package testingFramework.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponent.AbstractComponent;

public class ProductCatalog extends AbstractComponent {

	WebDriver driver;

	public ProductCatalog(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".mb-3")
	List<WebElement> products;
	
	@FindBy(css = ".ng-animating")
	WebElement spinner;

	By productsLocator = By.cssSelector(".mb-3");
	By addToCartLocator = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");

	public List<WebElement> getProductList() {
		waitForElementToAppear(productsLocator);
		return products;
	}

	public WebElement getProductByName(String productName) {
		WebElement selectedProduct = getProductList().stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);

		return selectedProduct;
	}

	public void addProductToCart(String productName) {
		WebElement selectedProduct = getProductByName(productName);
		selectedProduct.findElement(addToCartLocator).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
	}

}
