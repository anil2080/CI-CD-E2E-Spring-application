package util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class TestBase {

	private WebDriver driver;
	private String chromeBinaryPath = null;
	private String chromeVersion = null;
	private String chromeDriverPath = null;

	@Before()
	public void init() throws IOException {
		String browserName = ConfigReader.getInstance().getValue("browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			chromeBinaryPath = System.getenv("CHROME_BIN");
			chromeVersion = System.getenv("CHROME_VERSION");
			if (chromeVersion != null && chromeVersion != "") {
				chromeVersion = chromeVersion.substring(0, 2);
				chromeDriverPath = System.getProperty("user.dir") + "\\src\\test\\java\\resources\\chromedriver_v"
						+ chromeVersion + ".exe";
			} else {
				System.out.println("CHROME VERSION IS NOT SET, PLEASE SET IN ENV VARIABLES !!! EXITING........");
				System.exit(0);
			}
			if (chromeBinaryPath != null && chromeBinaryPath != "") {
				options.setBinary(new File(chromeBinaryPath));
			} else {
				System.out.println("CHROME BINARY PATH IS NOT SET, PLEASE SET IN ENV VARIABLES !!! EXITING........");
				System.exit(0);
			}
			File file = new File(chromeDriverPath);
			if (!file.exists()) {
				System.out.println("CHROME DRIVER DO NOT EXISTS IN RESOURCES FOLDER !!! EXITING ....");
				System.out.println("EXIT !!! ADD CHROME DRIVER v " + chromeVersion + "TO RESOURCES FOLDER...");
				System.exit(0);
			}
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			driver = new ChromeDriver(options);
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestContsants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestContsants.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(ConfigReader.getInstance().getValue(System.getProperty("Application_URL")));
	}

	public WebDriver getDriver() {
		return driver;
	}

	@After()
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "Failure");
		}
		driver.close();
		driver.quit();
	}

}
