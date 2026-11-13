package auto.pages;

import auto.common.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * 学生首页：等待页面加载完成，并读取地址与页面文本用于断言。
 */
public class StudentDashboardPage {

    private static final By SIDE_MENU = By.cssSelector(".el-menu");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public StudentDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(TestConfig.getInt("wait.explicit.seconds")));
    }

    public StudentDashboardPage waitUntilLoaded() {
        wait.until(ExpectedConditions.urlContains("/student/dashboard"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(SIDE_MENU));
        return this;
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    public String pageText() {
        return driver.findElement(By.tagName("body")).getText();
    }
}
