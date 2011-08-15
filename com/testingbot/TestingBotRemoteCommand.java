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

import com.thoughtworks.selenium.DefaultRemoteCommand;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author testingbot
 */
public class TestingBotRemoteCommand extends DefaultRemoteCommand 
{
   public String command;
   public String[] args;
   
   private String _command;
   private String[] _args;
   
   public TestingBotRemoteCommand(String command, String[] args) {
        super(command, args);
        this._command = command;
        this._args = args;
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

   public String getCommandURLString() {
        StringBuffer sb = new StringBuffer("cmd=");
        sb.append(urlEncode(this._command));
        String[] tokens = {"0", "0"};
        try {
            tokens = this.getTestingBotData();
        } catch (IOException ex) {
            System.out.println("Could not find file ~/.testingbot, please create it first.");
            Logger.getLogger(TestingBotRemoteCommand.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        sb.append("&client_key=" + tokens[0] + "&client_secret=" + tokens[1]);
        if (this._args == null) return sb.toString();
        for (int i = 0; i < this._args.length; i++) {
            sb.append('&');
            sb.append(Integer.toString(i+1));
            sb.append('=');
            sb.append(urlEncode(this._args[i]));
        }
        return sb.toString();
    }
}
