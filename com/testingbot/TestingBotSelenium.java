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

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.HttpCommandProcessor;

/**
 *
 * @author testingbot
 */
public class TestingBotSelenium extends DefaultSelenium 
{
    public TestingBotSelenium(String serverHost, int serverPort, String browserStartCommand, String browserURL) {
        super(serverHost, serverPort, browserStartCommand, browserURL);
        this.commandProcessor = new TestingBotHttpCommandProcessor(serverHost, serverPort, browserStartCommand, browserURL);
    }
    
    public TestingBotSelenium(CommandProcessor processor) {
        super(processor);
    }
    
    public TestingBotHttpCommandProcessor getProcessor() {
        return (TestingBotHttpCommandProcessor) this.commandProcessor;
    }
}
