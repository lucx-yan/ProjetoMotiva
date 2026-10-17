package br.com.fiap.motiva;

import br.com.fiap.motiva.equipamento.CodigoSap;
import br.com.fiap.motiva.equipamento.Criticidade;
import br.com.fiap.motiva.equipamento.EscadaRolante;
import br.com.fiap.motiva.excecao.RegraNegocioException;
import br.com.fiap.motiva.localizacao.Estacao;
import br.com.fiap.motiva.localizacao.Linha;
import br.com.fiap.motiva.manutencao.ManutencaoCorretiva;
import br.com.fiap.motiva.manutencao.ManutencaoPreventiva;
import br.com.fiap.motiva.manutencao.RotinaPreventiva;

import java.time.LocalDate;

// STORYBOARD - apoio à manutenção das escadas rolantes da Linha 4-Amarela

//   [1] Cadastro          [2] Histórico         [3] Análise              [4] Planejamento
//   Linha -> Estação  ->  preventivas e     ->  falhas de rolete,    ->  próxima preventiva
//   -> Escada rolante     corretivas do SAP     horas paradas, pares     pelo plano da criticidade
//
//   [5] Em todas as etapas, dados inválidos e regras de negócio são barrados por exceções próprias.

// Cenário: escada SP04-6-ECR-PIN-ER32 (Pinheiros, crítica)
// Corretivas: notas reais do SAP. Preventiva: exemplo (DEMO)

public class Main {
    public static void main(String[] args) {
        // CADASTRO (a linha, a estação e as escadas acompanhadas)
        System.out.println("\n1. CADASTRO");
        Linha linha4 = new Linha(4, "Amarela", "ViaQuatro");
        Estacao pinheiros = new Estacao("PIN", "Pinheiros", linha4);
        EscadaRolante er32 = new EscadaRolante(new CodigoSap("SP04-6-ECR-PIN-ER32"), pinheiros, "TUGELA", Criticidade.CRITICA, 91);
        EscadaRolante er22 = new EscadaRolante(new CodigoSap("SP04-6-ECR-PIN-ER22"), pinheiros, "TUGELA", Criticidade.CRITICA, 112);
        System.out.println(er32);
        System.out.println(er22);

        // HISTÓRICO (o que já foi feitona ER32)
        System.out.println("\n2. HISTÓRICO DA ER32");
        ManutencaoPreventiva prev = new ManutencaoPreventiva("DEMO-PREV-0001", er32, LocalDate.of(2025, 7, 10), RotinaPreventiva.P6);
        ManutencaoCorretiva[] corretivas = {
                new ManutencaoCorretiva("14681838", er32, LocalDate.of(2025, 6, 18), "", "INOPERANTE", "", "", 3.49),
                new ManutencaoCorretiva("14764937", er32, LocalDate.of(2025, 8, 5), "ROLETE PARA DEGRAU", "INOPERANTE", "DESGASTE NATURAL", "SUBSTITUIDO(A)", 0.16),
                new ManutencaoCorretiva("14810421", er32, LocalDate.of(2025, 8, 31), "ROLETE PARA DEGRAU", "ALARME-QUEBRA DE DEGRAU INFERIOR", "DESGASTE NATURAL", "SUBSTITUIDO(A)", 1.58),
                new ManutencaoCorretiva("14828589", er32, LocalDate.of(2025, 9, 10), "ROLETE PARA DEGRAU", "ALARME-QUEBRA DE DEGRAU INFERIOR", "DESGASTE NATURAL", "SUBSTITUIDO(A)", 4.99),
                new ManutencaoCorretiva("14848250", er32, LocalDate.of(2025, 9, 22), "ROLETE PARA DEGRAU", "ALARME-QUEBRA DE DEGRAU SUPERIOR", "DESGASTE NATURAL", "SUBSTITUIDO(A)", 12.9)
        };
        System.out.println(prev);
        for (ManutencaoCorretiva c : corretivas) {
            System.out.println(c);
        }

        // ANÁLISE (quanto do histórico é falha de rolete, o componente que o StepScan mede)
        System.out.println("\n3. ANÁLISE");
        int falhasRolete = 0;
        double horasParadasRolete = 0;
        int semDiagnostico = 0;
        for (ManutencaoCorretiva c : corretivas) {
            if (c.isFalhaRolete()) {
                falhasRolete++;
                horasParadasRolete += c.getHorasParada();
            }
            if (!c.isDiagnosticoCompleto()) {
                semDiagnostico++;
            }
        }
        System.out.println("Corretivas registradas: " + corretivas.length);
        System.out.println("Falhas de rolete: " + falhasRolete + " (" + String.format("%.2f", horasParadasRolete) + " h paradas)");
        System.out.println("Notas sem diagnóstico completo: " + semDiagnostico);
        System.out.println("ER32 e ER22 no mesmo grupo de comparação: " + er32.isMesmoGrupoDe(er22));

        // PLANEJAMENTO (próxima preventiva pelo plano da criticidade)
        System.out.println("\n4. PLANEJAMENTO");
        System.out.println("Próxima " + prev.getRotina() + " da ER32: " + prev.getProximaExecucao());

        // PROTEÇÃO (o sistema barra registros que quebram regras)
        System.out.println("\n5. PROTEÇÃO");
        try {
            new EscadaRolante(new CodigoSap("SP04-6-ECR-PIN-ER32"), new Estacao("FAL", "Faria Lima", linha4), "TUGELA", Criticidade.CRITICA, 91);
        } catch (RegraNegocioException e) {
            System.out.println("Barrado: " + e.getMessage());
        }
    }
}
