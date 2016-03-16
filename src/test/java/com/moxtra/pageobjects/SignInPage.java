package com.moxtra.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Angie_Yang on 3/16/2016.
 */
public class SignInPage {
    public boolean doesSignInPageDisplayed(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 20);      //Sometimes webdriver runs too fast to wait until the element loaded. Put driver to wait for 20 secs.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("page-title")));    //wait until the expected element shows up
        WebElement AccountSignIn = driver.findElement(By.className("page-title"));               //find the element by its class name

        return AccountSignIn.isDisplayed();

    }

    public static void enterEmail(WebDriver driver, String emailAdress) {
        WebElement emailTextBox = driver.findElement(By.name("email"));     //locate email textbox by its name
        emailTextBox.clear();      //clear the text box before sending keys
        emailTextBox.sendKeys(emailAdress);   //send the key to the emailTextBox, email should appear in the text box

    }

    public static void enterPassword(WebDriver driver, String password) {
        WebElement passwordTextBox = driver.findElement(By.name("password"));   //locate passwordTextboxt by its name
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
    }

    public static MoxtraHomePage clickSignIn(WebDriver driver) {
        WebElement signinButton = driver.findElement(By.xpath("//button[contains(text(),'Sign In')]"));  //locate button by its XPath
        signinButton.click();  //Use .click() to click the button
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Timeline")));
        return PageFactory.initElements(driver, MoxtraHomePage.class);
    }
}
