import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.thread.IThreadWorkerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.Thread;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class MDIAmazonTest {

    private WebDriver driver;

    //this function is to open the browser and go to amazon website
    @BeforeTest
    public void InitBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.amazon.eg/"); //navigate to amazon website

        driver.manage().window().maximize();

    }

    @Test(priority = 1)
    public void InvalidLogin(){
        
        //click on signin Button
        WebElement SignInButton = driver.findElement(By.id("nav-link-accountList-nav-line-1"));
        SignInButton.click();
        
        //click of the Email textbox and typing your Email
        WebElement Email = driver.findElement(By.xpath("//input[@name=\"email\"]"));
        Email.sendKeys("tarekmaher1498@gmail.com"); // typing the email in email typebox

        //click on continue button
        WebElement ContinueButton = driver.findElement(By.className("a-button-input"));
        ContinueButton.click();

        //check if the Exepected equal to the actual or not
        WebElement errorMessage   = driver.findElement(By.className("a-alert-content"));
        Assert.assertTrue(errorMessage.isDisplayed(),".لا يمكننا العثور على حساب بعنوان البريد الإلكتروني هذا");
        
    }

    @Test(priority = 2)
    public void VerifyItemAdded(){

        driver.get("https://www.amazon.eg/");
        
        //click on today'sDeals
        WebElement todayDeals = driver.findElement(By.xpath("//a[@href=\"/deals?ref_=nav_cs_gb\"]"));
        todayDeals.click();

        //select the second category
        WebElement secondCategory = driver.findElement(By.xpath("(//div[@class=\"a-row a-carousel-controls a-carousel-row a-carousel-has-buttons\"]//a)[2]"));
        secondCategory.click();

        //select the first product
        WebElement firstProduct = driver.findElement(By.xpath("(//ol[@class=\"sl-sobe-carousel-viewport-row-inner\"]//a)[1]"));
        firstProduct.click();

        //click on the second item
        WebElement secondItem1 = driver.findElement(By.xpath("(//ul[@class=\"a-unordered-list a-nostyle a-horizontal octopus-pc-card-list octopus-pc-card-height-v3\"]//a)[2]"));
        secondItem1.click();

        //click on the choose quantity dropbox
        WebElement qty= driver.findElement(By.id("a-autoid-9-announce"));
        qty.click();

        //click on 2
        WebElement qty1= driver.findElement(By.id("quantity_1"));
        qty1.click();

        //click on add to cart
        WebElement addToCartButton1 = driver.findElement(By.id("add-to-cart-button"));
        addToCartButton1.click();

        //click on cart button
        WebElement cartButton = driver.findElement(By.xpath("//a[@href=\"/cart?ref_=sw_gtc\"]"));
        cartButton.click();

        //getting the item data
        WebElement itemName     = driver.findElement(By.xpath("//span[@class=\"a-truncate-cut\"]"));
        WebElement itemPrice    = driver.findElement(By.xpath("//span[@class=\"a-size-medium a-color-base sc-price sc-white-space-nowrap sc-product-price a-text-bold\"]"));
        WebElement itemQty      = driver.findElement(By.xpath("//span[@class=\"a-dropdown-prompt\"]"));
        WebElement itemSubtotal = driver.findElement(By.xpath("//span[@class=\"a-size-medium a-color-base sc-price sc-white-space-nowrap\"]"));

        //check the expected to the actual
        Assert.assertEquals(itemName.getText(), "اندورا طقم من 3 شرابات سادة غير مرئية مضادة للانزلاق - اسود - مقاس واحد، قطن، الرجال" , "Item Name Is In/Correct");
        Assert.assertEquals(itemPrice.getText(), "85.00 جنيه" , "Price is In/correct");
        Assert.assertEquals(itemQty.getText(), "2" , "quantity is In/correct");
        Assert.assertEquals(itemSubtotal.getText(), "898.00 جنيه" , "Subtotal is In/correct");


    }

    @Test(priority = 3)
    public void YourLists(){

        driver.get("https://www.amazon.eg/");
        WebElement yourOrders = driver.findElement(By.linkText("مشترياتك"));
        yourOrders.click();
        //go to home page
        driver.navigate().back();

        //click on your addresses
        WebElement yourAdresses = driver.findElement(By.linkText("عناوينك"));
        yourAdresses.click();

        //go to home page
        driver.navigate().back();

        //click on your Lists
        WebElement yourLists = driver.findElement(By.linkText("قوائمك"));
        yourLists.click();

        //check if the expected equal to the actual
        WebElement screen = driver.findElement(By.xpath("//span[@class=\"al-intro-banner-header\"]"));
        Assert.assertTrue(screen.isDisplayed(), "القوائم");

    }
    //taking screenshot if the test fails
    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(result.getName() + "_Failure");
        }
    }
    //function to take screenshot
    public void takeScreenshot(String methodName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        String destPath = "./screenshots/" + methodName + "_" + timestamp + ".png";
        try {
            FileUtils.copyFile(srcFile, new File(destPath));
            System.out.println("Screenshot taken: " + destPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //close the browser
    @AfterTest
    public void closeBrowser(){
         driver.close();

    }







}
