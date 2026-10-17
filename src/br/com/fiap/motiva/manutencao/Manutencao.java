package br.com.fiap.motiva.manutencao;

import br.com.fiap.motiva.equipamento.EscadaRolante;
import br.com.fiap.motiva.excecao.DadoInvalidoException;

import java.time.LocalDate;

// Registro de manutenção de uma escada (nota ou ordem do SAP)
// Abstrata: toda manutenção é preventiva ou corretiva

public abstract class Manutencao {

    private final String numeroRegistroSap;
    private final EscadaRolante escada;
    private final LocalDate data;

    protected Manutencao(String numeroRegistroSap, EscadaRolante escada, LocalDate data) {
        this.numeroRegistroSap = validarTexto(numeroRegistroSap, "Número do registro SAP");
        validarNaoNulo(escada, "escada");
        this.escada = escada;
        this.data = validarData(data);
    }

    public abstract boolean isPlanejada();

    public abstract String getDescricao();

    public String getNumeroRegistroSap() {
        return numeroRegistroSap;
    }

    public EscadaRolante getEscada() {
        return escada;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public String toString() {
        return numeroRegistroSap + " | " + data + " | " + escada.getCodigoSap() + " | " + getDescricao();
    }

    // Garante que a data exista e não esteja no futuro
    private static LocalDate validarData(LocalDate data) {
        validarNaoNulo(data, "data");
        if (data.isAfter(LocalDate.now())) {
            throw new DadoInvalidoException("A data da manutenção não pode estar no futuro.");
        }
        return data;
    }

    protected static void validarNaoNulo(Object valor, String campo) {
        if (valor == null) {
            throw new DadoInvalidoException("O campo " + campo + " é obrigatório.");
        }
    }

    protected static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }
}
