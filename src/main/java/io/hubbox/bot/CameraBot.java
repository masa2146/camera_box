package io.hubbox.bot;

import io.hubbox.model.WhiteListData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;

/**
 * @author fatih
 */
public class CameraBot {

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    public CameraBot() {
        this.driver = BotDriver.getDriver();
        this.webDriverWait = new WebDriverWait(driver, 1);
    }

    /**
     * @param userName user name to login
     * @param password password to login
     * @param address  domain address
     */
    public void login(String userName, String password, String address) {
        driver.get(address);
        WebElement usernameField = driver.findElement(By.xpath("//input[@id='username']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@id='password']"));
        WebElement loginButton = driver.findElement(By.xpath("//a[@id='b_login']"));

        usernameField.sendKeys(userName);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    /**
     * Go to White list tab
     */
    private void whiteListTab() {
        // Click 'Setting' tab in main template
        WebElement settingsMenu = driver.findElement(By.xpath("//a[@id='b_config']"));
        settingsMenu.click();

        // Wait for load page and click ITC dropdawn menu on the left of the opened page
        WebElement ITCMenu = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("m_blackwhite_list")));//driver.findElement(By.xpath("//a[@id='b_config']"));
        ITCMenu.click();

        // Select 'White List Search' tab
        WebElement whiteListSearch = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("bw_tab_title_white")));
        whiteListSearch.click();
    }

    /**
     * Create white list
     *
     * @param data {@link WhiteListData}
     */
    public void addWhiteList(WhiteListData data) {

        whiteListTab();
        checkPopUpWindows();
        // Check checkbox value. If it's not enable then click enable
        WebElement enableCheckBox = driver.findElement(By.id("bw_enable_whiteS"));
        if (!enableCheckBox.isSelected())
            enableCheckBox.click();

        WebElement addButton = driver.findElement(By.xpath("//a[@id='bw_add_white']"));
        addButton.click();

        // Add plate number and master name
        WebElement plateNumberField = driver.findElement(By.xpath("//input[@id='bw_add_plateNumber']"));
        WebElement masterNameField = driver.findElement(By.xpath("//input[@id='bw_add_masterOfCar']"));

        WebElement startYearField = driver.findElement(By.xpath("//input[@id='bw_add_beginDate0']"));
        WebElement startMountField = driver.findElement(By.xpath("//input[@id='bw_add_beginDate1']"));
        WebElement startDayField = driver.findElement(By.xpath("//input[@id='bw_add_beginDate2']"));

        WebElement startHourField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime0']"));
        WebElement startMinutesField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime1']"));
        WebElement startSecondsField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime2']"));

        WebElement endYearField = driver.findElement(By.xpath("//input[@id='bw_add_cancelDate0']"));
        WebElement endMountField = driver.findElement(By.xpath("//input[@id='bw_add_cancelDate1']"));
        WebElement endDayField = driver.findElement(By.xpath("//input[@id='bw_add_cancelDate2']"));

        WebElement endHourField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime0']"));
        WebElement endMinutesField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime1']"));
        WebElement endSecondsField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime2']"));

        plateNumberField.sendKeys(data.getPlateNumber());

        Calendar cal = Calendar.getInstance();
        cal.setTime(data.getStartTime());

        startYearField.sendKeys(cal.get(Calendar.YEAR) + "");
        startMountField.sendKeys(cal.get(Calendar.MONTH) + "");
        startDayField.sendKeys(cal.get(Calendar.DATE) + "");

        startHourField.sendKeys(cal.get(Calendar.HOUR) + "");
        startMinutesField.sendKeys(cal.get(Calendar.MINUTE) + "");
        startSecondsField.sendKeys(cal.get(Calendar.SECOND) + "");

        cal.setTime(data.getEndTime());

        endYearField.sendKeys(cal.get(Calendar.YEAR) + "");
        endMountField.sendKeys(cal.get(Calendar.MONTH) + "");
        endDayField.sendKeys(cal.get(Calendar.DATE) + "");

        endHourField.sendKeys(cal.get(Calendar.HOUR) + "");
        endMinutesField.sendKeys(cal.get(Calendar.MINUTE) + "");
        endSecondsField.sendKeys(cal.get(Calendar.SECOND) + "");

        masterNameField.sendKeys(data.getMasterName());

        WebElement yesButton = driver.findElement(By.xpath("//a[@id='bw_add_confirm']"));
        yesButton.click();
    }

    /**
     *
     * @param filePath File to be import
     * @return Process status
     */
    public String importWhiteList(String filePath) {
        whiteListTab();
        WebElement upload = driver.findElement(By.xpath("//input[@id='bw_importFile_white']"));
        upload.sendKeys(filePath);
        WebElement importButton = driver.findElement(By.xpath("//a[@id='bw_import_white']"));
        importButton.click();
        WebElement confirmButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='bw_importEncrypt_confirm']")));
        confirmButton.click();
        while (driver.findElement(By.xpath("//div[@id='bw_status_white']")).getText().contains("Importing"))
            System.out.print("Importing..... \r");
        String importStatus = driver.findElement(By.xpath("//div[@id='bw_status_white']")).getText();
        if (importStatus.contains("Failed"))
            return "Failed";
        else if (importStatus.contains("Succeed"))
            return "Succeed";
        else
            return importStatus;
    }

    public void clearWhiteList(){
        whiteListTab();
        WebElement clearButton = driver.findElement(By.xpath("/a[@id='bw_clearAll_white']"));
        clearButton.click();
        WebElement confirmButton = driver.findElement(By.xpath("//a[@id='bw_clear_confirm']"));
        confirmButton.click();
    }

    /**
     * Go to black list tab
     */
    private void blackListTab() {
        // Click 'Setting' tab in main template
        WebElement settingsMenu = driver.findElement(By.xpath("//a[@id='b_config']"));
        settingsMenu.click();

        // Wait for load page and click ITC dropdawn menu on the left of the opened page
        WebElement ITCMenu = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("m_blackwhite_list")));//driver.findElement(By.xpath("//a[@id='b_config']"));
        ITCMenu.click();

        // Select 'Black List Search' tab
        WebElement blackListSearch = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("bw_tab_title_black")));
        blackListSearch.click();
    }

    /**
     * Create black list
     *
     * @param data {@link WhiteListData}
     */
    public void addBlackList(WhiteListData data) {

        blackListTab();
        checkPopUpWindows();
        // Check checkbox value. If it's not enable then click enable
        WebElement enableCheckBox = driver.findElement(By.id("bw_enable_whiteS"));
        if (!enableCheckBox.isSelected())
            enableCheckBox.click();

        WebElement addButton = driver.findElement(By.xpath("//a[@id='bw_add_white']"));
        addButton.click();

        // Add plate number and master name
        WebElement plateNumberField = driver.findElement(By.xpath("//input[@id='bw_add_plateNumber']"));
        WebElement masterNameField = driver.findElement(By.xpath("//input[@id='bw_add_masterOfCar']"));

        WebElement startYearField = driver.findElement(By.xpath("//input[@id='bw_add_beginDate0']"));
        WebElement startMountField = driver.findElement(By.xpath("//input[@id='bw_add_beginDate1']"));
        WebElement startDayField = driver.findElement(By.xpath("//input[@id='bw_add_beginDate2']"));

        WebElement startHourField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime0']"));
        WebElement startMinutesField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime1']"));
        WebElement startSecondsField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime2']"));

        WebElement endYearField = driver.findElement(By.xpath("//input[@id='bw_add_cancelDate0']"));
        WebElement endMountField = driver.findElement(By.xpath("//input[@id='bw_add_cancelDate1']"));
        WebElement endDayField = driver.findElement(By.xpath("//input[@id='bw_add_cancelDate2']"));

        WebElement endHourField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime0']"));
        WebElement endMinutesField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime1']"));
        WebElement endSecondsField = driver.findElement(By.xpath("//input[@id='bw_add_beginTime2']"));

        plateNumberField.sendKeys(data.getPlateNumber());

        Calendar cal = Calendar.getInstance();
        cal.setTime(data.getStartTime());

        startYearField.sendKeys(cal.get(Calendar.YEAR) + "");
        startMountField.sendKeys(cal.get(Calendar.MONTH) + "");
        startDayField.sendKeys(cal.get(Calendar.DATE) + "");

        startHourField.sendKeys(cal.get(Calendar.HOUR) + "");
        startMinutesField.sendKeys(cal.get(Calendar.MINUTE) + "");
        startSecondsField.sendKeys(cal.get(Calendar.SECOND) + "");

        cal.setTime(data.getEndTime());

        endYearField.sendKeys(cal.get(Calendar.YEAR) + "");
        endMountField.sendKeys(cal.get(Calendar.MONTH) + "");
        endDayField.sendKeys(cal.get(Calendar.DATE) + "");

        endHourField.sendKeys(cal.get(Calendar.HOUR) + "");
        endMinutesField.sendKeys(cal.get(Calendar.MINUTE) + "");
        endSecondsField.sendKeys(cal.get(Calendar.SECOND) + "");

        masterNameField.sendKeys(data.getMasterName());

        WebElement yesButton = driver.findElement(By.xpath("//a[@id='bw_add_confirm']"));
        yesButton.click();
    }

    /**
     *
     * @param filePath File to be import
     * @return Process status
     */
    public String importBlackList(String filePath) {
        blackListTab();
        WebElement upload = driver.findElement(By.xpath("//input[@id='bw_importFile_black']"));
        upload.sendKeys(filePath);
        WebElement importButton = driver.findElement(By.xpath("//a[@id='bw_import_black']"));
        importButton.click();
        WebElement confirmButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='bw_importEncrypt_confirm']")));
        confirmButton.click();
        while (driver.findElement(By.xpath("//div[@id='bw_status_black']")).getText().contains("Importing"))
            System.out.print("Importing..... \r");
        String importStatus = driver.findElement(By.xpath("//div[@id='bw_status_black']")).getText();
        if (importStatus.contains("Failed"))
            return "Failed";
        else if (importStatus.contains("Succeed"))
            return "Succeed";
        else
            return importStatus;
    }

    public void clearBlackList(){
        blackListTab();
        WebElement clearButton = driver.findElement(By.xpath("/a[@id='bw_clearAll_black']"));
        clearButton.click();
        WebElement confirmButton = driver.findElement(By.xpath("//a[@id='bw_clear_confirm']"));
        confirmButton.click();
    }

    /**
     * Check pop-up window by css top value.
     */
    private void checkPopUpWindows() {
        WebElement addContent = driver.findElement(By.xpath("//div[@id='bw_add_content']"));
        String topPx = addContent.getCssValue("top");
        if (topPx.contains("px")) {
            if (Integer.parseInt(topPx.split("px")[0].strip()) > 0) {
                WebElement closeButton = driver.findElement(By.xpath("//div[@id='bw_add_content']//div[@class='u-dialog-head']//a[@class='i-close']"));
                closeButton.click();
            }
        }
    }
}
