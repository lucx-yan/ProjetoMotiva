package br.com.fiap.motiva.equipamento;

import br.com.fiap.motiva.excecao.DadoInvalidoException;

import java.util.Objects;

// Código SAP que identifica uma escada rolante (exemplo: SP04-6-ECR-PIN-ER32)
// Value Object: imutável, validado na criação e comparado pelo valor

public final class CodigoSap {
    private static final String FORMATO = "SP\\d{2}-\\d-ECR-[A-Z]{3}-ER\\d{2}";

    private final String valor;

    public CodigoSap(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException("O código SAP é obrigatório.");
        }

        String normalizado = valor.trim().toUpperCase();

        if (!normalizado.matches(FORMATO)) {
            throw new DadoInvalidoException("Código SAP inválido: " + normalizado + ". Formato esperado: SP04-6-ECR-PIN-ER32.");
        }
        this.valor = normalizado;
    }

    // Retorna o código completo
    public String getValor() {
        return valor;
    }

    // Retorna a sigla da estação que está dentro do código
    public String getSiglaEstacao() {
        return valor.split("-")[3];
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CodigoSap codigoSap = (CodigoSap) o;
        return Objects.equals(valor, codigoSap.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(valor);
    }
}
