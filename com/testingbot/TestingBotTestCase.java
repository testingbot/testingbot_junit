/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testingbot;

import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;
import junit.framework.AssertionFailedError;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestResult;

/**
 *
 * @author testingbot
 */
public class TestingBotTestCase extends SeleneseTestCase {
    private SeleneseTestBase stb = new SeleneseTestBase();
        
    /** Use this object to run all of your selenium tests */
    public Selenium selenium;
}
