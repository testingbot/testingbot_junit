TestingBot.com JUnit package to use our Selenium Grid.
You can find more info on http://testingbot.com

Install
-------

Download our TestingBot-x-x-x.jar or use the source files in the com directory.

You also need JUnit (https://github.com/KentBeck/junit/downloads) and the Selenium Java Client Driver (http://release.seleniumhq.org/selenium-remote-control/1.0.1/).

Once you have these 3 jars, set your CLASSPATH to point to them:

    CLASSPATH=junit-4.10.jar:TestingBot-0.0.1.jar:testingbot/test/:selenium-java-client-driver.jar

Next, you need to make sure you have the file ~/.testingbot which stores your API key and API secret in this format: API_KEY:API_SECRET

You can get these keys by signing up on http://testingbot.com

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
                "firefox",
                "http://www.google.com/");
        selenium.start("version=10;platform=WINDOWS;screenshot=false");
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

Compile from Source
-------------------

To compile and test the Jar yourself you can use the following commands:

    javac -cp .:com/testingbot/:`echo lib/* | tr ' ' ':'` com/testingbot/* && jar cvf lib/TestingBot-0.0.1.jar com/testingbot/*.class
    javac -cp .:`echo lib/* | tr ' ' ':'` examples/*
    cd examples/
    java -cp .:`echo ../lib/* | tr ' ' ':'` com.testingbot.TestingBotTestRunner TestGoogle

Where lib contains all the dependency jars listed above.

Copyright
---------

Copyright (c) 2012 TestingBot.com
See LICENSE for more information.