package com.moxtra.pageobjects;

import com.moxtra.util.WebUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


/**
 * Created by Angie_Yang on 3/16/2016.
 */
public class SignInPage {
    public boolean doesSignInPageDisplayed(WebDriver driver){
        WebUtil.waitForElementToBeVisible(driver, By.className("page-title"));
        //find the element by its class name
        return WebUtil.doesElementDisplayed(driver, By.className("page-title"));
    }

    public static void enterEmail(WebDriver driver, String emailAdress) {
        WebUtil.clearAndFillKeys(driver, By.name("email"), emailAdress);
    }

    public static void enterPassword(WebDriver driver, String password) {
        WebUtil.clearAndFillKeys(driver, By.name("password"), password);
    }

    public static MoxtraHomePage clickSignIn(WebDriver driver) {
        WebUtil.click(driver, By.xpath("//button[contains(text(),'Sign In')]"));
        WebUtil.waitForElementToBeVisible(driver, By.linkText("Timeline"));
        return PageFactory.initElements(driver, MoxtraHomePage.class);
    }
}
