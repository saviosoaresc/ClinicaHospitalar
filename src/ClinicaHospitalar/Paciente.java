package ClinicaHospitalar;

import java.sql.SQLException;

public class Paciente extends Pessoa {

    protected int numeroRegistroPaciente;
    protected String problema;
    
    Sql sql = new  Sql();
    
    // CONSTRUTOR DO PACIENTE
    public Paciente(String nome, String telefone, String problema, String consulta) throws SQLException {
        super(nome, telefone, consulta);
        this.problema = problema;
        numeroRegistroPaciente = sql.getIdPac(nome, telefone);
    }

    // INFORMACOES DO PACIENTE
    // se nao tiver consulta, retorna "Empty"
    public String infoPaciente() {
        String saida = "==== " + this.nome.toUpperCase() + " ====\n"
                + "ID: " + numeroRegistroPaciente
                + "\nTelefone: " + this.telefone
                + "\nDiagnostico: " + problema
                + "\nLista de Consultas: ";
        if (consulta == null) {
            saida += "Empty";
        } else {
            saida += consulta;
        }
        return saida;
    }

    public void setListaConsultas(String consulta) {
        super.consulta = consulta;
    }

    public int getNumeroRegistroPaciente() {
        return numeroRegistroPaciente;
    }

    @Override
    public String toString() {
        return "#" + numeroRegistroPaciente + ": " + nome + ": " + problema;
    }

}
