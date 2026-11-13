package auto.common;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

/**
 * Selenium 自动化测试基类：按 test.properties 启动/关闭 Firefox，
 * 用例失败时自动截图，并提供截图工具方法。
 */
public class AutoBaseTest {

    /** 实习系统前端入口，来自 test.properties */
    protected static final String BASE_URL = TestConfig.get("base.url");

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", TestConfig.get("gecko.driver.path"));

        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(TestConfig.get("firefox.binary"));
        options.addArguments("--width=1366", "--height=900");

        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(TestConfig.getInt("wait.implicit.seconds")));
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver == null) {
            return;
        }
        if (result.getStatus() == ITestResult.FAILURE) {
            String fileName = "fail_"
                    + result.getTestClass().getRealClass().getSimpleName() + "_"
                    + result.getMethod().getMethodName() + "_"
                    + System.currentTimeMillis() + ".png";
            try {
                saveScreenshot(fileName);
            } catch (RuntimeException e) {
                // 截图失败不应掩盖用例本身的失败原因
                System.err.println("失败截图保存异常：" + e.getMessage());
            }
        }
        driver.quit();
    }

    /** 把当前页面截图保存到 test.properties 配置的截图目录 */
    protected void saveScreenshot(String fileName) {
        File dir = new File(TestConfig.get("screenshot.dir"));
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("无法创建截图目录：" + dir.getAbsolutePath());
        }
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(source, new File(dir, fileName));
        } catch (IOException e) {
            throw new RuntimeException("截图保存失败：" + fileName, e);
        }
    }
}
