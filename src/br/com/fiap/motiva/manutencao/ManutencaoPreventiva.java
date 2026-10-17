package br.com.fiap.motiva.manutencao;

import br.com.fiap.motiva.equipamento.Criticidade;
import br.com.fiap.motiva.equipamento.EscadaRolante;
import br.com.fiap.motiva.excecao.RegraNegocioException;

import java.time.LocalDate;

// Manutenção feita por calendário

public class ManutencaoPreventiva extends Manutencao {
    private final RotinaPreventiva rotina;

    public ManutencaoPreventiva(String numeroRegistroSap, EscadaRolante escada, LocalDate data, RotinaPreventiva rotina) {
        super(numeroRegistroSap, escada, data);
        validarNaoNulo(rotina, "rotina");
        validarRotinaDoPlano(rotina, escada.getCriticidade());
        this.rotina = rotina;
    }

    public RotinaPreventiva getRotina() {
        return rotina;
    }

    // Data que a mesma rotina tem que ser feita de novo
    public LocalDate getProximaExecucao() {
        return getData().plusMonths(rotina.getMesesPeriodicidade());
    }

    @Override
    public boolean isPlanejada() {
        return true;
    }

    @Override
    public String getDescricao() {
        return "Preventiva " + rotina + " - próxima em " + getProximaExecucao();
    }

    // Verifica se a rotina está no plano de criticidade da escada
    private static void validarRotinaDoPlano(RotinaPreventiva rotina, Criticidade criticidade) {
        if (!rotina.pertenceAoPlano(criticidade)) {
            throw new RegraNegocioException("A rotina " + rotina + " não faz parte do plano de escada " + criticidade.getDescricao().toLowerCase() + ".");
        }
    }
}
