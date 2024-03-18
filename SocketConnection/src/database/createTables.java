package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class createTables {

    public static void main(String[] args) {
        // JDBC连接数据库的URL，用户名和密码
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "2019_lhy";

        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 打开连接
            Connection connection = DriverManager.getConnection(url, user, password);

            // 创建 Statement 对象
            Statement statement = connection.createStatement();

            // 创建表的 SQL 语句
            String createTableSQL = "CREATE TABLE userMessage ("
                    + "id INT(10) PRIMARY KEY AUTO_INCREMENT, "
                    + "username VARCHAR(255), "
                    + "password VARCHAR(255))";

            // 执行创建表的 SQL 语句
            statement.executeUpdate(createTableSQL);

            System.out.println("Table 'userMessage' created successfully.");

            // 关闭连接
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}