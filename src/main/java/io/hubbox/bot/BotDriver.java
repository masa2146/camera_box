package io.hubbox.bot;

import io.hubbox.tool.ParentFile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * @author fatih
 */
public class BotDriver {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            String parentFile = ParentFile.getParenFilePath();
            System.setProperty("webdriver.chrome.driver", parentFile + "/browser_driver/chromedriver");
            driver = new ChromeDriver(getChromeOptions());
        }
        return driver;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        options.addArguments("--no-sandbox");
        options.setBinary("/usr/bin/chromium-browser");
        options.addArguments("--enable-javascript");
        options.addArguments("ignore-certificate-errors");
        return options;
    }
}
