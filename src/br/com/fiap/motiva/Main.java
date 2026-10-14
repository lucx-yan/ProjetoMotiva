package br.com.fiap.motiva;
import br.com.fiap.motiva.localizacao.Linha;
import br.com.fiap.motiva.localizacao.Estacao;

public class Main {
    public static void main(String[] args) {

        // Linha
        Linha linha4 = new Linha(4, "Amarela", "ViaQuatro");
        System.out.println(linha4);
        System.out.println(linha4.getNomeCompleto());

        try {
            new Linha(-4, "Amarela", "ViaQuatro");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        // Estacao
        Estacao pinheiros = new Estacao("pin", "Pinheiros", linha4);
        Estacao higienopolis = new Estacao("HIG", "Higienópolis-Mackenzie", linha4);

        System.out.println(pinheiros);
        System.out.println(higienopolis);
        System.out.println(higienopolis.getIdentificacao());
        System.out.println(pinheiros.getLinha().getNomeCompleto());

        try {
            new Estacao("PIN", "Pinheiros", null);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        try {
            new Estacao("", "Pinheiros", linha4);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}
