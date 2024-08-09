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


    @BeforeTest
    public void InitBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.amazon.eg/"); //navigate to amazon website

        driver.manage().window().maximize();

    }

    @Test(priority = 1)
    public void InvalidLogin(){


        WebElement SignInButton = driver.findElement(By.id("nav-link-accountList-nav-line-1"));
        SignInButton.click();

        WebElement Email = driver.findElement(By.xpath("//input[@name=\"email\"]"));
        Email.sendKeys("tarekmaher1498@gmail.com"); // typing the email in email typebox

        WebElement ContinueButton = driver.findElement(By.className("a-button-input"));
        ContinueButton.click();

        WebElement errorMessage   = driver.findElement(By.className("a-alert-content"));
        Assert.assertTrue(errorMessage.isDisplayed(),".لا يمكننا العثور على حساب بعنوان البريد الإلكتروني هذا");

        takeScreenshot("VerifyItemAdded");
    }

    @Test(priority = 2)
    public void VerifyItemAdded(){

        driver.get("https://www.amazon.eg/");

        WebElement todayDeals = driver.findElement(By.xpath("//a[@href=\"/deals?ref_=nav_cs_gb\"]"));
        todayDeals.click();

        WebElement secondCategory = driver.findElement(By.xpath("(//div[@class=\"a-row a-carousel-controls a-carousel-row a-carousel-has-buttons\"]//a)[2]"));
        secondCategory.click();

        WebElement firstProduct = driver.findElement(By.xpath("(//ol[@class=\"sl-sobe-carousel-viewport-row-inner\"]//a)[1]"));
        firstProduct.click();

        WebElement secondItem1 = driver.findElement(By.xpath("(//ul[@class=\"a-unordered-list a-nostyle a-horizontal octopus-pc-card-list octopus-pc-card-height-v3\"]//a)[2]"));
        secondItem1.click();


        WebElement qty= driver.findElement(By.id("a-autoid-9-announce"));
        qty.click();

        WebElement qty1= driver.findElement(By.id("quantity_1"));
        qty1.click();

        WebElement addToCartButton1 = driver.findElement(By.id("add-to-cart-button"));
        addToCartButton1.click();

//        WebElement Name= driver.findElement(By.xpath("//span[@id=\"productTitle\"]"));


        WebElement cartButton = driver.findElement(By.xpath("//a[@href=\"/cart?ref_=sw_gtc\"]"));
        cartButton.click();

        WebElement itemName     = driver.findElement(By.xpath("//span[@class=\"a-truncate-cut\"]"));
        WebElement itemPrice    = driver.findElement(By.xpath("//span[@class=\"a-size-medium a-color-base sc-price sc-white-space-nowrap sc-product-price a-text-bold\"]"));
        WebElement itemQty      = driver.findElement(By.xpath("//span[@class=\"a-dropdown-prompt\"]"));
        WebElement itemSubtotal = driver.findElement(By.xpath("//span[@class=\"a-size-medium a-color-base sc-price sc-white-space-nowrap\"]"));

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

        driver.navigate().back();

        WebElement yourAdresses = driver.findElement(By.linkText("عناوينك"));
        yourAdresses.click();

        driver.navigate().back();

        WebElement yourLists = driver.findElement(By.linkText("قوائمك"));
        yourLists.click();

        WebElement screen = driver.findElement(By.xpath("//span[@class=\"al-intro-banner-header\"]"));
        Assert.assertTrue(screen.isDisplayed(), "القوائم");

    }

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(result.getName() + "_Failure");
        }
    }

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



    @AfterTest
    public void closeBrowser(){
         driver.close();

    }







}
