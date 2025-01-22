package com;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ebay {

    public static void main(String[] args) {
        
        System.setProperty("webdriver.chrome.driver", "D:\\AutomationScript\\assignments\\chrome.exe"); // Change this path

        
        WebDriver driver = new ChromeDriver();

        try {
           
            driver.get("https://www.ebay.com");

           
            WebElement searchBox = driver.findElement(By.xpath("//input[@id='gh-ac']"));
            searchBox.sendKeys("book");
            searchBox.submit();

            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".s-item")));

            List<WebElement> searchResults = driver.findElements(By.cssSelector(".s-item .s-item__link"));
            if (searchResults.size() > 0) {
                WebElement firstBook = searchResults.get(0); 
                firstBook.click();
            } else {
                System.out.println("No search results found.");
                return;
            }

            
            Set<String> windowHandles = driver.getWindowHandles();
            String mainWindow = driver.getWindowHandle(); 
            for (String handle : windowHandles) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle); 
                    break;
                }
            }

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Add to cart')]")));
            WebElement addToCartButton = driver.findElement(By.xpath("//span[contains(text(),'Add to cart')]"));
            addToCartButton.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[aria-label='Your shopping cart contains 1 item'] svg")));
            WebElement cart = driver.findElement(By.cssSelector("a[aria-label='Your shopping cart contains 1 item'] svg"));

            String cartCount = cart.getText();
            if (cartCount.equals("1")) {
                System.out.println("Test Passed: Item successfully added to cart.");
            } else {
                System.out.println("Test Failed: Cart count is incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}

