package ClinicaHospitalar;

import java.sql.SQLException;

public class Medico extends Pessoa {

    protected int numeroRegistroMedico;
    Label especializacao;

    Sql sql = new Sql();

    Medico(String nome, String telefone, Label especializacao, String consulta) throws SQLException {
        super(nome, telefone, consulta);
        this.especializacao = especializacao;
        numeroRegistroMedico = sql.getIdMed(nome, telefone);
    }

    // INFORMACOES DO MEDICO
    // se nao tiver consulta, retorna "Empty"
    public String infoMedico() {
        String saida = "==== " + this.nome.toUpperCase() + " ====\n"
                + "CRM: " + numeroRegistroMedico
                + "\nTelefone: " + this.telefone
                + "\nEspecializacao: " + this.especializacao
                + "\nLista de Pacientes: ";

        if (consulta == null) {
            saida += "Empty";
        } else {
            saida += consulta;
        }
        return saida;
    }

    public void setListaPacientes(String consulta) {
        super.consulta = consulta;
    }

    @Override
    public String toString() {
        return "@" + numeroRegistroMedico + ": " + nome + ": " + especializacao;
    }

}
