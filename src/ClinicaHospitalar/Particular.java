package ClinicaHospitalar;

public class Particular extends Consulta {
    protected double valor;

    // CONSTRUTOR DA CONSULTA PARTICULAR
    public Particular(Paciente paciente, Medico medico, String data, String diagnostico, String valor) {
        super(paciente, medico, data, diagnostico);
        this.valor = number(valor);
    }

    @Override
    public String toString() {
        return "Paciente: " + paciente.numeroRegistroPaciente + "/"
                + paciente.nome
                + ", Medico: " + medico.nome + "/" + medico.especializacao
                + ", Diagnostico: " + diagnostico
                + ", Valor da Consulta: " + valor
                + ", Data: " + data;
    }
    
    private double number(String value) {
        return Double.parseDouble(value);
    }
}
