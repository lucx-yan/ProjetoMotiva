package br.com.fiap.motiva.localizacao;

import br.com.fiap.motiva.excecao.DadoInvalidoException;

// Linha metroferroviária onde ficam as estações; toda estação pertence a uma linha.

public class Linha {
    private final int numero; // Número oficial da linha (exemplo: 4)

    private final String nome; // Nome da linha (exemplo: "Amarela")

    private String concessionaria; // Concessionária que opera a linha (exemplo: "ViaQuatro")

    public Linha(int numero, String nome, String concessionaria) {
        this.numero = validarNumero(numero);
        this.nome = validarTexto(nome, "nome da linha");
        this.concessionaria = validarTexto(concessionaria, "concessionária");
    }

    // Nome igual ao da sinalização (Exemplo: Linha 4-Amarela)
    public String getNomeCompleto() {
        return "Linha " + numero + "-" + nome;
    }

    // Retorna o número oficial da linha.
    public int getNumero() {
        return numero;
    }

    // Retorna o nome da linha.
    public String getNome() {
        return nome;
    }

    /** Retorna a concessionária que opera a linha.
     *
     * @return nome da concessionária
     * @since 1.0
     * */
    public String getConcessionaria() {
        return concessionaria;
    }

    // Altera a concessionária que opera a linha.

    public void setConcessionaria(String concessionaria) {
        this.concessionaria = validarTexto(concessionaria, "concessionária");
    }

    @Override
    public String toString() {
        return getNomeCompleto() + " (" + concessionaria + ")";
    }

    // Garante que o número seja positivo.
    private static int validarNumero(int numero) {
        if (numero <= 0) {
            throw new DadoInvalidoException("O número da linha deve ser maior que zero.");
        }
        return numero;
    }

    // Garante que o texto não seja nulo ou vazio.

    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }
}
