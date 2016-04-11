package com.moxtra.pageobjects;

import com.moxtra.util.WebUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Angie_Yang on 4/6/2016.
 */
public class MoxtraCalendarPage {
    public boolean doesScheduleMeetDisplayed(WebDriver driver) {
        //Verify Timeline text exists by linkText
        return WebUtil.doesElementDisplayed(driver, By.linkText("Schedule Meet"));
    }
}
