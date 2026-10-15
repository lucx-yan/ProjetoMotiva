package br.com.fiap.motiva;
import br.com.fiap.motiva.excecao.DadoInvalidoException;
import br.com.fiap.motiva.localizacao.Linha;
import br.com.fiap.motiva.localizacao.Estacao;
import br.com.fiap.motiva.equipamento.CodigoSap;

public class Main {
    public static void main(String[] args) {

        // Linha
        Linha linha4 = new Linha(4, "Amarela", "ViaQuatro");
        System.out.println(linha4);
        System.out.println(linha4.getNomeCompleto());

        try {
            new Linha(-4, "Amarela", "ViaQuatro");
        } catch (DadoInvalidoException e) {
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
        } catch (DadoInvalidoException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        try {
            new Estacao("", "Pinheiros", linha4);
        } catch (DadoInvalidoException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        // CodigoSap
        CodigoSap codigo1 = new CodigoSap(" sp04-6-ecr-pin-er32 ");
        CodigoSap codigo2 = new CodigoSap("SP04-6-ECR-PIN-ER32");

        System.out.println(codigo1);
        System.out.println(codigo1.getSiglaEstacao());
        System.out.println(codigo1.equals(codigo2));
        System.out.println(codigo1 == codigo2);

        try {
            new CodigoSap("ESCADA-32");
        } catch (DadoInvalidoException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}
