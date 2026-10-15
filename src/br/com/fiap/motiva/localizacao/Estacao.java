package br.com.fiap.motiva.localizacao;

import br.com.fiap.motiva.excecao.DadoInvalidoException;

// Estação onde as escadas rolantes estão instaladas; toda estação pertence a uma linha.

public class Estacao {

    private final String sigla; // Sigla da estacao (Exemplo: PIN)
    private String nome; // Nome da estacao (Exemplo: Pinheiros)
    private final Linha linha; // Linha que a estação pertence

    public Estacao(String sigla, String nome, Linha linha) {
        this.sigla = validarTexto(sigla, "sigla da estação").toUpperCase();
        this.nome = validarTexto(nome, "nome da estação");
        this.linha = validarLinha(linha);
    }

    // Retorna a sigla da estação
    public String getSigla() {
        return sigla;
    }

    // Retorna o nome da estação
    public String getNome() {
        return nome;
    }

    // Altera o nome da estação
    public void setNome(String nome) {
        this.nome = validarTexto(nome, "nome da estação");
    }

    // Retorna a linha da estação
    public Linha getLinha() {
        return linha;
    }

    public String getIdentificacao() {
        return nome + " (" + linha.getNomeCompleto() + ")";
    }

    @Override
    public String toString() {
        return "Estação " + getSigla() + " - " + getIdentificacao();
    }

    // Garante que o texto não seja nulo ou vazio
    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }

    // Garante que a linha não seja nulo
    private static Linha validarLinha(Linha linha) {
        if (linha == null) {
            throw new DadoInvalidoException("A estação precisa pertencer a uma linha.");
        }
        return linha;
    }
}
