package br.com.fiap.motiva;
import br.com.fiap.motiva.excecao.DadoInvalidoException;
import br.com.fiap.motiva.localizacao.Linha;
import br.com.fiap.motiva.localizacao.Estacao;
import br.com.fiap.motiva.equipamento.CodigoSap;
import br.com.fiap.motiva.equipamento.Criticidade;
import br.com.fiap.motiva.equipamento.EscadaRolante;
import br.com.fiap.motiva.excecao.RegraNegocioException;

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

        // EscadaRolante
        Estacao fariaLima = new Estacao("FAL", "Faria Lima", linha4);

        EscadaRolante er32 = new EscadaRolante(new CodigoSap("SP04-6-ECR-PIN-ER32"), pinheiros, "TUGELA", Criticidade.CRITICA, 91);
        EscadaRolante er22 = new EscadaRolante(new CodigoSap("SP04-6-ECR-PIN-ER22"), pinheiros, "TUGELA", Criticidade.CRITICA, 112);
        EscadaRolante er04 = new EscadaRolante(new CodigoSap("SP04-6-ECR-PIN-ER04"), pinheiros, "ESCAL", Criticidade.CRITICA, 116, 0.5, 400);
        EscadaRolante fal06 = new EscadaRolante(new CodigoSap("SP04-6-ECR-FAL-ER06"), fariaLima, "TUGELA", Criticidade.NAO_CRITICA, 114);

        System.out.println(er32);
        System.out.println(fal06);
        System.out.println(er32.isCritica());
        System.out.println(fal06.isCritica());
        System.out.println(er32.isMesmoGrupoDe(er22));
        System.out.println(er32.isMesmoGrupoDe(er04));
        System.out.println(er32.isMesmoGrupoDe(fal06));
        System.out.println(er32.getVelocidadeNominal() + " m/s, passo " +er32.getPassoNominal() + " mm");

        // simulando uma reclassificação
        fal06.setCriticidade(Criticidade.CRITICA);
        System.out.println(er32.isMesmoGrupoDe(fal06));

        try {
            new EscadaRolante(new CodigoSap("SP04-6-ECR-PIN-ER32"), fariaLima, "TUGELA", Criticidade.CRITICA, 91);
        } catch (RegraNegocioException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        try {
            new EscadaRolante(new CodigoSap("SP04-6-ECR-PIN-ER32"), pinheiros, "TUGELA", Criticidade.CRITICA, 0);
        } catch (DadoInvalidoException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}
