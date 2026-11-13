package auto.pages;

import auto.common.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * 注册页：填写学生注册表单并提交，读取注册结果提示。
 */
public class RegisterPage {

    private static final By USERNAME_INPUT =
            By.cssSelector("input[placeholder='3-30 位字符']");
    private static final By REAL_NAME_INPUT =
            By.cssSelector("input[placeholder='请输入真实姓名']");
    private static final By PASSWORD_INPUT =
            By.cssSelector("input[placeholder='至少 6 位']");
    private static final By CONFIRM_PASSWORD_INPUT =
            By.cssSelector("input[placeholder='再次输入密码']");
    private static final By OPTIONAL_INPUTS =
            By.cssSelector("input[placeholder='选填']");
    private static final By SUBMIT_BUTTON =
            By.cssSelector("button.login-btn");
    private static final By MESSAGE =
            By.cssSelector(".el-message__content");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(TestConfig.getInt("wait.explicit.seconds")));
    }

    public RegisterPage open(String baseUrl) {
        driver.get(baseUrl + "/register");
        return this;
    }

    /** 填写注册表单，选填项按顺序为手机号、邮箱 */
    public RegisterPage fill(String username, String realName, String password,
                             String phone, String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT))
                .sendKeys(username);
        driver.findElement(REAL_NAME_INPUT).sendKeys(realName);
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        driver.findElement(CONFIRM_PASSWORD_INPUT).sendKeys(password);

        List<WebElement> optionalInputs = driver.findElements(OPTIONAL_INPUTS);
        optionalInputs.get(0).sendKeys(phone);
        optionalInputs.get(1).sendKeys(email);
        return this;
    }

    public RegisterPage submit() {
        driver.findElement(SUBMIT_BUTTON).click();
        return this;
    }

    /** 等待并返回注册结果提示文本 */
    public String successMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE)).getText();
    }

    /** 等待注册成功后跳回登录页 */
    public void waitUntilBackToLogin() {
        wait.until(ExpectedConditions.urlContains("/login"));
    }
}
