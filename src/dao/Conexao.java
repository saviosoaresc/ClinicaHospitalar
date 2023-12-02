package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Conexao {

//    final private String driver = "org.postgresql.Driver";
//    final private String url = "jdbc:postgresql://localhost:5432/primepizza";
//    final private String usuario = "postgres";
//    final private String senha = "19122002";
//    private Connection conexao;
    public ResultSet resultset;
    public Statement statement;
    
//    public Connection getConnection() throws SQLException {
//        try {
//            conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/primepizza", "postgres", "19122002");
//            return conexao;
//        } catch (Exception e) {
//            return null;
//        }
//    }
    public static Connection conector() {
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/ClinicaHospitalar";
        String usuario = "postgres";
        String senha = "19122002";
        try {
            Class.forName(driver);
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            return conexao;
        } catch (Exception e) {
            return null;
        }
    }

//    public void conecta() throws SQLException {
//        try {
//            Class.forName(driver);
//            conexao = DriverManager.getConnection(url, usuario, senha);
//        } catch (ClassNotFoundException Fonte) {
//            JOptionPane.showMessageDialog(null, "Driver não locaizado \n" + Fonte);
//        } catch (SQLException Fonte) {
//            JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor \n" + Fonte);
//        }
//    }
//
//    public void desconecta() {
//        try {
//            conexao.close();
//        } catch (SQLException fech) {
//            JOptionPane.showMessageDialog(null, "Erro ao fechar conexão \n" + fech);
//        }
//    }
//
//    public void executaSQL(String sql) {
//        try {
//            statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            resultset = statement.executeQuery(sql);
//        } catch (SQLException sqlex) {
//            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando SQL"
//                    + sqlex + "O comando passado foi" + sql);
//        }
//    }
//
//    public void executa(String sql) {
//        try {
//            statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            statement.execute(sql);
//        } catch (SQLException sqlex) {
//            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando SQL"
//                    + sqlex + "O comando passado foi" + sql);
//        }
//    }

    
}