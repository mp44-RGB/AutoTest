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
 * 登录页：打开登录页、填写账号密码并提交。
 */
public class LoginPage {

    private static final By USERNAME_INPUT =
            By.cssSelector("input[placeholder='请输入用户名/学号']");
    private static final By PASSWORD_INPUT =
            By.cssSelector("input[placeholder='请输入密码']");
    private static final By LOGIN_BUTTON =
            By.cssSelector("button.login-btn");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(TestConfig.getInt("wait.explicit.seconds")));
    }

    public LoginPage open(String baseUrl) {
        driver.get(baseUrl + "/login");
        return this;
    }

    /** 填入账号密码并点击登录 */
    public LoginPage login(String username, String password) {
        typeInto(wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT)), username);
        typeInto(driver.findElement(PASSWORD_INPUT), password);
        driver.findElement(LOGIN_BUTTON).click();
        return this;
    }

    /**
     * 登录页输入框有默认值，SPA 路由切换后直接 clear() 可能失效，
     * 这里统一用“点击 → 光标移到末尾 → 逐字退格 → 输入新值”的方式覆盖。
     */
    private void typeInto(WebElement input, String value) {
        input.click();
        input.sendKeys(Keys.END);
        int length = input.getAttribute("value").length();
        for (int i = 0; i < length; i++) {
            input.sendKeys(Keys.BACK_SPACE);
        }
        input.sendKeys(value);
    }
}
