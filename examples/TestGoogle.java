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