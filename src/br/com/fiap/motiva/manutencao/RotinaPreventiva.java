package br.com.fiap.motiva.manutencao;

import br.com.fiap.motiva.equipamento.Criticidade;

// Rotinas preventivas da Motiva e em qual plano cada uma entra (PMP-6ECR-0001)
// Críticas: P1/P6/P12/P36/P72; Não críticas: P1/P9/P18/P36

public enum RotinaPreventiva {
    P1(1, true, true),
    P6(6, true, false),
    P9(9, false, true),
    P12(12, true, false),
    P18(18, false, true),
    P36(36, true, true),
    P72(72, true, false);

    private final int mesesPeriodicidade;
    private final boolean planoCritica; // Verificar se entra no plano das escadas críticas
    private final boolean planoNaoCritica; // Verificar se entra no plano das escadas não críticas

    RotinaPreventiva(int mesesPeriodicidade, boolean planoCritica, boolean planoNaoCritica) {
        this.mesesPeriodicidade = mesesPeriodicidade;
        this.planoCritica = planoCritica;
        this.planoNaoCritica = planoNaoCritica;
    }

    public int getMesesPeriodicidade() {
        return mesesPeriodicidade;
    }

    // Indica se a rotina faz parte do plano da criticidade informada
    public boolean pertenceAoPlano(Criticidade criticidade) {
        if (criticidade == Criticidade.CRITICA) {
            return planoCritica;
        }
        return planoNaoCritica;
    }
}
