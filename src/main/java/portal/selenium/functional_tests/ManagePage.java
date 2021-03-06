package portal.selenium.functional_tests;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This page object represents the manage page for the portal. 
 * @author Tonisha Whyte	
 *
 */
public class ManagePage extends Page {
	static String URL;

	/**
	 * Check that the manage page is open.
	 */
	public ManagePage (){
		String currentLocation = DRIVER.getCurrentUrl();
		if (!currentLocation.endsWith("links")){
			throw new IllegalStateException("This is not the manage page, current page is: " 
					+ currentLocation); 
		}
	}
  
	/**
	 * Open the manage page.
	 */
	public static void open() {
		DRIVER.get(CONFIG.getManageURL());
	}
  
  /**
   * Locators
   */
  By emptyListLocator = By.id("empty-link-list");

  By linkCreateDateLocator = By
		  .xpath("//strong[contains(., 'Created')]/.."); 

  By nextArrowLocator = By.cssSelector("ul.pager i.fa-caret-right");
  By nextArrowDisabledLocator = By.cssSelector("ul.pager li.disabled i.fa-caret-right");

  By pageTitleLocator = By.className("manage"); 
 
  /**
   * Get the list of displayed creation dates
   * @return All creation dates
   */
  public  List<String> getCreateDates(){
	  List<String> dates = new ArrayList<String>();	  

	  if (!isListEmpty()){

		  List<WebElement> createDates = new WebDriverWait(DRIVER, 5)
		  .until(ExpectedConditions.presenceOfAllElementsLocatedBy(linkCreateDateLocator));

		  for (WebElement element : createDates){

			  // Take off the label leaving just the date
			  String regex = "\\s*\\bCreated\\b:\\s*";
			  dates.add(element.getText().replaceAll(regex, ""));
		  }
	  }
	  return dates; 
  }
  
  /**
   * Check if there are any content in the list.   
   * 
   * @return True if the list is empty and false otherwise.  Also false if the current
   * page doesn't display the list of content. 
   */
  public boolean isListEmpty() {
	  boolean emptyList = false;
	  if (!isElementDisplayed(pageTitleLocator)){
		  emptyList = true;
	  }
	  
	  try {
	     DRIVER.findElement(emptyListLocator);
	  }catch(NoSuchElementException e) {
		 emptyList = true; 
	  }
	  return emptyList;
  }
  
  /** 
   * Use the next arrow to move forward in the list of content.
   * @return The manage page.
   */
  public ManagePage next(){
     clickThenWait(nextArrowLocator, dialogShadowLocator);
	 return this;
  }
  
  /**
   * Check whether the button to view more links is enabled. 
   * @return
   */
  public boolean isNextButtonAvailable() {
	  try {
		  DRIVER.findElement(nextArrowDisabledLocator);
	  }catch(NoSuchElementException e){
		  return true; 
	  }
	  return false;
  }
}