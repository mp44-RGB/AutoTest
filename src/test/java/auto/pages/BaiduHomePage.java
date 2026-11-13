package auto.pages;

import auto.common.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * 百度首页：执行关键字搜索并等待结果页加载。
 */
public class BaiduHomePage {

    /**
     * 百度首页存在两套搜索框：经典版为 #kw，新版为 #chat-textarea，
     * 不同账号/时段下发的版本不同，这里按顺序取第一个可见的。
     */
    private static final By[] SEARCH_BOXES = {
            By.id("kw"),
            By.cssSelector("#chat-textarea"),
            By.cssSelector("input[name='wd']")
    };
    private static final By RESULT_AREA = By.id("content_left");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public BaiduHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(TestConfig.getInt("wait.explicit.seconds")));
    }

    public BaiduHomePage open(String url) {
        driver.get(url);
        return this;
    }

    public BaiduHomePage search(String keyword) {
        WebElement searchBox = waitForVisibleSearchBox();
        searchBox.click();
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
        return this;
    }

    /** 轮询候选搜索框，返回第一个当前可见的 */
    private WebElement waitForVisibleSearchBox() {
        return wait.until(d -> {
            for (By locator : SEARCH_BOXES) {
                for (WebElement element : d.findElements(locator)) {
                    if (element.isDisplayed()) {
                        return element;
                    }
                }
            }
            return null;
        });
    }

    /** 等待结果页标题出现关键字且结果区域可见 */
    public BaiduHomePage waitForResults(String keyword) {
        wait.until(ExpectedConditions.titleContains(keyword));
        wait.until(ExpectedConditions.visibilityOfElementLocated(RESULT_AREA));
        return this;
    }

    public String title() {
        return driver.getTitle();
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    public String pageText() {
        return driver.findElement(By.tagName("body")).getText();
    }
}
