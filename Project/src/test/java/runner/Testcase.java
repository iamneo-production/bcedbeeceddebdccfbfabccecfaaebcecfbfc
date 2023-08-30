package runner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import utils.EventHandler;
import utils.base64;
import utils.LoggerHandler;
import utils.Reporter;
import utils.Screenshot;

public class Testcase extends Base {
    java.util.logging.Logger log =  LoggerHandler.getLogger();
    base64 screenshotHandler = new base64();
    ExtentReports reporter = Reporter.generateExtentReport();;

    
@Test(priority = 1,dataProvider = "LoginData")

    public void loginTest(String usrname,String pass,String expectedResult) throws InterruptedException, IOException {
        try{
            ExtentTest test = reporter.createTest("LoginTest", "Execution of LoginTest");    
                driver.get(prop.getProperty("url"));
                log.info("Browser Navigated to the Web Page");
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_TIME));
                driver.manage().window().maximize();
                WebElement uname=driver.findElement(By.name("username"));
                    uname.sendKeys(usrname);
                    WebElement pwd=driver.findElement(By.name("password"));
                    pwd.sendKeys(pass);
                    WebElement LoginBTN=driver.findElement(By.xpath("//button[@type='submit']"));
                    LoginBTN.click();
                    //This method will bring the current URL of the Page
                    String actualResult=driver.getCurrentUrl();
                    Screenshot.getScreenShot("Loginpage_Screenshot");
                    Assert.assertEquals(actualResult, expectedResult);
                    Screenshot.getScreenShot("Login Test_Screenshot");
                    test.log(Status.PASS, "Login Test");
                }
        catch (Exception ex) {
            ExtentTest test = reporter.createTest("login Test Exception");
            Screenshot.getScreenShot("Login_Screenshot");
            String base64Screenshot = screenshotHandler.captureScreenshotAsBase64(driver);
            test.log(Status.FAIL, "Unable to Perform loginTest", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            ex.printStackTrace();

            }
            WebDriverListener listener = new EventHandler();
            driver = new EventFiringDecorator<>(listener).decorate(driver);
            return;
    }
 @Test(priority=2)
     public void SearchTest() throws InterruptedException, IOException
     {
        try{
            ExtentTest test = reporter.createTest("SearchTest", "Execution of SearchTest");                
                driver.get(prop.getProperty("url"));
                 WebElement Uname=driver.findElement(By.name("username"));
                    Uname.clear();
                    Uname.sendKeys("Admin");
                    //4.Locate the password webelement
                    WebElement pwd = driver.findElement(By.name("password"));
                    pwd.clear();
                    pwd.sendKeys("admin123");
                    //5.Locate and click the login BTN
                    WebElement loginBTN=driver.findElement(By.xpath("//button[@type='submit']"));
                    loginBTN.click();
                    driver.findElement(By.xpath("//span[text()='Admin']")).click();
                    List<WebElement> name= driver.findElements(By.xpath("//input[@class='oxd-input oxd-input--active']"));
                    name.get(1).sendKeys("Charlie.Carter");
                    List<WebElement> dropdowns= driver.findElements(By.xpath("//div[@class='oxd-select-text-input']"));
                    name.get(0).click();
                    Thread.sleep(300);
                    Actions act=new Actions(driver);
                    act.moveToElement(dropdowns.get(0))
                    .click()
                    .keyDown(Keys.ARROW_DOWN)
                    .keyDown(Keys.ARROW_DOWN)
                    .release()
                    .build()
                    .perform();
                    //driver.findElement(By.xpath("//input[@placeholder='Type for hints...']")).sendKeys("Charlie Carter");
                    act.moveToElement(driver.findElement(By.xpath("//input[@placeholder='Type for hints...']")))
                    .click()
                    .sendKeys("Char").build()
                    .perform();
                    Thread.sleep(1500);
                    act .sendKeys("l")
                    .keyDown(Keys.ARROW_DOWN)
                    .keyDown(Keys.ENTER)
                    .release()
                    .build()
                    .perform();
                    Thread.sleep(500);
                    act.moveToElement(dropdowns.get(1))
                    .click()
                    .keyDown(Keys.ARROW_DOWN)
                    .keyDown(Keys.ENTER)
                    .release()
                    .build()
                    .perform();
                    driver.findElement(By.xpath("//button[@type='submit']")).click();
                    Screenshot.getScreenShot("SearchTest_Screenshot");
                    test.log(Status.PASS, "SearchTest");
                }
        catch (Exception ex) {
            ExtentTest test = reporter.createTest("SearchTest Exception");
            Screenshot.getScreenShot("SearchTest_Screenshot");
            String base64Screenshot = screenshotHandler.captureScreenshotAsBase64(driver);
            test.log(Status.FAIL, "Unable to Perform SearchTest", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            ex.printStackTrace();
            }   
               
            }

@DataProvider(name="LoginData")
               public Object[][] dataSource() {
               Object[][] data = new Object[2][3];
               data[0][0] ="Admin";              
               data[0][1] ="admin123";
               data[0][2] ="https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
               data[1][0] ="Admin";
               data[1][1] ="12345";
               data[1][2] ="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
               
               return data;
               
               }
               @BeforeMethod
    public void beforeMethod() throws MalformedURLException {
        openBrowser();
        WebDriverListener listener = new EventHandler();
        driver = new EventFiringDecorator<>(listener).decorate(driver);

    }

    @AfterMethod
    public void afterMethod() {
    driver.quit();
        reporter.flush();
        log.info("Browser closed");
    }

        
    

                   
                        
    
}
