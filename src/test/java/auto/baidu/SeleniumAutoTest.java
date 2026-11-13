package auto.baidu;

import auto.common.AutoBaseTest;
import auto.common.TestConfig;
import auto.pages.BaiduHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 题目1：用 Firefox 打开百度，自动搜索关键字 selenium，并判定搜索是否成功。
 */
public class SeleniumAutoTest extends AutoBaseTest {

    private static final String KEYWORD = "selenium";

    @Test(description = "百度搜索 selenium：标题、地址和结果区域都应包含关键字")
    public void testBaiduSearch() {
        BaiduHomePage page = new BaiduHomePage(driver)
                .open(TestConfig.get("baidu.url"))
                .search(KEYWORD)
                .waitForResults(KEYWORD);

        String title = page.title();
        String url = page.currentUrl();
        String pageText = page.pageText();

        System.out.println("当前标题：" + title);
        System.out.println("当前地址：" + url);

        Assert.assertTrue(title.toLowerCase().contains(KEYWORD),
                "页面标题未包含关键字 selenium");
        Assert.assertTrue(url.contains("wd=" + KEYWORD),
                "地址栏中未包含搜索参数 wd=selenium");
        Assert.assertTrue(pageText.toLowerCase().contains(KEYWORD),
                "搜索结果中没有出现关键字 selenium");

        saveScreenshot("baidu_search_result.png");
    }
}
