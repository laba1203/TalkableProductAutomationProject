package util;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import util.logging.Log;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class DriverConfig {

    //URL for selenium
    private static final String SELENOID_URL = "http://selenoid.tkbl:4444//wd/hub";
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /*The flag indicates whether the test will be executed on Local or Remote machine.
    !!! Please note always put the flag to TRUE before creation of pull request to origin/master !!!*/
    private static final boolean remoteExecution = ExecutionVariables.remoteExecution();

    @Parameters()
    private void setNewLocalDriver()
    {
        System.out.println("LOG - Util: Start creation of new Local WebDriver");
        final File file = new File(PropertyLoader.loadProperty("path.mac.webDriver"));
        System.setProperty(PropertyLoader.loadProperty("webDriver"), file.getAbsolutePath());

        tlDriver.set(new ChromeDriver());
        WaitFactory.setDefaultImplicitlyWait();

        System.out.println("LOG - Util: New Local WebDriver is created");
    }

    @Parameters()
    private static void setNewRemoteDriver(){
        System.out.println("LOG - Util: Start creation of new Remote WebDriver. Thread ID: <" + Thread.currentThread().getId() + ">");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("66.0");

//        capabilities.setBrowserName("firefox");
//        capabilities.setVersion("60.0");
        capabilities.setCapability("enableVNC", true);
//        capabilities.setCapability("enableVideo", true);

        URL url = null;
        try {
             url = new URL(SELENOID_URL);
        } catch (MalformedURLException e) {
            System.err.println("Exception found");
            e.printStackTrace();
        }

        RemoteWebDriver rDriver = new RemoteWebDriver(
                    url,
                    capabilities
            );
        rDriver.setFileDetector(new LocalFileDetector());//for uploading file into remote
        tlDriver.set(rDriver);
        resizeBrowser(tlDriver.get());

        System.out.println("LOG - Util: New Remote WebDriver created. Thread ID: <" + Thread.currentThread().getId() + ">");
    }

    public static boolean isDriverCreated(){
        return tlDriver.get() != null;
    }

    public static WebDriver getDriver(){
        if(isDriverCreated()){
            return tlDriver.get();
        }else {
            Assert.fail("FAILED in DriverConfig. Getting driver when it was not created. Thread <" + Thread.currentThread().getId() + ">");
            return null;
        }
    }

    public static void createDriver(){
        if(isDriverCreated()){
            quitAndRemoveWebDriver();
        }
        if(remoteExecution) {
            DriverConfig.setNewRemoteDriver(); //for remote testing
            WaitFactory.setDefaultImplicitlyWait();
        }else {
            new DriverConfig().setNewLocalDriver(); //for local testing
            WaitFactory.setDefaultImplicitlyWait();
        }
    }

    public static void quitAndRemoveWebDriver(){
        if(isDriverCreated()) {
            getDriver().quit();
        }
        tlDriver.remove();
    }

    private static void resizeBrowser(WebDriver driver) {
        Dimension d = new Dimension(1300,1200);
        //Resize current window to the set dimension
        driver.manage().window().setSize(d);
        tlDriver.set(driver);
    }

    //to be refactored

    public static String switchToUnknownWindow(String parentHandle){
        String switchedChildHandle = null;
        for(String childHandle : getDriver().getWindowHandles()){
            switchToUnknownWindow(parentHandle, childHandle);
            switchedChildHandle = childHandle;
        }
        return switchedChildHandle;
    }

    private static void switchToUnknownWindow(String parentHandle, String childHandle){
        if (!childHandle.equals(parentHandle)){
            getDriver().switchTo().window(childHandle);
            Log.switchedToWindowMsg(childHandle);
        }
    }

    public static void switchToWindow(String windowHandle){
        getDriver().switchTo().window(windowHandle);
        Log.switchedToWindowMsg(windowHandle);
    }



}
