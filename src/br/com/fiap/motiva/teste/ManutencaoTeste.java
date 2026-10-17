package br.com.fiap.motiva.teste;

import br.com.fiap.motiva.equipamento.CodigoSap;
import br.com.fiap.motiva.equipamento.Criticidade;
import br.com.fiap.motiva.equipamento.EscadaRolante;
import br.com.fiap.motiva.excecao.DadoInvalidoException;
import br.com.fiap.motiva.excecao.RegraNegocioException;
import br.com.fiap.motiva.localizacao.Estacao;
import br.com.fiap.motiva.localizacao.Linha;
import br.com.fiap.motiva.manutencao.Manutencao;
import br.com.fiap.motiva.manutencao.ManutencaoCorretiva;
import br.com.fiap.motiva.manutencao.ManutencaoPreventiva;
import br.com.fiap.motiva.manutencao.RotinaPreventiva;

import java.time.LocalDate;

// Driver de teste do pacote manutencao (preventiva, corretiva e rotinas)

public class ManutencaoTeste {
    public static void main(String[] args) {
        System.out.println("TESTE MANUTENÇÃO");

        Linha linha4 = new Linha(4, "Amarela", "ViaQuatro");
        Estacao pinheiros = new Estacao("PIN", "Pinheiros", linha4);
        EscadaRolante er32 = new EscadaRolante(new CodigoSap("SP04-6-ECR-PIN-ER32"), pinheiros, "TUGELA", Criticidade.CRITICA, 91);

        // Manutencao
        Estacao oscarFreire = new Estacao("FRE", "Oscar Freire", linha4);
        EscadaRolante fre01 = new EscadaRolante(new CodigoSap("SP04-6-ECR-FRE-ER01"), oscarFreire, "TUGELA", Criticidade.NAO_CRITICA, 106);

        // Preventiva de exemplo: o dataset não traz ordens preventivas
        ManutencaoPreventiva prev = new ManutencaoPreventiva("DEMO-PREV-0001", er32, LocalDate.of(2025, 7, 10), RotinaPreventiva.P6);

        // Corretivas reais do SAP (base_ref_sap_notas.csv)
        ManutencaoCorretiva corr1 = new ManutencaoCorretiva("14764937", er32, LocalDate.of(2025, 8, 5),
                "ROLETE PARA DEGRAU", "INOPERANTE", "DESGASTE NATURAL", "SUBSTITUIDO(A)", 0.16);
        ManutencaoCorretiva corr2 = new ManutencaoCorretiva("14681838", er32, LocalDate.of(2025, 6, 18),
                "", "INOPERANTE", "", "", 3.49);

        Manutencao[] historico = {prev, corr1, corr2};
        for (Manutencao m : historico) {
            System.out.println(m);
        }

        System.out.println(prev.isPlanejada());
        System.out.println(corr1.isPlanejada());
        System.out.println(corr1.isFalhaRolete());
        System.out.println(corr1.isDiagnosticoCompleto());
        System.out.println(corr2.isDiagnosticoCompleto());
        System.out.println(RotinaPreventiva.P9.pertenceAoPlano(Criticidade.NAO_CRITICA));

        try {
            new ManutencaoPreventiva("DEMO-PREV-0002", fre01, LocalDate.of(2025, 7, 10), RotinaPreventiva.P6);
        } catch (RegraNegocioException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        try {
            new ManutencaoCorretiva("99999999", er32, LocalDate.now().plusDays(1), "DEGRAUS", "INOPERANTE", "", "", 1.0);
        } catch (DadoInvalidoException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}
