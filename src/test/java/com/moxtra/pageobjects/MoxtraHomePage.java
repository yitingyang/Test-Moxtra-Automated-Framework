package com.moxtra.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Angie_Yang on 3/16/2016.
 */
public class MoxtraHomePage {
    public boolean doesTimeLineDisplayed(WebDriver driver) {
        // If user did sign in, the "Timeline" text will be displayed.
        // Use .findElement() to find the element by its linkText
        // and then use .isDisplayed() check if it did display.
        return driver.findElement(By.linkText("Timeline")).isDisplayed();
    }

    public SignInPage signOut(WebDriver driver) {

        WebElement accountIcon = driver.findElement(By.linkText("Account"));   //find Account Icon by its link Text
        accountIcon.click();                                                   //Click the Account Icon

        WebElement logoutButton = driver.findElement(By.linkText("Log Out"));    //find log out button by its link Text
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", logoutButton);

        //When you click on the log out button, it should pop out a alert msg which ask you to comfirm Logout which contains yes and no button
        //The id changes everytime, so we shouldn't use id as a locator in this case.
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(@class, 'modal-title ellipsis') and text() = 'Confirm Logout']")));

        //Make sure the confirmation dialog is popped out and there should be "Confirm Logout" on the dialog.
        WebElement logoutDialog = driver.findElement(By.xpath("//h4[contains(@class, 'modal-title ellipsis') and text() = 'Confirm Logout']"));
        assertTrue("Validation FAILED : TEXT \"Confirm Logout\" NOT FOUND", logoutDialog.isDisplayed());

        //button's id changes every time as well, shouldn't use id as a locator.
        //if click no, stay at the profile page, and the profile image should be on the page.
        WebElement noButton = driver.findElement(By.xpath("//button[contains(text(),'No')]"));
        noButton.click();
        WebElement profileImage = driver.findElement(By.id("user_avatar"));
        assertTrue("Validation FAILED : IMAGE \"Profile Image\" NOT FOUND", profileImage.isDisplayed());

        //if click yes, go to account sign in page
        executor.executeScript("arguments[0].click();", logoutButton);
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Yes')]")));
        WebElement yesButton = driver.findElement(By.xpath("//button[contains(text(),'Yes')]"));
        WebElement confirmmsg = driver.findElement(By.xpath("//div[contains(@class, 'modal-body') and text() = 'Are you sure you want to logout?']"));
        assertTrue("Validation FAILED : TEXT \"Confirm Logout\" NOT FOUND", confirmmsg.isDisplayed());
        yesButton.click();

        return PageFactory.initElements(driver, SignInPage.class);
    }

    public void clickConversation(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@title='For testing']")));
        WebElement conversation = driver.findElement(By.xpath("//span[@title='For testing']"));
        conversation.click();
    }

    public void sendMsg(WebDriver driver, String message) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='postComment']")));
        WebElement msgTextBox = driver.findElement(By.xpath("//textarea[@name='postComment']"));
        msgTextBox.clear();
        String expectMsg = message;
        msgTextBox.sendKeys(expectMsg + "\n");
    }

    public void verifyMessage(WebDriver driver, String expectedMsg) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='mx-message'])[last()]//span")));
        //There is a message box contains all message in this chatting room
        //so we need to find the last message element, which we just sent, using last() method of xpath
        WebElement actualMsg = driver.findElement(By.xpath("(//div[@class='mx-message'])[last()]//span"));
        //check if the message we passed is equal to the message displayed in the message box.
        assertEquals("Validation FAILED : THE MESSAGE DISPLAYED NOT EQUAL TO THE MESSAGE WE SENT.", expectedMsg, actualMsg.getText());
    }
}
