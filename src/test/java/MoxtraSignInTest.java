import com.moxtra.pageobjects.MoxtraHomePage;
import com.moxtra.pageobjects.SignInPage;
import com.moxtra.util.WebUtil;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


import static org.junit.Assert.assertTrue;

/* Sign In Log Out test case for the Moxtra application
 * Created by Yiting_Yang on 2/25/2016.
 */
public class MoxtraSignInTest {

    WebDriver driver = new FirefoxDriver();   //create a webdriver

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
    @Test
    public void SendAndReceiveMsg(){
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
    @After
    //Close the browser after each test case runs
    public void tearDown(){
        driver.close();
    }
}
