import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class POM_A {
WebDriver driver;
public POM_A (WebDriver driver)
{
    this.driver=driver;
}

public WebElement search ()
{
return driver.findElement(By.id("APjFqb"));
}
public WebElement resultStats ()
{
    return driver.findElement(By.id("result-stats"));
}
public WebElement nextBtn ()
{
    return  driver.findElement(By.id("pnnext"));
}
public WebElement searchSuggestions ()
{
    return driver.findElement(By.className("y6Uyqe"));
}







}
