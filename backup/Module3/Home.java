package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // SLEEP_STMT_10: Wait for Logout to complete
            // Wait for Logout to Complete
            Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any
     * errors
     */
    public Boolean searchForProduct(String product) {
        try {
            WebElement searchbox = driver.findElement(By.xpath("//*[@id='root']/div/div/div[1]/div[2]/div/input"));
            searchbox.clear();
            searchbox.sendKeys(product);
            //Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div/h4")), 
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div/div/div[1]/p[1]"))));
            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {
        };
        try {
            searchResults = driver.findElements(
                    By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div/div/div[1]/p[1]"));
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            WebElement result = driver
                    .findElement(By.xpath("//*[@id='root']/div/div/div[3]/div[1]/div[2]/div/h4"));
            status = result.isDisplayed();
            System.out.println("The statement is displayed" + status);
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            List<WebElement> product = driver.findElements(By.className("css-sycj1h"));
            // Thread.sleep(3000);
            for (WebElement cell : product) {
                if (productName.contains(cell.findElement(By.className("css-yg30e6")).getText())) {
                    cell.findElement(By.tagName("button")).click();
                    Thread.sleep(3000);
                    return true;
                }
            }
            /*
             * Iterate through each product on the page to find the WebElement corresponding
             * to the matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            System.out.println("Unable to find the given product");
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            WebElement checkoutbtn = driver.findElement(By.className("css-177pwqq"));

            checkoutbtn.click();
            
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart
     * operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {
            List<WebElement> cartproducts =
                    driver.findElements(By.xpath("//*[@class='MuiBox-root css-zgtx0t']"));
            //WebDriverWait wait = new WebDriverWait(driver, 10);
            int currentQty;
            for (int i = 1; i <= cartproducts.size(); i++) {
                String productname =
                        cartproducts.get(i - 1).findElement(By.className("css-1gjj37g"))
                                .findElement(By.tagName("div")).getText();
                if (productName.contains(productname)) {
                    currentQty = Integer.valueOf(cartproducts.get(i - 1)
                            .findElement(By.className("css-olyig7")).getText());
                    System.out.println(currentQty);
                    while (currentQty != quantity) {
                        if (currentQty < quantity) {
                            cartproducts.get(i - 1).findElements(By.tagName("button")).get(1)
                                    .click();
                           //wait.until(ExpectedConditions.textToBePresentInElement(cartproducts.get(i - 1).findElement(By.className("css-1gjj37g")), String.valueOf(currentQty + 1)));
                            Thread.sleep(2000);
                        } else {
                            cartproducts.get(i - 1).findElements(By.tagName("button")).get(0)
                                    .click();
                            //wait.until(ExpectedConditions.textToBePresentInElement(cartproducts.get(i - 1).findElement(By.className("css-1gjj37g")), String.valueOf(currentQty - 1)));
                            Thread.sleep(2000);
                        }
                        currentQty = Integer.valueOf(cartproducts.get(i - 1)
                                .findElement(By.xpath("//div[@data-testid=\"item-qty\"]"))
                                .getText());
                    }
                    return true;

                }

            }




            return false;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            WebElement cartParent = driver.findElement(By.className("cart"));
            List<WebElement> cartContents = cartParent.findElements(By.className("css-zgtx0t"));

            ArrayList<String> actualCartContents = new ArrayList<String>() {
            };
            for (WebElement cartItem : cartContents) {
                actualCartContents.add(cartItem.findElement(By.className("css-1gjj37g")).getText().split("\n")[0]);
            }

            for (String expected : expectedCartContents) {
                if (!actualCartContents.contains(expected)) {
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
}
