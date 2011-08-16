TestingBot.com JUnit package to use our Selenium Grid.
You can find more info on http://www.testingbot.com

Install
-------

Download our TestingBot-x-x-x.jar or use the source files in the com directory.

You also need JUnit (https://github.com/KentBeck/junit/downloads) and the Selenium Java Client Driver (http://release.seleniumhq.org/selenium-remote-control/1.0.1/).

Once you have these 3 jars, set your CLASSPATH to point to them:
    CLASSPATH=junit-4.9b4.jar:TestingBot-0.0.1.jar:testingbot/test/:selenium-java-client-driver.jar

Next, you need to make sure you have the file ~/.testingbot which stores your API key and API secret in this format: API_KEY:API_SECRET

You can get these keys by signing up on http://www.testingbot.com

You can now run your Selenium tests with Java.

In our repository you will find a test script which you can run by executing:
    java com.testingbot.TestingBotTestRunner TestGoogle


Example
-------

    import com.thoughtworks.selenium.*;
    import org.junit.*;
    import com.testingbot.*;


    public class TestGoogle extends TestingBotTestCase {
      public void setUp() throws Exception {
        TestingBotSelenium selenium = new TestingBotSelenium(
                "hub.testingbot.com",
                4444,
                "*safari",
                "http://www.google.com/");
        selenium.start();
        this.selenium = selenium;
      }
      public void testGoogle() throws Exception {
        this.selenium.open("/");
        assertEquals("Google", this.selenium.getTitle());
      }
    
     public void tearDown() throws Exception {
        this.selenium.stop();
     }
   }
        
Copyright
---------

Copyright (c) 2011 TestingBot.com
See LICENSE for more information.