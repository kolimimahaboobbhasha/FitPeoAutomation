package FitPeo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;  // Import TestNG Assert class

import java.time.Duration;

public class FitPeoAutomation {
    static WebDriver driver;


    public static void main(String[] args) {
        // Set up WebDriver
        WebDriverManager.edgedriver().setup();
         driver = new EdgeDriver();
         driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='80%'");

        try {
            // Step 1: Navigate to FitPeo Homepage
            driver.get("https://fitpeo.com");

            // Step 2: Navigate to the Revenue Calculator Page
            driver.findElement(By.linkText("Revenue Calculator")).click();

            // Step 3: Scroll Down to the Slider Section
            WebElement sliderSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiBox-root css-19zjbfs']")));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sliderSection);

            // Step 4: Adjust the Slider to 820
            WebElement slider = driver.findElement(By.cssSelector("span[class*=\"MuiSlider-th\"]"));
            WebElement sliderValueField = driver.findElement(By.cssSelector("input[class*=\"MuiInputBase-input\"]"));


            // Loop until the slider value reaches 820
             Actions action = new Actions(driver);
            while (true) {
                // Move the slider
                action.clickAndHold(slider).moveByOffset(2, 0).release().perform();  // Adjust the offset as per slider functionality

                // Get the value of the slider (assumed it's in an input field with id "slider-value-id")
                String sliderValue = sliderValueField.getAttribute("value");

                // Check if the value has reached 820
                if (sliderValue.equals("818")) {
                    action.sendKeys(Keys.ARROW_RIGHT).perform();
                    action.sendKeys(Keys.ARROW_RIGHT).perform();
                    System.out.println("Slider updated to 820 successfully.");
                    break;  // Exit the loop when the slider reaches 820
                }
            }

            // Step 5: Update the Text Field to 560
            sliderValueField.click();
            Thread.sleep(3000);

            // Use JavascriptExecutor to clear the input field
            js.executeScript("arguments[0].value = '';", sliderValueField);

            Thread.sleep(3000);
            String textToType = "560";

            // Using JavaScript to set the input field value and simulate input events
            js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", sliderValueField, textToType);
            System.out.println("560 Entered");

            // Step 6: Validate Slider Value
            String sliderValueAfterTextInput = sliderValueField.getAttribute("value");
            Assert.assertEquals(sliderValueAfterTextInput, "560", "Slider value does not match expected value.");

            // Wait for the checkboxes container to be visible
            Thread.sleep(4000);
            WebElement RPM_element=driver.findElement(By.xpath("//p[contains(text(),'30 minutes of professional time dedicated to the patient per 30-day period.')]"));
        js.executeScript("arguments[0].scrollIntoView(true)",RPM_element);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@type='checkbox']")).click();
        driver.findElement(By.xpath("//div[@class='MuiBox-root css-1p19z09']//div[2]//label[1]//span[1]//input[1]")).click();
        driver.findElement(By.xpath("//div[3]//label[1]//span[1]//input[1]")).click();
        driver.findElement(By.xpath("//div[8]//label[1]//span[1]//input[1]")).click();

            // Step 7: Select CPT Codes
//            String[] cptCodes = {"99091", "99453", "99454", "99474"};
//            for (int i = 0; i < cptCodes.length; i++) {
//                // Find the checkbox for the current CPT code
//                WebElement checkbox = driver.findElement(By.xpath("//input[text()='CPT-" + cptCodes[i] + "']"));
//
//                // If checkbox is not selected, click it
//                if (!checkbox.isSelected()) {
//                    checkbox.click();
//                }
//
//                // If we are on the second group of checkboxes (99454, 99474), scroll into view after clicking the first two
//                if (i == 1) {
//                    WebElement nextCheckbox = driver.findElement(By.xpath("//input[@value='CPT-" + cptCodes[i + 1] + "']"));
//                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextCheckbox);
//                }
//            }

            // Step 8: Validate Total Recurring Reimbursement
            WebElement totalReimbursement = driver.findElement(By.cssSelector("p:nth-child(4) p:nth-child(1)"));
            String reimbursementValue = totalReimbursement.getText();
            if (reimbursementValue.equals("$110700")) {
                System.out.println("Total Recurring Reimbursement is validated successfully.");
            } else {
                System.out.println("Reimbursement validation failed. Expected: $110700, Found: " + reimbursementValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
       }
    }
}
