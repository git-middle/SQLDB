package jp.co.kiramex.dbSample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnectSample02 {

    public static void main(String[] args) {
        //３．データベース接続と結果取得のための変数宣言
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //１．ドライバのクラスjava上で読み込む

            //２．DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "MySQL1192"
                    );

            //４．DBとやりとりする窓口（Statementオブジェクト）の作成
            stmt = con.createStatement();

            //５、６．Select文のじっくおと結果を格納／代入
            String sql = "SELECT * FROM country where Code = 'ABW'";
            rs = stmt.executeQuery(sql);

            //７－１．更新前の結果を表示する
            System.out.println("");
            if(rs.next()) {
              //Name列の値を取得
                String name = rs.getString("Name");
                //Population列の値を取得
                int population = rs.getInt("Population");
                //取得した値を表示
                System.out.println(name + "\n" + population);
            }

            //７－２．更新処理を行う
            System.out.println("");
            String updateSql = "update country set Population = 105000 where Code = 'ABW'";
            int count = stmt.executeUpdate(updateSql);
            System.out.println("更新行数" + count);

            //７－３．更新後の結果を表示する
            rs.close();
            System.out.println("");
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                String name = rs.getString("name");
                int population = rs.getInt("Population");
                System.out.println(name + "\n" + population);
            }


        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        }finally {
          //８．接続を閉じる
            if(rs != null) {
                try {
                    rs.close();
                }catch(SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if(stmt != null) {
                try {
                    stmt.close();
                }catch(SQLException e) {
                    System.err.println("Statementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }


            if(con!=null) {
                try {
                    con.close();
                }catch(SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }

        }

    }

}
