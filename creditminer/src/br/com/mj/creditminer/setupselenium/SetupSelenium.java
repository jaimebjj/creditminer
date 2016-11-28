package br.com.mj.creditminer.setupselenium;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.mj.creditminer.enumerator.DriverPhantomJSEnum;
import br.com.mj.creditminer.enumerator.SystemEnum;

public class SetupSelenium {

	private static SetupSelenium instance = null;
	private static DesiredCapabilities desiredCapabilities;
	private static WebDriver webDriver = null;
	private static WebDriverWait wait = null;

	/**
	 * Singleton
	 * 
	 * @return instance
	 */
	public static synchronized SetupSelenium getInstance() {

		if (instance == null) {
			instance = new SetupSelenium();
			getWebDriverSetup();
		}

		return instance;

	}

	private static void getWebDriverSetup() {

		desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setJavascriptEnabled(true);
		desiredCapabilities.setCapability("takesScreenshot", false);
		desiredCapabilities.setCapability("diskCache", true);
		desiredCapabilities.setCapability("loadImages", false);
		desiredCapabilities.setCapability("webSecurity", false);
		desiredCapabilities.setCapability("ignoreSslErros", true);
		desiredCapabilities.setBrowserName("phantomjs");
		desiredCapabilities.setPlatform(Platform.LINUX);

		if (System.getProperty("os.name").toUpperCase().equals(SystemEnum.LINUX.getSystem())) {
			desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/bin/phantomjs");
		} else {
			desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
					System.getProperty("phantomjs.binary.path", DriverPhantomJSEnum.PATH_DRIVER_PHANTOMJS_WINDOWS.getPath()));
		}

		webDriver = new PhantomJSDriver(desiredCapabilities);
		wait = new WebDriverWait(webDriver, 20);

	}

	/**
	 * @return the desiredCapabilities
	 */
	public static DesiredCapabilities getDesiredCapabilities() {
		return desiredCapabilities;
	}

	/**
	 * @param desiredCapabilities
	 *            the desiredCapabilities to set
	 */
	public static void setDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
		SetupSelenium.desiredCapabilities = desiredCapabilities;
	}

	/**
	 * @return the webDriver
	 */
	public WebDriver getWebDriver() {
		return webDriver;
	}

	/**
	 * @param webDriver
	 *            the webDriver to set
	 */
	public static void setWebDriver(WebDriver webDriver) {
		SetupSelenium.webDriver = webDriver;
	}

	/**
	 * @return the wait
	 */
	public WebDriverWait getWait() {
		return wait;
	}

	/**
	 * @param wait
	 *            the wait to set
	 */
	public static void setWait(WebDriverWait wait) {
		SetupSelenium.wait = wait;
	}

	public void closeWebDriver() {
		webDriver.quit();
	}

}
