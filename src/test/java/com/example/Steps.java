package com.example;

import com.example.model.ElementInfo;
import com.google.common.reflect.TypeToken;
import com.opencsv.CSVReader;
import io.cucumber.messages.internal.com.google.gson.Gson;
import org.apache.log4j.Logger;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The type Steps.
 */
public class Steps {

    /**
     * The Web driver.
     */
    public WebDriver webDriver;
    /**
     * The Actions.
     */
    public Actions actions;

    private static String SAVED_ATTRIBUTE;

    /**
     * The Web driver wait.
     */
    public WebDriverWait webDriverWait;

    /**
     * The Statement.
     */
    private final int timeOut = 30;
    private final int sleepTime = 150;
    /**
     * The constant DEFAULT_MAX_ITERATION_COUNT.
     */
    public static int DEFAULT_MAX_ITERATION_COUNT = 150;
    /**
     * The constant DEFAULT_MILLISECOND_WAIT_AMOUNT.
     */
    public static int DEFAULT_MILLISECOND_WAIT_AMOUNT = 100;

    /**
     * The Logger.
     */
    protected Logger logger = Logger.getLogger(getClass());
    private static final String DEFAULT_DIRECTORY_PATH = "elementValues";
    /**
     * The Element map list.
     */
    ConcurrentMap<String, Object> elementMapList = new ConcurrentHashMap<>();
    /**
     * The Users.
     */
    Map<String, String> users = new HashMap<>();
    /**
     * Instantiates a new Steps.
     */
    public Steps() {
        initMap(getFileList());
        this.webDriver = Hooks.getWebDriver();
        this.actions = new Actions(this.webDriver);
        this.webDriverWait = new WebDriverWait(webDriver, timeOut, sleepTime);
    }


