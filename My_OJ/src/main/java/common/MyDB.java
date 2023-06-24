package common;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDB {
    // 以下的sql服务器需要自己修改，这里使用本地的mysql作为演示
    //private static final String URL = "jdbc:mysql://[你的服务器的ip地址]:3306/MyOJ?characterEncoding=utf8&useSSL=false";
    private static final String URL = "jdbc:mysql://localhost:3306/MyOJ?characterEncoding=utf8&useSSL=false";
    //private static final String USER_NAME = "[填写你的服务器sql用户名]";
    private static final String USER_NAME = "root";
    //private static final String PASSWORD = "[填写你的服务器sql密码]";
    private static final String PASSWORD = "123456";
    private static volatile DataSource dataSource = null;

    private static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (MyDB.class) {
                if (dataSource == null) {
                    MysqlDataSource mysqlDataSource = new MysqlDataSource();
                    mysqlDataSource.setUrl(URL);
                    mysqlDataSource.setUser(USER_NAME);
                    mysqlDataSource.setPassword(PASSWORD);
                    dataSource = mysqlDataSource;
                }
            }
        }
        return dataSource;
    }
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
    public static void close(Connection con, PreparedStatement pre, ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pre != null) {
            try {
                pre.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
