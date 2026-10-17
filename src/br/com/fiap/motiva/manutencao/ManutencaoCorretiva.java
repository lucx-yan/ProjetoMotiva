package br.com.fiap.motiva.manutencao;

import br.com.fiap.motiva.equipamento.EscadaRolante;
import br.com.fiap.motiva.excecao.DadoInvalidoException;

import java.time.LocalDate;

// Manutenção feita depois de uma falha, registrada como nota no SAP
// Objeto, sintoma, causa e ação podem vir em branco, como acontece no histórico real

public class ManutencaoCorretiva extends Manutencao {

    private final String objeto; // componente afetado
    private final String sintoma;
    private final String causa;
    private final String acao;
    private final double horasParada;

    public ManutencaoCorretiva(String numeroRegistroSap, EscadaRolante escada, LocalDate data, String objeto,
                               String sintoma, String causa, String acao, double horasParada) {
        super(numeroRegistroSap, escada, data);
        this.objeto = textoOpcional(objeto);
        this.sintoma = textoOpcional(sintoma);
        this.causa = textoOpcional(causa);
        this.acao = textoOpcional(acao);
        this.horasParada = validarNaoNegativo(horasParada, "horas de parada");
    }

    public String getObjeto() {
        return objeto;
    }

    public String getSintoma() {
        return sintoma;
    }

    public String getCausa() {
        return causa;
    }

    public String getAcao() {
        return acao;
    }

    public double getHorasParada() {
        return horasParada;
    }

    @Override
    public boolean isPlanejada() {
        return false;
    }

    // Indica se a falha foi no rolete de degrau, o componente que o StepScan monitora
    public boolean isFalhaRolete() {
        return "ROLETE PARA DEGRAU".equals(objeto);
    }

    // Indica se a nota tem objeto, sintoma, causa e ação preenchidos
    public boolean isDiagnosticoCompleto() {
        return objeto != null && sintoma != null && causa != null && acao != null;
    }

    @Override
    public String getDescricao() {
        return "Corretiva - objeto: " + ouNaoInformado(objeto) + " - " + horasParada + " h parada";
    }

    // Texto em branco vira null
    private static String textoOpcional(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        return valor.trim().toUpperCase();
    }

    private static String ouNaoInformado(String valor) {
        if (valor == null) {
            return "não informado";
        }
        return valor;
    }

    // Garante que o número não seja negativo (zero é permitido)
    private static double validarNaoNegativo(double valor, String campo) {
        if (valor < 0) {
            throw new DadoInvalidoException("O campo " + campo + " não pode ser negativo.");
        }
        return valor;
    }
}
