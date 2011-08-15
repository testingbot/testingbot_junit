package com.testingbot;

import com.testingbot.TestingBotHttpCommandProcessor;
import com.thoughtworks.selenium.SeleneseTestCase;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;

/**
 *
 * @author testingbot
 */
public class TestingBotTestListener implements TestListener {
    
        private TestCase test; 
        ArrayList<String> errors = new ArrayList<String>();
        
        public TestingBotTestListener() { 
        } 

        public void addError(Test test, Throwable t) { 
            this.errors.add(t.getMessage());
        } 

        public void addFailure(Test test, AssertionFailedError t) { 
            this.errors.add(t.getMessage());
        } 
   
        public void endTest(Test test) { 
            try {
                this.sendData((TestingBotTestCase) test);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(TestingBotTestListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(TestingBotTestListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TestingBotTestListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
       private static String[] getTestingBotData() throws java.io.IOException{
            String filePath = System.getProperty("user.home") + "/.testingbot";

            byte[] buffer = new byte[(int) new File(filePath).length()];
            BufferedInputStream f = null;
            try {
                f = new BufferedInputStream(new FileInputStream(filePath));
                f.read(buffer);
            } finally {
                if (f != null) try { f.close(); } catch (IOException ignored) { }
            }
            String data = new String(buffer);
            String[] tokens = data.split(":");

            return tokens;
        }

        public void sendData(TestingBotTestCase test) throws UnsupportedEncodingException, MalformedURLException, IOException { 
            TestingBotSelenium selenium = (TestingBotSelenium) test.selenium;
            TestingBotHttpCommandProcessor processor = selenium.getProcessor();
            
            String data = null;
            
            StringBuilder sb = new StringBuilder();
            for(String s: this.errors) {
                sb.append(s);
            }
            
            String success = this.errors.isEmpty() ? "1" : "0";
            
            String[] tokens = {"0", "0"};
            try {
                tokens = this.getTestingBotData();
            } catch (IOException ex) {
                System.out.println("Could not find file ~/.testingbot, please create it first.");
                Logger.getLogger(TestingBotRemoteCommand.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        
            try {
                data = "client_key=" + tokens[0] + "&client_secret=" + tokens[1] + "&kind=4&success=" + success + "&session_id=" + URLEncoder.encode(processor.getSessionId(), "UTF-8") + "&name=" + URLEncoder.encode(test.getName(), "UTF-8") + "&status_message=" + URLEncoder.encode(sb.toString(), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(TestingBotTestListener.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Send data
            URL url = new URL("http://localhost:3000/hq");
            URLConnection conn = null;
            try {
                conn = url.openConnection();
            } catch (IOException ex) {
                Logger.getLogger(TestingBotTestListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            try {
                wr.write(data);
            } catch (IOException ex) {
                Logger.getLogger(TestingBotTestListener.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            wr.flush();
            
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            wr.close();
            rd.close();
        } 

        public void startTest(Test test) { 
            
        } 
}
