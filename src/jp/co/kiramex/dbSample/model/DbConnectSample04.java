package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnectSample04 {

    public static void main(String[] args) {
        //３．データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
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
            String sql = "SELECT * FROM country WHERE Name = ?";
            pstmt = con.prepareStatement(sql);

            //５、６．Select文のじっくおと結果を格納／代入
            System.out.println("検索ワードを入力してください>");
            String input = keyIn();

            pstmt.setString(1, input);
            rs = pstmt.executeQuery();

            //７．結果を表示する
            while(rs.next()) {
                //Name列の値を取得
                String name = rs.getString("Name");
                //Population列の値を取得
                int population = rs.getInt("Population");
                //取得した値を表示
                System.out.println(name);
                System.out.println(population);
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
            if(pstmt != null) {
                try {
                    pstmt.close();
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

    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        }catch(IOException e){
    }
        return line;

}

}
