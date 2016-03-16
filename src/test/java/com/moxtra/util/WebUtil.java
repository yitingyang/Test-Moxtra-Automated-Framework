package com.moxtra.util;

import com.moxtra.pageobjects.SignInPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Angie_Yang on 3/16/2016.
 */
public class WebUtil {
    public static SignInPage goToSignInPage(WebDriver driver) {
        driver.get("https://www.moxtra.com/service/#login");     //use the driver to go to moxtra
        return PageFactory.initElements(driver, SignInPage.class);
    }
}
