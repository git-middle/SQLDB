package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnectSample05 {

    public static void main(String[] args) {
        //３．データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement spstmt = null;
        PreparedStatement ipstmt = null;
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
            String selectsql = "SELECT * FROM city where CountryCode = ?";
            spstmt = con.prepareStatement(selectsql);

            System.out.println("CountryCodeを入力してください>");
            String str1 = keyIn();

            spstmt.setString(1,str1);

            //５、６．Select文の実行と結果を格納／代入
            rs = spstmt.executeQuery();

            //７－１．更新前の結果を表示する
            System.out.println("更新前＝＝＝＝＝＝＝");
            while(rs.next()) {
                //Name列の値を取得
                String name = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                String district = rs.getString("District");
                //Population列の値を取得
                int population = rs.getInt("Population");
                //取得した値を表示
                System.out.println(name + "\t" + countryCode + "\t" + district + "\t" + population);

            }

            //７－２．更新処理を行う
            System.out.println("更新処理実行=====");

            //更新用SQLおよび更新用PreparedStatementオブジェクトを取得
            String insertSql = "INSERT INTO city(Name,CountryCode,District,Population)VALUES('Rafah',?,'Rafah',?)";
            ipstmt = con.prepareStatement(insertSql);

            //更新するPopulationを入力
            System.out.println("Populationを数字で入力してください>");
            int num1 = keyInNum();

            //入力されたPopulationとCountryCOdeをPreparedStatementオブジェクトにセット
            ipstmt.setString(1,str1);
            ipstmt.setInt(2, num1);

            //update処理の実行および更新された行数を取得
            int count = ipstmt.executeUpdate();
            System.out.println("更新行数：" + count);

            //７－３．更新後の結果を表示する
            rs.close();//更新後の検索のため、一旦閉じる（閉じないと警告が出るため）
            System.out.println("更新後==========");
            //検索の再実行と結果を格納／代入

            rs = spstmt.executeQuery();

            while(rs.next()) {
                String name = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                String district = rs.getString("District");
                int population = rs.getInt("Population");
                System.out.println(name + "\t" + countryCode + "\t" + district + "\t" + population);
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
            if(ipstmt != null) {
                try {
                    ipstmt.close();
                }catch(SQLException e) {
                    System.err.println("Statementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }

            if(spstmt != null) {
                try {
                    spstmt.close();
                }catch(SQLException e) {
                    System.err.println("PrepareStatementを閉じるときにエラーが発生しました。");
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

    private static int keyInNum() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        }catch(NumberFormatException e) {

        }
        return result;
    }

}
