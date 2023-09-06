import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class scopeA {
    public WebDriver driver = new ChromeDriver();
    POM_A pom = new POM_A(driver);

    @BeforeTest
    public void openBrowser() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void firstScope() {
        driver.navigate().to("https://www.google.com");
        driver.manage().window().maximize();

        // search for first keyword
        pom.search().sendKeys("facebook");
        pom.search().sendKeys(Keys.ENTER);

        // search for second keyword
        pom.search().clear();
        pom.search().sendKeys("news");
        pom.search().sendKeys(Keys.ENTER);

        // check number of results exist or not on UI
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(pom.resultStats().isDisplayed(), "Number of results not exist");

        // Scroll down
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // go to next page
        pom.nextBtn().click();

        // validate The number of results on page 2 is equal or not to page 3
        String text1 = pom.resultStats().getText(); // get # of results from page 2
        System.out.println(text1); // print # of results from page 2

        // Scroll down
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // go to next page
        pom.nextBtn().click();

        // get # of results from page 3
        String text2 = pom.resultStats().getText();
        System.out.println(text2); // print # of results from page 3

        // split number of result from text
        String number = text1.replaceAll("[^\\d,.]+", "").trim();
        // remove any numbers around the number of results
        String numOfResults = number.substring(1, number.length() - 4);

        // print number of results
        System.out.println("Number of Results : " + numOfResults);

        // Validate if the number of results on page 2 is equal to page 3 or not
        if (text1.contains(numOfResults) && text2.contains(numOfResults)) {
            System.out.println("The number of results on page 2 is equal to page 3 ");
        } else {
            System.out.println("The number of results on page 2 is not equal to page 3 ");
        }

        // Validate there are different search suggestions displayed at the end of the page
        soft.assertTrue(pom.searchSuggestions().isDisplayed(), "No different search suggestions displayed at the end of the page");

        // print different search suggestions
        //System.out.println(pom.searchSuggestions().getText());

        soft.assertAll();
    }

    @AfterTest
    public void closeBrowser() {
        driver.close();
    }


}


