package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChatbotSteps {
    WebDriver driver;

    @Before
    public void setup() {
        driver = DriverFactory.getDriver();
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Given("I launch the chatbot UI")
    public void i_launch_the_chatbot_ui() {
        driver.get("file://" + System.getProperty("user.dir") + "/chatbot-ui/index.html");
        System.out.println(" Step 1. Called Basic HTML Chat UI");
    }

    @When("I enter {string}")
    public void i_enter(String message) {
        WebElement input = driver.findElement(By.id("userInput"));
        input.sendKeys(message);
        driver.findElement(By.id("sendButton")).click();
        System.out.println(" Step 2. Clicked Send Button");
    }

//    @Then("I should see the bot respond with {string}")
//    public void i_should_see_the_bot_respond_with(String expected) throws InterruptedException {
//        Thread.sleep(1000); // wait for response
//       // WebElement response = driver.findElement(By.cssSelector(".bot-response:last-of-type"));
//        String expected="//div[text()='Sure, who would you like to see?, Intent Book_Appointment";
//        WebElement response= driver.findElement(By.xpath("//div[text()='Sure, who would you like to see?, Intent Book_Appointment"));
//
//        assertEquals(expected, response.getText());
//        System.out.println(" Step 3. Response Validated" +response.getText());
//    }
@Then("I should see the bot respond with {string}")
public void i_should_see_the_bot_respond_with(String expected) throws InterruptedException {
    Thread.sleep(1000);
    List<WebElement> responses = driver.findElements(By.cssSelector(".bot-response"));
    WebElement lastResponse = responses.get(responses.size() - 1);
    String actualText = lastResponse.getText();
    System.out.println("Bot Response: " + actualText);
    assertEquals(expected, actualText);

    System.out.println("Step 3. Response and intent validated.");
}

}
