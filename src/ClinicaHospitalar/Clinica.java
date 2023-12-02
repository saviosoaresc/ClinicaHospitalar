package ClinicaHospitalar;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Clinica {

    private List<Paciente> pacientes = new ArrayList<>();
    private List<Medico> medicos = new ArrayList<>();

    Sql sql = new Sql();

    public Clinica() throws SQLException {
    }

    public void addPessoa(Pessoa pessoa) throws Exception {
        if (pessoa instanceof Paciente) {
            sql.adicionarPaciente(
                    ((Paciente) pessoa).nome,
                    ((Paciente) pessoa).telefone,
                    ((Paciente) pessoa).problema);
            return;
        }

        sql.adicionarMedico(
                ((Medico) pessoa).nome,
                ((Medico) pessoa).telefone,
                ((Medico) pessoa).especializacao);
    }

    public void addConsulta(Consulta consulta) throws SQLException {
        if (consulta instanceof Particular) {
            sql.adicionarConsulta(
                    consulta.paciente.numeroRegistroPaciente,
                    consulta.paciente.nome,
                    consulta.medico.nome,
                    consulta.medico.especializacao,
                    consulta.diagnostico,
                    consulta.data,
                    "Particular",
                    ((Particular) consulta).valor
            );
            sql.addConsultaNoPaciente(consulta.paciente.numeroRegistroPaciente);
            sql.addConsultaNoMedico(consulta.medico.nome);
            return;
        }

        sql.adicionarConsulta(
                consulta.paciente.numeroRegistroPaciente,
                consulta.paciente.nome,
                consulta.medico.nome,
                consulta.medico.especializacao,
                consulta.diagnostico,
                consulta.data,
                "Plano(" + ((Plano) consulta).nomePlano + ")",
                0
        );
        sql.addConsultaNoPaciente(consulta.paciente.numeroRegistroPaciente);
        sql.addConsultaNoMedico(consulta.medico.nome);
    }

    public Paciente existePac(int numRegPac, String nomePac) {
        for (Paciente pac : pacientes) {
            if (pac.nome.equals(nomePac) && pac.numeroRegistroPaciente == numRegPac) {
                return pac;
            }
        }
        return null;
    }

    public Medico existeMed(String nomeMed) {
        for (Medico med : medicos) {
            if (med.nome.equals(nomeMed)) {
                return med;
            }
        }
        return null;
    }

    String getMedicosPorEspecialidade(Label especialidade) {
        String saida = "";
        for (Medico med : medicos) {
            if (med.especializacao.equals(especialidade)) {
                saida += "[" + med.nome + "]";
            }
        }
        return saida;
    }

    public String infoPaciente(int numRegPac, String nome) throws Exception {
        for (Paciente pac : pacientes) {
            if (nome.equals(pac.nome) && numRegPac == pac.numeroRegistroPaciente) {
                return pac.infoPaciente();
            }
        }
        throw new Exception("Paciente Inexistente");
    }

    public String infoMedico(String nome) throws Exception {
        for (Medico med : medicos) {
            if (nome.equals(med.nome)) {
                return med.infoMedico();
            }
        }
        throw new Exception("Medico Inexistente");
    }

    public void update() throws Exception {
        try {
            pacientes = new ArrayList<>();
            pacientes = new ArrayList<>((List<Paciente>) sql.updateListPac((ArrayList<Paciente>) pacientes));
            medicos = new ArrayList<>();
            medicos = new ArrayList<>((List<Medico>) sql.updateListMed((ArrayList<Medico>) medicos));
        } catch (SQLException ex) {
            Logger.getLogger(Clinica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removerPaciente(int idPac, String nomePac) throws Exception {
        Paciente pac = existePac(idPac, nomePac);
        if (pac == null) {
            throw new Exception("Paciente " + nomePac + " com id " + idPac + " inexistente.");
        }
        if (pac.consulta != null) {
            System.out.print(nomePac.toUpperCase() + " tem consulta existente, deseja exclui-la?(s/n) ");
            String confirmar = ClinicaHospitalar.input();
            if (confirmar.equals("s")) {
                int idpac = idPac;
                sql.removerConsulta(idpac, nomePac);
            }
        }
        int idpac = idPac;
        sql.removerPaciente(idpac, nomePac);
    }
    
    public void removerMedico(String nomeMed) throws Exception {
        Medico med = existeMed(nomeMed);
        if (med == null) {
            throw new Exception("Medico " + nomeMed + " inexistente.");
        }
        sql.removerMedico(nomeMed);
        if (med.consulta != null) {
            ClinicaHospitalar.println(nomeMed.toUpperCase() + " tem consulta existente, avise aos seguintes pacientes: ");
            ClinicaHospitalar.print(sql.getNomeDoPacienteNaConsultaDoMedico(nomeMed));
        }        
    }
    
    @Override
    public String toString() {
        String saida = "";

        saida += "==== PACIENTES ====\n";

        for (Paciente pac : pacientes) {
            saida += "[" + pac + "]\n";
        }

        saida += "==== MEDICOS ==== \n";
        for (Medico med : medicos) {
            saida += "[" + med + "]\n";
        }
        return saida;
    }

}
