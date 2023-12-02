package ClinicaHospitalar;

import dao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.*;

/**
 *
 * @author savio
 */
public class Sql {

    Connection connection = Conexao.conector();
    PreparedStatement pst;
    ResultSet rs = null;

//======================= PACIENTE ==================================
    public void adicionarPaciente(String nome, String telefone, String problema) {
        String sql = "insert into pacientes(nome, telefone, problema) values(?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            pst.setString(2, telefone);
            pst.setString(3, problema);
            pst.executeUpdate();
            ClinicaHospitalar.println("Paciente adicionado!");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public String pesquisaPac() throws SQLException {
        String sql = "select * from pacientes";
        String titlePac = "==== Pacientes ====\n";
        pst = connection.prepareStatement(sql);
        rs = pst.executeQuery();
        if (rs.next()) {
            return titlePac + "[#" + rs.getInt(1) + ": "
                    + rs.getString(2) + ": "
                    + rs.getString(4) + ']';
        }
        return "Vazio";

    }

    public ArrayList<Paciente> updateListPac(ArrayList<Paciente> pacientes) throws SQLException {
        String sql = "SELECT * FROM pacientes";
        pst = connection.prepareStatement(sql);
        rs = pst.executeQuery();

        while (rs.next()) {
            String nome = rs.getString(2);
            String telefone = rs.getString(3);
            String problema = rs.getString(4);
            String listaConsulta = rs.getString(5);
            Paciente paciente = new Paciente(nome, telefone, problema, listaConsulta);
            pacientes.add(paciente);
        }
        return pacientes;
    }

    public int getIdPac(String nome, String telefone) throws SQLException {
        String sql = "SELECT numero_registro_paciente FROM pacientes WHERE "
                + "nome = ? and telefone = ?";
        pst = connection.prepareStatement(sql);
        pst.setString(1, nome);
        pst.setString(2, telefone);
        rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return 0;
    }

    public void addConsultaNoPaciente(int id) throws SQLException {
        String sql = "UPDATE pacientes"
                + " SET lista_consultas = COALESCE(lista_consultas, '') ||"
                + " CONCAT(nomemed, ' -> ', datacons, ' -> ', tipo)"
                + " FROM consultas"
                + " WHERE consultas.idpac = ? and pacientes.numero_registro_paciente = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
//======================= MEDICO ==================================

    public void adicionarMedico(String nome, String telefone, Label especializacao) {
        String sql = "insert into medicos(nome, telefone, especializacao) values(?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            pst.setString(2, telefone);
            pst.setString(3, especializacao.name());
            pst.executeUpdate();
            ClinicaHospitalar.print("Medico adicionado!");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public ArrayList<Medico> updateListMed(ArrayList<Medico> medicos) throws SQLException, Exception {
        String sql = "SELECT * FROM medicos";
        pst = connection.prepareStatement(sql);
        rs = pst.executeQuery();

        while (rs.next()) {
//            int idPac = rs.getInt(1);
            String nome = rs.getString(2);
            String telefone = rs.getString(3);
            Label especializacao = Label.converter(rs.getString(4));
            String listaPacientes = rs.getString(5);
            Medico medico = new Medico(nome, telefone, especializacao, listaPacientes);
            medicos.add(medico);
        }
        return medicos;
    }

    public int getIdMed(String nome, String telefone) throws SQLException {
        String sql = "SELECT numero_registro_medico FROM medicos WHERE "
                + "nome = ? and telefone = ?";
        pst = connection.prepareStatement(sql);
        pst.setString(1, nome);
        pst.setString(2, telefone);
        rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return 0;
    }

    public void addConsultaNoMedico(String nome) {
        String sql = "UPDATE medicos"
                + " SET lista_pacientes = COALESCE(lista_pacientes, '') ||"
                + " CONCAT(consultas.nomepac, ' -> ', consultas.datacons, ' -> ', consultas.diagnostico)"
                + " FROM consultas"
                + " WHERE consultas.nomemed = ? and medicos.nome = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            pst.setString(2, nome);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

//======================= CONSULTA ==================================
    public void adicionarConsulta(int idPac, String nomePac, String nomeMed, Label especializacao, String diagnostico, String dataCons, String tipo, double valor) {
        String sql = "insert into consultas values(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idPac);
            pst.setString(2, nomePac);
            pst.setString(3, nomeMed);
            pst.setString(4, especializacao.name());
            pst.setString(5, diagnostico);
            pst.setString(6, dataCons);
            pst.setString(7, tipo);
            pst.setDouble(8, valor);
            pst.executeUpdate();
            ClinicaHospitalar.println("Consulta adicionada!!!");

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public String pesquisaConsulta() throws SQLException {
        StringBuilder result = new StringBuilder();
        String sqlC = "select * from consultas";
        String titleCons = "==== Consultas ====\n";
        pst = connection.prepareStatement(sqlC);
        rs = pst.executeQuery();
        result.append(titleCons);

        while (rs.next()) {
            result.append("[Paciente: ").append(rs.getInt(1)).append("/").append(rs.getString(2))
                    .append(", Medico: ").append(rs.getString(3)).append("/").append(rs.getString(4))
                    .append(", Diagnostico: ").append(rs.getString(5))
                    .append(", Tipo: ").append(rs.getString(7)).append("/").append(rs.getString(8))
                    .append(", Data: ").append(rs.getString(6)).append("]\n");
        }

        if (result.length() == 0) {
            return "Vazio";
        } else {
            return result.toString();
        }
    }

    public void remarcarConsulta(int idPac, String nomePac, String data) {
        String sql = "UPDATE consultas SET datacons = ?"
                  + " WHERE idpac = ? and nomepac = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, data);
            pst.setInt(2, idPac);
            pst.setString(3, nomePac);
            pst.executeUpdate();
            ClinicaHospitalar.println("Data alterada!");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
    
    public void removerConsulta(int idPac, String nomePac) {
        String sql = "DELETE FROM consultas"
                + " WHERE idpac = ? AND nomepac = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idPac);
            pst.setString(2, nomePac);
            pst.executeUpdate();
            ClinicaHospitalar.println("Consulta com " + nomePac + " deletado");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    
    
    public void removerPaciente(int idPac, String nomePac) {
        String sql = "DELETE FROM pacientes"
                + " WHERE numero_registro_paciente = ? AND nome = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idPac);
            pst.setString(2, nomePac);
            pst.executeUpdate();
            //da um fail: comando invalido
            ClinicaHospitalar.print("Paciente " + nomePac + " deletado");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

}
