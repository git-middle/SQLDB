package jp.co.kiramex.dbSample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TryWithResourcesSample01 {
    public static void main(String[]args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "SELECT * FROM country LIMIT 50";

                try (
                    Connection con = DriverManager.getConnection(
                         "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true","root","MySQL1192");

                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery(sql);){

                        while(rs.next()) {
                            String name = rs.getString("Name");
                            System.out.println(name);
                        }
                    }
        }catch(ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        }catch(SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        }
    }

}
