package auto.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 测试配置读取工具：默认读取 src/test/resources/test.properties，
 * 同名 JVM 系统属性（-Dkey=value）优先级更高，便于临时覆盖。
 */
public final class TestConfig {

    private static final String CONFIG_FILE = "test.properties";

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = TestConfig.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new IllegalStateException(
                        "测试配置缺失：src/test/resources/" + CONFIG_FILE);
            }
            PROPS.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException("读取测试配置失败：" + CONFIG_FILE, e);
        }
    }

    private TestConfig() {
    }

    public static String get(String key) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            value = PROPS.getProperty(key);
        }
        if (value == null) {
            throw new IllegalArgumentException("缺少测试配置项：" + key);
        }
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
