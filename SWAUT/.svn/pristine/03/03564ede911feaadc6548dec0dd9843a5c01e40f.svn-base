package Utilities;


import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener
  extends TestListenerAdapter
{
  public TestListener() {}
  
  public void onTestFailure(ITestResult result)
  {
    GlobalKeys.testFailure = true;
    GlobalKeys.testFailureCount += 1;
  }
  
  public void onTestSuccess(ITestResult result)
  {
    GlobalKeys.testSuccessCount += 1;
  }
  

  public void onTestSkipped(ITestResult result)
  {
    GlobalKeys.testSkippedCount += 1;
  }
}
