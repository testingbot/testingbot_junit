package com.testingbot;

import java.io.PrintStream;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.textui.ResultPrinter;
import junit.textui.TestRunner;

/**
 *
 * @author testingbot
 */
public class TestingBotTestRunner extends TestRunner {
    
    private static TestingBotTestListener testListener;
    TestResult results = null;
    public TestingBotResultPrinter fPrinter;
    
    public TestingBotTestRunner(String testClassName) {
        this(System.out);
        testListener = new TestingBotTestListener();
        Test test = super.getTest(testClassName);
        results = this.doRun(test);
    }
    
    public TestingBotTestRunner(PrintStream writer) {
       this(new TestingBotResultPrinter(writer));
    }
    
    /**
     * Constructs a TestRunner using the given ResultPrinter all the output
     */
    public TestingBotTestRunner(TestingBotResultPrinter printer) {
            fPrinter= printer;
    }
    
    @Override
    public TestResult doRun(Test test, boolean wait) {
        TestResult testEventDriver = createTestResult();
        testEventDriver.addListener(testListener);
        testEventDriver.addListener(fPrinter);
        long startTime= System.currentTimeMillis();
        test.run(testEventDriver);
        long endTime= System.currentTimeMillis();
	long runTime= endTime-startTime;
        fPrinter.print(testEventDriver, runTime);
        
        pause(wait);
        return testEventDriver;
    }
    
    public static void main(String args[]) {
        TestResult results = null;
        TestingBotTestRunner runner = null;
        
        runner = new TestingBotTestRunner(args[0]);
    } 
    
    public TestResult getResults() {
        return this.results;
    }
    
    public void setPrinter(ResultPrinter printer) {
            fPrinter = (TestingBotResultPrinter) printer;
    }
    
    public void setPrinter(TestingBotResultPrinter printer) {
            fPrinter = printer;
    }
}
