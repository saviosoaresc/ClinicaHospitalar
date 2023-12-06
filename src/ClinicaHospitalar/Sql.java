package ClinicaHospitalar;

import dao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    // adiciona paciente no banco de dados
    public void adicionarPaciente(String nome, String telefone, String problema) {
        // comando sql
        String sql = "insert into pacientes(nome, telefone, problema) values(?, ?, ?)";
        try {
            // prepara o comando sql para ser executado no banco de dados
            pst = connection.prepareStatement(sql);
            // substitui os ? pelos valores passados
            pst.setString(1, nome);
            pst.setString(2, telefone);
            pst.setString(3, problema);
            // executa o comando sql
            pst.executeUpdate();
            ClinicaHospitalar.println("Paciente adicionado!");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                // fecha a conexao com o banco de dados
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }
    }

    // pesquisa paciente no banco de dados
    // retorna uma string com os dados do paciente
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

    // atualiza a lista de pacientes
    // retorna um arraylist com os pacientes do banco de dados
    public ArrayList<Paciente> updateListPac(ArrayList<Paciente> pacientes) throws SQLException {
        String sql = "SELECT * FROM pacientes ORDER BY numero_registro_paciente ASC";
        pst = connection.prepareStatement(sql);
        rs = pst.executeQuery();

        while (rs.next()) {
            String nome = rs.getString(2);
            String telefone = rs.getString(3);
            String problema = rs.getString(4);
            String listaConsulta = rs.getString(5);
            pacientes.add(new Paciente(nome, telefone, problema, listaConsulta));
        }
        return pacientes;
    }

    // recebe o nome e o telefone do paciente
    // retorna o id do paciente de acordo com o nome e o telefone passados como parametro
    // retorna -1 se nao encontrar o paciente
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

        return -1;
    }

    // adiciona a consulta no paciente
    // adiciona a consulta no paciente de acordo com o id passado como parametro
    public void addConsultaNoPaciente(int id) throws SQLException {
        String sql = "UPDATE pacientes"
                + " SET lista_consultas = COALESCE(lista_consultas, '') ||"
                + " CONCAT(nomemed, ' -> ', datacons, ' -> ', tipo, ' + ')"
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
                System.out.print(ex);
            }
        }
    }

    // remove o paciente do banco de dados
    // remove o paciente do banco de dados de acordo com o id e o nome passados como parametro
    public void removerPaciente(int idPac, String nomePac) {
        String sql = "DELETE FROM pacientes"
                + " WHERE numero_registro_paciente = ? AND nome = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idPac);
            pst.setString(2, nomePac);
            pst.executeUpdate();
            //da um fail: comando invalido
            ClinicaHospitalar.println("Paciente " + nomePac + " deletado");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }
    }

//======================= MEDICO ==================================
    // adiciona medico no banco de dados
    // adiciona medico no banco de dados de acordo com o nome, telefone e especializacao passados como parametro
    // especializacao eh um enum
    public void adicionarMedico(String nome, String telefone, Label especializacao) {
        String sql = "insert into medicos(nome, telefone, especializacao) values(?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            pst.setString(2, telefone);
            pst.setString(3, especializacao.name());
            pst.executeUpdate();
            ClinicaHospitalar.println("Medico adicionado!");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }
    }

    // pesquisa medico no banco de dados
    // retorna um arraylist com os medicos do banco de dados
    public ArrayList<Medico> updateListMed(ArrayList<Medico> medicos) throws SQLException, Exception {
        String sql = "SELECT * FROM medicos  ORDER BY numero_registro_medico ASC";
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

    // retorna o id do medico de acordo com o nome e o telefone passados como parametro
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

    // adiciona a consulta no medico
    // adiciona a consulta no medico de acordo com o nome passado como parametro
    public void addConsultaNoMedico(String nome) {
        String sql = "UPDATE medicos"
                + " SET lista_pacientes = COALESCE(lista_pacientes, '') ||"
                + " CONCAT(consultas.nomepac, ' -> ', consultas.datacons, ' -> ', consultas.diagnostico, ' + ')"
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
                System.out.print(ex);
            }
        }
    }

    // remove o medico do banco de dados de acordo com o nome passado como parametro
    public void removerMedico(String nomeMed) {
        String sql = "DELETE FROM medicos"
                + " WHERE nome = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nomeMed);
            pst.executeUpdate();
            ClinicaHospitalar.println("Medico(a) " + nomeMed + " dispensado(a)");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }
    }

//======================= CONSULTA ==================================
    // adiciona consulta no banco de dados de acordo com o id do paciente, nome do paciente, nome do medico, especializacao,
    // diagnostico, data da consulta, tipo e valor passados como parametro
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
                System.out.print(ex);
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
                    .append(", Tipo: ").append(rs.getString(7)).append("/").append(rs.getString(8)).append("$")
                    .append(", Data: ").append(rs.getString(6)).append("]\n");
        }

        if (result.length() == 0) {
            return "Vazio";
        } else {
            return result.toString();
        }
    }

    public void remarcarConsulta(int idPac, String nomePac, String nomeMed, String data) {
        String sql = "UPDATE consultas SET datacons = ?"
                + " WHERE idpac = ? and nomepac = ? and nomemed = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, data);
            pst.setInt(2, idPac);
            pst.setString(3, nomePac);
            pst.setString(4, nomeMed);
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
                System.out.print(ex);
            }
        }
    }

    public void removerConsultaGeral(int idPac, String nomePac, String nomeMed) {
        String sql = "DELETE FROM consultas"
                + " WHERE idpac = ? AND nomepac = ? AND nomemed = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idPac);
            pst.setString(2, nomePac);
            pst.setString(3, nomeMed);
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
                System.out.print(ex);
            }
        }
    }
    
    public void removerConsultadoPaciente(int idPac, String nomePac) {
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
                System.out.print(ex);
            }
        }
    }

    public String getNomeDoPacienteNaConsultaDoMedico(String nomeMed) throws SQLException {
        StringBuilder result = new StringBuilder();
        String sql = "select idpac, nomepac from consultas where nomemed = ?";
        pst = connection.prepareStatement(sql);
        pst.setString(1, nomeMed);
        rs = pst.executeQuery();

        while (rs.next()) {
            result.append("[").append(rs.getInt(1)).append(" - ").append(rs.getString(2)).append("]\n");
        }

        if (result.length() == 0) {
            return "Vazio";
        } else {
            return result.toString();
        }
    }
}
