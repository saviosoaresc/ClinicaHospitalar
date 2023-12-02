package ClinicaHospitalar;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author savio
 */
//no show dps q adiona um Pac, a saida eh duplicada
//erro no diagnostico, ele so pega a ultima palavra
public class ClinicaHospitalar {

    public static void main(String args[]) throws SQLException, Exception {
        Clinica clinica = new Clinica();
        Sql sql = new Sql();

        System.out.println("======== SHELL ========");
        System.out.println("$addMed nome telefone especializacao");
        System.out.println("$addPac nome telefone diagnostico");
        System.out.println("$diagnostico seu-problema");
        System.out.println("$addCons");
        System.out.println("$remarcarCons");
        System.out.println("$infoPac idPac nomePac");
        System.out.println("$infoMed nomeMed");
        System.out.println("$removeCons");

        while (true) {
            String line = input();
            println("$" + line);
            String[] arg = line.split(" ");
            clinica.update();
            try {
                switch (arg[0].toLowerCase()) {
                    case "cls": {
                        clearConsole();
                        break;
                    }
                    case "end": {
                        return;
                    }
                    case "addmed": {
                        //nome telefone especializacao
                        print("NOME: ");
                        String nome = input();
                        print("TELEFONE: ");
                        String telefone = input();
                        println("  =====  ESPECIALIZACOES  =====");
                        println("- CARDIOLOGISTA  - DERMATOLOGISTA\n- NEUROLOGISTA   - GINECOLOGISTA\n"
                                + "- OFTALMOLOGISTA - ODONTOLOGISTA\n- GERAL");
                        print("ESPECIALIZACAO: ");
                        String especializacao = input();

                        clinica.addPessoa(new Medico(nome, telefone, Label.converter(especializacao), null));
                        break;
                    }
                    case "addpac": {
                        print("NOME: ");
                        String nome = input();
                        print("TELEFONE: ");
                        String telefone = input();
                        print("DIAGNOSTICO: ");
                        String problema = input();
                        clinica.addPessoa(new Paciente(nome, telefone, problema, null));
                        break;
                    }
                    case "show":
                        print(clinica.toString());
                        println(sql.pesquisaConsulta());
                        break;
                    case "diagnostico": {
                        String diag = "";
                        Label saida;
                        for (int i = 0; i < arg.length; i++) {
                            if (i >= 1) {
                                diag += arg[i] + " ";
                            }
                        }
                        saida = Label.verificarEspecialidade(diag);
                        println("==== " + saida + " ====\nMedico(s): " + clinica.getMedicosPorEspecialidade(saida));
                        break;
                    }
                    case "addcons": {
                        //addCons particular
                        //addCons plano
                        println("PLANO OU PARTICULAR? ");
                        String opcao = input();
                        switch (opcao.toUpperCase()) {
                            case "PARTICULAR": {
                                print("ID DO PACIENTE: ");
                                int numRegPac = number(input());
                                print("NOME DO PACIENTE: ");
                                String nomePac = input();
                                print("NOME DO MEDICO: ");
                                String nomeMed = input();
                                Paciente pac = clinica.existePac(numRegPac, nomePac);
                                Medico med = clinica.existeMed(nomeMed);
                                if (pac == null) {
                                    println("Paciente Inexistente");
                                    break;
                                } else if (med == null) {
                                    println("Medico Inexistente");
                                    break;
                                }
                                print("DATA: ");
                                String data = input();
                                print("VALOR: ");
                                String valor = input();
                                clinica.addConsulta(new Particular(pac, med, data, pac.problema, valor));
                                break;
                            }
                            case "PLANO": {
                                print("ID DO PACIENTE: ");
                                int numRegPac = number(input());
                                print("NOME DO PACIENTE: ");
                                String nomePac = input();
                                print("NOME DO MEDICO: ");
                                String nomeMed = input();
                                Paciente pac = clinica.existePac(numRegPac, nomePac);
                                Medico med = clinica.existeMed(nomeMed);
                                if (pac == null) {
                                    println("Paciente Inexistente");
                                    break;
                                }
                                if (med == null) {
                                    println("Medico Inexistente");
                                    break;
                                }
                                print("DATA: ");
                                String data = input();
                                print("NOME DO PLANO: ");
                                String nomePlan = input();
                                clinica.addConsulta(new Plano(pac, med, data, pac.problema, nomePlan));
                                break;
                            }
                            default:
                                println("Tipo de consulta nao especificado!");
                        }
                        break;
                    }
                    case "remarcarcons": {
                        print("ID DO PACIENTE: ");
                        int numRegPac = number(input());
                        print("NOME DO PACIENTE: ");
                        String nomePac = input();
                        print("DATA: ");
                        String data = input();
                        sql.remarcarConsulta(numRegPac, nomePac, data);
                        break;
                    }
                    //quando um paciente tem mais de uma consulta ela eh repetida
                    case "infopac": {
                        println(clinica.infoPaciente(number(arg[1]), arg[2]));
                        break;
                    }
                    case "infomed": {
                        println(clinica.infoMedico(arg[1]));
                        break;
                    }
                    case "removecons": {
                        print("ID DO PACIENTE: ");
                        int numRegPac = number(input());
                        print("NOME DO PACIENTE: ");
                        String nomePac = input();
                        sql.removerConsulta(numRegPac, nomePac);
                        break;
                    }
                    case "removepac": {
                        print("ID DO PACIENTE: ");
                        int idpac = number(input());
                        print("NOME DO PACIENTE: ");
                        String nomePac = input();
                        clinica.removerPaciente(idpac, nomePac);
                        break;
                    }
                    default:
                        println("fail: comando invalido");
                }
            } catch (Exception e) {
                println(e.getMessage());
            }
        }
    }
    private static final Scanner scanner = new Scanner(System.in);

    public static String input() {
        return scanner.nextLine();
    }

    private static int number(String value) {
        return Integer.parseInt(value);
    }

    public static void println(Object value) {
        System.out.println(value);
    }

    public static void print(Object value) {
        System.out.print(value);
    }

    private static void clearConsole() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (AWTException e) {
        }
    }
}
