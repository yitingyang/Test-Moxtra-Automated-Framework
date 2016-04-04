import com.moxtra.categories.Critical;
import com.moxtra.categories.Major;
import com.moxtra.pageobjects.MoxtraHomePage;
import com.moxtra.pageobjects.SignInPage;
import com.moxtra.util.WebUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.io.IOException;

import static org.junit.Assert.assertTrue;

/* Sign In Log Out test case for the Moxtra application
 * Created by Yiting_Yang on 2/25/2016.
 */
public class MoxtraSignInTest {

    WebDriver driver = new FirefoxDriver();   //create a webdriver
    @Category({Critical.class})
    @Test
    public void moxtraSignInShoudBeSucessful() {

        //Step1. Go to moxtra website (http://www.moxtra.com)
        SignInPage signInPage = WebUtil.goToSignInPage(driver);

        //Step2. Verify the Sign In Page
        assertTrue("Validation FAILED : TEXT \"Account Sign In\" NOT FOUND", signInPage.doesSignInPageDisplayed(driver));  // if the element is not displayed, throws an AssertionError with the given msg.

        //Step3. Enter email and  password
        SignInPage.enterEmail(driver, "testmoxtraframework@gmail.com");
        SignInPage.enterPassword(driver, "thisisfortesting");

        //Step4. Sign in
        MoxtraHomePage moxtraHomePage = SignInPage.clickSignIn(driver);

        //Step5. Verify user did sign in
        assertTrue("Validation FAILED : TEXT \"Timeline\" NOT FOUND", moxtraHomePage.doesTimeLineDisplayed(driver));

        //Step6. Sign out
        signInPage = moxtraHomePage.signOut(driver);


        //Step8. Verify user did sign out
        assertTrue("Validation FAILED : TEXT \"Account Sign In\" NOT FOUND", signInPage.doesSignInPageDisplayed(driver));

    }
    @Category({Major.class})
    @Test
    public void SendAndReceiveMsg() {
        //Step1. Go to moxtra website (http://www.moxtra.com)
        SignInPage signInPage = WebUtil.goToSignInPage(driver);

        //Step2. Verify the Sign In Page
        assertTrue("Validation FAILED : TEXT \"Account Sign In\" NOT FOUND", signInPage.doesSignInPageDisplayed(driver));  // if the element is not displayed, throws an AssertionError with the given msg.

        //Step3. Enter email and  password
        SignInPage.enterEmail(driver, "testmoxtraframework@gmail.com");
        SignInPage.enterPassword(driver, "thisisfortesting");

        //Step4. Sign in
        MoxtraHomePage moxtraHomePage = SignInPage.clickSignIn(driver);

        //Step5. Verify user did sign in
        assertTrue("Validation FAILED : TEXT \"Timeline\" NOT FOUND", moxtraHomePage.doesTimeLineDisplayed(driver));

        //Step6. Click the conversation that you want to send message
        moxtraHomePage.clickConversation(driver);

        //Step7. Fill in the message and press enter to send the message
        final String expectedMsg = "Hello";
        moxtraHomePage.sendMsg(driver, expectedMsg);

        //Step8. Verify if the message displayed as we sent
        moxtraHomePage.verifyMessage(driver, expectedMsg);
    }
    @Test
    public void createNotesShouldBeSuccessful(){
        //Step1. Go to moxtra website (http://www.moxtra.com)
        SignInPage signInPage = WebUtil.goToSignInPage(driver);

        //Step2. Verify the Sign In Page
        assertTrue("Validation FAILED : TEXT \"Account Sign In\" NOT FOUND", signInPage.doesSignInPageDisplayed(driver));  // if the element is not displayed, throws an AssertionError with the given msg.

        //Step3. Enter email and  password
        SignInPage.enterEmail(driver, "testmoxtraframework@gmail.com");
        SignInPage.enterPassword(driver, "thisisfortesting");

        //Step4. Sign in
        MoxtraHomePage moxtraHomePage = SignInPage.clickSignIn(driver);

        //Step5. Verify user did sign in
        assertTrue("Validation FAILED : TEXT \"Timeline\" NOT FOUND", moxtraHomePage.doesTimeLineDisplayed(driver));

        //Step6. Click the conversation that you want to send fil
        moxtraHomePage.clickConversation(driver);

        //Step7. Click the add file button
        WebUtil.click(driver, By.xpath("//a[@data-original-title = 'Add File']"));

        //Step8. Click Desktop
        String notes = "This is for testing";
        WebUtil.executorButtonClick(driver, By.linkText("Note"));
        WebUtil.doesElementDisplayed(driver, By.className("mx-webdoc-editor"));

        //Step9. switch to iframe (notes editor)
        driver.switchTo().frame("mx-webdoc-editor-textarea_ifr");

        //Step10. fill in text
        String testNotes = "This is for testing";
        WebUtil.clearAndFillKeys(driver,By.xpath("//body[@id = 'tinymce']"), testNotes);

        //Step11. Switch back from iframe and click Close
        driver.switchTo().defaultContent();
        WebUtil.click(driver, By.id("closeEditor"));

        //Step12. Verify the return to the homepage and the note did create
        moxtraHomePage.doesTimeLineDisplayed(driver);
        moxtraHomePage.verifyUploadedFile(driver,testNotes);
    }
    @Test
    public void sendAndDeleteFileShouldBeSuccessful() throws IOException {
        final String file = "jvminternal.JPG";
        //Step1. Go to moxtra website (http://www.moxtra.com)
        SignInPage signInPage = WebUtil.goToSignInPage(driver);

        //Step2. Verify the Sign In Page
        assertTrue("Validation FAILED : TEXT \"Account Sign In\" NOT FOUND", signInPage.doesSignInPageDisplayed(driver));  // if the element is not displayed, throws an AssertionError with the given msg.

        //Step3. Enter email and  password
        SignInPage.enterEmail(driver, "testmoxtraframework@gmail.com");
        SignInPage.enterPassword(driver, "thisisfortesting");

        //Step4. Sign in
        MoxtraHomePage moxtraHomePage = SignInPage.clickSignIn(driver);

        //Step5. Verify user did sign in
        assertTrue("Validation FAILED : TEXT \"Timeline\" NOT FOUND", moxtraHomePage.doesTimeLineDisplayed(driver));

        //Step6. Click the conversation that you want to delete the file
        moxtraHomePage.clickConversation(driver);

        //Step7. Click the add file button
        WebUtil.click(driver, By.xpath("//a[@data-original-title = 'Add File']"));

        //Step8. Click Desktop
        WebUtil.executorButtonClick(driver, By.linkText("Desktop"));

        //Step9. Use AutoIT to upload the file since there is not "input" field for this element
        Runtime.getRuntime().exec("C:\\Users\\Angie_Yang\\Desktop\\deleteFile.exe");    // uploadFile.exe is the compiled file of AutoIT script

        //Step 10. Verify if the file did upload

        moxtraHomePage.verifyUploadedFile(driver,file);

        //Step11. Locate the file you want to delete
        WebUtil.click(driver, By.xpath("//div[@class='mx-chat-item mx-branding-feed']/div[@class='mx-message' and contains(text(), 'Moxtra Test added an image') and contains(text(), '" + file +"')]/..//a[@data-original-title='More Options']"));
        WebUtil.click(driver, By.xpath("//div[@class='mx-chat-item mx-branding-feed']/div[@class='mx-message' and contains(text(), 'Moxtra Test added an image') and contains(text(), '" + file +"')]/..//span[text()='Delete']"));
        WebUtil.click(driver, By.xpath("//button[text() = 'OK']"));

        //Step12. Verify if it did delete
        WebUtil.doesElementDisplayed(driver, By.xpath("(//div[@class='mx-message'])[last()][contains(text(), 'Moxtra Test deleted a file') and contains(text(),'" + file + "')]"));
    }

    @After
    //Close the browser after each test case runs
    public void tearDown() {
        driver.close();
    }
}
