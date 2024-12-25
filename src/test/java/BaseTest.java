import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BaseTest {
    private String s = null;

    @Test
    public void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        System.out.println(sdf.format(new Date()));
        System.out.println(sdf.format(System.currentTimeMillis()));
    }

    @Test
    public void testSb() {
        StringBuilder sb = new StringBuilder();
        sb.append("abc")
                .append("def")
                .append(s);
        System.out.println(sb);
    }
}
