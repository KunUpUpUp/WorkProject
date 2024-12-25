import com.taosdata.jdbc.TSDBDriver;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class LogTest {

    final static String jdbcUrl = "jdbc:TAOS://localhost:6030?user=root&password=taosdata";
    final static Properties connProps = new Properties();
    final static Logger logger = LoggerFactory.getLogger(LogTest.class);

    static {
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_CHARSET, "UTF-8");
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_LOCALE, "en_US.UTF-8");
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_TIME_ZONE, "UTC-8");
    }

    @Test
    public void testDQL() throws Exception {
        Calendar c = Calendar.getInstance();
        java.util.Date tenMinutes = new Date();
        c.setTime(tenMinutes);
        //防止最近时间的数据还没计算出来，所以往前提10分钟
        c.add(Calendar.MINUTE, -10);
        long tenMinutesAgo = c.getTime().getTime();
        String sql = "SELECT ts, current, location FROM test.meters WHERE ts > " + tenMinutesAgo + " limit 100";
//        String sql = "SELECT ts, current, location FROM test.meters WHERE ts > '" + tenMinutesAgo + "' limit 100";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, connProps);
             Statement stmt = connection.createStatement();
             // query data, make sure the database and table are created before
             ResultSet resultSet = stmt.executeQuery(sql)) {
            Timestamp ts;
            float current;
            String location;
            while (resultSet.next()) {
                ts = resultSet.getTimestamp(1);
                current = resultSet.getFloat(2);
                // we recommend using the column name to get the value
                location = resultSet.getString("location");

                // you can check data here
                logger.error("执行成功，SQL语句为 {}", sql);
            }
        } catch (Exception ex) {
            logger.error("执行出错，SQL语句为 {}\n异常信息: {}", sql, ex.getMessage(), ex);
        }
    }
}
