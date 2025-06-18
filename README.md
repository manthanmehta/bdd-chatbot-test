# BDD Chatbot Automation Testing Framework

This project simulates automated QA testing for a healthcare chatbot using Java, Selenium, and Cucumber BDD. Also defined process to scale this project using docker and selenium grid

##  Project Structure

```
bdd-chatbot-test/
├── pom.xml
├── README.md
├── src/
│   └── test/
│       ├── java/
│       │   ├── stepdefs/ChatbotSteps.java
│       │   └── utils/DriverFactory.java
│       └── resources/
│           └── features/chatbot.feature
├── chatbot-ui/
│   └── index.html (sample bot interface)
```

## Features Covered

- Simulate user conversations with chatbot UI
- Validate correct intent recognition and bot response
- Uses **Singleton WebDriver** to maintain a single browser instance
- Easily scalable with **Dockerized Selenium Grid (disposable nodes)**

---

## Setup Instructions

### 1. Prerequisites

- Java 11+
- Maven
- Chrome + ChromeDriver (or use Dockerized grid)

### 2. Install dependencies

```bash
mvn clean install
```

### 3. Run tests

```bash
mvn test
```

---

## Singleton WebDriver Usage(Instantiate single WebDriver instance(driver) throughout the test session)

We use a `DriverFactory` class to instantiate WebDriver once and reuse across all steps.

###  `DriverFactory.java`

```java
public class DriverFactory {
    private static WebDriver driver;
    private  DriverFactory(){}
    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
```

In your `@Before` and `@After` Cucumber hooks:

```java
@Before
public void setup() {
    driver = DriverFactory.getDriver();
}

@After
public void tearDown() {
    DriverFactory.quitDriver();
}
```

---

## Docker + Selenium Grid (Disposable)

To scale this across different browsers or different os or run parallel sessions:
Can be achieved through docker-compose as well! 

###  1. Create a disposable Selenium Grid

```bash
docker network create grid

docker run -d -p 4444:4444 --name selenium-hub --net grid selenium/hub

docker run -d --net grid --shm-size="2g" -e HUB_HOST=selenium-hub selenium/node-chrome
```

###  2. Update DriverFactory to use RemoteWebDriver

```java
if (driver == null) {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setBrowserName("chrome");
    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
}
```

### 🧪 3. Run tests pointing to the grid

No additional change needed—your Selenium will now execute inside Dockerized grid browsers.

---

## Sample Feature File (chatbot.feature)

```gherkin
Feature: Validate chatbot intent and response
  Scenario: Booking an appointment
    Given I launch the chatbot UI
    When I enter "I want to book an appointment"
    Then I should see the bot respond with "Sure, who would you like to see?"
```

---

##  Contact

For questions, reach out to manthanmehtapx@gmail.com

---

