/*
 * Copyright 2006 ThoughtWorks, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

/* Overwriting code from ThoughtWorks to be used with TestingBot.com */

package com.testingbot;

import com.thoughtworks.selenium.HttpCommandProcessor;

/**
 *
 * @author testingbot
 */
public class TestingBotHttpCommandProcessor extends HttpCommandProcessor
{
    public String sessionIdBackup;
    
    /** Specifies a server host/port, a command to launch the browser, and a starting URL for the browser.
     * 
     * @param serverHost - the host name on which the Selenium Server resides
     * @param serverPort - the port on which the Selenium Server is listening
     * @param browserStartCommand - the command string used to launch the browser, e.g. "*firefox" or "c:\\program files\\internet explorer\\iexplore.exe"
     * @param browserURL - the starting URL including just a domain name.  We'll start the browser pointing at the Selenium resources on this URL,
     * @param extensionJs - extension Javascript for this session
     * e.g. "http://www.google.com" would send the browser to "http://www.google.com/selenium-server/core/RemoteRunner.html"
     */
    public TestingBotHttpCommandProcessor(String serverHost, int serverPort, String browserStartCommand, String browserURL) {
        super(serverHost, serverPort, browserStartCommand, browserURL);
    }
    
    /** Specifies the URL to the CommandBridge servlet, a command to launch the browser, and a starting URL for the browser.
     * 
     * @param pathToServlet - the URL of the Selenium Server Driver, e.g. "http://localhost:4444/selenium-server/driver/" (don't forget the final slash!)
     * @param browserStartCommand - the command string used to launch the browser, e.g. "*firefox" or "c:\\program files\\internet explorer\\iexplore.exe"
     * @param browserURL - the starting URL including just a domain name.  We'll start the browser pointing at the Selenium resources on this URL,
     * @param extensionJs - extension Javascript for this session
     */
    public TestingBotHttpCommandProcessor(String pathToServlet, String browserStartCommand, String browserURL) {
        super(pathToServlet, browserStartCommand, browserURL);
    }
    
    public String getSessionId() {
        return this.sessionIdBackup;
    }
    
    protected void setSessionInProgress(String result) {
        if (result != null) {
            this.sessionIdBackup = result;
        }
        super.setSessionInProgress(result);
    }
    
    public String doCommand(String commandName, String[] args) {
        TestingBotRemoteCommand command = new TestingBotRemoteCommand(commandName,args);
        String result = executeCommandOnServlet(command.getCommandURLString());
        if (result == null) {
            throw new NullPointerException("Selenium Bug! result must not be null");
        }
        if (!result.startsWith("OK")) {
            return throwAssertionFailureExceptionOrError(result);
        }
        return result;
    }
}
