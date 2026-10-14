package br.com.fiap.motiva.localizacao;

/**
 * Linha metroferroviária onde ficam as estações. É o nível mais alto da
 * localização de uma escada rolante: toda estação pertence a uma linha.
 *
 * @author Gustavo Macedo Daniel
 * @author João Victor Melo Santos
 * @author Murilo Jeronimo Ferreira Nunes
 * @author Vinicius Kozonoe Guaglini
 * @author Yan Lucas Gonçalves da Silva
 * @version 1.0
 * @since 1.0
 *
 * */

public class Linha {
    /** Número oficial da linha (exemplo: 4). */
    private int numero;

    /** Nome da linha (exemplo: "Amarela"). */
    private String nome;

    /** Concessionária que opera a linha (exemplo: "ViaQuatro"). */
    private String concessionaria;

    /** Cria uma linha com todos os dados obrigatórios.
     *
     * @param numero (precisa ser maior que zero)
     * @param nome (não pode ser nulo ou vazio)
     * @param concessionaria (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException (se algum valor for inválido)
     * @since 1.0
     * */
    public Linha(int numero, String nome, String concessionaria) {
        this.numero = validarNumero(numero);
        this.nome = validarTexto(nome, "nome da linha");
        this.concessionaria = validarTexto(concessionaria, "concessionária");
    }

    /** Monta o nome completo da linha, igual na sinalização.
     *
     * @return nome no seguinte formato "Linha 4-Amarela"
     * @since 1.0
     * */
    public String getNomeCompleto() {
        return "Linha " + numero + "-" + nome;
    }

    /** Retorna o número oficial da linha.
     *
     * @return número da linha
     * @since 1.0
     * */
    public int getNumero() {
        return numero;
    }

    /** Altera o número oficial da linha.
     *
     * @param numero novo número (deve ser maior que zero)
     * @throws IllegalArgumentException (se o número for menor ou igual a zero)
     * @since 1.0
     * */
    public void setNumero(int numero) {
        this.numero = validarNumero(numero);
    }

    /** Retorna o nome da linha.
     *
     * @return nome da linha
     * @since 1.0
     * */
    public String getNome() {
        return nome;
    }

    /** Altera o nome da linha.
     *
     * @param nome novo nome (não pode ser nulo e nem vazio)
     * @throws IllegalArgumentException (se o nome for nulo ou vazio)
     * @since 1.0
     * */
    public void setNome(String nome) {
        this.nome = validarTexto(nome, "nome da linha");
    }

    /** Retorna a concessionária que opera a linha.
     *
     * @return nome da concessionária
     * @since 1.0
     * */
    public String getConcessionaria() {
        return concessionaria;
    }

    /** Altera a concessionária que opera a linha.
     *
     * @param concessionaria nova concessionária (não pode ser nula e nem vazia)
     * @throws IllegalArgumentException (se for nulo ou vazio)
     * @since 1.0
     * */
    public void setConcessionaria(String concessionaria) {
        this.concessionaria = validarTexto(concessionaria, "concessionária");
    }

    /** Representação da linha, que usa quando imprimir o objeto.
     * @return texto no formato "Linha 4-Amarela (ViaQuatro)"
     * @since 1.0
     * */
    @Override
    public String toString() {
        return getNomeCompleto() + " (" + concessionaria + ")";
    }

    /** Garante que o número seja positivo.
     * @param numero valor a validar
     * @return o próprio número (caso ele seja válido)
     * @throws IllegalArgumentException (se o número for menor ou igual a zero)
     * @since 1.0
     * */
    private static int validarNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("O número da linha deve ser maior que zero.");
        }
        return numero;
    }

    /** Garante que o texto não seja nulo ou vazio.
     * @param valor texto a validar
     * @param campo nome do campo (usado na mensagem de erro)
     * @return o texto sem espaços nas pontas
     * @throws IllegalArgumentException (se o texto for nulo ou vazio)
     * @since 1.0
     * */
    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }
}
