# AutoTest 自动化测试工程

本工程是《软件测试》课程实验四的 Selenium 自动化测试练习项目，使用 Maven 管理依赖，TestNG 组织测试用例，Selenium WebDriver 驱动 Firefox 浏览器完成页面操作与断言。

## 技术栈

- Java 21
- Maven
- Selenium WebDriver 4.18.1
- TestNG 7.8.0
- Firefox + geckodriver 0.37.1

## 测试用例

| 用例类 | 说明 |
| --- | --- |
| `auto.baidu.SeleniumAutoTest#testBaiduSearch` | 打开百度首页，搜索关键词并校验结果 |
| `auto.train.TrainLoginRegisterTest#testLogin` | 实习管理系统学生账号登录 |
| `auto.train.TrainLoginRegisterTest#testRegisterAndLogin` | 注册新账号并登录 |

## 目录结构

```
src/main/java      应用入口与通用代码
src/test/java      测试用例、页面对象、测试基类
src/test/resources 测试配置
```

## 运行方式

```bash
mvn test
```

运行前需安装 Firefox 浏览器，工程已内置 geckodriver 0.37.1。
