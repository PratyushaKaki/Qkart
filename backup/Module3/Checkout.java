package QKART_SANITY_LOGIN.Module1;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/checkout";

    public Checkout(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToCheckout() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    /*
     * Return Boolean denoting the status of adding a new address
     */
    public Boolean addNewAddress(String addresString) {
        try {
            WebElement addressbtn = driver.findElement(By.id("add-new-btn"));
            addressbtn.click();
            driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[2]/div[1]/div/textarea[1]")).clear();
            driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[2]/div[1]/div/textarea[1]")).sendKeys(addresString);

            //WebDriverWait wait = new WebDriverWait(driver, 10);

            // try {
            //     wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[1]/div[2]/div[1]")));

            // } catch(Exception e) {
            //     return false;
            // }

            Thread.sleep(2000);
            WebElement addbtn = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[2]/div[2]/button[1]"));
            addbtn.click();
            /*
             * Click on the "Add new address" button, enter the addressString in the address
             * text box and click on the "ADD" button to save the address
             */
            return false;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            return false;

        }
    }

    /*
     * Return Boolean denoting the status of selecting an available address
     */
    public Boolean selectAddress(String addressToSelect) {
        try {
            if(driver.findElement(By.xpath("//*[text()='"+addressToSelect+"']")).isDisplayed()) {
                driver.findElement(By.xpath("//*[text()='"+addressToSelect+"']//preceding-sibling::span")).click();
                return true;

            } else {
                System.out.println("Unable to find the given address");
            }

            WebElement parentBox = driver.findElement(By.xpath("(//div[@class='MuiBox-root css-0'])[1]"));
            List<WebElement> allBoxes = parentBox.findElements(By.className("not-selected"));
            for(WebElement box: allBoxes) {
                if(box.findElement(By.xpath("//div[contains(@class, 'address-item not selected')]/div/p")).getText().replaceAll(" ", "").equals(addressToSelect.replaceAll(" ", ""))) {
                    box.findElement(By.tagName("input")).click();
                    return true;
                }
            }
            
            /*
             * Iterate through all the address boxes to find the address box with matching
             * text, addressToSelect and click on it
             */
            System.out.println("Unable to find the given address");
            return false;
        } catch (Exception e) {
            System.out.println("Exception Occurred while selecting the given address: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of place order action
     */
    public Boolean placeOrder() {
        try {
            WebElement placeorderbtn = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[1]/div/button[2]"));
            placeorderbtn.click();
            return false;

            //return false;

        } catch (Exception e) {
            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the insufficient balance message is displayed
     */
    public Boolean verifyInsufficientBalanceMessage() {
        try {
            WebElement alert = driver.findElement(By.id("notistack-snackbar"));
            if(alert.isDisplayed()) {
                if(alert.getText().equals("You do not have enough balance in your wallet for this purchase")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exception while verifying insufficient balance message: " + e.getMessage());
            return false;
        }
    }
}
