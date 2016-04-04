package com.moxtra.pageobjects;

import com.moxtra.util.WebUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Angie_Yang on 3/16/2016.
 */
public class MoxtraHomePage {
    public boolean doesTimeLineDisplayed(WebDriver driver) {
        //Verify Timeline text exists by linkText
        return WebUtil.doesElementDisplayed(driver, By.linkText("Timeline"));
    }

    public SignInPage signOut(WebDriver driver) {

        WebUtil.click(driver, By.linkText("Account"));
        WebUtil.executorButtonClick(driver, By.linkText("Log Out"));

        //When you click on the log out button, it should pop out a alert msg which ask you to comfirm Logout which contains yes and no button
        //The id changes everytime, so we shouldn't use id as a locator in this case.
        //Make sure the confirmation dialog is popped out and there should be "Confirm Logout" on the dialog.
        WebElement logoutDialog = driver.findElement(By.xpath("//h4[contains(@class, 'modal-title ellipsis') and text() = 'Confirm Logout']"));
        assertTrue("Validation FAILED : TEXT \"Confirm Logout\" NOT FOUND", WebUtil.doesElementDisplayed(driver, By.xpath("//h4[contains(@class, 'modal-title ellipsis') and text() = 'Confirm Logout']")));

        //button's id changes every time as well, shouldn't use id as a locator.
        //if click no, stay at the profile page, and the profile image should be on the page.
        WebUtil.click(driver, By.xpath("//button[contains(text(),'No')]"));
        assertTrue("Validation FAILED : IMAGE \"Profile Image\" NOT FOUND",WebUtil.doesElementDisplayed(driver,By.id("user_avatar")));

        //if click yes, go to account sign in page
        WebUtil.executorButtonClick(driver, By.linkText("Log Out"));
        assertTrue("Validation FAILED : TEXT \"Confirm Logout\" NOT FOUND",  WebUtil.doesElementDisplayed(driver, By.xpath("//div[contains(@class, 'modal-body') and text() = 'Are you sure you want to logout?']")));
        WebUtil.click(driver, By.xpath("//button[contains(text(),'Yes')]"));

        return PageFactory.initElements(driver, SignInPage.class);
    }

    public void clickConversation(WebDriver driver) {
        WebUtil.click(driver, By.xpath("//span[@title='For testing']"));
    }

    public void sendMsg(WebDriver driver, String message) {
        WebUtil.waitForElementToBeVisible(driver, By.xpath("//textarea[@name='postComment']"));
        //Locate message text box by xpath and then clear and send message
        WebUtil.clearAndSendMsg(driver, By.xpath("//textarea[@name='postComment']"), message);
    }

    public void verifyMessage(WebDriver driver, String expectedMsg) {

        WebUtil.waitForElementToBeVisible(driver, By.xpath("(//div[@class='mx-message'])[last()]//span"));
        //There is a message box contains all message in this chatting room
        //so we need to find the last message element, which we just sent, using last() method of xpath
        WebElement actualMsg = driver.findElement(By.xpath("(//div[@class='mx-message'])[last()]//span"));
        //check if the message we passed is equal to the message displayed in the message box.
        assertEquals("Validation FAILED : THE MESSAGE DISPLAYED NOT EQUAL TO THE MESSAGE WE SENT.", expectedMsg, actualMsg.getText());
    }

    public void verifyUploadedFile(WebDriver driver, String expectedUploadFile) {
        WebUtil.waitForElementToBeVisible(driver, By.xpath("(//div[@class='mx-message'])[last()][contains(text(),'" + expectedUploadFile + "')]"));
        //There is a message box contains all message in this chatting room
        //so we need to find the last message element, which we just sent, using last() method of xpath
        WebElement actualFile = driver.findElement(By.xpath("(//div[@class='mx-message'])[last()][contains(text(),'" + expectedUploadFile + "')]"));
        //check if the uploaded message contains the name of the file we uploaded
        assertTrue("Validation FAILED : THE NAME OF THE FILE WAS NOT CONTAINED IN THE MESSAGE.", actualFile.getText().contains(expectedUploadFile));
    }
}
