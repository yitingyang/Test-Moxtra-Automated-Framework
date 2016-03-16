import com.moxtra.pageobjects.MoxtraHomePage;
import com.moxtra.pageobjects.SignInPage;
import com.moxtra.util.WebUtil;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;


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
        //Step1. Sign In

        //Go to moxtra website (http://www.moxtra.com)
        driver.get("https://www.moxtra.com/service/#login");

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("page-title")));
        WebElement SignInPage = driver.findElement(By.className("page-title"));
        assertTrue("Validation FAILED : TEXT \"Account Sign In\" NOT FOUND", SignInPage.isDisplayed());


        WebElement emailTextBox = driver.findElement(By.name("email"));
        emailTextBox.clear();
        emailTextBox.sendKeys("testmoxtraframework@gmail.com");
        WebElement passwordTextBox = driver.findElement(By.name("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys("thisisfortesting");

        WebElement signinButton = driver.findElement(By.xpath("//button[contains(text(),'Sign In')]"));  //locate button by its XPath
        signinButton.click();  //Use .click() to click the button

        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Timeline")));
        assertTrue("Validation FAILED : TEXT \"Timeline\" NOT FOUND", driver.findElement(By.linkText("Timeline")).isDisplayed());

        //Step3. Click the conversation that you want to send message
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@title='For testing']")));
        WebElement conversation = driver.findElement(By.xpath("//span[@title='For testing']"));
        conversation.click();

        //Step4. Fill in the message and press enter to send the message
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='postComment']")));
        WebElement msgTextBox = driver.findElement(By.xpath("//textarea[@name='postComment']"));
        msgTextBox.clear();
        String expectMsg = "Hello";
        msgTextBox.sendKeys(expectMsg + "\n");

        //Step5. Verify if the message displayed as we sent
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='mx-message'])[last()]//span")));
        //There is a message box contains all message in this chatting room
        //so we need to find the last message element, which we just sent, using last() method of xpath
        WebElement actualMsg = driver.findElement(By.xpath("(//div[@class='mx-message'])[last()]//span"));
        //check if the message we passed is equal to the message displayed in the message box.
        assertEquals("Validation FAILED : THE MESSAGE DISPLAYED NOT EQUAL TO THE MESSAGE WE SENT.", expectMsg, actualMsg.getText());

    }
     @After
     //Close the browser after each test case runs
     public void tearDown(){
         driver.close();
     }
}
