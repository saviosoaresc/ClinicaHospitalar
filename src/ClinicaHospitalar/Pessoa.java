package ClinicaHospitalar;

public class Pessoa {
    protected String nome;
    protected String telefone;
    protected String consulta;
    
    public Pessoa(String nome, String telefone, String consulta){
        this.nome = nome;
        this.telefone = telefone;
        this.consulta = consulta;
    }
}