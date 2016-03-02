import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertTrue;

/* Sign In Log Out test case for the Moxtra application
 * Created by Yiting_Yang on 2/25/2016.
 */
public class MoxtraSignInTest {

    WebDriver driver = new FirefoxDriver();   //create a webdriver

    @Test
    public void moxtraSignInShoudBeSucessful() {

        //Step1. Go to moxtra website (http://www.moxtra.com)
        driver.get("https://www.moxtra.com/service/#login");     //use the driver to go to moxtra

        /*Step2. Verify the Sign In Page is displayed by validation the text "Account Sign In"
         *       which is displayed on the sign in page. If it is displayed, then we know the Sign
         *      In Page is displayed.
         */
        WebDriverWait wait = new WebDriverWait(driver, 20);  //Sometimes webdriver runs too fast to wait until the element loaded. Put driver to wait for 20 secs.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("page-title")));  //wait until the expected element shows up
        WebElement SignInPage = driver.findElement(By.className("page-title"));   //find the element by its class name
        assertTrue("Validation FAILED : TEXT \"Account Sign In\" NOT FOUND", SignInPage.isDisplayed());  // if the element is not displayed, throws an AssertionError with the given msg.

        //Step3. Enter email and  password
        WebElement emailTextBox = driver.findElement(By.name("email"));     //locate email textbox by its name
        emailTextBox.clear();      //clear the text box before sending keys
        emailTextBox.sendKeys("testmoxtraframework@gmail.com");   //send the key to the emailTextBox, email should appear in the text box

        WebElement passwordTextBox = driver.findElement(By.name("password"));   //locate passwordTextboxt by its name
        passwordTextBox.clear();
        passwordTextBox.sendKeys("thisisfortesting");

        //Step4. Sign in
        WebElement signinButton = driver.findElement(By.xpath("//button[contains(text(),'Sign In')]"));  //locate button by its XPath
        signinButton.click();  //Use .click() to click the button


        //Step5. Verify user did sign in, make sure the "Timeline" text is displayed.
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Timeline")));
        assertTrue("Validation FAILED : TEXT \"Timeline\" NOT FOUND", driver.findElement(By.linkText("Timeline")).isDisplayed());

        //Step6. Sign out
        WebElement accountIcon = driver.findElement(By.linkText("Account"));   //find Account Icon by its link Text
        accountIcon.click();  //Click the Account Icon

        WebElement logoutButton = driver.findElement(By.linkText("Log Out"));    //find log out button by its link Text
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", logoutButton);

        //Step7. Confirm Logout dialog.

        //When you click on the log out button, it should pop out a aler msg which ask you to comfirm Logout which contains yes and no button
        //The id changes everytime, so we shouldn't use id as a locator in this case.
        wait = new WebDriverWait(driver, 10);
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


        //Step8. Verify user did sign out
        //If users log out successfully, Account Sign In page will be displayed
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("page-title")));
        SignInPage = driver.findElement(By.className("page-title"));
        assertTrue("Validation FAILED : TEXT \"Account Sign In\" NOT FOUND", SignInPage.isDisplayed());


    }
     @After
     //Close the browser after each test case runs
     public void tearDown(){
         driver.close();
     }
}
