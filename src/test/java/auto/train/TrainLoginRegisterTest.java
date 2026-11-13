package auto.train;

import auto.common.AutoBaseTest;
import auto.common.TestConfig;
import auto.pages.LoginPage;
import auto.pages.RegisterPage;
import auto.pages.StudentDashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 题目2：实习系统登录、注册的 Selenium 自动化测试。
 */
public class TrainLoginRegisterTest extends AutoBaseTest {

    @Test(description = "使用演示账号登录，应跳转到学生首页并显示姓名")
    public void testLogin() {
        new LoginPage(driver)
                .open(BASE_URL)
                .login(TestConfig.get("student.username"), TestConfig.get("student.password"));

        StudentDashboardPage dashboard = new StudentDashboardPage(driver).waitUntilLoaded();
        String url = dashboard.currentUrl();

        Assert.assertTrue(url.contains("/student/dashboard"),
                "登录后未跳转到学生首页");
        Assert.assertTrue(
                dashboard.pageText().contains(TestConfig.get("student.expected.name")),
                "学生首页未显示登录学生的姓名");

        saveScreenshot("train_login_success.png");
    }

    @Test(description = "注册新学生账号，注册成功后用新账号登录")
    public void testRegisterAndLogin() {
        String username = "stu_auto_" + System.currentTimeMillis();
        String password = TestConfig.get("student.password");

        // 1. 打开注册页并填写表单
        RegisterPage registerPage = new RegisterPage(driver)
                .open(BASE_URL)
                .fill(username,
                        TestConfig.get("register.real.name"),
                        password,
                        TestConfig.get("register.phone"),
                        TestConfig.get("register.email"))
                .submit();

        // 2. 校验注册结果：出现成功提示并跳回登录页
        Assert.assertTrue(registerPage.successMessage().contains("注册成功"),
                "注册后未出现成功提示");
        registerPage.waitUntilBackToLogin();
        saveScreenshot("train_register_success.png");

        // 3. 用刚注册的账号登录，验证注册数据已生效
        new LoginPage(driver)
                .open(BASE_URL)
                .login(username, password);
        new StudentDashboardPage(driver).waitUntilLoaded();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/student/dashboard"),
                "新注册账号登录失败");

        saveScreenshot("train_register_login_success.png");
    }
}
