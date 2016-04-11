package com.moxtra.util;

import com.moxtra.pageobjects.SignInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Angie_Yang on 3/16/2016.
 */
public class WebUtil {
    private static final long WAIT_TIME_OUT_IN_SECOND = 100;

    public static SignInPage goToSignInPage(WebDriver driver) {
        driver.get("https://www.moxtra.com/service/#login");     //use the driver to go to moxtra
        return PageFactory.initElements(driver, SignInPage.class);
    }


    public static boolean doesElementDisplayed(WebDriver driver, By by) {
        //Validate that the element is displayed as expect
        //Use .findElement() to get the elements that meets the by locator
        //Then check if it is displayed.
        WebUtil.waitForElementToBeVisible(driver, by);
        return driver.findElement(by).isDisplayed();
    }
    public static void click(WebDriver driver, By by) {
        WebUtil.waitForElementToBeVisible(driver,by);
        WebElement button = driver.findElement(by);   //find button by its locator
        button.click();                              //Click the button

    }

    public static void executorButtonClick(WebDriver driver, By by) {
        WebElement logoutButton = driver.findElement(by);    //find log out button by its link Text
        waitForElementToBeVisible(driver, by);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", logoutButton);
    }

    public static void waitForElementToBeVisible(WebDriver driver, By by) {
        //Sometimes webdriver runs too fast to wait until the element loaded. Put driver to wait for 20 secs.
        // wait until the expected element shows up
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME_OUT_IN_SECOND);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void clearAndSendMsg(WebDriver driver, By by, String message) {
        WebElement msgTextBox = driver.findElement(by);
        waitForElementToBeVisible(driver,by);
        msgTextBox.clear();
        String expectMsg = message;
        msgTextBox.sendKeys(expectMsg + "\n");
    }

    public static void clearAndFillKeys(WebDriver driver, By by, String string) {
        WebElement TextBox = driver.findElement(by);     //locate the textbox by its locator
        waitForElementToBeVisible(driver,by);
        TextBox.clear();      //clear the text box before sending keys
        TextBox.sendKeys(string);   //send the key to the TextBox, the string passed should appear in the text box
    }

    public static int monthStringToInt(String text) {
        if(text.equals("January"))
            return 1;
        else if(text.equals("February"))
            return 2;
        else if(text.equals("March"))
            return 3;
        else if(text.equals("April"))
            return 4;
        else if(text.equals("May"))
            return 5;
        else if(text.equals("June"))
            return 6;
        else if(text.equals("July"))
            return 7;
        else if(text.equals("August"))
            return 8;
        else if(text.equals("September"))
            return 9;
        else if(text.equals("October"))
            return 10;
        else if(text.equals("November"))
            return 11;
        else
            return 12;
    }
}