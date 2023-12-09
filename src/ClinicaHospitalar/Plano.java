package ClinicaHospitalar;

public class Plano extends Consulta{
    protected String nomePlano; 
    
    // CONSTRUTOR DA CONSULTA PELO PLANO
    public Plano(Paciente paciente, Medico medico, String data, String diagnostico, String nomePlano) {
        super(paciente, medico, data, diagnostico);
        this.nomePlano = nomePlano;
    }
    
    @Override
    public String toString() {
        return "Paciente: " + paciente.numeroRegistroPaciente + "/"
                + paciente.nome +
                ", Medico: " + medico.nome + "/" + medico.especializacao +
                ", Diagnostico: " + diagnostico +
                ", Valor da Consulta: " + this.nomePlano +"("+ (super.valorConsulta - 200) +")" +
                ", Data: " + data;
    }
}
