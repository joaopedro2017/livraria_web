package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoRelatorio {

    private static Connection con;

    public static Connection getConexao() throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/livraria", "root", "");
            System.out.println("Banco de dados conectado");
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("\nNão foi possível estabelecer conexão com a base de dados.\n");
            e.printStackTrace();
            return null;
        }
    }

    public static void fecharConexao() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