    /**
     * Find element web element.
     *
     * @param key the key
     * @return the web element
     */
    WebElement findElement(String key) {
        ElementInfo elementInfo = findElementInfoByKey(key);
        By infoParam = getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 20);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;


    }
    /**
     * Find elements list.
     *
     * @param key the key
     * @return the list
     */
    List<WebElement> findElements(String key) {
        ElementInfo elementInfo = findElementInfoByKey(key);
        By infoParam = getElementInfoToBy(elementInfo);
        return webDriver.findElements(infoParam);
    }

    /**
     * Gets element info to by.
     *
     * @param elementInfo the element info
     * @return the element info to by
     */
    public static By getElementInfoToBy(ElementInfo elementInfo) {
        By by = null;
        if (elementInfo.getType().equals("css")) {
            by = By.cssSelector(elementInfo.getValue());
        } else if (elementInfo.getType().equals(("name"))) {
            by = By.name(elementInfo.getValue());
        } else if (elementInfo.getType().equals("id")) {
            by = By.id(elementInfo.getValue());
        } else if (elementInfo.getType().equals("xpath")) {
            by = By.xpath(elementInfo.getValue());
        } else if (elementInfo.getType().equals("linkText")) {
            by = By.linkText(elementInfo.getValue());
        } else if (elementInfo.getType().equals(("partialLinkText"))) {
            by = By.partialLinkText(elementInfo.getValue());
        }
        return by;
    }


    /**
     * Init map.
     *
     * @param fileList the file list
     */
    public void initMap(File[] fileList) {
        Type elementType = new TypeToken<List<ElementInfo>>() {
        }.getType();
        Gson gson = new Gson();
        List<ElementInfo> elementInfoList = null;
        for (File file : fileList) {
            try {
                elementInfoList = gson
                        .fromJson(new FileReader(file), elementType);
                elementInfoList.parallelStream()
                        .forEach(elementInfo -> elementMapList.put(elementInfo.getKey(), elementInfo));
            } catch (FileNotFoundException e) {
                logger.warn("{} not found", e);
            }
        }
    }

    /**
     * Get file list file [ ].
     *
     * @return the file [ ]
     */
    public File[] getFileList() {
        File[] fileList = new File(
                this.getClass().getClassLoader().getResource(DEFAULT_DIRECTORY_PATH).getFile())
                .listFiles(pathname -> !pathname.isDirectory() && pathname.getName().endsWith(".json"));
        if (fileList == null) {
            logger.warn(
                    "File Directory Is Not Found! Please Check Directory Location. Default Directory Path = {}" +
                            DEFAULT_DIRECTORY_PATH);
            throw new NullPointerException();
        }
        return fileList;
    }
    /**
     * Find element info by key element info.
     *
     * @param key the key
     * @return the element info
     */
    public ElementInfo findElementInfoByKey(String key) {
        return (ElementInfo) elementMapList.get(key);
    }

    /**
     * Save value.
     *
     * @param key   the key
     * @param value the value
     */
    public void saveValue(String key, String value) {
        elementMapList.put(key, value);
    }

    /**
     * Gets value.
     *
     * @param key the key
     * @return the value
     */
    public String getValue(String key) {
        return elementMapList.get(key).toString();
    }

    /**
     * Find element with key web element.
     *
     * @param key the key
     * @return the web element
     */
    public WebElement findElementWithKey(String key) {
        return findElement(key);
    }

    /**
     * Go website.
     *
     * @param url the uri
     */
    @Given("Go to {string} address")
    public void goToUrl(String url) {
        webDriver.get(url);
        logger.info("Go to the "+url);
    }

    /**
     * hoverElement
     *
     * @param element hover
     */
    private void hoverElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }


    /**
     * clickElement
     *
     * @param element click
     */
    private void clickElement(WebElement element) {
        element.click();
    }

    /**
     * Wait by milli seconds.
     *
     * @param milliseconds the milliseconds
     */
    public void waitByMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickElement(String key) {
        if (!key.isEmpty()) {
            hoverElement(findElement(key));
            clickElement(findElement(key));
        }
    }
    @And("Click to element {string}")
    public void checkElementExistsThenClick(String key) {
        logger.info("Wait element Then click  " + key);
        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(infoParam));

        getElementWithKeyIfExists(key);
        clickElement(key);
        logger.info(key + " Clicked.");
    }
    @And("Check if current URL contains the value {string}")
    public void checkURLContainsRepeat(String expectedURL) {
        int loopCount = 0;
        String actualURL = "";
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            actualURL = webDriver.getCurrentUrl();

            if (actualURL != null && actualURL.contains(expectedURL)) {
                logger.info("Current URL " + expectedURL + " contains the value.");
                return;
            }

        }
        Assert.fail("Actual URL doesn't match the expected." + "Expected: " + expectedURL + ", Actual: " + actualURL);
    }

    @And("Find element from csv file and write to element {string}")
    public void writeValueToElement(String key) throws IOException{
        logger.info("Write the element "+key);
        CSVReader readcsv = new CSVReader(new FileReader("fileCSV/examplecsv.csv"));
        String[] nextLine;
        while ((nextLine = readcsv.readNext()) != null) {
            String text = nextLine[0];
            findElement(key).sendKeys(text);
        }
    }

    @And("Hover and click the element {string}")
    public void checkElementExistsTkhenClick(String key) {
        logger.info("Hover on element then Click " + key);
        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(infoParam));

        getElementWithKeyIfExists(key);
        hover(key);
        clickElement(key);
        logger.info(key + " Clicked.");
    }
    @And("Wait on element {string}")
    public void hover(String key) {
        hoverElement(findElement(key));
        logger.info("Hover on " + key);

    }
    @And("Press ENTER {string}")
    public void ClickKeyboardKey(String key){

        findElement(key).sendKeys(Keys.ENTER);
        logger.info("Press ENTER " + key);
    }

    @Then("Check if element {string} contains text {string}")
    public WebElement getElementText(String key, String expectedText) {

        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(infoParam));

        Boolean containsText = findElement(key).getText().contains(expectedText);
        assertTrue(containsText);
        logger.info(key + " element  " + expectedText + "  contains value.");
        return null;
    }

    @Then("{int} seconds wait")
    public void secondsWait(int second) {
        try {
            logger.info(second + " seconds wait");
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("Check The Element {string}")
    public WebElement getElementWithKeyIfExists(String key) {

        logger.info("Check The ELemnet "+key);
        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(infoParam));

        WebElement webElement;
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            try {
                webElement = findElementWithKey(key);
                logger.info(key + " element was found.");
                return webElement;
            } catch (WebDriverException e) {
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        assertFalse(Boolean.parseBoolean("Element: '" + key + "' doesn't exist."));
        return null;
    }


}
