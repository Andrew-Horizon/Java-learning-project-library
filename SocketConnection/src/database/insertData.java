package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class insertData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "2019_lhy";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String insertDataSQL = "INSERT INTO userMessage (id,username, password) VALUES ('00001','marx', '12345567890')";
            statement.executeUpdate(insertDataSQL);
            System.out.println("数据插入成功");
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}