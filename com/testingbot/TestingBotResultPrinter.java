/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testingbot;

import java.io.PrintStream;
import junit.framework.TestResult;
import junit.textui.ResultPrinter;

/**
 *
 * @author jochen
 */
public class TestingBotResultPrinter extends ResultPrinter {
    
    public TestingBotResultPrinter(PrintStream writer) {
            super(writer);
    }
    
    public void print(TestResult result, long runTime) {
	printHeader(runTime);
	printErrors(result);
	printFailures(result);
	printFooter(result);
    }
}
