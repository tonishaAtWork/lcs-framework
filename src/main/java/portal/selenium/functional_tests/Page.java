package portal.selenium.functional_tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import portal.selenium.functional_tests.Configuration.Stack;

/**
 * The base class for portal page objects. 
 * @author Tonisha Whyte
 *
 */
public abstract class Page {
    protected static WebDriver DRIVER;
	protected static Configuration CONFIG;

    /**
     * Creates a page object.
     * @param driver
     * @param stack The configuration stack.
     */
    public static void configure (Configuration config, String browser) {
        CONFIG = config;
        DRIVER = config.getDriver(browser); 
    }

    /**
     * LOCATORS
     */
    By navigationBarSignInLinkLocator = By.id("signInLink");
    By signOutDropDownMenuLocator = By.id("dropdownMenu1");
    By signOutLinkLocator = By.cssSelector("ul.dropdown-menu li a");
    By createAccountLocator = By.id("signUpButton");

    /**
     * Find out whether an element is displayed.
     */
    protected boolean isElementDisplayed(By locator){
    	boolean displayed;
    	try {
    		displayed = new WebDriverWait(DRIVER, 10)
    		.until(ExpectedConditions.presenceOfElementLocated(locator)).isDisplayed();
    	}catch (TimeoutException e){
    	    displayed = false;	
    	}

    	return displayed; 
    }
    
    /** 
     * Click an element on the page after waiting for the element to become visible and enabled. 
     * @param locator locator for the element on the page.
     */
    protected void click(By locator) {
    	int waitTime = 10;
    	WebElement element = new WebDriverWait(DRIVER, waitTime)
    	.until(ExpectedConditions.visibilityOfElementLocated(locator));

 	    element.click();
    }
    
    /** 
     * Click an element on the page and then wait for loading to complete. 
     * @param target locator for the element on the page.
     * @param staleElement locator for the element you expect to be stale when loading completes.
     * 
     */
    protected void clickThenWait(By target, By staleElement) {
    	int waitTime = 10;
    	WebElement element = new WebDriverWait(DRIVER, waitTime)
    	.until(ExpectedConditions.elementToBeClickable(target));

 	    element.click();
 	     
 	    new WebDriverWait(DRIVER, waitTime)
 	    .until(ExpectedConditions.invisibilityOfElementLocated(staleElement));
    }

    /**
     * Select the sign in link on top of the navigation bar.  
     * @return The sign in page. 
     */
    public SignInPage signInFromNavigationBar() {
  	  new WebDriverWait (DRIVER, 5)
  	  .until (ExpectedConditions.presenceOfElementLocated(navigationBarSignInLinkLocator))
  	  .click();
  	
  	  new WebDriverWait (DRIVER, 5).until(
  			  ExpectedConditions.stalenessOf(DRIVER.findElement(navigationBarSignInLinkLocator)));
  	 
        return new SignInPage();  
    }

  /**
   * Determine whether or not the sign out drop down menu is present on the page. 
   * @return Whether the drop down menu is displayed.
   */
  public boolean isTheSignOutOptionAvailable(){
	  return isElementDisplayed(signOutDropDownMenuLocator);
  }
  
  /**
   * Click the sign out button on the navigation bar. 
   */
  public void signOut(){
     
	  // click on the sign in/out drop down menu
  	  new WebDriverWait (DRIVER, 5)
  	  .until (ExpectedConditions.presenceOfElementLocated(signOutDropDownMenuLocator))
  	  .click();
  	  
  	  // click on the sign out link
  	  DRIVER.findElement(signOutLinkLocator).click();
  	  
  	  // wait for sign out to complete
  	  new WebDriverWait (DRIVER, 5).until(
  			  ExpectedConditions.visibilityOfElementLocated(navigationBarSignInLinkLocator));
  }
}