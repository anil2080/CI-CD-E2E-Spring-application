package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import util.TestBase;

public class LoginPage {

	TestBase testbaseObj;
	
	//intialised page object
	public LoginPage(TestBase testbaseObj) {
		this.testbaseObj = testbaseObj;
		PageFactory.initElements(testbaseObj.getDriver(), this);
	}
	
	@FindBy(id= "")
	@CacheLookup
	WebElement user;
	
	
}
