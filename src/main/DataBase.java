package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

public class DataBase {
    public static void insertB(String nume_fisier, String nume_tabela, int a) {
    Connection c = null;
    Statement stmt = null;
    try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + nume_fisier + ".db");
        c.setAutoCommit(false);
        stmt = c.createStatement();

        String sql = "INSERT INTO " + nume_tabela + "(scor) " + "VALUES (" + a + ");";
        stmt.executeUpdate(sql);
        stmt.close();
        c.commit();
        c.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        System.exit(0);
    }

}

    public static int getB(String nume_fisier, String nume_tabela) {
        Connection c = null;
        Statement stmt = null;
        int value = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + nume_fisier + ".db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM " + nume_tabela + ";");
            while (rs.next()) {
                value = rs.getInt("scor");
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return value;
    }


    public static int descending(String nume_fisier, String nume_tabela, int x) {
        int value = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + nume_fisier + ".db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            String sql = "SELECT * FROM " + nume_tabela + " ORDER BY scor DESC;";
            //stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery(sql);
            for (int i = 0; i < x; i++) {
                rs.next();
                value = rs.getInt("scor");
            }
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return value;

    }
}
